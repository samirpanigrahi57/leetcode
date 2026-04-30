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
- Do not present, export, or create application folders for jobs that are expired, closed, unavailable, older than 60 days, or whose apply/details link is broken.
- Treat search-result visibility, cached pages, and indexed snippets as insufficient proof that a job is open.
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
   - Verify each candidate before accepting it:
     - detail page returns a real posting, not a 404, expired page, redirect to generic search, or "job no longer available" page
     - apply link or platform job detail exists and is not closed/unavailable
     - posting date is present and no more than 60 days old
     - for company sites with stale marketing pages (for example UHG/Radancy plus Taleo), check the official careers search result and the apply requisition; do not rely on an old detail URL alone
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
   - Required JSON validation fields for each accepted posting:
     - `posting_date`: ISO date (`YYYY-MM-DD`) from the posting or board
     - `last_verified_date`: today's ISO date (`YYYY-MM-DD`)
     - `availability_status`: `active`
     - `link_status`: `working`
     - `apply_url`: direct apply URL when distinct from `job_url`
     - `validation_notes`: short evidence, for example "official detail page loaded; apply requisition 2354640 opens Taleo privacy gate, not closed-job page"
   - If any required validation field is missing, stale, or not active/working, do not create the application folder and do not include the job in the table.
6. Update `generated/seen_job_urls.json` after each accepted posting so daily runs can suppress duplicates.
7. Export the ranked table to a daily file under `jobs_application/daily-exports/` using a filename that ends with the current date in `YYYY-MM-DD` format.
   - Keep one file per day.
   - On same-day reruns, append only newly discovered rows to that day's file and deduplicate by job URL.
   - On a new date, create a new dated file and do not delete or replace older daily export files.
   - Mirror the same dated export into `generated/exports/` so the repo-local copy stays in sync with the external `jobs_application` export.
   - The export script filters out records that are missing active/working validation, have no valid posting date, or are older than 60 days.
8. For resume tailoring, copy the base resume and apply targeted replacements with `scripts/tailor_resume_copy.ps1`.

## Availability Validation Standard

Before a job reaches the ranked table, confirm all of the following:

- The posting date is within 60 days of the run date.
- The official job detail page loads the same role, company, and location.
- The page is not a stale/cached result, 404, generic search redirect, or closed-job message.
- The apply link is present and points to a real requisition or application flow.
- If a backend blocks scripts with cookie/privacy text, distinguish that from a closed job by checking whether the message is only a cookie/privacy gate and the official search page still lists the same requisition.
- If a job board blocks scripted validation or returns a misleading HTTP status, verify in the browser-visible web result before accepting it. Record that manual/browser-visible evidence in `validation_notes`.
- If the posting exposes a past application deadline, reject it even when the board says it was recently updated.
- If the apply system says "job no longer available" or equivalent, reject the role even when the careers page still renders.

For UnitedHealth Group specifically, prefer the current official search URL result and verify the requisition in the detail page. UHG detail pages can remain visible after Taleo closes the requisition, so any closed Taleo requisition must be rejected.

When validating existing application folders, run:

```text
python scripts\validate_job_links.py C:\Users\samir\Documents\swagatika\jobs_application --report generated\validation-report-YYYY-MM-DD-existing.json --write
```

Review false negatives from boards that block scripts, especially Dice, before marking a browser-visible current posting as unavailable.

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
- `scripts/validate_job_links.py`
  - Checks existing generated records or application folders for broken/expired links, stale posting dates, known closed UHG links, and past application deadlines.
  - Writes `availability_status`, `link_status`, `posting_date`, `last_verified_date`, and `validation_notes` when run with `--write`.

## Output Standard

Each application set should contain:

- a company-level position index when multiple openings exist
- the original job title and URL for each role
- the source, employment type, work mode, and location for each role
- a concise fit score with reasons
- a tailored copy of the original resume that preserves factual history
- notes that call out any gaps, unsupported tools, or risky stretches
- validation evidence showing the posting is active, working, and less than 60 days old
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
