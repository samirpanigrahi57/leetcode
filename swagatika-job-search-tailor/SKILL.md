---
name: swagatika-job-search-tailor
description: Use when searching current jobs for Swagatika Nayak across LinkedIn, Dice, Indeed, company career pages, and structured boards, deduplicating by URL, and generating company-specific application folders with truthful tailored resumes and notes.
---

# Swagatika Job Search Tailor

## Quick Command

Start from:

```text
C:\Users\samir\Documents\Learning\Java\Projects\leetcode
```

Then start Codex in that folder and run:

Use this prompt when you want to run the skill:

```text
Use the swagatika-job-search-tailor skill. Search LinkedIn, Dice, Indeed, and official company job boards for Swagatika's target roles. Skip anything already present in jobs_application/seen_job_urls.json. For each new role, create or update the company folder under jobs_application, keep separate subfolders for multiple positions, copy and tailor the base resume truthfully, show me a ranked table with score, work mode, location, apply link, and resume folder, and export the table to a daily file ending with today's date.
```

Use this skill when the task is to find current jobs for Swagatika Nayak and prepare manual-application packages.

## Guardrails

- Keep all tailoring truthful.
- Never change employer names or employment dates.
- Do not invent responsibilities, tools, metrics, or domains.
- You may reorder, rephrase, or foreground existing experience to better match a job description.
- Prefer summary, headline, skills ordering, and bullet emphasis over title rewriting.
- If a posting asks for a neighboring technology, map it truthfully to adjacent experience instead of claiming unsupported direct usage.
- Record unsupported technologies as gaps in `notes.md` or `fit_score.json`; do not insert them as factual resume experience.

## Candidate Focus

Read [references/target-roles.md](references/target-roles.md) for the role families and search domains.

Swagatika's strongest fit is:
- Healthcare Data Analyst
- ETL Analyst
- Data Quality Analyst
- Payer / Claims Data Analyst
- Data Warehouse QA / Validation Analyst

## Workflow

1. Extract the latest source profile from the resume and LinkedIn export.
2. Search current jobs using the web tool across LinkedIn, Dice, Indeed, ZipRecruiter, official company career pages, and structured boards.
   - Restrict discovery to postings from the last 60 days whenever the source exposes posting age/date.
3. Check `generated/seen_job_urls.json` before presenting or regenerating an already tracked posting.
4. Shortlist roles across all domains that match SQL, ETL validation, data quality, reporting, and cross-functional analytics work.
   - Do not restrict discovery to healthcare-only roles.
   - Healthcare, payer, and claims roles remain higher-fit priorities for scoring, but roles from any industry may be included if the functional match is strong.
5. Save each selected posting under a company folder and position subfolder:
   - `<Company>/open_positions.json`
   - `<Company>/<JobSlug>/job_description.md`
   - `<Company>/<JobSlug>/job_url.txt`
   - `<Company>/<JobSlug>/fit_score.json`
   - `<Company>/<JobSlug>/role_metadata.json`
   - `<Company>/<JobSlug>/Swagatika_Nayak_Resume.docx`
   - `<Company>/<JobSlug>/tailoring_plan.json`
   - `<Company>/<JobSlug>/notes.md`
6. Update `generated/seen_job_urls.json` after each accepted posting so daily runs can suppress duplicates.
7. Export the ranked table to a daily file under `jobs_application/daily-exports/` using a filename that ends with the current date in `YYYY-MM-DD` format.
   - Keep one file per day.
   - On same-day reruns, append only newly discovered rows to that day's file and deduplicate by job URL.
   - On a new date, create a new dated file and do not delete or replace older daily export files.
   - Mirror the same dated export into `generated/exports/` so the repo-local copy stays in sync with the external `jobs_application` export.
8. For resume tailoring, copy the base resume and apply targeted replacements with `scripts/tailor_resume_copy.ps1`.

## Local Scripts

- `scripts/extract_profile.py`
  - Extracts plain text from the base `.docx` resume.
- `scripts/make_application.py`
  - Creates company and role folders, writes job metadata, updates the company position index, and updates the seen-URL registry.
- `scripts/tailor_resume_copy.ps1`
  - Copies the base `.docx` resume into a company folder and applies exact-text replacements using Word COM.
- `scripts/rebuild_seen_urls.py`
  - Rebuilds `generated/seen_job_urls.json` from the current job JSON records.
- `scripts/export_daily_table.py`
  - Builds the ranked markdown table, appends new same-day rows into the existing dated file, creates a new file for later dates without deleting older exports, and can render clickable resume-folder links when given the applications root path.

## Output Standard

Each application set should contain:

- a company-level position index when multiple openings exist
- the original job title and URL for each role
- the source, employment type, work mode, and location for each role
- a concise fit score with reasons
- a tailored copy of the original resume that preserves factual history
- notes that call out any gaps, unsupported tools, or risky stretches
- when presenting local file locations to the user, use relative paths rooted at `jobs_application/` rather than full absolute filesystem paths
- export a daily markdown summary file with the date suffix
- same-day reruns should append only new rows into that day's file instead of replacing prior rows
- later dates should create a new daily file and leave older export files intact

## Search Preference

Prioritize:
- `linkedin.com/jobs`
- `dice.com`
- `indeed.com`
- direct company careers pages
- `jobs.lever.co`
- `boards.greenhouse.io`
- `api.ashbyhq.com`
- `apply.workable.com`
- `myworkdayjobs.com`

Use LinkedIn, Dice, and Indeed for mandatory discovery coverage. When a matching official company posting exists, prefer that page for extraction and final tracking.
Also include ZipRecruiter in mandatory discovery coverage.
Limit discovery to the last 60 days when posting dates or age filters are available.

## Named Sources To Cover

Mandatory boards:
- `linkedin.com/jobs`
- `dice.com`
- `indeed.com`
- `ziprecruiter.com`

Named company career targets:
- Elevance Health
- UnitedHealth / Optum
- CVS Health / Aetna
- Cigna / Evernorth
- Humana
- Cognizant
- Accenture
- Deloitte
- Infosys
- TCS
- Capgemini

When these company career sites have relevant openings, search them explicitly and prefer the official posting URL over aggregator copies.
