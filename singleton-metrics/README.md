Exercise A — Singleton Refactoring (Metrics Registry)
----------------------------------------------------
Narrative
A CLI tool called **PulseMeter** collects runtime metrics (counters) and exposes them globally
so any part of the app can increment counters like `REQUESTS_TOTAL`, `DB_ERRORS`, etc.

The current implementation is **not a real singleton**, **not thread-safe**, and is vulnerable to
**reflection** and **serialization** breaking the singleton guarantee.

Your job is to refactor it into a **proper, thread-safe, lazy-initialized Singleton**.

What you have (Starter)
- `MetricsRegistry` is *intended* to be global, but:
  - `getInstance()` can return different objects under concurrency.
  - The constructor is not private.
  - Reflection can create multiple instances.
  - Serialization/deserialization can produce a new instance.
- `MetricsLoader` incorrectly uses `new MetricsRegistry()`.

Tasks
1) Make `MetricsRegistry` a proper, **thread-safe singleton**
   - **Lazy initialization**
   - **Private constructor**
   - Thread safety: pick one approach (recommended: static holder or double-checked locking)

2) Block reflection-based multiple construction
   - If the constructor is called when an instance already exists, throw an exception
   - (Hint: use a static flag/instance check inside the constructor)

3) Preserve singleton on serialization
   - Implement `readResolve()` so deserialization returns the same singleton instance

4) Update `MetricsLoader` to use the singleton
   - No `new MetricsRegistry()` anywhere in code

Acceptance
- Single instance across threads within a JVM run.
- Reflection cannot construct a second instance.
- Deserialization returns the same instance.
- Loading metrics from `metrics.properties` works.
- Values are accessible via:
  - `increment(key)`
  - `getCount(key)`
  - `getAll()`

Build/Run (Starter)
  cd singleton-metrics/src
  javac com/example/metrics/*.java
  java com.example.metrics.App

Useful Demo Commands (after you fix it)
- Concurrency check:
  java com.example.metrics.ConcurrencyCheck
- Reflection attack check:
  java com.example.metrics.ReflectionAttack
- Serialization check:
  java com.example.metrics.SerializationCheck

Note
This starter is intentionally broken. Some of these checks will "succeed" in breaking the singleton
until you fix the implementation.


The Concept: "There Can Be Only One"
The intent of a Singleton is to ensure that a class has exactly one instance and provides a global access point to it.


Why are we concerned? Imagine a Connection Pool or a Logger. If every part of your app creates its own Logger, you might have 100 different files being written to at once, causing a crash or data loss.

What are we trying to achieve? We want to guarantee that every single thread in your application is talking to the exact same object in memory.



The Front Door was Open: The constructor was public. Any class (like MetricsLoader) could just type new MetricsRegistry() and create their own private copy.The Concurrency Trap: The getInstance() method used "lazy loading" to save memory. But it had no locking mechanism. If 80 threads asked for the instance at the exact same millisecond, they would all see INSTANCE == null and create 80 different objects.The Back Door (Reflection): Even if we made the constructor private, malicious code could use Java's Reflection API to say, "I know this is private, but make it accessible anyway," and force the creation of a new object.The Network Window (Serialization): In Java, when you deserialize a saved object from a file or a network, the JVM magically creates a brand new object in memory, bypassing constructors entirely.

Step 1: The Private ConstructorThe Action: We changed public MetricsRegistry() to private MetricsRegistry().The Why: This restricts object creation. By making it private, we physically prevent other classes from writing new MetricsRegistry().The Goal: Centralize control. If you want the registry, you must ask the class for it via getInstance().

Step 2: Fixing Thread Safety (Double-Checked Locking)The Action: We implemented Double-Checked Locking (DCL) inside getInstance() and added the volatile keyword to the INSTANCE variable.The Why: We could have just added synchronized to the method signature , but that forces every thread to wait in a single-file line every single time they want to log a metric, which destroys performance.DCL solves this by only locking the very first time the object is created.The volatile keyword is mandatory here. Without it, the CPU might reorder the creation instructions (allocate memory, assign reference, initialize). A thread might see a non-null reference to a half-constructed object and crash. volatile guarantees a strict "happens-before" relationship.The Goal: Achieve maximum performance (no waiting in line) while perfectly guaranteeing only one object is created, even under massive concurrency.

Step 3: Defeating the Reflection Attack
The Action: We added a guard clause inside the private constructor: if (INSTANCE != null) throw new IllegalStateException();


The Why: Reflection can bypass private modifiers. We can't stop Reflection from calling the constructor, but we can make the constructor self-destruct.

The Goal: Guarantee that even if an attacker hacks the access modifiers, the JVM will throw a fatal error rather than allowing a second instance to exist.

Step 4: Surviving Serialization
The Action: We added the protected Object readResolve() method to the class.


The Why: Regular singletons require readResolve() to be safe during deserialization. When the JVM is deserializing byte streams back into an object, it secretly looks to see if the class has a readResolve() method. If it does, the JVM throws away the new object it was about to create and uses whatever readResolve() returns instead.

The Goal: Force the deserialization process to return our existing INSTANCE, ensuring network and disk operations don't secretly duplicate our singleton.




