# 🚀 Git-Resume
**Stop losing track of your Git repositories. Get a birds-eye view of your development workspace in seconds.**

Git-Resume is a lightweight, Java-based CLI utility designed to scan your local environment, identify Git repositories, and analyze their status (Clean/Dirty). It helps developers manage context switching by providing an "AI-style" diagnostic report of what needs to be committed.

---

## 🌟 Key Features
- **Recursive Discovery:** Automatically traverses directories to find all your Git-initialized projects.
- **Status Monitoring:** Instantly identifies "Dirty" repositories with unsaved changes.
- **Context Recovery:** Generates a clean, actionable report of modified files, helping you decide what to stage next.
- **High Performance:** Built with native Java to ensure near-instant execution.
- **Zero-Dependency:** Runs on any machine with Java installed—no external libraries required.

---

## 🛠️ How it Works
Git-Resume acts as a bridge between your terminal and your local development environment. By performing a recursive scan, it maps your repositories to a numbered dashboard, allowing you to select a project and view its current state via a simple CLI interface.

---

## 🚀 Getting Started

### Prerequisites
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) (Version 8 or higher)
- Git installed and available in your system PATH.

### Installation
1. **Clone the repository:**
   `git clone https://github.com/Keerthybavana/git-resume.git`
2. **Compile the source code:**
   `javac GitResume.java`
3. **Add to PATH:**
   Add the folder containing your compiled class files to your System Environment Variables (PATH).

### Usage
Simply open your terminal in any directory and type:
`git-resume or resume`

---

## 📄 License
This project is licensed under the **MIT License**.
