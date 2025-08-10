package com.example.parkapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.parkapp.Complaint;
import com.example.parkapp.FirebaseComplaintManager;
import com.example.parkapp.UserStatsManager;
import com.example.parkapp.AuthManager;
import java.util.List;

public class ReportIssueFragment extends Fragment {
    
    private static final String ARG_PARK_NAME = "park_name";
    private static final String ARG_PARK_DEPARTMENT = "park_department";
    
    private String parkName;
    private String parkDepartment;
    private Park selectedPark;
    private List<Park> allParks;
    
    public static ReportIssueFragment newInstance(String parkName, String parkDepartment) {
        ReportIssueFragment fragment = new ReportIssueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARK_NAME, parkName);
        args.putString(ARG_PARK_DEPARTMENT, parkDepartment);
        fragment.setArguments(args);
        return fragment;
    }
    
    // Yeni constructor - park seçimi için
    public static ReportIssueFragment newInstance() {
        return new ReportIssueFragment();
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            parkName = getArguments().getString(ARG_PARK_NAME);
            parkDepartment = getArguments().getString(ARG_PARK_DEPARTMENT);
        }
        allParks = Park.getAllMalatyaParks();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_issue, container, false);
        
        Spinner parkSpinner = view.findViewById(R.id.park_spinner);
        Spinner issueTypeSpinner = view.findViewById(R.id.issue_type_spinner);
        EditText descriptionEdit = view.findViewById(R.id.description_edit);
        Button submitButton = view.findViewById(R.id.submit_button);
        
        // Park seçimi için adapter
        String[] parkNames = new String[allParks.size()];
        for (int i = 0; i < allParks.size(); i++) {
            parkNames[i] = allParks.get(i).getName();
        }
        
        android.widget.ArrayAdapter<String> parkAdapter = new android.widget.ArrayAdapter<>(
            getContext(), 
            android.R.layout.simple_spinner_item, 
            parkNames
        );
        parkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parkSpinner.setAdapter(parkAdapter);
        
        // Park seçimi değiştiğinde
        parkSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                selectedPark = allParks.get(position);
                parkName = selectedPark.getName();
                parkDepartment = selectedPark.getManager();
            }
            
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                // Hiçbir şey seçilmediğinde yapılacak işlem
            }
        });
        
        // Eğer park adı verilmişse, o parkı seç
        if (parkName != null) {
            for (int i = 0; i < allParks.size(); i++) {
                if (allParks.get(i).getName().equals(parkName)) {
                    parkSpinner.setSelection(i);
                    selectedPark = allParks.get(i);
                    break;
                }
            }
        }
        
        // Tüm sorun türlerini yükle
        String[] issueTypes = getAllIssueTypes();
        android.widget.ArrayAdapter<String> issueAdapter = new android.widget.ArrayAdapter<>(
            getContext(), 
            android.R.layout.simple_spinner_item, 
            issueTypes
        );
        issueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        issueTypeSpinner.setAdapter(issueAdapter);
        
        // Sorun türü değiştiğinde birimi güncelle
        issueTypeSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedIssueType = parent.getItemAtPosition(position).toString();
                String responsibleDepartment = getDepartmentForIssueType(selectedIssueType);
                parkDepartment = responsibleDepartment;
            }
            
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                // Hiçbir şey seçilmediğinde yapılacak işlem
            }
        });
        
        submitButton.setOnClickListener(v -> {
            if (selectedPark == null) {
                Toast.makeText(getContext(), "Lütfen bir park seçin", Toast.LENGTH_SHORT).show();
                return;
            }
            
            String issueType = issueTypeSpinner.getSelectedItem().toString();
            String description = descriptionEdit.getText().toString();
            
            if (description.trim().isEmpty()) {
                Toast.makeText(getContext(), "Lütfen şikayet açıklaması girin", Toast.LENGTH_SHORT).show();
                return;
            }
            
            // Sorun türüne göre birim belirle
            String responsibleDepartment = getDepartmentForIssueType(issueType);
            
            // Debug için Toast göster
            Toast.makeText(getContext(), "Şikayet: " + issueType + " → Birim: " + responsibleDepartment, Toast.LENGTH_LONG).show();
            
            // Şikayeti kaydet
            Complaint complaint = new Complaint(parkName, responsibleDepartment, issueType, description);
            
            // Debug bilgisi - Park adını kontrol et
            System.out.println("ReportIssueFragment: Şikayet kaydediliyor - Park: " + parkName + ", Tür: " + issueType + " → Birim: " + responsibleDepartment);
            
            FirebaseComplaintManager.getInstance().addComplaint(complaint, new FirebaseComplaintManager.OnComplaintAddedListener() {
                @Override
                public void onSuccess(String documentId) {
                    // Kullanıcı istatistiklerini güncelle
                    User currentUser = AuthManager.getInstance().getCurrentUser();
                    if (currentUser != null) {
                        UserStatsManager.getInstance().incrementComplaintsCount(currentUser.getUid());
                    }
                    
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "✅ Şikayetiniz başarıyla gönderildi\n📍 Park: " + parkName, Toast.LENGTH_LONG).show();
                        // Ana sayfaya dön
                        FragmentManager fragmentManager = getParentFragmentManager();
                        fragmentManager.popBackStack();
                    });
                }
                
                @Override
                public void onFailure(Exception e) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "❌ Şikayet gönderilirken hata oluştu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
        
        return view;
    }
    
    private String[] getAllIssueTypes() {
        return new String[]{
            "Kırık Bank", "Çöp Kutusu Arızası", "Çevre Kirliliği", "Gürültü Kirliliği",
            "Aydınlatma Arızası", "Elektrik Sorunu", "Sokak Lambası Kırık", "Enerji Kesintisi",
            "Çim Bakımı", "Ağaç Dikimi", "Çiçek Bakımı", "Sulama Sistemi",
            "Çöp Toplama", "Temizlik Eksikliği", "Atık Sorunu", "Hijyen Problemi",
            "Yol Arızası", "Kaldırım Sorunu", "Altyapı Problemi", "Su Birikintisi",
            "Spor Ekipmanı Arızası", "Spor Sahası Sorunu", "Fitness Aleti Kırık", "Spor Tesisi Problemi"
        };
    }
    
    private String getDepartmentForIssueType(String issueType) {
        switch (issueType) {
            case "Kırık Bank":
            case "Çöp Kutusu Arızası":
            case "Çevre Kirliliği":
            case "Gürültü Kirliliği":
                return "Çevre ve Şehircilik";
                
            case "Aydınlatma Arızası":
            case "Elektrik Sorunu":
            case "Sokak Lambası Kırık":
            case "Enerji Kesintisi":
                return "Aydınlatma ve Enerji";
                
            case "Çim Bakımı":
            case "Ağaç Dikimi":
            case "Çiçek Bakımı":
            case "Sulama Sistemi":
                return "Park ve Bahçeler";
                
            case "Çöp Toplama":
            case "Temizlik Eksikliği":
            case "Atık Sorunu":
            case "Hijyen Problemi":
                return "Temizlik İşleri";
                
            case "Yol Arızası":
            case "Kaldırım Sorunu":
            case "Altyapı Problemi":
            case "Su Birikintisi":
                return "Yol ve Altyapı";
                
            case "Spor Ekipmanı Arızası":
            case "Spor Sahası Sorunu":
            case "Fitness Aleti Kırık":
            case "Spor Tesisi Problemi":
                return "Spor ve Gençlik";
                
            default:
                return "Çevre ve Şehircilik";
        }
    }
} 