package com.example.tickets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class IncidentTicket { 

    private final String id;
    private final String reporterEmail;
    private final String title;
    private final String description;
    private final String priority;
    private final List<String> tags; 
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final Integer slaMinutes;
    private final String source;

    private IncidentTicket(Builder builder) {
        this.id = builder.id;
        this.reporterEmail = builder.reporterEmail;
        this.title = builder.title;
        this.description = builder.description;
        this.priority = builder.priority;
        this.assigneeEmail = builder.assigneeEmail;
        this.customerVisible = builder.customerVisible;
        this.slaMinutes = builder.slaMinutes;
        this.source = builder.source;
        this.tags = new ArrayList<>(builder.tags); 
    }

    public String getId() { return id; }
    public String getReporterEmail() { return reporterEmail; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public String getAssigneeEmail() { return assigneeEmail; }
    public boolean isCustomerVisible() { return customerVisible; }
    public Integer getSlaMinutes() { return slaMinutes; }
    public String getSource() { return source; }

    public List<String> getTags() { 
        return Collections.unmodifiableList(tags); 
    }

    public Builder toBuilder() {
        Builder b = new Builder()
                .id(this.id)
                .reporterEmail(this.reporterEmail)
                .title(this.title)
                .description(this.description)
                .priority(this.priority)
                .assigneeEmail(this.assigneeEmail)
                .customerVisible(this.customerVisible)
                .slaMinutes(this.slaMinutes)
                .source(this.source);
        b.tags.addAll(this.tags);
        return b;
    }

    public static class Builder {
        private String id;
        private String reporterEmail;
        private String title;
        private String description;
        private String priority;
        private List<String> tags = new ArrayList<>();
        private String assigneeEmail;
        private boolean customerVisible;
        private Integer slaMinutes;
        private String source;

        public Builder id(String id) { this.id = id; return this; }
        public Builder reporterEmail(String reporterEmail) { this.reporterEmail = reporterEmail; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder priority(String priority) { this.priority = priority; return this; }
        public Builder assigneeEmail(String assigneeEmail) { this.assigneeEmail = assigneeEmail; return this; }
        public Builder customerVisible(boolean customerVisible) { this.customerVisible = customerVisible; return this; }
        public Builder slaMinutes(Integer slaMinutes) { this.slaMinutes = slaMinutes; return this; }
        public Builder source(String source) { this.source = source; return this; }
        
        public Builder addTag(String tag) { 
            this.tags.add(tag); 
            return this; 
        }

        public IncidentTicket build() {
            Validation.requireTicketId(id);
            Validation.requireEmail(reporterEmail, "reporterEmail");
            
            Validation.requireNonBlank(title, "title");
            Validation.requireMaxLen(title, 80, "title");
            
            if (assigneeEmail != null) Validation.requireEmail(assigneeEmail, "assigneeEmail");
            if (priority != null) Validation.requireOneOf(priority, "priority", "LOW", "MEDIUM", "HIGH", "CRITICAL");
            if (slaMinutes != null) Validation.requireRange(slaMinutes, 5, 7200, "slaMinutes");

            return new IncidentTicket(this);
        }
    }

    @Override
    public String toString() {
        return "IncidentTicket{id='" + id + "', priority='" + priority + "', tags=" + tags + "}";
    }
}