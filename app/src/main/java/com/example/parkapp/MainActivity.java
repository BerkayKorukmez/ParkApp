package com.example.parkapp;

import android.os.Bundle;
import android.view.Menu;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * MainActivity - Uygulamanın ana aktivitesi
 * 
 * Bu sınıf, uygulamanın ana navigasyon yapısını yönetir ve
 * kullanıcı tipine göre farklı fragment'ları gösterir.
 * 
 * @author Berkay Körükmez
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    
    // Constants
    private static final String TAG = "MainActivity";
    
    // UI Components
    private BottomNavigationView bottomNavigationView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeViews();
        setupBottomNavigation();
        updateNavigationForUser();
        setDefaultFragment(savedInstanceState);
    }
    
    /**
     * UI bileşenlerini başlatır
     */
    private void initializeViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }
    
    /**
     * Bottom navigation'ı yapılandırır
     */
    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = getFragmentForNavigationItem(item.getItemId());
            
            if (selectedFragment != null) {
                navigateToFragment(selectedFragment);
                return true;
            }
            return false;
        });
    }
    
    /**
     * Navigation item ID'sine göre uygun fragment'ı döndürür
     * 
     * @param itemId Navigation item ID'si
     * @return Seçilen fragment veya null
     */
    private Fragment getFragmentForNavigationItem(int itemId) {
        if (itemId == R.id.nav_map) {
            return new ParkListFragment();
        } else if (itemId == R.id.nav_complaints) {
            return new ComplaintsFragment();
        } else if (itemId == R.id.nav_profile) {
            return new ProfileFragment();
        }
        return null;
    }
    
    /**
     * Belirtilen fragment'a geçiş yapar
     * 
     * @param fragment Geçiş yapılacak fragment
     */
    private void navigateToFragment(@NonNull Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
    }
    
    /**
     * Kullanıcı tipine göre navigation'ı günceller
     */
    private void updateNavigationForUser() {
        User currentUser = AuthManager.getInstance().getCurrentUser();
        if (currentUser != null && currentUser.isAdmin()) {
            updateNavigationForAdmin();
        }
    }
    
    /**
     * Admin kullanıcılar için navigation'ı günceller
     */
    private void updateNavigationForAdmin() {
        Menu menu = bottomNavigationView.getMenu();
        menu.findItem(R.id.nav_complaints).setTitle(getString(R.string.nav_complaint_management));
        menu.findItem(R.id.nav_map).setTitle(getString(R.string.nav_park_info));
    }
    
    /**
     * Varsayılan fragment'ı ayarlar
     * 
     * @param savedInstanceState Kaydedilmiş instance state
     */
    private void setDefaultFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            navigateToFragment(new ParkListFragment());
        }
    }
} 