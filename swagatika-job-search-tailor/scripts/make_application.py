from __future__ import annotations

import json
import re
import sys
from datetime import date, datetime
from pathlib import Path

MAX_POSTING_AGE_DAYS = 60
REQUIRED_VALIDATION_FIELDS = (
    "availability_status",
    "link_status",
    "posting_date",
    "last_verified_date",
)


def slugify(value: str) -> str:
    slug = re.sub(r"[^a-z0-9]+", "-", value.lower()).strip("-")
    return slug or "role"


def load_json(path: Path, default: object) -> object:
    if not path.exists():
        return default
    return json.loads(path.read_text(encoding="utf-8-sig"))


def parse_date(value: str, field_name: str) -> date:
    value = value.strip()
    for fmt in ("%Y-%m-%d", "%Y-%m-%dT%H:%M:%S", "%Y-%m-%dT%H:%M:%SZ"):
        try:
            return datetime.strptime(value, fmt).date()
        except ValueError:
            pass
    raise ValueError(f"{field_name} must be an ISO date like YYYY-MM-DD")


def validate_job_record(data: dict) -> list[str]:
    errors: list[str] = []
    for field in REQUIRED_VALIDATION_FIELDS:
        if not str(data.get(field, "")).strip():
            errors.append(f"missing {field}")

    availability_status = str(data.get("availability_status", "")).strip().lower()
    if availability_status != "active":
        errors.append("availability_status must be active")

    link_status = str(data.get("link_status", "")).strip().lower()
    if link_status != "working":
        errors.append("link_status must be working")

    posting_date_value = str(data.get("posting_date", "")).strip()
    if posting_date_value:
        try:
            posting_date = parse_date(posting_date_value, "posting_date")
        except ValueError as exc:
            errors.append(str(exc))
        else:
            age_days = (date.today() - posting_date).days
            if age_days < 0:
                errors.append("posting_date cannot be in the future")
            if age_days > MAX_POSTING_AGE_DAYS:
                errors.append(f"posting_date is older than {MAX_POSTING_AGE_DAYS} days")

    last_verified_value = str(data.get("last_verified_date", "")).strip()
    if last_verified_value:
        try:
            parse_date(last_verified_value, "last_verified_date")
        except ValueError as exc:
            errors.append(str(exc))

    return errors


def main() -> int:
    if len(sys.argv) != 3:
        print("Usage: python make_application.py <input.json> <output_dir>")
        return 1

    input_path = Path(sys.argv[1])
    output_dir = Path(sys.argv[2])
    data = json.loads(input_path.read_text(encoding="utf-8-sig"))
    validation_errors = validate_job_record(data)
    if validation_errors:
        print("Refusing to create application for unverified or stale job:")
        for error in validation_errors:
            print(f"- {error}")
        return 2

    company_dir = output_dir / data["company"]
    job_dir = company_dir / slugify(data["job_title"])
    company_dir.mkdir(parents=True, exist_ok=True)
    job_dir.mkdir(parents=True, exist_ok=True)

    (job_dir / "job_description.md").write_text(data["job_description"], encoding="utf-8")
    (job_dir / "job_url.txt").write_text(data["job_url"].strip() + "\n", encoding="utf-8")
    role_metadata = {
        "company": data["company"],
        "job_title": data["job_title"],
        "job_url": data["job_url"],
        "source": data.get("source", "Unknown"),
        "employment_type": data.get("employment_type", "Unknown"),
        "work_mode": data.get("work_mode", "Unknown"),
        "location": data.get("location", "Unknown"),
        "fit_score": data["fit_score"],
        "posting_date": data["posting_date"],
        "last_verified_date": data["last_verified_date"],
        "availability_status": data["availability_status"],
        "link_status": data["link_status"],
        "apply_url": data.get("apply_url", data["job_url"]),
        "validation_notes": data.get("validation_notes", ""),
    }
    (job_dir / "role_metadata.json").write_text(
        json.dumps(role_metadata, indent=2),
        encoding="utf-8",
    )
    (job_dir / "fit_score.json").write_text(
        json.dumps(
            {
                "job_title": data["job_title"],
                "score": data["fit_score"],
                "source": data.get("source", "Unknown"),
                "employment_type": data.get("employment_type", "Unknown"),
                "work_mode": data.get("work_mode", "Unknown"),
                "location": data.get("location", "Unknown"),
                "posting_date": data["posting_date"],
                "last_verified_date": data["last_verified_date"],
                "availability_status": data["availability_status"],
                "link_status": data["link_status"],
                "strengths": data.get("strengths", []),
                "gaps": data.get("gaps", []),
            },
            indent=2,
        ),
        encoding="utf-8",
    )
    (job_dir / "notes.md").write_text(data.get("notes", ""), encoding="utf-8")
    (job_dir / "tailoring_plan.json").write_text(
        json.dumps({"replacements": data.get("replacements", [])}, indent=2),
        encoding="utf-8",
    )

    positions_path = company_dir / "open_positions.json"
    positions = load_json(positions_path, [])
    positions = [entry for entry in positions if entry.get("job_url") != data["job_url"]]
    positions.append(
        {
            "job_title": data["job_title"],
            "job_url": data["job_url"],
            "source": data.get("source", "Unknown"),
            "employment_type": data.get("employment_type", "Unknown"),
            "work_mode": data.get("work_mode", "Unknown"),
            "location": data.get("location", "Unknown"),
            "fit_score": data["fit_score"],
            "posting_date": data["posting_date"],
            "last_verified_date": data["last_verified_date"],
            "availability_status": data["availability_status"],
            "link_status": data["link_status"],
            "folder": job_dir.name,
        }
    )
    positions.sort(key=lambda item: (-item["fit_score"], item["job_title"]))
    positions_path.write_text(json.dumps(positions, indent=2), encoding="utf-8")

    seen_path = output_dir / "seen_job_urls.json"
    seen = load_json(seen_path, [])
    seen = [entry for entry in seen if entry.get("job_url") != data["job_url"]]
    seen.append(
        {
            "company": data["company"],
            "job_title": data["job_title"],
            "job_url": data["job_url"],
            "source": data.get("source", "Unknown"),
            "employment_type": data.get("employment_type", "Unknown"),
            "work_mode": data.get("work_mode", "Unknown"),
            "location": data.get("location", "Unknown"),
            "posting_date": data["posting_date"],
            "last_verified_date": data["last_verified_date"],
            "availability_status": data["availability_status"],
            "link_status": data["link_status"],
        }
    )
    seen.sort(key=lambda item: (item["company"], item["job_title"], item["job_url"]))
    seen_path.write_text(json.dumps(seen, indent=2), encoding="utf-8")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
