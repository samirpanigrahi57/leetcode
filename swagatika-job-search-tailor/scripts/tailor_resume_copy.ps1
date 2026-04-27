param(
    [Parameter(Mandatory = $true)]
    [string]$BaseResumePath,
    [Parameter(Mandatory = $true)]
    [string]$OutputResumePath,
    [Parameter(Mandatory = $true)]
    [string]$PlanJsonPath
)

$plan = Get-Content -LiteralPath $PlanJsonPath -Raw | ConvertFrom-Json
New-Item -ItemType Directory -Force -Path (Split-Path -Parent $OutputResumePath) | Out-Null
Copy-Item -LiteralPath $BaseResumePath -Destination $OutputResumePath -Force

$word = $null
$doc = $null

try {
    $word = New-Object -ComObject Word.Application
    $word.Visible = $false
    $doc = $word.Documents.Open($OutputResumePath)

    foreach ($replacement in $plan.replacements) {
        foreach ($paragraph in $doc.Paragraphs) {
            $text = $paragraph.Range.Text.Trim("`r", "`n", [char]7)
            if ($text -eq $replacement.find) {
                $paragraph.Range.Text = $replacement.replace + "`r"
                break
            }
        }
    }

    $doc.Save()
}
finally {
    if ($doc -ne $null) {
        $doc.Close()
    }
    if ($word -ne $null) {
        $word.Quit()
    }
}
