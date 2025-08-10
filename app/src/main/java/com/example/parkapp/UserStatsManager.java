package com.example.parkapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.HashMap;
import java.util.Map;

/**
 * UserStatsManager - Kullanıcı istatistikleri yönetimi
 * 
 * Bu sınıf, kullanıcıların istatistiklerini Firebase Firestore'da
 * yönetir. Şikayet sayıları, park ziyaretleri ve çözülen şikayetler
 * gibi verileri takip eder.
 * 
 * @author Berkay Körükmez
 * @version 1.0
 */
public class UserStatsManager {
    
    // Constants
    private static final String TAG = "UserStatsManager";
    private static final String USERS_COLLECTION = "users";
    private static final String FIELD_COMPLAINTS_COUNT = "complaintsCount";
    private static final String FIELD_PARKS_VISITED = "parksVisited";
    private static final String FIELD_RESOLVED_COMPLAINTS = "resolvedComplaints";
    
    // Singleton instance
    private static UserStatsManager instance;
    
    // Firebase components
    private final FirebaseFirestore db;
    
    /**
     * Private constructor for singleton pattern
     */
    private UserStatsManager() {
        db = FirebaseFirestore.getInstance();
    }
    
    /**
     * Singleton instance'ını döndürür
     * 
     * @return UserStatsManager instance
     */
    public static UserStatsManager getInstance() {
        if (instance == null) {
            synchronized (UserStatsManager.class) {
                if (instance == null) {
                    instance = new UserStatsManager();
                }
            }
        }
        return instance;
    }
    
    /**
     * Kullanıcı istatistiklerini günceller
     * 
     * @param userId Kullanıcı ID'si
     * @param statType İstatistik türü
     * @param increment Artırılacak değer
     */
    public void updateUserStats(@NonNull String userId, @NonNull String statType, int increment) {
        DocumentReference userRef = db.collection(USERS_COLLECTION).document(userId);
        
        Map<String, Object> updates = new HashMap<>();
        updates.put(statType, increment);
        
        userRef.update(statType, increment)
                .addOnSuccessListener(aVoid -> {
                    // Başarılı güncelleme log'u eklenebilir
                })
                .addOnFailureListener(e -> {
                    // Hata durumu log'u eklenebilir
                });
    }
    
    /**
     * Kullanıcı istatistiklerini yükler
     * 
     * @param userId Kullanıcı ID'si
     * @param listener Stats loaded listener
     */
    public void loadUserStats(@NonNull String userId, @Nullable OnStatsLoadedListener listener) {
        DocumentReference userRef = db.collection(USERS_COLLECTION).document(userId);
        
        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                User user = createUserFromDocument(documentSnapshot);
                if (user != null) {
                    if (listener != null) {
                        listener.onSuccess(user);
                    }
                } else {
                    if (listener != null) {
                        listener.onFailure(new Exception("Kullanıcı verisi okunamadı"));
                    }
                }
            } else {
                if (listener != null) {
                    listener.onFailure(new Exception("Kullanıcı bulunamadı"));
                }
            }
        }).addOnFailureListener(e -> {
            if (listener != null) {
                listener.onFailure(e);
            }
        });
    }
    
    /**
     * Firestore document'ından User objesi oluşturur
     * 
     * @param documentSnapshot Firestore document
     * @return User objesi veya null
     */
    private User createUserFromDocument(@NonNull DocumentSnapshot documentSnapshot) {
        try {
            User user = new User();
            user.setUid(documentSnapshot.getString("uid"));
            user.setEmail(documentSnapshot.getString("email"));
            user.setName(documentSnapshot.getString("name"));
            user.setRole(documentSnapshot.getString("role"));
            user.setDepartment(documentSnapshot.getString("department"));
            
            // Statistics
            Long complaintsCount = documentSnapshot.getLong(FIELD_COMPLAINTS_COUNT);
            Long parksVisited = documentSnapshot.getLong(FIELD_PARKS_VISITED);
            Long resolvedComplaints = documentSnapshot.getLong(FIELD_RESOLVED_COMPLAINTS);
            
            user.setComplaintsCount(complaintsCount != null ? complaintsCount.intValue() : 0);
            user.setParksVisited(parksVisited != null ? parksVisited.intValue() : 0);
            user.setResolvedComplaints(resolvedComplaints != null ? resolvedComplaints.intValue() : 0);
            
            return user;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Şikayet sayısını artırır
     * 
     * @param userId Kullanıcı ID'si
     */
    public void incrementComplaintsCount(@NonNull String userId) {
        updateUserStats(userId, FIELD_COMPLAINTS_COUNT, 1);
    }
    
    /**
     * Park ziyaret sayısını artırır
     * 
     * @param userId Kullanıcı ID'si
     */
    public void incrementParksVisited(@NonNull String userId) {
        updateUserStats(userId, FIELD_PARKS_VISITED, 1);
    }
    
    /**
     * Çözülen şikayet sayısını artırır (admin için)
     * 
     * @param userId Kullanıcı ID'si
     */
    public void incrementResolvedComplaints(@NonNull String userId) {
        updateUserStats(userId, FIELD_RESOLVED_COMPLAINTS, 1);
    }
    
    /**
     * Admin için toplam istatistikleri günceller
     * 
     * @param userId Kullanıcı ID'si
     * @param totalComplaints Toplam şikayet sayısı
     * @param totalParks Toplam park sayısı
     * @param resolvedComplaints Çözülen şikayet sayısı
     */
    public void updateAdminStats(@NonNull String userId, int totalComplaints, int totalParks, int resolvedComplaints) {
        DocumentReference userRef = db.collection(USERS_COLLECTION).document(userId);
        
        Map<String, Object> updates = new HashMap<>();
        updates.put(FIELD_COMPLAINTS_COUNT, totalComplaints);
        updates.put(FIELD_PARKS_VISITED, totalParks);
        updates.put(FIELD_RESOLVED_COMPLAINTS, resolvedComplaints);
        
        userRef.update(updates)
                .addOnSuccessListener(aVoid -> {
                    // Başarılı güncelleme log'u eklenebilir
                })
                .addOnFailureListener(e -> {
                    // Hata durumu log'u eklenebilir
                });
    }
    
    /**
     * İstatistik yükleme işlemleri için callback interface
     */
    public interface OnStatsLoadedListener {
        /**
         * Başarılı yükleme callback'i
         * 
         * @param user Yüklenen kullanıcı
         */
        void onSuccess(@NonNull User user);
        
        /**
         * Başarısız yükleme callback'i
         * 
         * @param e Hata
         */
        void onFailure(@NonNull Exception e);
    }
} 