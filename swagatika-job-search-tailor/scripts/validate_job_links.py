from __future__ import annotations

import argparse
import json
import re
import ssl
import sys
from datetime import date, datetime, timedelta
from pathlib import Path
from urllib.error import HTTPError, URLError
from urllib.request import Request, urlopen


MAX_POSTING_AGE_DAYS = 60

EXPIRED_PATTERNS = [
    "this job has expired",
    "job has expired",
    "job is no longer available",
    "the job is no longer available",
    "no longer accepting applications",
    "not accepting applications",
    "position has been filled",
    "this position is no longer available",
    "this job was removed",
    "job was removed",
    "this job posting has been removed",
    "posting has been removed",
    "404 not found",
    "page not found",
]

HARD_EXPIRED_PATTERNS = [
    "sorry this job is no longer available",
    "this job has expired",
    "the job is no longer available",
    "this position is no longer available",
]

OPEN_JOB_SIGNALS = [
    "apply now",
    "apply save",
    "apply instructions",
    "apply for this job",
    "submit your application",
    "apply on company site",
]

KNOWN_CLOSED_URL_PARTS = [
    "careers.unitedhealthgroup.com/job/eden-prairie/senior-data-analyst-remote/34088/94259827424",
    "careers.unitedhealthgroup.com/job/eden-prairie/data-analyst-remote/34088/94259827232",
    "careers.unitedhealthgroup.com/job/minnetonka/data-analyst-remote/34088/94451213408",
    "careers.unitedhealthgroup.com/job/minnetonka/senior-data-analyst-remote/34088/94451213360",
]

MONTHS = {
    "january": 1,
    "february": 2,
    "march": 3,
    "april": 4,
    "may": 5,
    "june": 6,
    "july": 7,
    "august": 8,
    "september": 9,
    "october": 10,
    "november": 11,
    "december": 12,
}


def read_text(path: Path) -> str:
    return path.read_text(encoding="utf-8-sig")


def write_json(path: Path, data: object) -> None:
    path.write_text(json.dumps(data, indent=2), encoding="utf-8")


def fetch(url: str) -> tuple[int | None, str, str]:
    request = Request(
        url,
        headers={
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/124 Safari/537.36",
            "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        },
    )
    context = ssl.create_default_context()
    try:
        with urlopen(request, timeout=25, context=context) as response:
            raw = response.read(600_000)
            charset = response.headers.get_content_charset() or "utf-8"
            return response.status, raw.decode(charset, errors="replace"), response.geturl()
    except HTTPError as exc:
        body = exc.read(120_000).decode("utf-8", errors="replace")
        return exc.code, body, exc.geturl()
    except URLError as exc:
        return None, "", str(exc.reason)


def strip_html(value: str) -> str:
    return re.sub(r"\s+", " ", re.sub(r"<[^>]+>", " ", value)).strip()


def parse_iso_date(value: object) -> date | None:
    if not isinstance(value, str) or not value.strip():
        return None
    value = value.strip()
    for fmt in ("%Y-%m-%d", "%Y-%m-%dT%H:%M:%S", "%Y-%m-%dT%H:%M:%SZ"):
        try:
            return datetime.strptime(value, fmt).date()
        except ValueError:
            pass
    return None


def parse_run_date(value: str) -> date:
    try:
        return datetime.strptime(value, "%Y-%m-%d").date()
    except ValueError as exc:
        raise argparse.ArgumentTypeError("--date must use YYYY-MM-DD format") from exc


def parse_natural_date(text: str, run_date: date) -> date | None:
    lowered = text.lower()
    match = re.search(r"posted\s+(\d+)\s+(hour|hours|day|days|week|weeks)\s+ago", lowered)
    if match:
        amount = int(match.group(1))
        unit = match.group(2)
        if unit.startswith("hour"):
            return run_date
        if unit.startswith("day"):
            return run_date - timedelta(days=amount)
        if unit.startswith("week"):
            return run_date - timedelta(days=amount * 7)

    match = re.search(r"posted\s+([a-z]+)\s+(\d{1,2}),\s*(\d{4})", lowered)
    if match and match.group(1) in MONTHS:
        return date(int(match.group(3)), MONTHS[match.group(1)], int(match.group(2)))

    match = re.search(r"date posted\s*[:\-]?\s*([0-9]{4})[-/]([0-9]{1,2})[-/]([0-9]{1,2})", lowered)
    if match:
        return date(int(match.group(1)), int(match.group(2)), int(match.group(3)))

    match = re.search(r"dateposted[\"']?\s*[:=]\s*[\"']([0-9]{4})[-/]([0-9]{1,2})[-/]([0-9]{1,2})", lowered)
    if match:
        return date(int(match.group(1)), int(match.group(2)), int(match.group(3)))

    return None


def parse_deadline(text: str) -> date | None:
    lowered = text.lower()
    match = re.search(r"application (?:deadline|window[^.]*close[^:]*):?\s*([0-9]{1,2})[-/]([0-9]{1,2})[-/]([0-9]{4})", lowered)
    if match:
        return date(int(match.group(3)), int(match.group(1)), int(match.group(2)))
    match = re.search(r"application deadline:\s*([0-9]{4})[-/]([0-9]{1,2})[-/]([0-9]{1,2})", lowered)
    if match:
        return date(int(match.group(1)), int(match.group(2)), int(match.group(3)))
    return None


def validate_record(record: dict, path: Path, run_date: date) -> dict:
    url = str(record.get("job_url") or record.get("apply_url") or "").strip()
    result = {
        "path": str(path),
        "company": record.get("company", ""),
        "job_title": record.get("job_title", ""),
        "job_url": url,
        "status": "unavailable",
        "link_status": "broken",
        "availability_status": "unavailable",
        "posting_date": record.get("posting_date", ""),
        "last_verified_date": run_date.isoformat(),
        "notes": [],
    }

    if not url:
        result["notes"].append("missing job_url")
        return result

    lowered_url = url.lower()
    if any(part in lowered_url for part in KNOWN_CLOSED_URL_PARTS):
        result["notes"].append("known closed UnitedHealth Group/Taleo requisition")
        return result

    status_code, html, final_url = fetch(url)
    result["http_status"] = status_code
    result["final_url"] = final_url
    text = strip_html(html).lower()

    if status_code is None:
        result["notes"].append(f"request failed: {final_url}")
        return result
    if status_code >= 400:
        result["notes"].append(f"http status {status_code}")
        return result
    expired_matches = [pattern for pattern in EXPIRED_PATTERNS if pattern in text]
    title_present = str(record.get("job_title", "")).strip().lower() in text
    company_present = str(record.get("company", "")).strip().lower() in text
    open_signal_present = any(pattern in text for pattern in OPEN_JOB_SIGNALS)
    hard_expired_match = any(pattern in text for pattern in HARD_EXPIRED_PATTERNS)
    if "error=true" in final_url.lower() and not title_present:
        result["notes"].append("redirected to generic error/search page")
        return result
    if expired_matches and (hard_expired_match or not (title_present and company_present and open_signal_present)):
        result["notes"].append(f"page contains expired/closed-job text: {', '.join(expired_matches[:3])}")
        return result

    deadline = parse_deadline(text)
    if deadline and deadline < run_date:
        result["notes"].append(f"application deadline passed: {deadline.isoformat()}")
        return result

    posting_date = (
        parse_iso_date(record.get("posting_date"))
        or parse_natural_date(text, run_date)
        or parse_natural_date(str(record.get("job_description", "")), run_date)
    )
    if posting_date is None:
        result["notes"].append("posting date could not be verified")
        return result

    age_days = (run_date - posting_date).days
    if age_days < 0:
        result["notes"].append(f"posting date is in the future: {posting_date.isoformat()}")
        return result
    if age_days > MAX_POSTING_AGE_DAYS:
        result["notes"].append(f"posting is older than {MAX_POSTING_AGE_DAYS} days: {posting_date.isoformat()}")
        return result

    result["status"] = "active"
    result["link_status"] = "working"
    result["availability_status"] = "active"
    result["posting_date"] = posting_date.isoformat()
    result["notes"].append("detail page loaded and posting date is within 60 days")
    return result


def iter_records(paths: list[Path]) -> list[tuple[Path, dict]]:
    records: list[tuple[Path, dict]] = []
    for path in paths:
        if path.is_dir():
            for json_path in sorted(path.rglob("*.json")):
                if json_path.name in {"candidate-profile.json", "seen_job_urls.json", "open_positions.json", "tailoring_plan.json", "fit_score.json"}:
                    continue
                try:
                    data = json.loads(read_text(json_path))
                except json.JSONDecodeError:
                    continue
                if isinstance(data, dict) and ("job_url" in data or "apply_url" in data):
                    records.append((json_path, data))
        elif path.is_file():
            data = json.loads(read_text(path))
            if isinstance(data, dict):
                records.append((path, data))
    return records


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("paths", nargs="+", type=Path)
    parser.add_argument("--report", type=Path, required=True)
    parser.add_argument("--date", type=parse_run_date, default=date.today())
    parser.add_argument("--write", action="store_true")
    args = parser.parse_args()

    args.report.parent.mkdir(parents=True, exist_ok=True)

    changes = []
    results = []
    for path, record in iter_records(args.paths):
        validation = validate_record(record, path, args.date)
        results.append(validation)
        if args.write:
            changed = False
            for key in ("posting_date", "last_verified_date", "availability_status", "link_status"):
                if validation.get(key) and record.get(key) != validation[key]:
                    record[key] = validation[key]
                    changed = True
            notes = "; ".join(validation["notes"])
            if record.get("validation_notes") != notes:
                record["validation_notes"] = notes
                changed = True
            if changed:
                write_json(path, record)
                changes.append(str(path))

    report = {
        "run_date": args.date.isoformat(),
        "checked": len(results),
        "active": sum(1 for item in results if item["status"] == "active"),
        "unavailable": sum(1 for item in results if item["status"] != "active"),
        "changed_files": changes,
        "results": results,
    }
    write_json(args.report, report)
    print(f"checked={report['checked']} active={report['active']} unavailable={report['unavailable']}")
    print(args.report)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
