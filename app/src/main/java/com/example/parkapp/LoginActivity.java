package com.example.parkapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    
    private EditText emailEdit, passwordEdit;
    private Button loginButton, registerButton;
    private TextView switchToRegisterText, switchToAdminText;
    private boolean isLoginMode = true;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // Check if user is already logged in
        if (AuthManager.getInstance().isLoggedIn()) {
            startMainActivity();
            return;
        }
        
        emailEdit = findViewById(R.id.email_edit);
        passwordEdit = findViewById(R.id.password_edit);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        switchToRegisterText = findViewById(R.id.switch_to_register_text);
        switchToAdminText = findViewById(R.id.switch_to_admin_text);
        
        setupUI();
        setupListeners();
    }
    
    private void setupUI() {
        if (isLoginMode) {
            // Normal kullanıcı giriş modu
            loginButton.setVisibility(View.VISIBLE);
            registerButton.setVisibility(View.GONE);
            switchToRegisterText.setText("Hesabın yok mu? Kayıt ol");
            switchToAdminText.setText("Admin girişi");
            switchToAdminText.setVisibility(View.VISIBLE);
        } else {
            // Normal kullanıcı kayıt modu
            loginButton.setVisibility(View.GONE);
            registerButton.setVisibility(View.VISIBLE);
            switchToRegisterText.setText("Zaten hesabın var mı? Giriş yap");
            switchToAdminText.setText("Admin girişi");
            switchToAdminText.setVisibility(View.VISIBLE);
        }
    }
    
    private void setupListeners() {
        switchToRegisterText.setOnClickListener(v -> {
            // Sadece kullanıcı kayıt/giriş geçişi
            isLoginMode = !isLoginMode;
            setupUI();
        });
        
        switchToAdminText.setOnClickListener(v -> {
            // Admin login activity'ye git
            Intent intent = new Intent(LoginActivity.this, AdminLoginActivity.class);
            startActivity(intent);
        });
        
        loginButton.setOnClickListener(v -> {
            String email = emailEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();
            
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                return;
            }
            
            loginButton.setEnabled(false);
            
            // Normal kullanıcı girişi
            AuthManager.getInstance().login(email, password, new AuthManager.OnAuthListener() {
                @Override
                public void onSuccess(User user) {
                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, "Giriş başarılı!", Toast.LENGTH_SHORT).show();
                        startMainActivity();
                    });
                }
                
                @Override
                public void onFailure(Exception e) {
                    runOnUiThread(() -> {
                        loginButton.setEnabled(true);
                        String errorMessage = "Giriş başarısız: ";
                        if (e.getMessage().contains("INVALID_LOGIN_CREDENTIALS")) {
                            errorMessage += "E-posta veya şifre hatalı";
                        } else if (e.getMessage().contains("USER_NOT_FOUND")) {
                            errorMessage += "Kullanıcı bulunamadı";
                        } else if (e.getMessage().contains("WRONG_PASSWORD")) {
                            errorMessage += "Şifre hatalı";
                        } else if (e.getMessage().contains("TOO_MANY_ATTEMPTS")) {
                            errorMessage += "Çok fazla deneme, daha sonra tekrar dene";
                        } else {
                            errorMessage += e.getMessage();
                        }
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    });
                }
            });
        });
        
        registerButton.setOnClickListener(v -> {
            String email = emailEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();
            
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                return;
            }
            
            registerButton.setEnabled(false);
            // Basit kullanıcı kaydı (admin kaydı için ayrı ekran gerekebilir)
            AuthManager.getInstance().registerUser(email, password, "Kullanıcı", new AuthManager.OnAuthListener() {
                @Override
                public void onSuccess(User user) {
                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, "Kayıt başarılı!", Toast.LENGTH_SHORT).show();
                        startMainActivity();
                    });
                }
                
                @Override
                public void onFailure(Exception e) {
                    runOnUiThread(() -> {
                        registerButton.setEnabled(true);
                        String errorMessage = "Kayıt başarısız: ";
                        if (e.getMessage().contains("WEAK_PASSWORD")) {
                            errorMessage += "Şifre en az 6 karakter olmalı";
                        } else if (e.getMessage().contains("INVALID_EMAIL")) {
                            errorMessage += "Geçersiz e-posta adresi";
                        } else if (e.getMessage().contains("EMAIL_ALREADY_IN_USE")) {
                            errorMessage += "Bu e-posta zaten kullanılıyor";
                        } else {
                            errorMessage += e.getMessage();
                        }
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    });
                }
            });
        });
    }
    
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
} 