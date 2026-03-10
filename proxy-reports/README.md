Proxy — Secure & Lazy-Load Reports (Refactoring)
------------------------------------------------
Narrative (Current Code)
A small CLI tool called CampusVault opens internal reports for different users.
Right now, ReportViewer talks directly to ReportFile and eagerly loads the report content every time.

Problems in the current design:
- No access control: any user can open any report.
- No lazy loading: expensive file loading happens immediately on each open.
- No caching: the same report may be loaded multiple times unnecessarily.
- Clients depend directly on the concrete implementation.

Your Task
1) Introduce a Report abstraction.
2) Keep the expensive file-reading logic inside a real subject (for example, RealReport).
3) Add a ReportProxy that:
   - checks whether the user is allowed to access the report
   - lazy-loads the real report only when needed
   - reuses the loaded real report for repeated views through the same proxy
4) Update ReportViewer / App so clients use the proxy instead of directly using the concrete file loader.

Acceptance Criteria
- Unauthorized users cannot view restricted reports.
- Real report loading happens only when access is granted.
- Real report content is loaded lazily (not during proxy construction).
- Repeated views of the same report through the same proxy should not reload the file every time.
- Output remains easy to verify from console logs.

Hints
- Define an interface: Report { void display(User user); }
- Let RealReport do the expensive load.
- Let ReportProxy hold metadata + a nullable RealReport reference.
- Add logs so it is obvious whether a report was really loaded.

Build & Run
  cd proxy-reports/src
  javac com/example/reports/*.java
  java com.example.reports.App

Repo intent
This is a refactoring assignment: the starter code works, but it does not use Proxy properly.
Students should refactor the design so access control + lazy loading happen via a proxy.


My Approach

1. What is the Question? (The Problem)
Currently, the "CampusVault" system is insecure and inefficient. The ReportFile class (the Real Subject) is doing too much, and the ReportViewer is talking directly to it.

The three main failures are:

No Access Control: A STUDENT can currently open an ADMIN report because ReportFile doesn't check credentials.

Eager/Redundant Loading: The expensive loadFromDisk() method (which has a simulated delay) runs every single time display() is called, even if the same user views the same report twice.

Tight Coupling: The ReportViewer specifically asks for a ReportFile object instead of an abstraction, making it impossible to swap in a Proxy without changing the viewer's code.

2. Pre-Implementation Workflow
Before we write the logic, we need to restructure the relationships between the classes:

Contract Definition: We must ensure both RealReport and ReportProxy implement the Report interface.

Decoupling the Viewer: Change ReportViewer to accept the Report interface. This allows us to pass a Proxy instead of the real file.

Encapsulation of "Heavy" Logic: Move the loadFromDisk() logic from the old ReportFile into the RealReport.

The Proxy State: The ReportProxy needs to hold the metadata (ID, Title, Classification) but not the actual report content initially. It should hold a null reference to a RealReport.

3. The Suitable Approach
We will implement a Smart Proxy that handles three distinct responsibilities:

The Gatekeeper (Protection): Before doing anything, the Proxy will use the AccessControl class to see if the User's role matches the report's classification. If not, it denies access immediately without loading the file.

The Lazy Initializer (Virtual): The RealReport object will not be created in the Proxy's constructor. It will only be instantiated inside the display() method after the access check passes.

The Cache (Performance): Once the RealReport is loaded, the Proxy will store it in a private field. If display() is called a second time on the same Proxy instance, it will reuse the existing RealReport instead of hitting the "disk" again.

4. What the Implementation will look like
Report.java: Our interface with void display(User user).

RealReport.java: Will contain the loadFromDisk() method and the actual printing of the report content.

ReportProxy.java:

Will have an if(accessControl.canAccess(...)) check.

Will have a if(realReport == null) { realReport = new RealReport(...); } block to handle lazy loading and caching.

ReportViewer.java: Will be updated to public void open(Report report, User user).

Does this approach make sense to you? If you are ready, say "proceed" and I will provide the refactored code for all classes.