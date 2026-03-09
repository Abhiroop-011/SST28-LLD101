package com.example.reports;

/**
 * TODO (student):
 * Implement Proxy responsibilities here:
 * - access check
 * - lazy loading
 * - caching of RealReport within the same proxy
 */

public class ReportProxy implements Report {
    private final String reportId;
    private final String title;
    private final String classification;
    private final AccessControl accessControl = new AccessControl();
    
    private RealReport realReport;

    public ReportProxy(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
    }

    @Override
    public void display(User user) {
        if (!accessControl.canAccess(user, classification)) {
            System.out.println("ACCESS DENIED: User " + user.getName() + " [" + user.getRole() + "] cannot access " + classification + " reports.");
            return;
        }

        if (realReport == null) {
            System.out.println("[proxy] User authorized. Initializing real report...");
            realReport = new RealReport(reportId, title, classification);
        } else {
            System.out.println("[proxy] Fetching from cache...");
        }

        realReport.display(user);
    }
}