package com.example.parkapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.parkapp.Park;
import com.example.parkapp.ReportIssueFragment;
import com.example.parkapp.User;
import com.example.parkapp.AuthManager;
import com.example.parkapp.UserStatsManager;

public class ParkDetailsFragment extends Fragment {
    
    private static final String ARG_PARK_NAME = "park_name";
    private static final String ARG_PARK_DESCRIPTION = "park_description";
    private static final String ARG_PARK_DEPARTMENT = "park_department";
    
    private String parkName;
    private String parkDescription;
    private String parkDepartment;
    
    public static ParkDetailsFragment newInstance(Park park) {
        ParkDetailsFragment fragment = new ParkDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARK_NAME, park.getName());
        args.putString(ARG_PARK_DESCRIPTION, park.getDescription());
        args.putString(ARG_PARK_DEPARTMENT, park.getManager());
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            parkName = getArguments().getString(ARG_PARK_NAME);
            parkDescription = getArguments().getString(ARG_PARK_DESCRIPTION);
            parkDepartment = getArguments().getString(ARG_PARK_DEPARTMENT);
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_park_details, container, false);
        
        // Park ziyaret sayısını artır
        User currentUser = AuthManager.getInstance().getCurrentUser();
        if (currentUser != null && !currentUser.isAdmin()) {
            UserStatsManager.getInstance().incrementParksVisited(currentUser.getUid());
        }
        
        TextView nameText = view.findViewById(R.id.park_name);
        TextView descriptionText = view.findViewById(R.id.park_description);
        TextView departmentText = view.findViewById(R.id.park_department);
        Button reportButton = view.findViewById(R.id.report_issue_button);
        
        nameText.setText(parkName);
        descriptionText.setText(parkDescription);
        
        // Yetkili kullanıcılar için farklı mesaj göster
        if (currentUser != null && currentUser.isAdmin()) {
            departmentText.setText("Bu parktan gelen şikayetler " + currentUser.getDepartment() + " biriminde görüntülenir");
        } else {
            departmentText.setText("Şikayet türüne göre ilgili birim yönlendirilir");
        }
        
        // Yetkili kullanıcılar için şikayet bildirme butonunu gizle
        if (currentUser != null && currentUser.isAdmin()) {
            reportButton.setVisibility(View.GONE);
        } else {
            reportButton.setOnClickListener(v -> {
                ReportIssueFragment fragment = ReportIssueFragment.newInstance(parkName, parkDepartment);
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            });
        }
        
        return view;
    }
} 