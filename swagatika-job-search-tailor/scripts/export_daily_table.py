from __future__ import annotations

import json
import re
import sys
from pathlib import Path


BUCKETS = [
    ("Apply First", 82, 10_000),
    ("Apply If Time", 74, 81),
    ("Skip / Stretch", 65, 73),
    ("Below 65", -10_000, 64),
]


def bucket_for(score: int) -> str:
    for label, low, high in BUCKETS:
        if low <= score <= high:
            return label
    return "Below 65"


def load_jobs(generated_dir: Path) -> list[dict]:
    jobs: list[dict] = []
    for path in sorted(generated_dir.glob("*.json")):
        if path.name in {"candidate-profile.json", "seen_job_urls.json"}:
            continue
        data = json.loads(path.read_text(encoding="utf-8-sig"))
        if not isinstance(data, dict):
            continue
        if "company" not in data or "job_title" not in data or "job_url" not in data:
            continue
        jobs.append(data)
    return jobs


def parse_existing_export(path: Path) -> list[dict]:
    if not path.exists():
        return []

    rows: list[dict] = []
    for line in path.read_text(encoding="utf-8-sig").splitlines():
        if not line.startswith("| "):
            continue
        if line.startswith("|---|") or "| Company | Role |" in line or "| None | None |" in line:
            continue

        parts = [part.strip() for part in line.strip().strip("|").split("|")]
        if len(parts) != 9:
            continue

        company, title, source, employment_type, work_mode, location, score_text, apply_cell, folder_cell = parts
        link_match = re.search(r"\[Apply\]\((.+)\)", apply_cell)
        folder_match = re.search(r"`(.+)`", folder_cell)
        if not link_match:
            continue

        try:
            fit_score = int(score_text)
        except ValueError:
            continue

        rows.append(
            {
                "company": company,
                "job_title": title,
                "source": source,
                "employment_type": employment_type,
                "work_mode": work_mode,
                "location": location,
                "fit_score": fit_score,
                "job_url": link_match.group(1),
                "resume_folder": folder_match.group(1) if folder_match else "",
            }
        )
    return rows


def slugify(value: str) -> str:
    slug = re.sub(r"[^a-z0-9]+", "-", value.lower()).strip("-")
    return slug or "role"


def merge_rows(existing_rows: list[dict], new_rows: list[dict]) -> list[dict]:
    merged: dict[str, dict] = {}
    for row in existing_rows + new_rows:
        job_url = row.get("job_url", "").strip()
        if not job_url:
            continue
        normalized = dict(row)
        normalized["resume_folder"] = row.get("resume_folder") or (
            f"jobs_application/{row.get('company', '')}/{slugify(row.get('job_title', ''))}"
        )
        merged[job_url] = normalized
    return list(merged.values())


def render_table(rows: list[dict], applications_root: Path | None = None) -> str:
    def escape_cell(value: object) -> str:
        return str(value).replace("|", "\\|")

    def folder_cell(row: dict) -> str:
        folder = row.get("resume_folder") or (
            f"jobs_application/{row.get('company', '')}/{slugify(row.get('job_title', ''))}"
        )
        if applications_root is None:
            return f"`{escape_cell(folder)}`"

        company = row.get("company", "")
        title = row.get("job_title", "")
        absolute_folder = applications_root / company / slugify(title)
        link_target = absolute_folder.resolve().as_posix()
        return f"[{escape_cell(folder)}](<{link_target}>)"

    output: list[str] = []
    grouped: dict[str, list[dict]] = {label: [] for label, _, _ in BUCKETS}
    for row in rows:
        grouped[bucket_for(int(row.get("fit_score", 0)))].append(row)

    for label, _, _ in BUCKETS:
        output.append(f"**{label}**")
        output.append("")
        output.append("| Company | Role | Source | Type | Work Mode | Location | Score | Apply Link | Resume Folder |")
        output.append("|---|---|---|---|---|---|---:|---|---|")
        bucket_rows = sorted(grouped[label], key=lambda r: (-int(r.get("fit_score", 0)), r.get("company", ""), r.get("job_title", "")))
        if not bucket_rows:
            output.append("| None | None | None | None | None | None | N/A | None | None |")
        else:
            for row in bucket_rows:
                company = row.get("company", "")
                title = row.get("job_title", "")
                source = row.get("source", "Unknown")
                employment_type = row.get("employment_type", "Unknown")
                work_mode = row.get("work_mode", "Unknown")
                location = row.get("location", "Unknown")
                score = int(row.get("fit_score", 0))
                link = row.get("job_url", "")
                output.append(
                    f"| {escape_cell(company)} | {escape_cell(title)} | {escape_cell(source)} | {escape_cell(employment_type)} | {escape_cell(work_mode)} | {escape_cell(location)} | {score} | [Apply]({link}) | {folder_cell(row)} |"
                )
        output.append("")
    return "\n".join(output).rstrip() + "\n"


def main() -> int:
    if len(sys.argv) not in {4, 5}:
        print("Usage: python export_daily_table.py <generated_dir> <output_dir> <date> [applications_root]")
        return 1

    generated_dir = Path(sys.argv[1])
    output_dir = Path(sys.argv[2])
    date_str = sys.argv[3]
    applications_root = Path(sys.argv[4]) if len(sys.argv) == 5 else None
    output_dir.mkdir(parents=True, exist_ok=True)
    out_path = output_dir / f"job-search-table-{date_str}.md"
    jobs = load_jobs(generated_dir)
    existing_rows = parse_existing_export(out_path)
    merged_rows = merge_rows(existing_rows, jobs)
    content = render_table(merged_rows, applications_root)
    out_path.write_text(content, encoding="utf-8")
    print(out_path)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
