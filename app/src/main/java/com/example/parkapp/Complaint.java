package com.example.parkapp;

import java.util.Date;

public class Complaint {
    private String id;
    private String parkName;
    private String department;
    private String issueType;
    private String description;
    private String status;
    private Date reportDate;
    private Date resolvedDate;
    
    public Complaint(String parkName, String department, String issueType, String description) {
        this.id = generateId();
        this.parkName = parkName;
        this.department = department;
        this.issueType = issueType;
        this.description = description;
        this.status = "Beklemede";
        this.reportDate = new Date();
    }
    
    private String generateId() {
        return "COMP_" + System.currentTimeMillis();
    }
    
    // Getters
    public String getId() { return id; }
    public String getParkName() { return parkName; }
    public String getDepartment() { return department; }
    public String getIssueType() { return issueType; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public Date getReportDate() { return reportDate; }
    public Date getResolvedDate() { return resolvedDate; }
    
    // Setters
    public void setId(String id) { this.id = id; }
    public void setStatus(String status) { this.status = status; }
    public void setReportDate(Date reportDate) { this.reportDate = reportDate; }
    public void setResolvedDate(Date resolvedDate) { this.resolvedDate = resolvedDate; }
} 