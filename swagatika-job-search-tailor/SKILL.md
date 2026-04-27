---
name: swagatika-job-search-tailor
description: Use when searching current jobs for Swagatika Nayak across LinkedIn, Dice, Indeed, company career pages, and structured boards, deduplicating by URL, and generating company-specific application folders with truthful tailored resumes and notes.
---

# Swagatika Job Search Tailor

## Quick Command

Use this prompt when you want to run the skill:

```text
Use the swagatika-job-search-tailor skill. Search LinkedIn, Dice, Indeed, and official company job boards for Swagatika's target roles. Skip anything already present in jobs_application/seen_job_urls.json. For each new role, create or update the company folder under jobs_application, keep separate subfolders for multiple positions, copy and tailor the base resume truthfully, and show me a ranked table with score, work mode, location, apply link, and resume folder.
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
2. Search current jobs using the web tool across LinkedIn, Dice, Indeed, official company career pages, and structured boards.
3. Check `generated/seen_job_urls.json` before presenting or regenerating an already tracked posting.
4. Shortlist roles that match healthcare, payer, claims, SQL, ETL validation, data quality, reporting, and cross-functional analytics work.
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
7. For resume tailoring, copy the base resume and apply targeted replacements with `scripts/tailor_resume_copy.ps1`.

## Local Scripts

- `scripts/extract_profile.py`
  - Extracts plain text from the base `.docx` resume.
- `scripts/make_application.py`
  - Creates company and role folders, writes job metadata, updates the company position index, and updates the seen-URL registry.
- `scripts/tailor_resume_copy.ps1`
  - Copies the base `.docx` resume into a company folder and applies exact-text replacements using Word COM.
- `scripts/rebuild_seen_urls.py`
  - Rebuilds `generated/seen_job_urls.json` from the current job JSON records.

## Output Standard

Each application set should contain:

- a company-level position index when multiple openings exist
- the original job title and URL for each role
- the source, employment type, work mode, and location for each role
- a concise fit score with reasons
- a tailored copy of the original resume that preserves factual history
- notes that call out any gaps, unsupported tools, or risky stretches
- when presenting local file locations to the user, use relative paths rooted at `jobs_application/` rather than full absolute filesystem paths

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
