package com.example.parkapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {
    
    private EditText emailEdit, passwordEdit;
    private Button loginButton, backButton;
    private ImageView logoImage;
    
    // Admin bilgileri - Farklı birimler için
    private static final String[][] ADMIN_CREDENTIALS = {
        {"cevre@malatya.gov.tr", "cevre123", "Çevre ve Şehircilik"},
        {"isik@malatya.gov.tr", "isik123", "Aydınlatma ve Enerji"},
        {"cim@malatya.gov.tr", "cim123", "Park ve Bahçeler"},
        {"temizlik@malatya.gov.tr", "temizlik123", "Temizlik İşleri"},
        {"yol@malatya.gov.tr", "yol123", "Yol ve Altyapı"},
        {"spor@malatya.gov.tr", "spor123", "Spor ve Gençlik"}
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        
        // Check if user is already logged in as admin
        if (AuthManager.getInstance().isLoggedIn() && AuthManager.getInstance().isAdmin()) {
            startMainActivity();
            return;
        }
        
        emailEdit = findViewById(R.id.admin_email_edit);
        passwordEdit = findViewById(R.id.admin_password_edit);
        loginButton = findViewById(R.id.admin_login_button);
        backButton = findViewById(R.id.admin_back_button);
        logoImage = findViewById(R.id.admin_logo);
        
        setupListeners();
    }
    
    private void setupListeners() {
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        
        loginButton.setOnClickListener(v -> {
            String email = emailEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();
            
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                return;
            }
            
            loginButton.setEnabled(false);
            
            // Admin girişi kontrolü - Farklı birimler için
            boolean adminFound = false;
            String department = "";
            
            for (String[] admin : ADMIN_CREDENTIALS) {
                if (email.equals(admin[0]) && password.equals(admin[1])) {
                    adminFound = true;
                    department = admin[2];
                    break;
                }
            }
            
            if (adminFound) {
                // Admin girişi başarılı
                User adminUser = new User("admin_uid", email, "Malatya Belediyesi", "admin", department);
                AuthManager.getInstance().setCurrentUser(adminUser);
                Toast.makeText(AdminLoginActivity.this, department + " girişi başarılı!", Toast.LENGTH_SHORT).show();
                
                // Debug bilgisi
                System.out.println("AdminLoginActivity: Admin girişi - Email: " + email + ", Departman: " + department);
                
                startMainActivity();
            } else {
                // Admin girişi başarısız
                loginButton.setEnabled(true);
                Toast.makeText(AdminLoginActivity.this, "Admin bilgileri hatalı!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
} 