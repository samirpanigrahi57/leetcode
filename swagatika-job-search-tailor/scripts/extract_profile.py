from __future__ import annotations

import json
import re
import sys
import zipfile
from pathlib import Path
from xml.etree import ElementTree as ET


def docx_text(path: Path) -> str:
    with zipfile.ZipFile(path) as zf:
        xml_bytes = zf.read("word/document.xml")
    root = ET.fromstring(xml_bytes)
    texts = [node.text for node in root.iter() if node.text]
    return re.sub(r"\s+", " ", " ".join(texts)).strip()


def build_profile(text: str) -> dict:
    return {
        "name": "Swagatika Nayak",
        "target_titles": [
            "Healthcare Data Analyst",
            "ETL Analyst",
            "Data Quality Analyst",
            "Payer Data Analyst",
            "Claims Data Analyst",
            "Data Warehouse Analyst",
        ],
        "priority_skills": [
            "SQL",
            "ETL validation",
            "source-to-target testing",
            "data reconciliation",
            "data quality",
            "healthcare claims",
            "member eligibility",
            "provider data",
            "Informatica",
            "Azure Data Factory",
            "Power BI",
            "Tableau",
            "Snowflake",
            "Databricks",
            "Python",
        ],
        "constraints": {
            "work_authorization": "Authorized to work in the U.S.; no sponsorship required",
            "location": "Austin, TX",
        },
        "resume_excerpt": text[:4000],
    }


def main() -> int:
    if len(sys.argv) != 3:
        print("Usage: python extract_profile.py <resume.docx> <output.json>")
        return 1

    resume_path = Path(sys.argv[1])
    output_path = Path(sys.argv[2])
    text = docx_text(resume_path)
    profile = build_profile(text)
    output_path.write_text(json.dumps(profile, indent=2), encoding="utf-8")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
