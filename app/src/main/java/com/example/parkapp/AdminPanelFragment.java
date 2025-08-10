package com.example.parkapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.example.parkapp.Complaint;
import com.example.parkapp.FirebaseComplaintManager;
import com.example.parkapp.AuthManager;
import com.example.parkapp.User;

public class AdminPanelFragment extends Fragment {
    
    private ListView complaintsListView;
    private ArrayAdapter<String> adapter;
    private List<String> complaintStrings;
    private List<Complaint> complaints;
    private Spinner departmentSpinner;
    private Button filterButton;
    private Button resolveButton;
    private String currentUserDepartment; // Kullanıcının departmanını sakla
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_panel, container, false);
        
        complaintsListView = view.findViewById(R.id.admin_complaints_list);
        departmentSpinner = view.findViewById(R.id.department_spinner);
        filterButton = view.findViewById(R.id.filter_button);
        resolveButton = view.findViewById(R.id.resolve_button);
        
        complaintStrings = new ArrayList<>();
        complaints = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, complaintStrings);
        complaintsListView.setAdapter(adapter);
        
        // Yetkili kullanıcının departmanını al
        User currentUser = AuthManager.getInstance().getCurrentUser();
        if (currentUser != null && currentUser.isAdmin()) {
            String userDepartment = currentUser.getDepartment();
            currentUserDepartment = userDepartment; // Departmanı sakla
            
            if (userDepartment != null) {
                // Departman spinner'ını kullanıcının departmanına ayarla
                for (int i = 0; i < departmentSpinner.getCount(); i++) {
                    if (departmentSpinner.getItemAtPosition(i).toString().equals(userDepartment)) {
                        departmentSpinner.setSelection(i);
                        break;
                    }
                }
                
                // Önce tüm şikayetleri yükle, sonra filtrele
                loadAllComplaintsForAdmin(userDepartment);
                
                // Departman spinner'ını ve filtre butonunu gizle (sadece kendi departmanını görebilsin)
                departmentSpinner.setVisibility(View.GONE);
                filterButton.setVisibility(View.GONE);
                
                // Başlığı güncelle
                if (getActivity() != null) {
                    getActivity().setTitle(userDepartment + " - Yetkili Paneli");
                }
            } else {
                // Departman bilgisi yoksa tüm şikayetleri yükle
                loadAllComplaints();
            }
        }
        
        filterButton.setOnClickListener(v -> {
            String selectedDepartment = departmentSpinner.getSelectedItem().toString();
            if (selectedDepartment.equals("Tümü")) {
                loadAllComplaints();
            } else {
                loadAllComplaintsForAdmin(selectedDepartment);
            }
        });
        
        resolveButton.setOnClickListener(v -> {
            int position = complaintsListView.getCheckedItemPosition();
            if (position >= 0 && position < complaints.size()) {
                Complaint complaint = complaints.get(position);
                
                // Şikayet zaten çözülmüş mü kontrol et
                if ("Çözüldü".equals(complaint.getStatus())) {
                    Toast.makeText(getContext(), "Bu şikayet zaten çözülmüş!", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                // Kullanıcıya onay sor
                new android.app.AlertDialog.Builder(getContext())
                    .setTitle("Şikayet Çözüldü")
                    .setMessage("Bu şikayeti çözüldü olarak işaretlemek istediğinizden emin misiniz?")
                    .setPositiveButton("Evet", (dialog, which) -> {
                        // Şikayeti çözüldü olarak işaretle
                        FirebaseComplaintManager.getInstance().updateComplaintStatus(complaint.getId(), "Çözüldü", new FirebaseComplaintManager.OnComplaintUpdatedListener() {
                            @Override
                            public void onSuccess() {
                                getActivity().runOnUiThread(() -> {
                                    Toast.makeText(getContext(), "✅ Şikayet başarıyla çözüldü olarak işaretlendi", Toast.LENGTH_SHORT).show();
                                    
                                    // Sadece kendi departmanının şikayetlerini yeniden yükle
                                    if (currentUserDepartment != null) {
                                        loadAllComplaintsForAdmin(currentUserDepartment);
                                    } else {
                                        loadAllComplaints();
                                    }
                                });
                            }
                            
                            @Override
                            public void onFailure(Exception e) {
                                getActivity().runOnUiThread(() -> {
                                    Toast.makeText(getContext(), "❌ Güncelleme sırasında hata oluştu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                            }
                        });
                    })
                    .setNegativeButton("İptal", null)
                    .show();
            } else {
                Toast.makeText(getContext(), "⚠️ Lütfen bir şikayet seçin", Toast.LENGTH_SHORT).show();
            }
        });
        
        // Admin değilse tüm şikayetleri yükle (normal kullanıcı için)
        if (currentUser == null || !currentUser.isAdmin()) {
            loadAllComplaints();
        }
        
        return view;
    }
    
    private void loadAllComplaints() {
        FirebaseComplaintManager.getInstance().getAllComplaints(new FirebaseComplaintManager.OnComplaintsLoadedListener() {
            @Override
            public void onSuccess(List<Complaint> complaintsList) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        complaints = complaintsList;
                        updateComplaintList();
                    });
                }
            }
            
            @Override
            public void onFailure(Exception e) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Şikayetler yüklenirken hata oluştu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }
    


    private void loadAllComplaintsForAdmin(String department) {
        FirebaseComplaintManager.getInstance().getAllComplaints(new FirebaseComplaintManager.OnComplaintsLoadedListener() {
            @Override
            public void onSuccess(List<Complaint> allComplaints) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        // Şimdi departmana göre filtrele
                        List<Complaint> filteredComplaints = new ArrayList<>();
                        for (Complaint complaint : allComplaints) {
                            if (complaint.getDepartment() != null && complaint.getDepartment().equals(department)) {
                                filteredComplaints.add(complaint);
                            }
                        }
                        
                        complaints = filteredComplaints;
                        updateComplaintList();
                        Toast.makeText(getContext(), department + " için " + filteredComplaints.size() + " şikayet bulundu", Toast.LENGTH_SHORT).show();
                    });
                }
            }
            
            @Override
            public void onFailure(Exception e) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Şikayetler yüklenirken hata oluştu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }
    
    private void updateComplaintList() {
        complaintStrings.clear();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        
        for (Complaint complaint : complaints) {
            String statusIcon = "Çözüldü".equals(complaint.getStatus()) ? "✅" : "⏳";
            String complaintText = String.format("%s %s\n📍 %s\n🔧 %s\n📝 %s\n📅 %s\n🏢 %s",
                    statusIcon,
                    complaint.getStatus(),
                    complaint.getParkName(),
                    complaint.getIssueType(),
                    complaint.getDescription(),
                    sdf.format(complaint.getReportDate()),
                    complaint.getDepartment());
            complaintStrings.add(complaintText);
        }
        
        adapter.notifyDataSetChanged();
        
        // Şikayet sayısını göster
        if (getActivity() != null) {
            int totalComplaints = complaints.size();
            int resolvedComplaints = 0;
            int pendingComplaints = 0;
            
            for (Complaint complaint : complaints) {
                if ("Çözüldü".equals(complaint.getStatus())) {
                    resolvedComplaints++;
                } else {
                    pendingComplaints++;
                }
            }
            
            String summaryText = String.format("📊 Toplam: %d | ⏳ Bekleyen: %d | ✅ Çözülen: %d", 
                    totalComplaints, pendingComplaints, resolvedComplaints);
            
            // Başlığı güncelle
            if (currentUserDepartment != null) {
                getActivity().setTitle(currentUserDepartment + " - " + summaryText);
            }
        }
    }
} 