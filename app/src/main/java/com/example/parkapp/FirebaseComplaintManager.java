package com.example.parkapp;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.WriteBatch;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseComplaintManager {
    private static FirebaseComplaintManager instance;
    private FirebaseFirestore db;
    
    private FirebaseComplaintManager() {
        db = FirebaseFirestore.getInstance();
    }
    
    public static FirebaseComplaintManager getInstance() {
        if (instance == null) {
            instance = new FirebaseComplaintManager();
        }
        return instance;
    }
    
    public void addComplaint(Complaint complaint, OnComplaintAddedListener listener) {
        Map<String, Object> complaintData = new HashMap<>();
        complaintData.put("parkName", complaint.getParkName());
        complaintData.put("department", complaint.getDepartment());
        complaintData.put("issueType", complaint.getIssueType());
        complaintData.put("description", complaint.getDescription());
        complaintData.put("status", complaint.getStatus());
        complaintData.put("reportDate", complaint.getReportDate());
        complaintData.put("resolvedDate", complaint.getResolvedDate());
        
        db.collection("complaints")
            .add(complaintData)
            .addOnSuccessListener(documentReference -> {
                if (listener != null) {
                    listener.onSuccess(documentReference.getId());
                }
            })
            .addOnFailureListener(e -> {
                if (listener != null) {
                    listener.onFailure(e);
                }
            });
    }
    
    public void getAllComplaints(OnComplaintsLoadedListener listener) {
        db.collection("complaints")
            .orderBy("reportDate")
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                List<Complaint> complaints = new ArrayList<>();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Complaint complaint = documentToComplaint(document);
                    complaints.add(complaint);
                }
                if (listener != null) {
                    listener.onSuccess(complaints);
                }
            })
            .addOnFailureListener(e -> {
                if (listener != null) {
                    listener.onFailure(e);
                }
            });
    }
    
    public void getComplaintsByDepartment(String department, OnComplaintsLoadedListener listener) {
        // Debug için log
        System.out.println("FirebaseComplaintManager: Departman için şikayet aranıyor: " + department);
        
        db.collection("complaints")
            .whereEqualTo("department", department)
            .orderBy("reportDate")
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                List<Complaint> complaints = new ArrayList<>();
                System.out.println("FirebaseComplaintManager: Bulunan şikayet sayısı: " + queryDocumentSnapshots.size());
                
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Complaint complaint = documentToComplaint(document);
                    complaints.add(complaint);
                    System.out.println("FirebaseComplaintManager: Şikayet - " + complaint.getIssueType() + " → Departman: " + complaint.getDepartment());
                }
                
                if (listener != null) {
                    listener.onSuccess(complaints);
                }
            })
            .addOnFailureListener(e -> {
                System.out.println("FirebaseComplaintManager: Hata oluştu: " + e.getMessage());
                if (listener != null) {
                    listener.onFailure(e);
                }
            });
    }
    
    public void updateComplaintStatus(String id, String status, OnComplaintUpdatedListener listener) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("status", status);
        if (status.equals("Çözüldü")) {
            updates.put("resolvedDate", new Date());
        }
        
        db.collection("complaints").document(id)
            .update(updates)
            .addOnSuccessListener(aVoid -> {
                if (listener != null) {
                    listener.onSuccess();
                }
            })
            .addOnFailureListener(e -> {
                if (listener != null) {
                    listener.onFailure(e);
                }
            });
    }
    
    private Complaint documentToComplaint(QueryDocumentSnapshot document) {
        String id = document.getId();
        String parkName = document.getString("parkName");
        String department = document.getString("department");
        String issueType = document.getString("issueType");
        String description = document.getString("description");
        String status = document.getString("status");
        Date reportDate = document.getDate("reportDate");
        Date resolvedDate = document.getDate("resolvedDate");
        
        Complaint complaint = new Complaint(parkName, department, issueType, description);
        complaint.setId(id);
        complaint.setStatus(status);
        complaint.setReportDate(reportDate);
        complaint.setResolvedDate(resolvedDate);
        
        return complaint;
    }
    
    // Listener interfaces
    public interface OnComplaintAddedListener {
        void onSuccess(String documentId);
        void onFailure(Exception e);
    }
    
    public interface OnComplaintsLoadedListener {
        void onSuccess(List<Complaint> complaints);
        void onFailure(Exception e);
    }
    
    public interface OnComplaintUpdatedListener {
        void onSuccess();
        void onFailure(Exception e);
    }
} 