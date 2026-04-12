# MD3 Expressive ToDo App

A highly opinionated, local-first, multi-platform ToDo application heavily inspired by **Microsoft To Do (Kuro)**, styled with **MD3 Expressive (MaterialKolor)**, and powered by **AI**.

## Features

*   **Microsoft To Do Architecture**: Retains the beloved "My Day", "Important", and "Task/Subtask" hierarchy without the bloat of collaborative features. Designed entirely for personal productivity.
*   **MD3 Expressive Design**: Uses dynamic color generation (`MaterialKolor`) based on priority, lists, or system themes. Features fluid, large-rounded-corner cards similar to `FastTimes` and `Tomato`.
*   **Retrospective Stats**: Integrated retrospective charting and streak views to understand how you perform over time (inspired by `Tomato`).
*   **AI Integration**: Incorporates an intelligent LLM gateway (inspired by `RikkaHub` and `FluxDO`). The AI can:
    *   Automatically break down complex tasks into subtasks.
    *   Summarize Markdown-heavy notes into actionable bullet points.
    *   Provide daily motivational summaries based on completed tasks.
*   **Rich Text / Markdown**: Ditch simple string descriptions for full-fledged Markdown support with syntax highlighting within the task card.

## Architecture

This project is built from the ground up as a **Compose Multiplatform** project.

*   **UI Framework**: Jetpack Compose / Compose Multiplatform (CMP 1.6.10+)
*   **Dependency Injection**: Koin
*   **Local Database**: Room Multiplatform (KMP 2.7.0-alpha01) & SQLite Bundled
*   **Networking (for AI Gateway)**: Ktor Client & Kotlinx Serialization
*   **CI/CD**: Fully automated Android APK releases using GitHub Actions

## Installation

You can download the latest Android `.apk` directly from the [GitHub Actions Artifacts](https://github.com/panyou1996/md3-to-do/actions).

1. Go to the Actions tab.
2. Click on the latest successful "Android CI" build.
3. Download `app-debug.apk` from the Artifacts section.

## Status

**Currently in active development.** Phase A (Local Database), Phase B (AI Gateway), and Phase C (MD3 UI/Markdown) implementations have been pushed. UI is continually being refined to match the high standards of `kuro` and `Tomato`.