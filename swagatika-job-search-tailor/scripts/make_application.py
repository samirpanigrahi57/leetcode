from __future__ import annotations

import json
import re
import sys
from pathlib import Path


def slugify(value: str) -> str:
    slug = re.sub(r"[^a-z0-9]+", "-", value.lower()).strip("-")
    return slug or "role"


def load_json(path: Path, default: object) -> object:
    if not path.exists():
        return default
    return json.loads(path.read_text(encoding="utf-8-sig"))


def main() -> int:
    if len(sys.argv) != 3:
        print("Usage: python make_application.py <input.json> <output_dir>")
        return 1

    input_path = Path(sys.argv[1])
    output_dir = Path(sys.argv[2])
    data = json.loads(input_path.read_text(encoding="utf-8-sig"))
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
        }
    )
    seen.sort(key=lambda item: (item["company"], item["job_title"], item["job_url"]))
    seen_path.write_text(json.dumps(seen, indent=2), encoding="utf-8")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
