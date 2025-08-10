package com.example.parkapp;

import java.util.ArrayList;
import java.util.List;
import com.example.parkapp.Complaint;

public class ComplaintManager {
    private static ComplaintManager instance;
    private List<Complaint> complaints;
    
    private ComplaintManager() {
        complaints = new ArrayList<>();
    }
    
    public static ComplaintManager getInstance() {
        if (instance == null) {
            instance = new ComplaintManager();
        }
        return instance;
    }
    
    public void addComplaint(Complaint complaint) {
        complaints.add(complaint);
    }
    
    public List<Complaint> getAllComplaints() {
        return new ArrayList<>(complaints);
    }
    
    public List<Complaint> getComplaintsByDepartment(String department) {
        List<Complaint> departmentComplaints = new ArrayList<>();
        for (Complaint complaint : complaints) {
            if (complaint.getDepartment().equals(department)) {
                departmentComplaints.add(complaint);
            }
        }
        return departmentComplaints;
    }
    
    public Complaint getComplaintById(String id) {
        for (Complaint complaint : complaints) {
            if (complaint.getId().equals(id)) {
                return complaint;
            }
        }
        return null;
    }
    
    public void updateComplaintStatus(String id, String status) {
        Complaint complaint = getComplaintById(id);
        if (complaint != null) {
            complaint.setStatus(status);
            if (status.equals("Çözüldü")) {
                complaint.setResolvedDate(new java.util.Date());
            }
        }
    }
} 