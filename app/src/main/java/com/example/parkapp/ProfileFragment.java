package com.example.parkapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.cardview.widget.CardView;
import com.example.parkapp.AuthManager;
import com.example.parkapp.User;
import com.example.parkapp.AdminPanelFragment;
import com.example.parkapp.UserStatsManager;

public class ProfileFragment extends Fragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_modern, container, false);
        
        TextView userName = view.findViewById(R.id.userName);
        TextView userEmail = view.findViewById(R.id.userEmail);
        TextView userRole = view.findViewById(R.id.userRole);
        TextView complaintsCount = view.findViewById(R.id.complaintsCount);
        TextView complaintsLabel = view.findViewById(R.id.complaintsLabel);
        TextView parksVisited = view.findViewById(R.id.parksVisited);
        TextView parksLabel = view.findViewById(R.id.parksLabel);
        CardView adminPanelCard = view.findViewById(R.id.adminPanelCard);
        CardView helpCard = view.findViewById(R.id.helpCard);
        CardView logoutCard = view.findViewById(R.id.logoutCard);
        
        // Kullanıcı bilgilerini göster
        User currentUser = AuthManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            userName.setText(currentUser.getName());
            userEmail.setText(currentUser.getEmail());
            
            // Kullanıcı istatistiklerini yükle
            UserStatsManager.getInstance().loadUserStats(currentUser.getUid(), new UserStatsManager.OnStatsLoadedListener() {
                @Override
                public void onSuccess(User updatedUser) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            updateUI(updatedUser, complaintsCount, complaintsLabel, parksVisited, parksLabel, userRole, adminPanelCard);
                        });
                    }
                }
                
                @Override
                public void onFailure(Exception e) {
                    // Hata durumunda varsayılan değerleri göster
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            updateUI(currentUser, complaintsCount, complaintsLabel, parksVisited, parksLabel, userRole, adminPanelCard);
                        });
                    }
                }
            });
        }
        
        // Yardım butonuna tıklandığında
        helpCard.setOnClickListener(v -> {
            HelpFragment helpFragment = new HelpFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, helpFragment)
                    .addToBackStack(null)
                    .commit();
        });
        
        logoutCard.setOnClickListener(v -> {
            AuthManager.getInstance().logout();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
        
        return view;
    }
    
    private void updateUI(User user, TextView complaintsCount, TextView complaintsLabel, 
                         TextView parksVisited, TextView parksLabel, TextView userRole, CardView adminPanelCard) {
        
        if (user.isAdmin()) {
            userRole.setText("Yetkili");
            adminPanelCard.setVisibility(View.VISIBLE);
            
            // Admin için farklı istatistikler
            complaintsLabel.setText("Toplam Şikayet");
            parksLabel.setText("Toplam Park");
            complaintsCount.setText(String.valueOf(user.getComplaintsCount()));
            parksVisited.setText(String.valueOf(user.getParksVisited()));
            
            adminPanelCard.setOnClickListener(v -> {
                AdminPanelFragment fragment = new AdminPanelFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            });
        } else {
            userRole.setText("Kullanıcı");
            adminPanelCard.setVisibility(View.GONE);
            
            // Normal kullanıcı için farklı istatistikler
            complaintsLabel.setText("Şikayet");
            parksLabel.setText("Park Ziyaret");
            complaintsCount.setText(String.valueOf(user.getComplaintsCount()));
            parksVisited.setText(String.valueOf(user.getParksVisited()));
        }
    }
} 