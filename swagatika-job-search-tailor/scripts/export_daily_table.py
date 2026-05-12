from __future__ import annotations

import argparse
import json
import re
from datetime import datetime
from pathlib import Path
from typing import Any

MAX_POSTING_AGE_DAYS = 30
ACTIVE_STATUS = "active"
WORKING_STATUS = "working"

BUCKETS = [
    ("Apply First", 82, 10_000),
    ("Apply If Time", 74, 81),
    ("Skip / Stretch", 65, 73),
    ("Below 65", -10_000, 64),
]


def parse_iso_date(value: object) -> datetime | None:
    if not isinstance(value, str) or not value.strip():
        return None
    value = value.strip()
    for fmt in ("%Y-%m-%d", "%Y-%m-%dT%H:%M:%S", "%Y-%m-%dT%H:%M:%SZ"):
        try:
            return datetime.strptime(value, fmt)
        except ValueError:
            pass
    return None


def slugify(value: str) -> str:
    slug = re.sub(r"[^a-z0-9]+", "-", value.lower()).strip("-")
    return slug or "role"


def bucket_for(score: int) -> str:
    for label, low, high in BUCKETS:
        if low <= score <= high:
            return label
    return "Below 65"


def load_json(path: Path, default: Any) -> Any:
    if not path.exists():
        return default
    return json.loads(path.read_text(encoding="utf-8-sig"))


def normalize_rows(data: Any) -> list[dict[str, Any]]:
    if isinstance(data, dict) and isinstance(data.get("accepted_jobs"), list):
        data = data["accepted_jobs"]
    if not isinstance(data, list):
        raise ValueError("accepted jobs input must be a list or an object with accepted_jobs")
    rows: list[dict[str, Any]] = []
    for item in data:
        if not isinstance(item, dict):
            continue
        rows.append(dict(item))
    return rows


def validate_row(row: dict[str, Any], export_date: datetime, seen_urls: set[str]) -> list[str]:
    errors: list[str] = []
    required = [
        "company",
        "job_title",
        "job_url",
        "source",
        "employment_type",
        "work_mode",
        "location",
        "posting_date",
        "last_verified_date",
        "availability_status",
        "link_status",
        "fit_score",
    ]
    for key in required:
        if str(row.get(key, "")).strip() == "":
            errors.append(f"missing {key}")

    job_url = str(row.get("job_url", "")).strip()
    if job_url and job_url not in seen_urls:
        errors.append("job_url is not present in seen_job_urls.json")

    if str(row.get("availability_status", "")).strip().lower() != ACTIVE_STATUS:
        errors.append("availability_status must be active")
    if str(row.get("link_status", "")).strip().lower() != WORKING_STATUS:
        errors.append("link_status must be working")

    posting_date = parse_iso_date(row.get("posting_date"))
    if posting_date is None:
        errors.append("posting_date must be ISO date")
    else:
        age_days = (export_date.date() - posting_date.date()).days
        if age_days < 0:
            errors.append("posting_date cannot be in the future")
        if age_days > MAX_POSTING_AGE_DAYS:
            errors.append(f"posting_date is older than {MAX_POSTING_AGE_DAYS} days")

    verified_date = parse_iso_date(row.get("last_verified_date"))
    if verified_date is None:
        errors.append("last_verified_date must be ISO date")
    elif verified_date.date() != export_date.date():
        errors.append("last_verified_date must match the export date for current-run exports")

    try:
        int(row.get("fit_score", ""))
    except (TypeError, ValueError):
        errors.append("fit_score must be an integer")

    return errors


def escape_cell(value: object) -> str:
    return str(value).replace("|", "\\|")


def row_value(row: dict[str, Any], *keys: str, default: str = "") -> str:
    for key in keys:
        value = row.get(key)
        if value is not None and str(value).strip():
            return str(value)
    return default


def resume_folder(row: dict[str, Any]) -> str:
    folder = row_value(row, "resume_folder", "folder")
    if folder:
        return folder.replace("\\", "/")
    company = row_value(row, "company")
    title = row_value(row, "job_slug") or slugify(row_value(row, "job_title"))
    return f"generated/{company}/{title}"


def render_table(rows: list[dict[str, Any]], export_date: str, run_started_at: str, notes: list[str]) -> str:
    lines = [
        f"# Job Search Table - {export_date}",
        "",
        f"Run started: {run_started_at}" if run_started_at else f"Run date: {export_date}",
        "",
        "Validation window: postings dated within 30 days of this export. "
        "All accepted rows were checked against the exact final detail/apply URL during this run "
        "and had an active apply path at verification time.",
    ]

    grouped: dict[str, list[dict[str, Any]]] = {label: [] for label, _, _ in BUCKETS}
    for row in rows:
        grouped[bucket_for(int(row["fit_score"]))].append(row)

    for label, _, _ in BUCKETS[:3]:
        lines.extend(
            [
                "",
                f"## {label}",
                "",
                "| Company | Role | Source | Type | Work mode | Location | Posted | Verified | Score | Job detail | Apply instruction | Resume folder |",
                "|---|---|---|---|---|---|---|---|---:|---|---|---|",
            ]
        )
        bucket_rows = sorted(
            grouped[label],
            key=lambda item: (-int(item["fit_score"]), row_value(item, "company"), row_value(item, "job_title")),
        )
        if not bucket_rows:
            lines.append("| None | None | None | None | None | None | N/A | N/A | N/A | None | None | None |")
        for row in bucket_rows:
            lines.append(
                "| "
                + " | ".join(
                    [
                        escape_cell(row_value(row, "company")),
                        escape_cell(row_value(row, "job_title")),
                        escape_cell(row_value(row, "source", default="Unknown")),
                        escape_cell(row_value(row, "employment_type", default="Unknown")),
                        escape_cell(row_value(row, "work_mode", default="Unknown")),
                        escape_cell(row_value(row, "location", default="Unknown")),
                        escape_cell(row_value(row, "posting_date")),
                        escape_cell(row_value(row, "last_verified_date")),
                        str(int(row["fit_score"])),
                        f"[Detail]({row_value(row, 'job_url')})",
                        escape_cell(row_value(row, "apply_instruction", default="Use the job detail page apply button.")),
                        f"`{escape_cell(resume_folder(row))}`",
                    ]
                )
                + " |"
            )

    if notes:
        lines.extend(["", "## Run Notes", ""])
        lines.extend(f"- {note}" for note in notes)

    return "\n".join(lines).rstrip() + "\n"


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("accepted_jobs_json", type=Path)
    parser.add_argument("output_dir", type=Path)
    parser.add_argument("date")
    parser.add_argument("--seen", type=Path, required=True)
    parser.add_argument("--run-started-at", default="")
    parser.add_argument("--note", action="append", default=[])
    args = parser.parse_args()

    export_date = datetime.strptime(args.date, "%Y-%m-%d")
    accepted = normalize_rows(load_json(args.accepted_jobs_json, []))
    seen_rows = load_json(args.seen, [])
    if not isinstance(seen_rows, list):
        raise ValueError("seen registry must be a JSON list")
    seen_urls = {str(item.get("job_url", "")).strip() for item in seen_rows if isinstance(item, dict)}

    validation_errors: list[str] = []
    deduped: dict[str, dict[str, Any]] = {}
    for index, row in enumerate(accepted, start=1):
        errors = validate_row(row, export_date, seen_urls)
        if errors:
            label = row_value(row, "job_url", "job_title", default=f"row {index}")
            validation_errors.extend(f"{label}: {error}" for error in errors)
            continue
        deduped[row_value(row, "job_url")] = row

    if validation_errors:
        print("Refusing to export invalid accepted jobs:")
        for error in validation_errors:
            print(f"- {error}")
        return 2

    args.output_dir.mkdir(parents=True, exist_ok=True)
    out_path = args.output_dir / f"job-search-table-{args.date}.md"
    out_path.write_text(
        render_table(list(deduped.values()), args.date, args.run_started_at, args.note),
        encoding="utf-8",
    )
    print(out_path)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
