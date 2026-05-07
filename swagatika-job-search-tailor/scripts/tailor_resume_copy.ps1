param(
    [Parameter(Mandatory = $true)]
    [string]$BaseResumePath,
    [Parameter(Mandatory = $true)]
    [string]$OutputResumePath,
    [Parameter(Mandatory = $true)]
    [string]$PlanJsonPath,
    [Parameter(Mandatory = $false)]
    [string]$PdfOutputPath
)

$plan = Get-Content -LiteralPath $PlanJsonPath -Raw | ConvertFrom-Json
New-Item -ItemType Directory -Force -Path (Split-Path -Parent $OutputResumePath) | Out-Null
Copy-Item -LiteralPath $BaseResumePath -Destination $OutputResumePath -Force
if ($PdfOutputPath) {
    New-Item -ItemType Directory -Force -Path (Split-Path -Parent $PdfOutputPath) | Out-Null
}

$word = $null
$doc = $null

try {
    $word = New-Object -ComObject Word.Application
    $word.Visible = $false
    $doc = $word.Documents.Open($OutputResumePath)

    foreach ($replacement in $plan.replacements) {
        $find = $doc.Content.Find
        $find.ClearFormatting()
        $find.Replacement.ClearFormatting()
        [void]$find.Execute(
            $replacement.find,     # FindText
            $false,                # MatchCase
            $false,                # MatchWholeWord
            $false,                # MatchWildcards
            $false,                # MatchSoundsLike
            $false,                # MatchAllWordForms
            $true,                 # Forward
            1,                     # Wrap (wdFindContinue)
            $false,                # Format
            $replacement.replace,  # ReplaceWith
            2                      # Replace (wdReplaceAll)
        )
    }

    $doc.Save()

    if ($PdfOutputPath) {
        $wdExportFormatPDF = 17
        $doc.ExportAsFixedFormat($PdfOutputPath, $wdExportFormatPDF)
    }
}
finally {
    if ($doc -ne $null) {
        $doc.Close()
    }
    if ($word -ne $null) {
        $word.Quit()
    }
}
