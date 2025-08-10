package com.example.parkapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * User - Kullanıcı modeli
 * 
 * Bu sınıf, uygulama kullanıcılarının bilgilerini ve istatistiklerini
 * tutar. Hem normal kullanıcılar hem de admin kullanıcılar için kullanılır.
 * 
 * @author Berkay Körükmez
 * @version 1.0
 */
public class User {
    
    // Role constants
    public static final String ROLE_USER = "user";
    public static final String ROLE_ADMIN = "admin";
    
    // User properties
    private String uid;
    private String email;
    private String name;
    private String role;
    private String department;
    
    // Statistics
    private int complaintsCount;
    private int parksVisited;
    private int resolvedComplaints;
    
    /**
     * Firebase için boş constructor
     */
    public User() {
        // Firebase Firestore için gerekli
    }
    
    /**
     * Normal kullanıcı için constructor
     * 
     * @param uid Kullanıcı ID'si
     * @param email Email adresi
     * @param name Kullanıcı adı
     * @param role Kullanıcı rolü
     */
    public User(@NonNull String uid, @NonNull String email, @NonNull String name, @NonNull String role) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.role = role;
        initializeStatistics();
    }
    
    /**
     * Admin kullanıcı için constructor
     * 
     * @param uid Kullanıcı ID'si
     * @param email Email adresi
     * @param name Kullanıcı adı
     * @param role Kullanıcı rolü
     * @param department Departman
     */
    public User(@NonNull String uid, @NonNull String email, @NonNull String name, 
                @NonNull String role, @NonNull String department) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.role = role;
        this.department = department;
        initializeStatistics();
    }
    
    /**
     * İstatistikleri sıfırlar
     */
    private void initializeStatistics() {
        this.complaintsCount = 0;
        this.parksVisited = 0;
        this.resolvedComplaints = 0;
    }
    
    // Getters
    
    /**
     * Kullanıcı ID'sini döndürür
     * 
     * @return Kullanıcı ID'si
     */
    @NonNull
    public String getUid() {
        return uid;
    }
    
    /**
     * Email adresini döndürür
     * 
     * @return Email adresi
     */
    @NonNull
    public String getEmail() {
        return email;
    }
    
    /**
     * Kullanıcı adını döndürür
     * 
     * @return Kullanıcı adı
     */
    @NonNull
    public String getName() {
        return name;
    }
    
    /**
     * Kullanıcı rolünü döndürür
     * 
     * @return Kullanıcı rolü
     */
    @NonNull
    public String getRole() {
        return role;
    }
    
    /**
     * Departman bilgisini döndürür
     * 
     * @return Departman veya null
     */
    @Nullable
    public String getDepartment() {
        return department;
    }
    
    /**
     * Şikayet sayısını döndürür
     * 
     * @return Şikayet sayısı
     */
    public int getComplaintsCount() {
        return complaintsCount;
    }
    
    /**
     * Ziyaret edilen park sayısını döndürür
     * 
     * @return Ziyaret edilen park sayısı
     */
    public int getParksVisited() {
        return parksVisited;
    }
    
    /**
     * Çözülen şikayet sayısını döndürür
     * 
     * @return Çözülen şikayet sayısı
     */
    public int getResolvedComplaints() {
        return resolvedComplaints;
    }
    
    // Setters
    
    /**
     * Kullanıcı ID'sini ayarlar
     * 
     * @param uid Kullanıcı ID'si
     */
    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }
    
    /**
     * Email adresini ayarlar
     * 
     * @param email Email adresi
     */
    public void setEmail(@NonNull String email) {
        this.email = email;
    }
    
    /**
     * Kullanıcı adını ayarlar
     * 
     * @param name Kullanıcı adı
     */
    public void setName(@NonNull String name) {
        this.name = name;
    }
    
    /**
     * Kullanıcı rolünü ayarlar
     * 
     * @param role Kullanıcı rolü
     */
    public void setRole(@NonNull String role) {
        this.role = role;
    }
    
    /**
     * Departman bilgisini ayarlar
     * 
     * @param department Departman
     */
    public void setDepartment(@Nullable String department) {
        this.department = department;
    }
    
    /**
     * Şikayet sayısını ayarlar
     * 
     * @param complaintsCount Şikayet sayısı
     */
    public void setComplaintsCount(int complaintsCount) {
        this.complaintsCount = complaintsCount;
    }
    
    /**
     * Ziyaret edilen park sayısını ayarlar
     * 
     * @param parksVisited Ziyaret edilen park sayısı
     */
    public void setParksVisited(int parksVisited) {
        this.parksVisited = parksVisited;
    }
    
    /**
     * Çözülen şikayet sayısını ayarlar
     * 
     * @param resolvedComplaints Çözülen şikayet sayısı
     */
    public void setResolvedComplaints(int resolvedComplaints) {
        this.resolvedComplaints = resolvedComplaints;
    }
    
    // Role checking methods
    
    /**
     * Kullanıcının admin olup olmadığını kontrol eder
     * 
     * @return Admin durumu
     */
    public boolean isAdmin() {
        return ROLE_ADMIN.equals(role);
    }
    
    /**
     * Kullanıcının normal kullanıcı olup olmadığını kontrol eder
     * 
     * @return Kullanıcı durumu
     */
    public boolean isUser() {
        return ROLE_USER.equals(role);
    }
    
    // Statistics update methods
    
    /**
     * Şikayet sayısını artırır
     */
    public void incrementComplaintsCount() {
        this.complaintsCount++;
    }
    
    /**
     * Ziyaret edilen park sayısını artırır
     */
    public void incrementParksVisited() {
        this.parksVisited++;
    }
    
    /**
     * Çözülen şikayet sayısını artırır
     */
    public void incrementResolvedComplaints() {
        this.resolvedComplaints++;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", department='" + department + '\'' +
                ", complaintsCount=" + complaintsCount +
                ", parksVisited=" + parksVisited +
                ", resolvedComplaints=" + resolvedComplaints +
                '}';
    }
} 