---
name: swagatika-job-search-tailor
description: Use when searching current jobs for Swagatika Nayak across LinkedIn, Dice, Indeed, company career pages, and structured boards, deduplicating by URL, validating live job detail pages and active apply paths, creating one tailored resume package per accepted role under generated, and exporting one daily table. Also use for one-off resume tailoring from a valid job posting URL or pasted job description. Do not write to jobs_application.
---

# Swagatika Job Search Tailor

## Quick Commands

Start from:

```text
C:\Users\samir\OneDrive\Documents\Swagatika\leetcode
```

### Search Run Prompt

```text
Use the swagatika-job-search-tailor skill. Set run_started_at, then search current jobs across LinkedIn, Dice, Indeed, ZipRecruiter, Built In, Glassdoor, Monster, CareerBuilder, FlexJobs, Wellfound, USAJOBS, Ladders, official company career pages, and common ATS boards. Build a broad inventory for Swagatika's target roles: healthcare/payer/claims/provider data, ETL, data quality, validation, RCM, reporting, BI, SQL analyst, business data analyst, and prompt/AI-adjacent analyst roles that still require SQL/data analysis. Skip URLs already in generated/seen_job_urls.json. Only accept postings from the last 30 days whose exact public detail URL is live in this run, shows the same employer/title, and has an active apply path. Prefer official company/ATS URLs over aggregators. Treat Dice, UHG/Optum/UnitedHealthcare, Centene, Indeed, Monster, CareerBuilder, Glassdoor, and ZipRecruiter as fragile. For UHG and Centene, store the public job detail URL, exact title, req/job number when visible, and a practical apply instruction; UHG instructions should prefer exact title search when the apply flow fails, while Centene instructions should prefer job number search. Keep a rejected-posting audit. Run exactly two miss-check searches before finalizing. Create each accepted resume package under swagatika-job-search-tailor\generated\<Company>\<job-slug>\ with exactly Swagatika_Nayak_Resume.docx and <company_name>_resume.pdf. Write accepted-jobs-YYYY-MM-DD.json, rejected-YYYY-MM-DD.md, and job-search-table-YYYY-MM-DD.md under generated\daily-exports. Update generated/seen_job_urls.json for dedupe only. Do not write to jobs_application.
```

### One-Off Tailoring Prompt

```text
Use the swagatika-job-search-tailor skill in one-off tailoring mode. I will provide a valid job posting URL, a pasted job description, or company/role requirements. Do not run a broad job search. If I provide a URL, verify that the exact public detail URL is live and shows the role/employer before tailoring. If I paste a JD without a URL, tailor from the pasted content only and do not update generated/seen_job_urls.json. Create the tailored resume package under swagatika-job-search-tailor\generated\<Company-or-valid-company-name>\<job-slug>\ with exactly Swagatika_Nayak_Resume.docx and <company_name>_resume.pdf. Use generated\base-resume\Swagatika_Nayak_Resume.docx as the base resume. Preserve truthful experience only: do not invent skills, tools, responsibilities, dates, employers, metrics, domains, or certifications. Return the final folder path and whether the source was a verified URL or pasted JD.
```

Use this skill when the task is to find current jobs for Swagatika Nayak, validate live detail pages and active apply paths, create ready-to-use Word and PDF resumes under `generated`, and export a daily job table without writing to `jobs_application`.

Also use this skill when the user provides a single valid job posting URL or pastes a job description and asks for a tailored resume. In that one-off tailoring mode, do not run a broad job search unless the user explicitly asks for one.

## One-Off Tailoring Mode

Use this mode when the user provides:

- a single job posting URL
- a pasted job description
- a company and role title plus requirements

Rules:

- Do not perform broad search, miss-check searches, daily table export, or current-run accepted jobs export unless the user explicitly asks.
- If the user provides a URL, fetch or otherwise verify the exact final URL is live, still available, and shows the same role/employer before using it as a validated posting.
- If the user pastes only a JD, treat it as user-provided source text. Do not claim live-posting validation and do not add it to `generated/seen_job_urls.json`.
- Infer a filesystem-safe company folder and job slug from the posting or pasted JD. If company name is missing, use `Unknown Company`; if role title is missing, use a concise role slug inferred from the strongest visible title or responsibilities.
- Create exactly:

```text
<repo>\swagatika-job-search-tailor\generated\<Company-or-valid-company-name>\<job-slug>\Swagatika_Nayak_Resume.docx
<repo>\swagatika-job-search-tailor\generated\<Company-or-valid-company-name>\<job-slug>\<company_name>_resume.pdf
```

- Keep the role folder limited to the two resume files.
- Return the final folder path and note whether the resume was based on a verified URL or pasted JD.

## Output Model

The search run has one user-facing output root:

```text
<repo>\swagatika-job-search-tailor\generated
```

New run outputs should be written under company/job folders and `daily-exports`.
Older legacy folders such as `exports`, `daily-run-*`, or folders containing
`role_metadata.json` may exist in `generated`; treat them as historical artifacts
unless the user explicitly asks to inspect or migrate them.

For each accepted job, create exactly:

```text
<repo>\swagatika-job-search-tailor\generated\<Company>\<job-slug>\Swagatika_Nayak_Resume.docx
<repo>\swagatika-job-search-tailor\generated\<Company>\<job-slug>\<company_name>_resume.pdf
```

The role folder must contain only:

- `Swagatika_Nayak_Resume.docx`
- `<company_name>_resume.pdf`

Also write exactly one daily table:

```text
<repo>\swagatika-job-search-tailor\generated\daily-exports\job-search-table-YYYY-MM-DD.md
```

Use `generated/seen_job_urls.json` only as the dedupe registry. Never mirror or copy the same job into `jobs_application`.

For every search run, create one temporary current-run accepted jobs file outside
role folders:

```text
<repo>\swagatika-job-search-tailor\generated\daily-exports\accepted-jobs-YYYY-MM-DD.json
```

This file is the input for daily table export and package validation. It may be
kept as the run audit record because it is not a per-role metadata file.

Do not write during a search run:

- `role_metadata.json`
- `job_url.txt`
- `job_description.md`
- `fit_score.json`
- `tailoring_plan.json`
- `notes.md`
- `open_positions.json`
- any job output under `jobs_application`
- any extra resume copies beyond the required `.docx` and `.pdf`

## Resume Creation

- Base resume: `generated\base-resume\Swagatika_Nayak_Resume.docx`
- Resume script: `scripts\tailor_resume_copy.ps1`
- Preserve the base resume formatting by copying the `.docx` and applying truthful, minimal Word Find/Replace changes.
- Export the final tailored Word document to PDF in the same role folder. Name the PDF `<company_name>_resume.pdf`, using a lowercase or filesystem-safe company name when needed.
- After resume generation, run `scripts\validate_generated_package.ps1` against the accepted jobs file and generated root. Do not finalize until every accepted role folder contains exactly the required `.docx` and `.pdf`.
- Do not invent skills, tools, responsibilities, dates, employers, metrics, domains, or certifications.
- If a replacement plan is needed, keep it temporary outside the role folder and delete it after the resume is created. The final role folder must still contain only the required `.docx` and `.pdf`.
- If Word COM or resume generation fails for a role, do not create placeholder files. Report the failure and keep the job in the table only if the link validation was otherwise valid.
- If PDF export fails but the `.docx` was created correctly, report the PDF failure clearly and leave the `.docx` in place.

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
- **UHG / Optum / UnitedHealthcare**: Always verify the exact requisition in the current official search/detail flow. Reject old Taleo/Radancy URLs, pages with `postingAvailable: false`, generic redirects, stale snippets, or any requisition that is not confirmed open in the same run. Do not label UHG links as durable apply links. Store the public job detail URL, requisition number, exact job title, and an apply instruction such as `Use the detail page Apply button; if Taleo fails, search the exact title on careers.unitedhealthgroup.com`. UHG title search is often more reliable than requisition-number search.
- **Centene**: Treat `jobs.centene.com` URLs as public job detail links, not durable apply links. Verify the detail page shows the role, job number, posting date, and active `Apply Now` path, but include the Centene job number and an apply instruction such as `Search job number <job_number> on jobs.centene.com if the Workday apply redirect fails`.
- **Indeed, Monster, CareerBuilder, Glassdoor, ZipRecruiter**: Use for discovery, but prefer official company URLs. Keep an aggregator URL only when the exact URL is confirmed live in the same run.
- **Workday**: When possible, check the Workday CXS JSON endpoint or page content for title/date/apply state. If the shell page exposes only a JavaScript shell and `postingAvailable: false`, do not use it for Apply First.
- **Blocked pages**: If a source blocks scripted fetch with 403/cookie gating, accept only when another current live-check path confirms the exact final URL. Otherwise reject.

## Shortlisting

- Score healthcare, payer, claims, ETL, data quality, validation, RCM, and reporting roles highest.
- Do not drop a current generic analyst role solely because it lacks healthcare language when it has strong SQL, dashboarding, reporting, stakeholder analytics, Excel/BI, or data integrity responsibilities.
- Penalize unclear work mode, non-remote requirements, over-seniority, heavy engineering requirements, and unsupported tools rather than hiding those roles silently.
- Keep an audit trail for rejected but relevant postings with the company, title or URL when available, and reason.
- Write the rejected-posting audit to `generated\daily-exports\rejected-YYYY-MM-DD.md`. This is allowed as a run-level audit file and does not violate the no per-role metadata rule.

## Daily Export Table

- Show a ranked table grouped by Apply First / Apply If Time / Skip Stretch.
- Include company, role, source, type, work mode, location, posting date, verification date, score, job detail link, apply instruction, and resume folder.
- Export only this run's accepted jobs by default. On same-day reruns, replace the same-day table with the newest run snapshot unless the user explicitly asks for an appended/cumulative same-day table.
- Use `generated\daily-exports\job-search-table-YYYY-MM-DD.md`.
- Use `scripts\export_daily_table.py` with the current-run accepted jobs JSON; do not export from legacy `role_metadata.json`.
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
- `scripts\tailor_resume_copy.ps1`: creates the final resume copy and exports PDF when `-PdfOutputPath` is provided. It uses Word SaveAs PDF because it has been more reliable than `ExportAsFixedFormat` in this environment. Use this during search runs.
- `scripts\make_application.py`: old full-package writer. Do not use during search runs because it creates `job_description.md`, `job_url.txt`, `role_metadata.json`, `fit_score.json`, `notes.md`, `tailoring_plan.json`, and `open_positions.json`.
- `scripts\rebuild_seen_urls.py`: scans generated metadata. Do not use for search runs that write no per-job generated metadata.
- `scripts\export_daily_table.py`: exports a daily table from the current-run accepted jobs JSON and validates each URL against `seen_job_urls.json`.
- `scripts\validate_generated_package.ps1`: verifies each accepted role folder contains exactly `Swagatika_Nayak_Resume.docx` and `<company_name>_resume.pdf`.
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
