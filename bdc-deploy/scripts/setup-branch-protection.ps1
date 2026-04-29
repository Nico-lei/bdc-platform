<#
.SYNOPSIS
  bdc-platform · 一键启用 GitHub main 分支保护规则（P0.1.3 配套）

.DESCRIPTION
  启用以下保护：
    - 禁止直推 main（包括 admin 强制）
    - PR 必须 >=1 reviewer 通过
    - CODEOWNERS 强制评审
    - 必检 status check：CI 汇总（必检入口）
    - 解决冲突后必须重新评审（require_code_owner_reviews=true,
      dismiss_stale_reviews=true）
    - 推送时必须 up-to-date with main

.PARAMETER Owner
  GitHub Owner（默认 Nico-lei）

.PARAMETER Repo
  仓库名（默认 bdc-platform）

.PARAMETER Branch
  保护分支（默认 main）

.PARAMETER Token
  GitHub PAT（具备 repo + admin:repo_hook 权限）。
  也可设置环境变量 $env:GITHUB_TOKEN（推荐）。

.PARAMETER RequiredReviewers
  通过 PR 需要的最少 reviewer 数（默认 1）

.EXAMPLE
  $env:GITHUB_TOKEN = "ghp_xxx"
  ./setup-branch-protection.ps1

.EXAMPLE
  ./setup-branch-protection.ps1 -Token ghp_xxx -RequiredReviewers 1

.NOTES
  - 优先使用 gh CLI（如已安装且已登录）；回退到 curl + REST API。
  - 必检 status check 名称必须与 .github/workflows/ci.yml 中
    "ci-summary" job 的 name 字段保持一致："CI 汇总（必检入口）"。
#>

[CmdletBinding()]
param(
  [string]$Owner             = "Nico-lei",
  [string]$Repo              = "bdc-platform",
  [string]$Branch            = "main",
  [string]$Token             = $env:GITHUB_TOKEN,
  [int]   $RequiredReviewers = 1,
  [string]$RequiredCheck     = "CI 汇总（必检入口）"
)

$ErrorActionPreference = "Stop"

function Write-Section($t) { Write-Host "`n=== $t ===" -ForegroundColor Cyan }

# ---------- 1) 参数校验 ----------
Write-Section "参数校验"
Write-Host "  Owner             : $Owner"
Write-Host "  Repo              : $Repo"
Write-Host "  Branch            : $Branch"
Write-Host "  RequiredReviewers : $RequiredReviewers"
Write-Host "  RequiredCheck     : $RequiredCheck"

if (-not $Token) {
  Write-Host "未提供 -Token，且环境变量 GITHUB_TOKEN 为空。" -ForegroundColor Yellow
  $secure = Read-Host "请输入 GitHub PAT（输入隐藏）" -AsSecureString
  $Token  = [Runtime.InteropServices.Marshal]::PtrToStringAuto(
              [Runtime.InteropServices.Marshal]::SecureStringToBSTR($secure))
}
if (-not $Token) { throw "缺少 GITHUB_TOKEN，无法继续。" }

# ---------- 2) 构造保护规则 payload（GitHub Branch Protection API v3） ----------
Write-Section "构造保护规则 payload"
# 文档：https://docs.github.com/en/rest/branches/branch-protection
$payload = @{
  required_status_checks = @{
    strict   = $true                       # 推送前必须 up-to-date with main
    contexts = @($RequiredCheck)
  }
  enforce_admins = $true                   # 管理员也不可绕过
  required_pull_request_reviews = @{
    dismiss_stale_reviews           = $true
    require_code_owner_reviews      = $true
    required_approving_review_count = $RequiredReviewers
    require_last_push_approval      = $true
  }
  restrictions     = $null                 # 不限制可推送账号（push 已被禁止）
  required_linear_history             = $true
  allow_force_pushes                  = $false
  allow_deletions                     = $false
  block_creations                     = $false
  required_conversation_resolution    = $true
  lock_branch                         = $false
  allow_fork_syncing                  = $true
}
$body = $payload | ConvertTo-Json -Depth 10
Write-Host $body

# ---------- 3) 调用 API ----------
Write-Section "启用分支保护"

$ghAvailable = $false
try {
  $null = Get-Command gh -ErrorAction Stop
  $ghAvailable = $true
} catch { }

if ($ghAvailable) {
  Write-Host "[模式] gh CLI" -ForegroundColor Green
  $tmp = New-TemporaryFile
  Set-Content -LiteralPath $tmp -Value $body -Encoding UTF8 -NoNewline
  try {
    & gh api -X PUT `
      "repos/$Owner/$Repo/branches/$Branch/protection" `
      -H "Accept: application/vnd.github+json" `
      -H "X-GitHub-Api-Version: 2022-11-28" `
      --input $tmp
  } finally {
    Remove-Item -LiteralPath $tmp -Force -ErrorAction SilentlyContinue
  }
} else {
  Write-Host "[模式] curl + REST API（gh 未安装）" -ForegroundColor Green
  $url = "https://api.github.com/repos/$Owner/$Repo/branches/$Branch/protection"
  $headers = @{
    "Authorization"        = "Bearer $Token"
    "Accept"               = "application/vnd.github+json"
    "X-GitHub-Api-Version" = "2022-11-28"
    "User-Agent"           = "bdc-platform-setup-branch-protection"
  }
  $resp = Invoke-RestMethod -Method Put -Uri $url -Headers $headers -Body $body -ContentType "application/json"
  $resp | ConvertTo-Json -Depth 10
}

# ---------- 4) 复核 ----------
Write-Section "复核保护规则"
$readUrl = "https://api.github.com/repos/$Owner/$Repo/branches/$Branch/protection"
$h2 = @{
  "Authorization"        = "Bearer $Token"
  "Accept"               = "application/vnd.github+json"
  "X-GitHub-Api-Version" = "2022-11-28"
  "User-Agent"           = "bdc-platform-setup-branch-protection"
}
$now = Invoke-RestMethod -Uri $readUrl -Headers $h2

$ok = $true
function Check($cond, $msg) {
  if ($cond) { Write-Host "  [OK]   $msg" -ForegroundColor Green }
  else       { Write-Host "  [FAIL] $msg" -ForegroundColor Red; $script:ok = $false }
}

Check ($now.required_pull_request_reviews.required_approving_review_count -ge $RequiredReviewers) `
      "Reviewer 数 = $($now.required_pull_request_reviews.required_approving_review_count) (>= $RequiredReviewers)"
Check ($now.required_pull_request_reviews.require_code_owner_reviews -eq $true) `
      "CODEOWNERS 评审必检"
Check ($now.required_status_checks.contexts -contains $RequiredCheck) `
      "必检 status check 包含 '$RequiredCheck'"
Check ($now.enforce_admins.enabled -eq $true) `
      "管理员同样受保护（enforce_admins=true）"
Check ($now.allow_force_pushes.enabled -eq $false) `
      "禁止 force push"
Check ($now.allow_deletions.enabled -eq $false) `
      "禁止删除分支"

if ($ok) {
  Write-Host "`n分支保护启用成功：$Owner/$Repo @ $Branch" -ForegroundColor Green
  Write-Host "  - 直推 main 将被拒绝"
  Write-Host "  - PR 必须 >= $RequiredReviewers 名 reviewer 通过"
  Write-Host "  - CI '$RequiredCheck' 必检"
  exit 0
} else {
  Write-Host "`n分支保护配置存在差异，请检查上述失败项。" -ForegroundColor Red
  exit 1
}
