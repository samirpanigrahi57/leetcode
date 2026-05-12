param(
    [Parameter(Mandatory = $true)]
    [string]$GeneratedRoot,
    [Parameter(Mandatory = $true)]
    [string]$AcceptedJobsJsonPath
)

$ErrorActionPreference = "Stop"

function Get-FieldValue {
    param(
        [Parameter(Mandatory = $true)]
        [object]$Object,
        [Parameter(Mandatory = $true)]
        [string[]]$Names
    )

    foreach ($name in $Names) {
        if ($Object.PSObject.Properties.Name -contains $name) {
            $value = $Object.$name
            if ($null -ne $value -and "$value".Trim().Length -gt 0) {
                return "$value"
            }
        }
    }
    return ""
}

function ConvertTo-Slug {
    param([Parameter(Mandatory = $true)][string]$Value)
    $slug = $Value.ToLowerInvariant() -replace "[^a-z0-9]+", "-"
    $slug = $slug.Trim("-")
    if ($slug.Length -eq 0) { return "role" }
    return $slug
}

function Get-ExpectedPdfName {
    param([Parameter(Mandatory = $true)][object]$Job)
    $pdfName = Get-FieldValue -Object $Job -Names @("pdf_name", "pdf", "resume_pdf")
    if ($pdfName) { return $pdfName }

    $company = Get-FieldValue -Object $Job -Names @("company")
    return "$(ConvertTo-Slug $company)_resume.pdf".Replace("-", "_")
}

$root = Resolve-Path -LiteralPath $GeneratedRoot
$accepted = Get-Content -LiteralPath $AcceptedJobsJsonPath -Raw | ConvertFrom-Json
if ($accepted.PSObject.Properties.Name -contains "accepted_jobs") {
    $accepted = $accepted.accepted_jobs
}
if ($null -eq $accepted) {
    throw "Accepted jobs file is empty or invalid: $AcceptedJobsJsonPath"
}

$errors = New-Object System.Collections.Generic.List[string]

foreach ($job in @($accepted)) {
    $folder = Get-FieldValue -Object $job -Names @("resume_folder", "folder")
    if (-not $folder) {
        $company = Get-FieldValue -Object $job -Names @("company")
        $slug = Get-FieldValue -Object $job -Names @("job_slug", "slug")
        if (-not $slug) {
            $slug = ConvertTo-Slug (Get-FieldValue -Object $job -Names @("job_title", "title"))
        }
        $folder = "generated/$company/$slug"
    }

    $relativeFolder = $folder -replace "^generated[\\/]", ""
    $roleDir = Join-Path $root $relativeFolder
    if (-not (Test-Path -LiteralPath $roleDir -PathType Container)) {
        $errors.Add("missing role folder: $roleDir")
        continue
    }

    $expectedDocx = Join-Path $roleDir "Swagatika_Nayak_Resume.docx"
    $expectedPdf = Join-Path $roleDir (Get-ExpectedPdfName -Job $job)
    if (-not (Test-Path -LiteralPath $expectedDocx -PathType Leaf)) {
        $errors.Add("missing docx: $expectedDocx")
    }
    if (-not (Test-Path -LiteralPath $expectedPdf -PathType Leaf)) {
        $errors.Add("missing pdf: $expectedPdf")
    }

    $files = @(Get-ChildItem -LiteralPath $roleDir -File)
    $allowed = @("Swagatika_Nayak_Resume.docx", (Split-Path -Leaf $expectedPdf))
    foreach ($file in $files) {
        if ($allowed -notcontains $file.Name) {
            $errors.Add("unexpected file in role folder: $($file.FullName)")
        }
    }
}

if ($errors.Count -gt 0) {
    Write-Output "Generated package validation failed:"
    foreach ($error in $errors) {
        Write-Output "- $error"
    }
    exit 2
}

Write-Output "Generated package validation passed for $(@($accepted).Count) role(s)."
