import java.io.*;
import java.util.*;

/**
 * GitResume: A lightweight CLI utility for monitoring workspace repository status.
 * Compile: javac GitResume.java
 * Run: java GitResume (or 'git resume' if aliased)
 */
public class GitResume {

    private static final int MAX_DEPTH = 3;
    private static final List<String> IGNORE_DIRS = Arrays.asList("node_modules", ".metadata", "target", ".git");
    private static final List<RepoInfo> repositories = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(" Initializing GitResume workspace scan...");
        scanDirectory(new File(System.getProperty("user.dir")), 0);

        if (repositories.isEmpty()) {
            System.out.println(" No Git repositories found in current scope.");
            return;
        }

        displayDashboard();
        handleUserSelection();
    }

    private static void scanDirectory(File dir, int depth) {
        if (depth > MAX_DEPTH) return;

        File[] files = dir.listFiles();
        if (files == null) return;

        boolean isGitRepo = false;
        for (File f : files) {
            if (f.isDirectory() && f.getName().equals(".git")) {
                isGitRepo = true;
                break;
            }
        }

        if (isGitRepo) {
            repositories.add(new RepoInfo(dir.getName(), dir.getAbsolutePath(), checkStatus(dir)));
        } else {
            for (File f : files) {
                if (f.isDirectory() && !IGNORE_DIRS.contains(f.getName())) {
                    scanDirectory(f, depth + 1);
                }
            }
        }
    }

    private static String checkStatus(File dir) {
        try {
            Process p = new ProcessBuilder("git", "status", "--porcelain").directory(dir).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            return (reader.readLine() != null) ? " Dirty" : " Clean";
        } catch (IOException e) {
            return " Error (Git not found)";
        }
    }

    private static void displayDashboard() {
        System.out.println("\n--- Project Dashboard ---");
        for (int i = 0; i < repositories.size(); i++) {
            System.out.println((i + 1) + ". [" + repositories.get(i).status + "] " + repositories.get(i).name);
        }
    }

    private static void handleUserSelection() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nSelect a project number for Context Recovery: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < repositories.size()) {
                RepoInfo repo = repositories.get(choice);
                if (repo.status.contains("Dirty")) {
                    generateContextReport(repo);
                } else {
                    System.out.println(" Project is clean. Nothing to recover.");
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid selection.");
        }
    }

    private static void generateContextReport(RepoInfo repo) {
        List<String> files = new ArrayList<>();
        try {
            Process p = new ProcessBuilder("git", "diff", "--name-only").directory(new File(repo.path)).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) files.add(line);
        } catch (IOException e) {
            System.err.println("Failed to retrieve diff.");
        }

        System.out.println("\n---  AI-Style Context Recovery Report ---");
        System.out.println("Project: " + repo.name);
        System.out.println("Modification Count: " + files.size());
        System.out.println("Active Target Paths:");
        files.forEach(f -> System.out.println("  - " + f));
        
        System.out.println("\nAnalytical Instruction:");
        if (files.stream().anyMatch(f -> f.endsWith(".java"))) {
            System.out.println(">> Detected Java changes. Suggestion: Run 'javac <file>' or verify unit tests before committing.");
        } else {
            System.out.println(">> Standard assets modified. Suggestion: Review diff and prepare for staged commit.");
        }
    }

    static class RepoInfo {
        String name, path, status;
        RepoInfo(String n, String p, String s) { this.name = n; this.path = p; this.status = s; }
    }
}