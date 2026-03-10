Flyweight — Deduplicate Map Marker Styles (Refactoring)
------------------------------------------------------
Narrative (Current Code)
A CLI tool called **GeoDash** renders a large list of map markers (pins).
Right now, every `MapMarker` stores its own style fields (shape, color, size, filled).
When rendering thousands of markers, we end up creating thousands of duplicate style objects → memory blow-up.

Your Task
1) Extract an immutable `MarkerStyle` (shape, color, size, filled) as **intrinsic state**.
2) Implement `MarkerStyleFactory` that caches and returns shared `MarkerStyle` instances by key.
3) Modify `MapMarker` to hold:
   - `MarkerStyle` (intrinsic)
   - marker-specific fields (extrinsic): `lat`, `lng`, `label`
4) Update `MapDataSource` (marker creation pipeline) to obtain styles via the factory
   (no `new MarkerStyle(...)` during marker creation).

Acceptance Criteria
- Same rendering “cost” as before (same number of markers rendered, same output format).
- Identical style configurations reuse the same `MarkerStyle` instance
  (see `QuickCheck` — it should report a small number of unique styles).
- `MarkerStyle` is immutable (all fields final, no setters).
- `MapMarker` stores only extrinsic state plus a reference to shared `MarkerStyle`.

Hints
- Use a `Map<String, MarkerStyle>` cache in the factory.
- Key suggestion: `"PIN|RED|12|F"` (shape|color|size|filledFlag)

Build & Run
  cd flyweight-markers/src
  javac com/example/map/*.java
  java com.example.map.App

Repo intent
This is a **refactoring assignment**: the starter code is intentionally wasteful.
Students should refactor to Flyweight without changing the external behavior.


my appproach:

1. What did we do? (The Mechanism)
We split the MapMarker data into two distinct parts:

Intrinsic State (Shared): This is the "Style" (Shape, Color, Size). It is the same for thousands of markers. We moved this into the MarkerStyle class.

Extrinsic State (Unique): This is the "Context" (Latitude, Longitude, Label). This is unique to every single marker and cannot be shared.

By using a MarkerStyleFactory, we ensured that if you ask for a "RED CIRCLE SIZE 10" and it already exists in our HashMap, the factory gives you a reference to the existing object instead of creating a new one.

2. Why did we do it? (The Motivation)
The primary goal is Memory Optimization.

Before: 30,000 markers = 30,000 Style Objects. If each style object takes 100 bytes, that’s 3MB of RAM just for styles.

After: 30,000 markers = ~96 Style Objects (the total unique combinations). 96 styles take only 9.6KB.

Performance: Creating objects is "expensive" for the CPU. By reusing objects, we reduce the work the Garbage Collector (GC) has to do, making the app smoother.

3. Why must it be Immutable? (The "Secret Sauce")
This is the most important part of the pattern. Flyweights must be immutable (read-only) because they are shared.

Imagine if the Flyweight was mutable (had setters):

Marker A and Marker B both share the same "RED PIN" style object.

You decide you want Marker A to be "BLUE".

You call markerA.getStyle().setColor("BLUE").

The Disaster: Because Marker B is looking at the exact same memory address, Marker B suddenly turns BLUE as well, even though you didn't touch it!

By making MarkerStyle immutable (using final fields and no setters), we guarantee that nobody can accidentally change a shared style. If a marker needs a different style, it must stop pointing at the old one and ask the Factory for a different shared instance.

Summary of our Implementation:
MarkerStyle: Became the Flyweight. We made it final and removed setters to ensure safety.

MarkerStyleFactory: Became the Manager. It uses a Map to act as the "Library" of styles.

MapMarker: Became the Context. it stores the unique coordinates and a "pointer" to a shared style.

MapDataSource: Was updated to "check out" styles from the library instead of buying new ones.