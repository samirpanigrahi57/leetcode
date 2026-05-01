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
Use the swagatika-job-search-tailor skill. Set a run_started_at timestamp, then search LinkedIn, Dice, Indeed, ZipRecruiter, Built In, Glassdoor, Monster, CareerBuilder, FlexJobs, Wellfound, USAJOBS, Ladders, official company career pages, and common ATS boards for Swagatika's target roles. Build a broad discovery inventory before shortlisting: include healthcare, payer, claims, ETL, data quality, validation, RCM, prompt/AI-adjacent analyst roles, plus general Data Analyst, Business Data Analyst, Reporting Analyst, BI Analyst, and SQL Analyst roles that mention SQL, Excel, Tableau, Power BI, Looker, dashboards, reporting, stakeholder analytics, or data integrity. Skip anything already present in jobs_application/seen_job_urls.json unless checking whether an existing saved job is still active. Only accept jobs posted within the last 60 days with a user-visible working apply/detail page; reject anything expired, closed, unavailable, broken, redirected to generic search, or saying "job no longer available." For every promising board result, check the employer's official careers site and prefer the official company apply URL when found; record official_site_checked, official_site_url, and official_site_search_notes. Treat Dice links as fragile: use a Dice URL only if a fresh same-run direct page check confirms the user-visible page is available, otherwise reject it. Keep an audit trail for relevant rejected postings and why they were rejected. Before finalizing, do a miss-check with broad LinkedIn and non-LinkedIn searches such as Data Analyst SQL, Data Analyst Excel BI, Business Data Analyst SQL, Reporting Analyst Power BI, and BI Analyst SQL. For each accepted role, create or update the company folder under jobs_application, keep separate subfolders for multiple positions, copy and tailor the base resume truthfully, record validation evidence and unsupported gaps, show me a ranked table with score, work mode, location, apply link, and resume folder, and export the table to a daily file ending with today's date.
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

## Work Mode Policy

- The skill is not remote-only.
- Prefer remote roles when fit is comparable.
- Include hybrid or on-site roles when the functional match is strong, the location is reasonable or explicitly requested, and the work mode is clearly shown in the table.
- Do not label a mixed or unclear posting as remote-only; use `Remote / On-site`, `Hybrid`, or `Unknown` and call out uncertainty in `notes.md`.

## Workflow

1. Extract the latest source profile from the resume and LinkedIn export.
2. Set the run boundary before searching:
   - Record `run_started_at` using the current local date/time.
   - Include postings that were visible before or at `run_started_at`.
   - Do not exclude a posting just because it appears after a search index crawl time if the visible job page shows it was posted before or during the run.
   - If a posting appears during the run and is otherwise valid, include it, but mark it as discovered during the run in notes.
3. Build a discovery inventory before shortlisting. Search current jobs using the web tool across LinkedIn, Dice, Indeed, ZipRecruiter, Built In, Glassdoor, Monster, CareerBuilder, FlexJobs, Wellfound, USAJOBS, Ladders, official company career pages, and structured boards.
   - Restrict discovery to postings from the last 60 days whenever the source exposes posting age/date.
   - Run both targeted and broad discovery passes:
     - targeted searches for healthcare, payer, claims, ETL, data quality, validation, RCM, and prompt/AI-adjacent analyst roles
     - broad searches for recent `Data Analyst`, `Business Data Analyst`, `Reporting Analyst`, `BI Analyst`, and `SQL Analyst` roles that mention SQL, Excel, Tableau, Power BI, Looker, dashboards, reporting, stakeholder analytics, or data integrity, even when the industry is not healthcare
   - For LinkedIn specifically, include a recent-post sweep for plain `Data Analyst` and `SQL Data Analyst` results before ending the run, because LinkedIn can surface fresh generic analyst postings that do not appear in healthcare-focused queries.
   - For each source, run at least these query families when source search supports them:
     - `Data Analyst SQL`
     - `Data Analyst Excel`
     - `Business Data Analyst SQL`
     - `Reporting Analyst Power BI`
     - `BI Analyst SQL`
     - `Healthcare Data Analyst`
     - `Claims Data Analyst`
     - `ETL Data Analyst`
     - `Data Quality Analyst`
     - `SQL Data Validation Analyst`
     - `RCM Data Analyst`
     - `Prompt Engineering Data Analyst`
   - Save or note rejected but relevant-looking results with a reason, so misses can be audited later.
   - Verify each candidate before accepting it:
     - detail page returns a real posting, not a 404, expired page, redirect to generic search, or "job no longer available" page
     - apply link or platform job detail exists and is not closed/unavailable
     - posting date is present and no more than 60 days old
     - for company sites with stale marketing pages (for example UHG/Radancy plus Taleo), check the official careers search result and the apply requisition; do not rely on an old detail URL alone
   - For every promising board result, extract the employer name and search that employer's official careers site for the same or similar role title before accepting the board URL.
   - Prefer the official company apply URL over the board URL whenever an official posting is found.
4. Check `generated/seen_job_urls.json` and `jobs_application/seen_job_urls.json` before presenting or regenerating an already tracked posting.
5. Shortlist roles across all domains that match SQL, ETL validation, data quality, reporting, and cross-functional analytics work.
   - Do not restrict discovery to healthcare-only roles or remote-only roles unless the user explicitly asks for that filter.
   - Healthcare, payer, and claims roles remain higher-fit priorities for scoring, but roles from any industry may be included if the functional match is strong.
   - Do not drop a current generic `Data Analyst` posting solely because it lacks healthcare language when it has SQL, dashboards/reporting, Excel/BI tooling, stakeholder analysis, data accuracy, or cross-functional analytics responsibilities. Score it lower than healthcare/payer roles if the domain match is weaker.
   - If the result count is high, keep a wider shortlist first and let fit score/order decide; do not silently discard valid postings because there are already enough high-fit rows.
   - Penalize unclear or inconvenient work modes in fit scoring instead of hiding them silently.
6. Save each selected posting under a company folder and position subfolder:
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
     - `official_site_checked`: `true` or `false`
     - `official_site_url`: official company posting URL when found
     - `official_site_search_notes`: where/how the company site was checked, or why it could not be checked
   - If any required validation field is missing, stale, or not active/working, do not create the application folder and do not include the job in the table.
7. Update `generated/seen_job_urls.json` and `jobs_application/seen_job_urls.json` after each accepted posting so daily runs can suppress duplicates.
8. Export the ranked table to a daily file under `jobs_application/daily-exports/` using a filename that ends with the current date in `YYYY-MM-DD` format.
   - Keep one file per day.
   - On same-day reruns, append only newly discovered rows to that day's file and deduplicate by job URL.
   - On a new date, create a new dated file and do not delete or replace older daily export files.
   - Mirror the same dated export into `generated/exports/` so the repo-local copy stays in sync with the external `jobs_application` export.
   - The export script filters out records that are missing active/working validation, have no valid posting date, or are older than 60 days.
9. For resume tailoring, copy the base resume and apply targeted replacements with `scripts/tailor_resume_copy.ps1`.
10. Before finalizing a run, perform a miss-check:
   - Search LinkedIn by broad recent terms such as `Data Analyst SQL United States`, `Data Analyst Excel BI United States`, and `Business Data Analyst SQL`.
   - Search at least one broad non-LinkedIn source such as Indeed or Built In with the same generic terms.
   - Compare the top current results against `seen_job_urls.json` and the generated daily table.
   - If a broad result is current and functionally relevant but not included, either add it or explicitly record why it was rejected.
   - Spot-check at least one source-specific URL pattern or known job ID if the user provides one, and use the miss to update the query matrix.

## Discovery Completeness Standard

No job-search run can guarantee every posting on the internet, but the skill should avoid avoidable misses from narrow queries or premature filtering.

For each run:

- Use a two-stage process: discovery inventory first, fit scoring second.
- Capture broad analyst postings before deciding they are lower fit.
- Treat the table as the accepted list, not the only list inspected.
- Keep an audit trail for relevant rejected postings with a reason such as duplicate, stale, unavailable, broken link, weak fit, work mode mismatch, no visible posting date, or official apply not found.
- If the user reports a missed valid posting, add the missed pattern to the query matrix immediately.
- Prefer recall over precision during discovery; precision is handled by scoring and final validation.

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
- For Dice postings, do not use the Dice URL as the final apply link unless a fresh direct page check in the same run confirms the page is available to the user and does not show "Sorry this job is no longer available" or equivalent.
- Prefer an official company, LinkedIn, Indeed, ZipRecruiter, Workday, Lever, Greenhouse, Ashby, or other stable apply URL over a Dice URL whenever one exists.
- If Dice is the only source and user-visible availability is uncertain, reject the posting instead of adding it to the table.

For UnitedHealth Group specifically, prefer the current official search URL result and verify the requisition in the detail page. UHG detail pages can remain visible after Taleo closes the requisition, so any closed Taleo requisition must be rejected.

## Company-Site Coverage Checklist

Use [references/company-targets.md](references/company-targets.md) as the seed list for direct company career searches. Expand it whenever a strong board result reveals a new relevant employer.

For each shortlisted board result:

- Identify the employer and normalized role title.
- Search the employer's official career site for the same title and related keywords.
- Search common ATS URLs for that employer when applicable: Workday, Lever, Greenhouse, Ashby, Workable, iCIMS, SmartRecruiters, and company `/careers` pages.
- Record `official_site_checked`, `official_site_url`, and `official_site_search_notes` in the generated job JSON.
- If the official posting is found, use the official URL as `job_url` and keep the board URL only in notes.
- If no official posting is found, keep the board URL only when it passes the current user-visible availability checks.
- If a board URL is fragile or reports "no longer available," reject it even if an indexed snippet still appears current.
- Add new strong-fit employers to `references/company-targets.md` with the company name, career site if known, and domain/category notes.

When validating existing application folders, run:

```text
python scripts\validate_job_links.py C:\Users\samir\Documents\swagatika\jobs_application --report generated\validation-report-YYYY-MM-DD-existing.json --write
```

Use `--date YYYY-MM-DD` for repeatable runs:

```text
python scripts\validate_job_links.py C:\Users\samir\Documents\swagatika\jobs_application --report generated\validation-report-YYYY-MM-DD-existing.json --date YYYY-MM-DD --write
```

Review false negatives from boards that block scripts before accepting or rejecting a posting, but do not rely on stale manual evidence. If a board blocks scripted validation, perform a fresh browser/page check and only keep the posting if the current page does not say expired, unavailable, filled, or no longer accepting applications. Dice links are especially fragile; when in doubt, exclude them.

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
   - Accepts `--date YYYY-MM-DD` so validation is reproducible for a specific daily run.

## Output Standard

Each application set should contain:

- a company-level position index when multiple openings exist
- the original job title and URL for each role
- the source, employment type, work mode, and location for each role
- a concise fit score with reasons
- a tailored copy of the original resume that preserves factual history
- notes that call out any gaps, unsupported tools, or risky stretches
- validation evidence showing the posting is active, working, and less than 60 days old
- company-site check evidence showing whether an official company posting was found
- when presenting local file locations to the user, use relative paths rooted at `jobs_application/` rather than full absolute filesystem paths
- export a daily markdown summary file with the date suffix
- same-day reruns should append only new rows into that day's file instead of replacing prior rows
- later dates should create a new daily file and leave older export files intact

## Search Preference

Prioritize:
- `linkedin.com/jobs`
- `dice.com`
- `indeed.com`
- `glassdoor.com`
- `builtin.com`
- `monster.com`
- `careerbuilder.com`
- `flexjobs.com`
- `wellfound.com`
- `usajobs.gov`
- `theladders.com`
- direct company careers pages
- `jobs.lever.co`
- `boards.greenhouse.io`
- `api.ashbyhq.com`
- `apply.workable.com`
- `myworkdayjobs.com`

Use LinkedIn, Dice, Indeed, ZipRecruiter, Built In, Glassdoor, Monster, CareerBuilder, FlexJobs, Wellfound, USAJOBS, and Ladders for discovery coverage when relevant results are available. When a matching official company posting exists, prefer that page for extraction and final tracking.
Limit discovery to the last 60 days when posting dates or age filters are available.

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
