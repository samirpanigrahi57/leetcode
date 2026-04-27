param(
    [Parameter(Mandatory = $true)]
    [string]$InputTextPath,
    [Parameter(Mandatory = $true)]
    [string]$OutputDocxPath
)

$content = Get-Content -LiteralPath $InputTextPath -Raw
$word = $null
$doc = $null

try {
    $word = New-Object -ComObject Word.Application
    $word.Visible = $false
    $doc = $word.Documents.Add()
    $doc.Content.Text = $content
    $doc.SaveAs([ref]$OutputDocxPath, [ref]16)
}
finally {
    if ($doc -ne $null) {
        $doc.Close()
    }
    if ($word -ne $null) {
        $word.Quit()
    }
}
