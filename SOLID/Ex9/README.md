# Ex9 — DIP: Assignment Evaluation Pipeline

## 1. Context
An evaluation pipeline checks submissions using a rubric, runs plagiarism checks, grades code, and writes a report.

## 2. Current behavior
- `EvaluationPipeline.evaluate` directly instantiates concrete graders/checkers/writers with `new`
- Prints a final summary line and “writes” a report

## 3. What’s wrong (at least 5 issues)
1. High-level pipeline depends on low-level concrete classes (hard-coded `new` everywhere).
2. Hard to test pipeline without running real checks.
3. Changing a component requires editing pipeline code.
4. No clear abstraction boundaries; responsibilities are mixed.
5. Configuration is embedded (paths, thresholds).

## 4. Your task
Checkpoint A: Run and capture output.
Checkpoint B: Introduce small abstractions for grader/checker/writer.
Checkpoint C: Inject dependencies into pipeline.
Checkpoint D: Keep output identical.

## 5. Constraints
- Preserve output and line order.
- Keep `Submission` fields unchanged.
- No external libraries.

## 6. Acceptance criteria
- Pipeline depends on abstractions, not concretes.
- Easy to substitute test doubles.

## 7. How to run
```bash
cd SOLID/Ex9/src
javac *.java
java Main
```

## 8. Sample output
```text
=== Evaluation Pipeline ===
PlagiarismScore=12
CodeScore=78
Report written: report-23BCS1007.txt
FINAL: PASS (total=90)
```

## 9. Hints (OOP-only)
- Define minimal interfaces with only what the pipeline needs.
- Pass dependencies via constructor.

## 10. Stretch goals
- Add a second grading strategy without editing pipeline logic.


It states that High-level modules (your business logic) should not depend on Low-level modules (your database, file system, or specific APIs). Both should depend on Abstractions (Interfaces).

Decoupling: If you want to change the ReportWriter to save to a Database instead of a .txt file, you don't have to touch a single line of code in EvaluationPipeline. You just create a DatabaseReportWriter and plug it in via Main.

Testability: In a real job, "Plagiarism Checking" might take 10 minutes and cost money (API calls). With DIP, you can pass a "Fake" checker to the pipeline during testing that returns 0 instantly.

Open/Closed Principle: You can add a SeniorGradingStrategy (the stretch goal) by creating a new class that implements ICodeGrader. The pipeline is "Closed" for modification but "Open" for new grading behaviors.

No More "Hard-Coded" Paths: By moving the Rubric and ReportWriter instantiation out of the pipeline, you can now configure them (like setting the bonus points) before the pipeline even starts.