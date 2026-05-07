from __future__ import annotations

import json
from pathlib import Path


def main() -> int:
    base_dir = Path(__file__).resolve().parent.parent / "generated"
    seen = []

    for role_file in sorted(base_dir.rglob("role_metadata.json")):
        data = json.loads(role_file.read_text(encoding="utf-8-sig"))
        if not isinstance(data, dict):
            continue
        if "company" not in data or "job_title" not in data or "job_url" not in data:
            continue
        seen.append(
            {
                "company": data["company"],
                "job_title": data["job_title"],
                "job_url": data["job_url"],
                "source": data.get("source", ""),
                "employment_type": data.get("employment_type", ""),
                "work_mode": data.get("work_mode", ""),
                "location": data.get("location", ""),
                "posting_date": data.get("posting_date", ""),
                "last_verified_date": data.get("last_verified_date", ""),
                "availability_status": data.get("availability_status", ""),
                "link_status": data.get("link_status", ""),
            }
        )

    (base_dir / "seen_job_urls.json").write_text(
        json.dumps(seen, indent=2),
        encoding="utf-8",
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
