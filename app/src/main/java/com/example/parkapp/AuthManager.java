package com.example.parkapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

/**
 * AuthManager - Firebase Authentication ve kullanıcı yönetimi
 * 
 * Bu sınıf, Firebase Authentication kullanarak kullanıcı kayıt, giriş
 * ve çıkış işlemlerini yönetir. Singleton pattern kullanır.
 * 
 * @author Berkay Körükmez
 * @version 1.0
 */
public class AuthManager {
    
    // Constants
    private static final String TAG = "AuthManager";
    private static final String USERS_COLLECTION = "users";
    private static final String FIELD_UID = "uid";
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_ROLE = "role";
    private static final String FIELD_DEPARTMENT = "department";
    
    // Singleton instance
    private static AuthManager instance;
    
    // Firebase components
    private final FirebaseAuth auth;
    private final FirebaseFirestore db;
    
    // Current user
    private User currentUser;
    
    /**
     * Private constructor for singleton pattern
     */
    private AuthManager() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }
    
    /**
     * Singleton instance'ını döndürür
     * 
     * @return AuthManager instance
     */
    public static AuthManager getInstance() {
        if (instance == null) {
            synchronized (AuthManager.class) {
                if (instance == null) {
                    instance = new AuthManager();
                }
            }
        }
        return instance;
    }
    
    /**
     * Yeni kullanıcı kaydı yapar
     * 
     * @param email Kullanıcı email'i
     * @param password Şifre
     * @param name Kullanıcı adı
     * @param listener Auth listener
     */
    public void registerUser(@NonNull String email, @NonNull String password, 
                           @NonNull String name, @Nullable OnAuthListener listener) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener(authResult -> {
                FirebaseUser firebaseUser = authResult.getUser();
                if (firebaseUser != null) {
                    User user = new User(firebaseUser.getUid(), email, name, User.ROLE_USER);
                    saveUserToFirestore(user, listener);
                }
            })
            .addOnFailureListener(e -> {
                if (listener != null) {
                    listener.onFailure(e);
                }
            });
    }
    
    /**
     * Yeni admin kaydı yapar
     * 
     * @param email Admin email'i
     * @param password Şifre
     * @param name Admin adı
     * @param department Departman
     * @param listener Auth listener
     */
    public void registerAdmin(@NonNull String email, @NonNull String password, 
                            @NonNull String name, @NonNull String department, 
                            @Nullable OnAuthListener listener) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener(authResult -> {
                FirebaseUser firebaseUser = authResult.getUser();
                if (firebaseUser != null) {
                    User admin = new User(firebaseUser.getUid(), email, name, User.ROLE_ADMIN, department);
                    saveUserToFirestore(admin, listener);
                }
            })
            .addOnFailureListener(e -> {
                if (listener != null) {
                    listener.onFailure(e);
                }
            });
    }
    
    /**
     * Kullanıcı girişi yapar
     * 
     * @param email Email
     * @param password Şifre
     * @param listener Auth listener
     */
    public void login(@NonNull String email, @NonNull String password, @Nullable OnAuthListener listener) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener(authResult -> {
                FirebaseUser firebaseUser = authResult.getUser();
                if (firebaseUser != null) {
                    loadUserFromFirestore(firebaseUser.getUid(), listener);
                }
            })
            .addOnFailureListener(e -> {
                if (listener != null) {
                    listener.onFailure(e);
                }
            });
    }
    
    /**
     * Kullanıcı çıkışı yapar
     */
    public void logout() {
        auth.signOut();
        currentUser = null;
    }
    
    /**
     * Mevcut kullanıcıyı döndürür
     * 
     * @return Mevcut kullanıcı veya null
     */
    @Nullable
    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Mevcut kullanıcıyı ayarlar
     * 
     * @param user Kullanıcı
     */
    public void setCurrentUser(@NonNull User user) {
        this.currentUser = user;
    }
    
    /**
     * Kullanıcının giriş yapıp yapmadığını kontrol eder
     * 
     * @return Giriş durumu
     */
    public boolean isLoggedIn() {
        return auth.getCurrentUser() != null && currentUser != null;
    }
    
    /**
     * Kullanıcının admin olup olmadığını kontrol eder
     * 
     * @return Admin durumu
     */
    public boolean isAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }
    
    /**
     * Kullanıcının normal kullanıcı olup olmadığını kontrol eder
     * 
     * @return Kullanıcı durumu
     */
    public boolean isUser() {
        return currentUser != null && currentUser.isUser();
    }
    
    /**
     * Kullanıcı bilgilerini Firestore'a kaydeder
     * 
     * @param user Kaydedilecek kullanıcı
     * @param listener Auth listener
     */
    private void saveUserToFirestore(@NonNull User user, @Nullable OnAuthListener listener) {
        Map<String, Object> userData = createUserDataMap(user);
        
        db.collection(USERS_COLLECTION).document(user.getUid())
            .set(userData)
            .addOnSuccessListener(aVoid -> {
                currentUser = user;
                if (listener != null) {
                    listener.onSuccess(user);
                }
            })
            .addOnFailureListener(e -> {
                if (listener != null) {
                    listener.onFailure(e);
                }
            });
    }
    
    /**
     * Kullanıcı verilerini Map'e dönüştürür
     * 
     * @param user Kullanıcı
     * @return Kullanıcı verileri Map'i
     */
    private Map<String, Object> createUserDataMap(@NonNull User user) {
        Map<String, Object> userData = new HashMap<>();
        userData.put(FIELD_UID, user.getUid());
        userData.put(FIELD_EMAIL, user.getEmail());
        userData.put(FIELD_NAME, user.getName());
        userData.put(FIELD_ROLE, user.getRole());
        
        if (user.getDepartment() != null) {
            userData.put(FIELD_DEPARTMENT, user.getDepartment());
        }
        
        return userData;
    }
    
    /**
     * Kullanıcı bilgilerini Firestore'dan yükler
     * 
     * @param uid Kullanıcı ID'si
     * @param listener Auth listener
     */
    private void loadUserFromFirestore(@NonNull String uid, @Nullable OnAuthListener listener) {
        db.collection(USERS_COLLECTION).document(uid)
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    User user = createUserFromDocument(documentSnapshot);
                    currentUser = user;
                    if (listener != null) {
                        listener.onSuccess(user);
                    }
                } else {
                    if (listener != null) {
                        listener.onFailure(new Exception("Kullanıcı bilgileri bulunamadı"));
                    }
                }
            })
            .addOnFailureListener(e -> {
                if (listener != null) {
                    listener.onFailure(e);
                }
            });
    }
    
    /**
     * Firestore document'ından User objesi oluşturur
     * 
     * @param documentSnapshot Firestore document
     * @return User objesi
     */
    private User createUserFromDocument(@NonNull com.google.firebase.firestore.DocumentSnapshot documentSnapshot) {
        User user = new User();
        user.setUid(documentSnapshot.getString(FIELD_UID));
        user.setEmail(documentSnapshot.getString(FIELD_EMAIL));
        user.setName(documentSnapshot.getString(FIELD_NAME));
        user.setRole(documentSnapshot.getString(FIELD_ROLE));
        user.setDepartment(documentSnapshot.getString(FIELD_DEPARTMENT));
        return user;
    }
    
    /**
     * Authentication işlemleri için callback interface
     */
    public interface OnAuthListener {
        /**
         * Başarılı işlem callback'i
         * 
         * @param user Kullanıcı
         */
        void onSuccess(@NonNull User user);
        
        /**
         * Başarısız işlem callback'i
         * 
         * @param e Hata
         */
        void onFailure(@NonNull Exception e);
    }
} 