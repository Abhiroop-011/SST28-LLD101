Exercise B — Immutable Classes (Incident Tickets)
------------------------------------------------
Narrative
A small CLI tool called **HelpLite** creates and manages support/incident tickets.
Today, `IncidentTicket` is **mutable**:
- multiple constructors
- public setters
- validation scattered across the codebase
- objects can be modified after being "created", causing audit/log inconsistencies

Refactor the design so `IncidentTicket` becomes **immutable** and is created using a **Builder**.

What you have (Starter)
- `IncidentTicket` has public setters + several constructors.
- `TicketService` creates a ticket, then mutates it later (bad).
- Validation is duplicated and scattered, making it easy to miss checks.
- `TryIt` demonstrates how the same object can change unexpectedly.

Tasks
1) Refactor `IncidentTicket` to an **immutable class**
   - private final fields
   - no setters
   - defensive copying for collections
   - safe getters (no internal state leakage)

2) Introduce `IncidentTicket.Builder`
   - Required: `id`, `reporterEmail`, `title`
   - Optional: `description`, `priority`, `tags`, `assigneeEmail`, `customerVisible`, `slaMinutes`, `source`
   - Builder should be fluent (`builder().id(...).title(...).build()`)

3) Centralize validation
   - Move ALL validation to `Builder.build()`
   - Use helpers in `Validation.java` (add more if needed)
   - Examples:
     - id: non-empty, length <= 20, only [A-Z0-9-] (you can reuse helper)
     - reporterEmail/assigneeEmail: must look like an email
     - title: non-empty, length <= 80
     - priority: one of LOW/MEDIUM/HIGH/CRITICAL
     - slaMinutes: if provided, must be between 5 and 7,200

4) Update `TicketService`
   - Stop mutating a ticket after creation
   - Any “updates” should create a **new** ticket instance (e.g., by Builder copy/from method)
   - Keep the API simple; you can add `toBuilder()` or `Builder.from(existing)`

Acceptance
- `IncidentTicket` has no public setters and fields are final.
- Tickets cannot be modified after creation (including tags list).
- Validation happens only in one place (`build()`).
- `TryIt` still works, but now demonstrates immutability (attempted mutations should not compile or have no effect).
- Code compiles and runs with the starter commands below.

Build/Run (Starter demo)
  cd immutable-tickets/src
  javac com/example/tickets/*.java TryIt.java
  java TryIt

Tip
After refactor, you can update `TryIt` to show:
- building a ticket
- “updating” by creating a new instance
- tags list is not mutable from outside


The "Before" State: A Leaky, Unpredictable Object
In the starter code, the IncidentTicket class was acting like a traditional "Java Bean" (a data container with getters and setters), which is terrible for a critical business object like a support ticket.

If you drew out its architecture, it was full of holes:

The Mutation Risk (Setters): Because it had public void setPriority(), any part of the codebase could accidentally (or maliciously) change a ticket's priority after it was created, leaving no audit trail.

The "Reference Leak" (Mutable Collections): The ticket held a List<String> tags. When getTags() was called, it handed over the exact memory reference to that internal list. A completely unrelated class could call ticket.getTags().add("HACKED"), and the ticket's internal data would change without any setter ever being called!

The "Telescoping Constructor" Anti-Pattern: It had three different constructors trying to handle different combinations of required and optional fields. This makes code hard to read and scale.

Scattered Validation: The rules (like "email must contain @") were written inside TicketService. This meant someone else could bypass the service, call new IncidentTicket(), and create a corrupted ticket with blank fields.

Step 1: Locking Down the Fields (Final & No Setters)
The Action: We added the final keyword to the class itself, made every field private final, and completely deleted every setX() method.

The Why: final on the class prevents someone from making a subclass (like HackedTicket) that overrides our safe methods. private final on the fields forces them to be assigned exactly once.

The Goal: Make it mathematically impossible for the ticket's primitive state (like its ID or Title) to change once the object is born.

Step 2: Plugging the Leaks (Defensive Copying)
The Action: Inside the constructor, we did this.tags = new ArrayList<>(builder.tags). Inside getTags(), we returned Collections.unmodifiableList(tags).

The Why: Java passes objects by reference. If we just assign this.tags = builder.tags, the Builder still holds the "remote control" to our list. By making a Defensive Copy new ArrayList<>(), we build a wall around our data. By returning an Unmodifiable View in the getter, we ensure anyone asking to see our tags gets a "read-only" window, not the remote control.

The Goal: Guarantee total isolation of internal state. No external code can mutate our collections.

Step 3: Taming Complexity (The Builder Pattern)
The Action: We made the IncidentTicket constructor private and created a static class Builder to handle construction.

The Why: We have 10 fields, many of which are optional. Passing 10 arguments into a constructor is a nightmare (new Ticket("id", null, null, true, 5...)). The Builder allows us to set exactly what we want, readably, step-by-step.

The Goal: Provide a clean, fluent API for creating complex objects without writing 20 different constructors.

Step 4: Centralizing Validation (The "Always Valid" Guarantee)
The Action: We moved all the regex, null checks, and length checks directly into the Builder.build() method.

The Why: Previously, validation was in the Service. By moving it to the exact moment of creation (build()), we establish a universal law: It is impossible for an invalid IncidentTicket to exist in memory. * The Goal: Fail fast. If the data is bad, the program crashes before the object is ever created, rather than failing 10 steps later in the database.

Step 5: Pure Functions (Updating the Service)
The Action: We changed TicketService so that when it "escalates" a ticket, it doesn't change the old ticket. Instead, it calls t.toBuilder().priority("CRITICAL").build() to return a brand new ticket.

The Why: This is the core principle of Functional Programming. Functions should not have "side effects" (mutating inputs). If you want a changed state, you produce a new copy with the changes applied.

The Goal: Make the system perfectly predictable and thread-safe. You can pass a ticket to 50 different threads safely because you know none of them can alter it.