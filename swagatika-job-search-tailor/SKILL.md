---
name: swagatika-job-search-tailor
description: Use when searching current jobs for Swagatika Nayak across LinkedIn, Dice, Indeed, company career pages, and structured boards, deduplicating by URL, validating live apply links, creating one tailored resume per accepted role, and exporting one daily table. Do not create redundant generated/jobs_application job records.
---

# Swagatika Job Search Tailor

## Quick Command

Start from:

```text
C:\Users\samir\Documents\Learning\Java\Projects\leetcode
```

Use this prompt for a search run:

```text
Use the swagatika-job-search-tailor skill. Set a run_started_at timestamp, then search LinkedIn, Dice, Indeed, ZipRecruiter, Built In, Glassdoor, Monster, CareerBuilder, FlexJobs, Wellfound, USAJOBS, Ladders, official company career pages, and common ATS boards for Swagatika's target roles. Build a broad discovery inventory before shortlisting: include healthcare, payer, claims, ETL, data quality, validation, RCM, prompt/AI-adjacent analyst roles, plus general Data Analyst, Business Data Analyst, Reporting Analyst, BI Analyst, and SQL Analyst roles that mention SQL, Excel, Tableau, Power BI, Looker, dashboards, reporting, stakeholder analytics, or data integrity. Skip anything already present in generated/seen_job_urls.json. Only accept jobs posted within the last 30 days whose exact final apply/detail URL is live in the current run; reject anything expired, closed, unavailable, older, redirected to generic search, removed, or whose apply link is broken. Search snippets may prove posting date for lower tiers, but never link availability. Prefer official company ATS URLs over aggregator URLs when found. Treat Dice, UHG/Optum/UnitedHealthcare, Monster, CareerBuilder, and Indeed links as fragile: reject any final URL you cannot confirm live in the same run. Keep an audit trail for relevant rejected postings. Before finalizing, run a brief miss-check with two broad searches. Show me a ranked table grouped by Apply First / Apply If Time / Skip Stretch. For each accepted job, create exactly one resume file under C:\Users\samir\Documents\swagatika\jobs_application\<Company>\<job-slug>\Swagatika_Nayak_Resume.docx; that role folder must contain only the resume. Export only this run's accepted jobs to C:\Users\samir\Documents\swagatika\jobs_application\daily-exports\job-search-table-YYYY-MM-DD.md. Update generated/seen_job_urls.json for dedupe only. Do not write role_metadata.json, job_url.txt, job_description.md, fit_score.json, tailoring_plan.json, notes.md, open_positions.json, or duplicate folders during the search run.
```

Use this skill when the task is to find current jobs for Swagatika Nayak, validate links, create ready-to-use resumes, and export a daily job table without redundant per-job records.

## Output Model

The search run has one user-facing output root:

```text
C:\Users\samir\Documents\swagatika\jobs_application
```

For each accepted job, create exactly:

```text
C:\Users\samir\Documents\swagatika\jobs_application\<Company>\<job-slug>\Swagatika_Nayak_Resume.docx
```

The role folder must contain only `Swagatika_Nayak_Resume.docx`.

Also write exactly one daily table:

```text
C:\Users\samir\Documents\swagatika\jobs_application\daily-exports\job-search-table-YYYY-MM-DD.md
```

Use `generated/seen_job_urls.json` only as the dedupe registry. Do not create duplicate job folders under `generated/`. Do not mirror the same job into both `generated/` and `jobs_application/`.

Do not write during a search run:

- `role_metadata.json`
- `job_url.txt`
- `job_description.md`
- `fit_score.json`
- `tailoring_plan.json`
- `notes.md`
- `open_positions.json`
- any second resume copy
- any duplicate per-job folder under `generated/`

## Resume Creation

- Base resume: `C:\Users\samir\Documents\swagatika\Swagatika_Nayak_Resume.docx`
- Resume script: `scripts\tailor_resume_copy.ps1`
- Preserve the base resume formatting by copying the `.docx` and applying truthful, minimal Word Find/Replace changes.
- Do not invent skills, tools, responsibilities, dates, employers, metrics, domains, or certifications.
- If a replacement plan is needed, keep it temporary outside the role folder and delete it after the resume is created. The final role folder must still contain only the resume.
- If Word COM or resume generation fails for a role, do not create placeholder files. Report the failure and keep the job in the table only if the link validation was otherwise valid.

## Guardrails

- Never change employer names or employment dates.
- Do not present, export, or create folders for jobs that are expired, closed, unavailable, older than 30 days, redirected to generic search, removed, or whose apply/details link is broken.
- Every accepted job must have its exact final URL checked during the current run. Search snippets can support posting date for lower tiers, but cannot prove that the link is live.
- Treat search-result visibility, cached pages, and indexed snippets as insufficient proof that a posting is open.
- If the exact final URL cannot be confirmed as live, reject the posting and record why.

## Candidate Focus

Read [references/target-roles.md](references/target-roles.md) for role families and search domains.

Swagatika's strongest fit is:

- Healthcare Data Analyst
- ETL Analyst
- Data Quality Analyst
- Payer / Claims Data Analyst
- Data Warehouse QA / Validation Analyst

Also include lower-scored but relevant Data Analyst, Business Data Analyst, Reporting Analyst, BI Analyst, SQL Analyst, and prompt/AI-adjacent analyst roles when they match SQL, Excel, Tableau, Power BI, Looker, dashboards, reporting, stakeholder analytics, data integrity, validation, claims, RCM, payer data, or ETL/data quality work.

## Work Mode Policy

- The skill is not remote-only.
- Prefer remote roles when fit is comparable.
- Include hybrid or on-site roles when the functional match is strong, the location is reasonable or explicitly requested, and the work mode is clearly shown in the table.
- Do not label a mixed or unclear posting as remote-only; use `Remote / On-site`, `Hybrid`, `On-site`, or `Unknown`.

## Search Workflow

1. Set the run boundary:
   - Record `run_started_at` using the current local date/time.
   - Include only postings visible before or during the run.
   - Use the current date to calculate the 30-day posting window.

2. Build a broad discovery inventory before shortlisting. Run discovery across:
   - LinkedIn
   - Dice
   - Indeed
   - ZipRecruiter
   - Built In
   - Glassdoor
   - Monster
   - CareerBuilder
   - FlexJobs
   - Wellfound
   - USAJOBS
   - Ladders
   - official company career pages
   - common ATS boards such as Workday, Lever, Greenhouse, Ashby, Workable, iCIMS, SmartRecruiters, and company `/careers` pages

3. Use broad and targeted search families:
   - `Healthcare Data Analyst remote SQL`
   - `ETL Data Analyst SQL remote`
   - `Data Quality Analyst healthcare SQL`
   - `Claims Data Analyst SQL Power BI remote`
   - `Business Data Analyst SQL Excel remote`
   - `Reporting Analyst Power BI Tableau remote`
   - `BI Analyst SQL dashboards remote`
   - `SQL Analyst data integrity reporting remote`
   - `RCM Data Analyst SQL`
   - `prompt AI data analyst SQL healthcare`

4. Search explicit target companies from [references/company-targets.md](references/company-targets.md), especially:
   - UnitedHealth Group / Optum / UnitedHealthcare
   - Elevance Health
   - CVS Health / Aetna
   - Cigna / Evernorth
   - Humana
   - Blue Cross / payer organizations
   - healthcare technology and RCM companies

5. Deduplicate before writing:
   - Skip exact URLs already in `generated/seen_job_urls.json`.
   - Prefer the official company/ATS URL over aggregator URLs when both point to the same role.

## Validation Standard

Before a job reaches the ranked table, confirm all of the following:

- The posting date is within 30 days of the run date.
- The exact final `job_url` or `apply_url` has been checked in the current run.
- The page is not broken, removed, closed, expired, unavailable, a stale cached page, or a generic search redirect.
- The page or current ATS response shows the same role title and employer.
- The posting has an apply button, open requisition, `directApply`, external apply path, or equivalent active apply state.

Validation tiers:

- **Apply First**: Fetch the exact final URL and confirm title, employer, current posting date, and active apply state from page content or ATS JSON.
- **Apply If Time**: Exact final URL still must be live. A search snippet may provide posting-date evidence when the fetched page is JavaScript-rendered or omits date metadata.
- **Skip / Stretch**: Exact final URL still must be live. Use this tier for weaker fit, seniority stretch, uncertain work mode, weaker domain match, or location friction.

Fragile-source rules:

- **Dice**: Always fetch the exact Dice URL in the same run. Reject if fetch fails, the connection aborts, the page says unavailable, or availability is uncertain. Prefer an official company URL whenever found.
- **UHG / Optum / UnitedHealthcare**: Always verify the exact requisition in the current official search/detail flow. Reject old Taleo/Radancy URLs, pages with `postingAvailable: false`, generic redirects, stale snippets, or any requisition that is not confirmed open in the same run.
- **Indeed, Monster, CareerBuilder, Glassdoor, ZipRecruiter**: Use for discovery, but prefer official company URLs. Keep an aggregator URL only when the exact URL is confirmed live in the same run.
- **Workday**: When possible, check the Workday CXS JSON endpoint or page content for title/date/apply state. If the shell page exposes only a JavaScript shell and `postingAvailable: false`, do not use it for Apply First.
- **Blocked pages**: If a source blocks scripted fetch with 403/cookie gating, accept only when another current live-check path confirms the exact final URL. Otherwise reject.

## Shortlisting

- Score healthcare, payer, claims, ETL, data quality, validation, RCM, and reporting roles highest.
- Do not drop a current generic analyst role solely because it lacks healthcare language when it has strong SQL, dashboarding, reporting, stakeholder analytics, Excel/BI, or data integrity responsibilities.
- Penalize unclear work mode, non-remote requirements, over-seniority, heavy engineering requirements, and unsupported tools rather than hiding those roles silently.
- Keep an audit trail for rejected but relevant postings with the company, title or URL when available, and reason.

## Daily Export Table

- Show a ranked table grouped by Apply First / Apply If Time / Skip Stretch.
- Include company, role, source, type, work mode, location, score, apply link, and resume folder.
- Export only this run's accepted jobs unless the user explicitly asks for the full active backlog.
- Use `C:\Users\samir\Documents\swagatika\jobs_application\daily-exports\job-search-table-YYYY-MM-DD.md`.
- On same-day reruns, append only newly accepted rows and deduplicate by URL.
- Do not carry stale same-day rows forward after validation marks a posting unavailable.

## Dedupe Registry

Use `generated/seen_job_urls.json` as the only persistent dedupe registry unless the user asks otherwise.

Each accepted job should add enough data to suppress duplicates later:

- `company`
- `job_title`
- `job_url`
- `source`
- `employment_type`
- `work_mode`
- `location`
- `posting_date`
- `last_verified_date`
- `availability_status`
- `link_status`

Do not write per-job `role_metadata.json` just to rebuild the seen registry. Update the registry directly from the accepted jobs in the current run.

## Miss-Check

Before finalizing, run exactly two broad miss-check searches:

- one LinkedIn-style broad search such as `LinkedIn Data Analyst SQL remote posted past month`
- one non-LinkedIn broad search such as `Healthcare Data Analyst SQL remote posted past month`

Add any new valid result, or record why it was rejected.

## Local Scripts

- `scripts/extract_profile.py`: extracts plain text from the base `.docx` resume.
- `scripts\tailor_resume_copy.ps1`: creates the final resume copy. Use this during search runs.
- `scripts\make_application.py`: old full-package writer. Do not use during search runs because it creates `job_description.md`, `job_url.txt`, `role_metadata.json`, `fit_score.json`, `notes.md`, `tailoring_plan.json`, and `open_positions.json`.
- `scripts\rebuild_seen_urls.py`: scans generated metadata. Do not use for search runs that write no per-job generated metadata.
- `scripts\export_daily_table.py`: exports from generated metadata. Do not use for search runs that write only resumes and a manual daily table.
- `scripts\validate_job_links.py`: checks existing generated records or application folders for broken/expired links, stale posting dates, known closed UHG links, and past application deadlines.

## Named Sources To Cover

Mandatory boards:

- `linkedin.com/jobs`
- `dice.com`
- `indeed.com`
- `ziprecruiter.com`
- `builtin.com`
- `glassdoor.com`
- `monster.com`
- `careerbuilder.com`
- `flexjobs.com`
- `wellfound.com`
- `usajobs.gov`
- `theladders.com`

Named company career targets:

- Elevance Health
- UnitedHealth Group / Optum / UnitedHealthcare
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
