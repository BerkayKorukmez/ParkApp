package com.example.parkapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.example.parkapp.Complaint;
import com.example.parkapp.FirebaseComplaintManager;
import com.example.parkapp.AuthManager;
import com.example.parkapp.User;
import com.example.parkapp.AdminPanelFragment;

public class ComplaintsFragment extends Fragment {
    
    private RecyclerView complaintsRecyclerView;
    private ComplaintAdapter complaintAdapter;
    private List<Complaint> complaints;
    private FloatingActionButton fabAddComplaint;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaints_modern, container, false);
        
        complaintsRecyclerView = view.findViewById(R.id.complaintsRecyclerView);
        fabAddComplaint = view.findViewById(R.id.fabAddComplaint);
        
        complaintsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        complaints = new ArrayList<>();
        complaintAdapter = new ComplaintAdapter(complaints);
        complaintsRecyclerView.setAdapter(complaintAdapter);
        
        // FAB click listener
        fabAddComplaint.setOnClickListener(v -> {
            ReportIssueFragment reportFragment = ReportIssueFragment.newInstance();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, reportFragment)
                    .addToBackStack(null)
                    .commit();
        });
        
        // Kullanıcı tipini kontrol et
        User currentUser = AuthManager.getInstance().getCurrentUser();
        if (currentUser != null && currentUser.isAdmin()) {
            // Admin ise admin paneline yönlendir
            AdminPanelFragment adminFragment = new AdminPanelFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, adminFragment)
                    .commit();
        } else {
            // Normal kullanıcı ise şikayetlerini yükle
            loadComplaints();
        }
        
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        loadComplaints();
    }
    
    private void loadComplaints() {
        complaints.clear();
        FirebaseComplaintManager.getInstance().getAllComplaints(new FirebaseComplaintManager.OnComplaintsLoadedListener() {
            @Override
            public void onSuccess(List<Complaint> loadedComplaints) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        complaints.clear();
                        complaints.addAll(loadedComplaints);
                        complaintAdapter.notifyDataSetChanged();
                    });
                }
            }
            
            @Override
            public void onFailure(Exception e) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        // Hata durumunda kullanıcıya bilgi ver
                    });
                }
            }
        });
    }
} 