# Ex5 — LSP: File Exporter Hierarchy

## 1. Context
A reporting tool exports student performance data to multiple formats.

## 2. Current behavior
- `Exporter` has `export(ExportRequest)` that returns `ExportResult`
- `PdfExporter` throws for large content (tightens preconditions)
- `CsvExporter` silently changes meaning by dropping newlines and commas poorly
- `JsonExporter` returns empty on null (inconsistent contract)
- `Main` demonstrates current behavior

## 3. What’s wrong (at least 5 issues)
1. Subclasses violate expectations of the base `Exporter` contract.
2. `PdfExporter` throws for valid requests (from base perspective).
3. `CsvExporter` changes semantics of fields (data corruption risk).
4. `JsonExporter` handles null differently than others.
5. Callers cannot rely on substitutability; they need format-specific workarounds.
6. Contract is not documented; behavior surprises are runtime.

## 4. Your task
Checkpoint A: Run and capture output.
Checkpoint B: Define a clear base contract (preconditions/postconditions).
Checkpoint C: Refactor hierarchy so all exporters honor the same contract.
Checkpoint D: Keep observable outputs identical for current inputs.

## 5. Constraints
- Keep `Main` outputs unchanged for the given samples.
- No external libraries.
- Default package.

## 6. Acceptance criteria
- Base contract is explicit and enforced consistently.
- No exporter tightens preconditions compared to base contract.
- Caller should not need `instanceof` to be safe.

## 7. How to run
```bash
cd SOLID/Ex5/src
javac *.java
java Main
```

## 8. Sample output
```text
=== Export Demo ===
PDF: ERROR: PDF cannot handle content > 20 chars
CSV: OK bytes=42
JSON: OK bytes=61
```

## 9. Hints (OOP-only)
- If a subtype cannot support the base contract, reconsider inheritance.
- Prefer composition: separate “format encoding” from “delivery constraints”.

## 10. Stretch goals
- Add a new exporter without changing existing exporters.




My Implementation - The previous design is aviolating of Liskov Substitution Principle (LSP). here is why:

JsonExporter (Inconsistent Contract): If the client passes a null request, this exporter arbitrarily decides to return an empty byte array. It made up its own rule for null handling, which surprises the client.

CsvExporter (Data Corruption): To avoid breaking the CSV format, this class silently deletes newlines and commas from the data. It changes the meaning of the data behind the client's back, which is incredibly dangerous.

PdfExporter (Tightening Preconditions): This exporter throws an IllegalArgumentException if the text is over 20 characters long. The base class never warned the client that length was a constraint.

Because of these inconsistencies, the client cannot trust the base Exporter type. To be safe, the client would have to use if-statements to check which specific format it's dealing with, which defeats the entire purpose of polymorphism."


Step 1: In the abstract Exporter class, I made export() a final method. This method now enforces a universal rule: if the request is null, it throws a standard, predictable exception. Now, subclasses like JsonExporter don't have to guess how to handle nulls. The base class guarantees a uniform response, and then delegates the rest of the work to a protected doExport() method.

Step 2: Isolating Validation. For the PDF size limit, I used the Strategy Pattern. I created an ExportValidator interface and a PdfValidator class. Inside the PdfExporter, I instantiate this validator. When doExport() is called, the PdfExporter simply delegates the size check to the validator before doing its work.

Step 3: Fixing the Formats. Finally, I fixed the data corruption in the other subclasses inside their doExport() methods. CsvExporter now uses proper RFC standard CSV escaping—wrapping text in quotes—instead of silently deleting characters. JsonExporter properly escapes newlines.

The client now gets predictable, reliable behavior across all formats."