package com.example.parkapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Park - Park modeli
 * 
 * Bu sınıf, Malatya'daki parkların bilgilerini tutar ve yönetir.
 * Park detayları, konum bilgileri, yönetim bilgileri ve istatistikler
 * bu sınıfta saklanır.
 * 
 * @author Berkay Körükmez
 * @version 1.0
 */
public class Park {
    
    // Park properties
    private String id;
    private String name;
    private String imageUrl;
    private String description;
    private String manager;
    private String address;
    private String facilities;
    private String openingHours;
    private boolean isOpen;
    private double rating;
    private int reviewCount;
    
    /**
     * Park constructor
     * 
     * @param id Park ID'si
     * @param name Park adı
     * @param imageUrl Park resmi URL'i
     * @param description Park açıklaması
     * @param manager Park yöneticisi
     * @param address Park adresi
     * @param facilities Park imkanları
     * @param openingHours Açılış saatleri
     * @param isOpen Açık durumu
     * @param rating Park puanı
     * @param reviewCount Değerlendirme sayısı
     */
    public Park(@NonNull String id, @NonNull String name, @Nullable String imageUrl, 
                @NonNull String description, @NonNull String manager, @NonNull String address, 
                @NonNull String facilities, @NonNull String openingHours, boolean isOpen, 
                double rating, int reviewCount) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.manager = manager;
        this.address = address;
        this.facilities = facilities;
        this.openingHours = openingHours;
        this.isOpen = isOpen;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }
    
    // Getters
    
    /**
     * Park ID'sini döndürür
     * 
     * @return Park ID'si
     */
    @NonNull
    public String getId() {
        return id;
    }
    
    /**
     * Park adını döndürür
     * 
     * @return Park adı
     */
    @NonNull
    public String getName() {
        return name;
    }
    
    /**
     * Park resmi URL'ini döndürür
     * 
     * @return Park resmi URL'i veya null
     */
    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }
    
    /**
     * Park açıklamasını döndürür
     * 
     * @return Park açıklaması
     */
    @NonNull
    public String getDescription() {
        return description;
    }
    
    /**
     * Park yöneticisini döndürür
     * 
     * @return Park yöneticisi
     */
    @NonNull
    public String getManager() {
        return manager;
    }
    
    /**
     * Park adresini döndürür
     * 
     * @return Park adresi
     */
    @NonNull
    public String getAddress() {
        return address;
    }
    
    /**
     * Park imkanlarını döndürür
     * 
     * @return Park imkanları
     */
    @NonNull
    public String getFacilities() {
        return facilities;
    }
    
    /**
     * Açılış saatlerini döndürür
     * 
     * @return Açılış saatleri
     */
    @NonNull
    public String getOpeningHours() {
        return openingHours;
    }
    
    /**
     * Parkın açık olup olmadığını döndürür
     * 
     * @return Açık durumu
     */
    public boolean isOpen() {
        return isOpen;
    }
    
    /**
     * Park puanını döndürür
     * 
     * @return Park puanı
     */
    public double getRating() {
        return rating;
    }
    
    /**
     * Değerlendirme sayısını döndürür
     * 
     * @return Değerlendirme sayısı
     */
    public int getReviewCount() {
        return reviewCount;
    }
    
    // Setters
    
    /**
     * Park ID'sini ayarlar
     * 
     * @param id Park ID'si
     */
    public void setId(@NonNull String id) {
        this.id = id;
    }
    
    /**
     * Park adını ayarlar
     * 
     * @param name Park adı
     */
    public void setName(@NonNull String name) {
        this.name = name;
    }
    
    /**
     * Park resmi URL'ini ayarlar
     * 
     * @param imageUrl Park resmi URL'i
     */
    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    /**
     * Park açıklamasını ayarlar
     * 
     * @param description Park açıklaması
     */
    public void setDescription(@NonNull String description) {
        this.description = description;
    }
    
    /**
     * Park yöneticisini ayarlar
     * 
     * @param manager Park yöneticisi
     */
    public void setManager(@NonNull String manager) {
        this.manager = manager;
    }
    
    /**
     * Park adresini ayarlar
     * 
     * @param address Park adresi
     */
    public void setAddress(@NonNull String address) {
        this.address = address;
    }
    
    /**
     * Park imkanlarını ayarlar
     * 
     * @param facilities Park imkanları
     */
    public void setFacilities(@NonNull String facilities) {
        this.facilities = facilities;
    }
    
    /**
     * Açılış saatlerini ayarlar
     * 
     * @param openingHours Açılış saatleri
     */
    public void setOpeningHours(@NonNull String openingHours) {
        this.openingHours = openingHours;
    }
    
    /**
     * Park açık durumunu ayarlar
     * 
     * @param open Açık durumu
     */
    public void setOpen(boolean open) {
        isOpen = open;
    }
    
    /**
     * Park puanını ayarlar
     * 
     * @param rating Park puanı
     */
    public void setRating(double rating) {
        this.rating = rating;
    }
    
    /**
     * Değerlendirme sayısını ayarlar
     * 
     * @param reviewCount Değerlendirme sayısı
     */
    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }
    
    /**
     * Malatya'daki tüm parkları döndürür
     * 
     * @return Malatya parkları listesi
     */
    public static List<Park> getAllMalatyaParks() {
        List<Park> parks = new ArrayList<>();
        
        // Kültür Parkı
        parks.add(new Park("1", "Kültür Parkı", null, 
            "Malatya'nın merkezindeki en büyük ve en popüler park. Yeşil alanları, yürüyüş yolları, dinlenme alanları ve çeşitli aktivite imkanları ile şehrin kalbi konumunda.", 
            "Malatya Büyükşehir Belediyesi", "Merkez, Malatya", 
            "Çocuk Oyun Alanı, Spor Sahaları, Yürüyüş Yolları, Piknik Alanları, Kafeler", 
            "24 Saat Açık", true, 4.5, 128));
            
        // Abdullah Gül Parkı
        parks.add(new Park("2", "Abdullah Gül Parkı", null, 
            "Modern tasarımı ve geniş yeşil alanları ile öne çıkan park. Spor alanları ve çocuk oyun parkları ile donatılmış.", 
            "Malatya Büyükşehir Belediyesi", "Yeşilyurt, Malatya", 
            "Basketbol Sahası, Tenis Kortu, Çocuk Oyun Alanı, Yürüyüş Yolları", 
            "06:00 - 23:00", true, 4.3, 95));
            
        // Mişmiş Parkı
        parks.add(new Park("3", "Mişmiş Parkı", null, 
            "Mişmiş mahallesinde bulunan kompakt ve kullanışlı park. Yerel halkın sıkça kullandığı sosyal alan.", 
            "Malatya Büyükşehir Belediyesi", "Mişmiş Mahallesi, Yeşilyurt", 
            "Çocuk Oyun Alanı, Dinlenme Bankları, Yeşil Alan", 
            "24 Saat Açık", true, 4.1, 67));
            
        // Beşkonaklar Parkı
        parks.add(new Park("4", "Beşkonaklar Parkı", null, 
            "Beşkonaklar mahallesinde bulunan modern park. Çocuk oyun alanları ve spor imkanları mevcut.", 
            "Malatya Büyükşehir Belediyesi", "Beşkonaklar Mahallesi, Battalgazi", 
            "Çocuk Oyun Alanı, Spor Ekipmanları, Yürüyüş Yolları", 
            "06:00 - 22:00", true, 4.2, 78));
            
        // Yeşilyurt Parkı
        parks.add(new Park("5", "Yeşilyurt Parkı", null, 
            "Yeşilyurt ilçesindeki büyük park. Piknik alanları ve doğal güzellikleri ile tanınır.", 
            "Yeşilyurt Belediyesi", "Yeşilyurt Merkez", 
            "Piknik Alanları, Çocuk Oyun Alanı, Spor Sahaları, Yürüyüş Yolları", 
            "24 Saat Açık", true, 4.4, 89));
            
        // Battalgazi Parkı
        parks.add(new Park("6", "Battalgazi Parkı", null, 
            "Battalgazi ilçesinde tarihi dokunun yanında yer alan park. Tarihi atmosfer ile modern park anlayışını birleştirir.", 
            "Battalgazi Belediyesi", "Battalgazi Merkez", 
            "Tarihi Dokuda Dinlenme Alanları, Çocuk Oyun Alanı, Kafeler", 
            "08:00 - 20:00", true, 4.0, 56));
            
        // Hacı Halil Parkı
        parks.add(new Park("7", "Hacı Halil Parkı", null, 
            "Hacı Halil mahallesinde bulunan modern park. Çocuk oyun alanları ve spor imkanları ile donatılmış.", 
            "Malatya Büyükşehir Belediyesi", "Hacı Halil Mahallesi, Merkez", 
            "Çocuk Oyun Alanı, Spor Ekipmanları, Yürüyüş Yolları, Dinlenme Bankları", 
            "06:00 - 23:00", true, 4.1, 72));
            
        // Fırat Parkı
        parks.add(new Park("8", "Fırat Parkı", null, 
            "Fırat nehri kenarında bulunan doğal güzellikleri ile öne çıkan park. Piknik alanları ve manzara seyir noktaları mevcut.", 
            "Malatya Büyükşehir Belediyesi", "Fırat Kenarı, Merkez", 
            "Piknik Alanları, Manzara Seyir Noktaları, Yürüyüş Yolları, Çocuk Oyun Alanı", 
            "24 Saat Açık", true, 4.6, 103));
            
        // Gazi Parkı
        parks.add(new Park("9", "Gazi Parkı", null, 
            "Gazi mahallesinde bulunan modern park. Spor alanları ve çocuk oyun parkları ile donatılmış.", 
            "Malatya Büyükşehir Belediyesi", "Gazi Mahallesi, Merkez", 
            "Basketbol Sahası, Çocuk Oyun Alanı, Spor Ekipmanları, Yürüyüş Yolları", 
            "06:00 - 22:00", true, 4.0, 58));
            
        // Cumhuriyet Parkı
        parks.add(new Park("10", "Cumhuriyet Parkı", null, 
            "Cumhuriyet mahallesinde bulunan tarihi park. Tarihi dokunun yanında yer alan yeşil alan.", 
            "Malatya Büyükşehir Belediyesi", "Cumhuriyet Mahallesi, Merkez", 
            "Tarihi Dokuda Dinlenme Alanları, Çocuk Oyun Alanı, Yürüyüş Yolları", 
            "08:00 - 20:00", true, 3.9, 45));
            
        // Yenişehir Parkı
        parks.add(new Park("11", "Yenişehir Parkı", null, 
            "Yenişehir mahallesinde bulunan modern park. Geniş yeşil alanları ve spor imkanları mevcut.", 
            "Malatya Büyükşehir Belediyesi", "Yenişehir Mahallesi, Merkez", 
            "Spor Sahaları, Çocuk Oyun Alanı, Yürüyüş Yolları, Piknik Alanları", 
            "06:00 - 23:00", true, 4.2, 81));
            
        // Fatih Parkı
        parks.add(new Park("12", "Fatih Parkı", null, 
            "Fatih mahallesinde bulunan kompakt park. Yerel halkın sıkça kullandığı sosyal alan.", 
            "Malatya Büyükşehir Belediyesi", "Fatih Mahallesi, Merkez", 
            "Çocuk Oyun Alanı, Dinlenme Bankları, Yeşil Alan", 
            "24 Saat Açık", true, 4.0, 63));
            
        // Atatürk Parkı
        parks.add(new Park("13", "Atatürk Parkı", null, 
            "Atatürk mahallesinde bulunan büyük park. Anıt ve heykeller ile donatılmış tarihi park.", 
            "Malatya Büyükşehir Belediyesi", "Atatürk Mahallesi, Merkez", 
            "Anıt ve Heykeller, Çocuk Oyun Alanı, Yürüyüş Yolları, Dinlenme Alanları", 
            "24 Saat Açık", true, 4.3, 92));
            
        // İnönü Parkı
        parks.add(new Park("14", "İnönü Parkı", null, 
            "İnönü mahallesinde bulunan modern park. Spor alanları ve çocuk oyun parkları ile donatılmış.", 
            "Malatya Büyükşehir Belediyesi", "İnönü Mahallesi, Merkez", 
            "Basketbol Sahası, Tenis Kortu, Çocuk Oyun Alanı, Spor Ekipmanları", 
            "06:00 - 22:00", true, 4.1, 74));
            
        // Orduze Parkı
        parks.add(new Park("15", "Orduze Parkı", null, 
            "Orduze mahallesinde bulunan doğal güzellikleri ile öne çıkan park. Piknik alanları mevcut.", 
            "Malatya Büyükşehir Belediyesi", "Orduze Mahallesi, Merkez", 
            "Piknik Alanları, Çocuk Oyun Alanı, Yürüyüş Yolları, Doğal Güzellikler", 
            "24 Saat Açık", true, 4.4, 87));
            
        // Çarşı Parkı
        parks.add(new Park("16", "Çarşı Parkı", null, 
            "Çarşı mahallesinde bulunan merkezi konumdaki park. Alışveriş merkezlerinin yanında yer alır.", 
            "Malatya Büyükşehir Belediyesi", "Çarşı Mahallesi, Merkez", 
            "Çocuk Oyun Alanı, Dinlenme Bankları, Yeşil Alan, Kafeler", 
            "06:00 - 23:00", true, 4.0, 69));
            
        // Hürriyet Parkı
        parks.add(new Park("17", "Hürriyet Parkı", null, 
            "Hürriyet mahallesinde bulunan modern park. Spor alanları ve çocuk oyun parkları ile donatılmış.", 
            "Malatya Büyükşehir Belediyesi", "Hürriyet Mahallesi, Merkez", 
            "Spor Sahaları, Çocuk Oyun Alanı, Yürüyüş Yolları, Spor Ekipmanları", 
            "06:00 - 22:00", true, 4.2, 76));
            
        // Yalvaç Parkı
        parks.add(new Park("18", "Yalvaç Parkı", null, 
            "Yalvaç mahallesinde bulunan kompakt park. Yerel halkın sıkça kullandığı sosyal alan.", 
            "Malatya Büyükşehir Belediyesi", "Yalvaç Mahallesi, Merkez", 
            "Çocuk Oyun Alanı, Dinlenme Bankları, Yeşil Alan", 
            "24 Saat Açık", true, 3.9, 52));
            
        // Çamlıca Parkı
        parks.add(new Park("19", "Çamlıca Parkı", null, 
            "Çamlıca mahallesinde bulunan doğal güzellikleri ile öne çıkan park. Çam ağaçları ile kaplı.", 
            "Malatya Büyükşehir Belediyesi", "Çamlıca Mahallesi, Merkez", 
            "Çam Ağaçları, Yürüyüş Yolları, Piknik Alanları, Çocuk Oyun Alanı", 
            "24 Saat Açık", true, 4.5, 94));
            
        // Gündüzbey Parkı
        parks.add(new Park("20", "Gündüzbey Parkı", null, 
            "Gündüzbey mahallesinde bulunan modern park. Spor alanları ve çocuk oyun parkları ile donatılmış.", 
            "Malatya Büyükşehir Belediyesi", "Gündüzbey Mahallesi, Merkez", 
            "Basketbol Sahası, Çocuk Oyun Alanı, Spor Ekipmanları, Yürüyüş Yolları", 
            "06:00 - 22:00", true, 4.1, 68));
            
        // Beydağı Parkı
        parks.add(new Park("21", "Beydağı Parkı", null, 
            "Beydağı mahallesinde bulunan doğal güzellikleri ile öne çıkan park. Manzara seyir noktaları mevcut.", 
            "Malatya Büyükşehir Belediyesi", "Beydağı Mahallesi, Merkez", 
            "Manzara Seyir Noktaları, Yürüyüş Yolları, Piknik Alanları, Çocuk Oyun Alanı", 
            "24 Saat Açık", true, 4.6, 105));
            
        // Turgut Özal Parkı
        parks.add(new Park("22", "Turgut Özal Parkı", null, 
            "Turgut Özal mahallesinde bulunan modern park. Anıt ve heykeller ile donatılmış.", 
            "Malatya Büyükşehir Belediyesi", "Turgut Özal Mahallesi, Merkez", 
            "Anıt ve Heykeller, Çocuk Oyun Alanı, Yürüyüş Yolları, Dinlenme Alanları", 
            "24 Saat Açık", true, 4.3, 89));
            
        // Şehit Fevzi Parkı
        parks.add(new Park("23", "Şehit Fevzi Parkı", null, 
            "Şehit Fevzi mahallesinde bulunan anıt park. Şehitlerin anısına yapılmış özel park.", 
            "Malatya Büyükşehir Belediyesi", "Şehit Fevzi Mahallesi, Merkez", 
            "Şehit Anıtı, Çocuk Oyun Alanı, Yürüyüş Yolları, Dinlenme Alanları", 
            "24 Saat Açık", true, 4.7, 112));
            
        // Yeni Emek Parkı
        parks.add(new Park("24", "Yeni Emek Parkı", null, 
            "Yeni Emek mahallesinde bulunan modern park. Spor alanları ve çocuk oyun parkları ile donatılmış.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Emek Mahallesi, Merkez", 
            "Spor Sahaları, Çocuk Oyun Alanı, Yürüyüş Yolları, Spor Ekipmanları", 
            "06:00 - 22:00", true, 4.2, 77));
            
        // Çilesiz Parkı
        parks.add(new Park("25", "Çilesiz Parkı", null, 
            "Çilesiz mahallesinde bulunan kompakt park. Yerel halkın sıkça kullandığı sosyal alan.", 
            "Malatya Büyükşehir Belediyesi", "Çilesiz Mahallesi, Merkez", 
            "Çocuk Oyun Alanı, Dinlenme Bankları, Yeşil Alan", 
            "24 Saat Açık", true, 4.0, 61));
            
        // Alacakapı Parkı
        parks.add(new Park("26", "Alacakapı Parkı", null, 
            "Alacakapı mahallesinde bulunan tarihi dokunun yanında yer alan park. Tarihi atmosfer ile modern park anlayışını birleştirir.", 
            "Malatya Büyükşehir Belediyesi", "Alacakapı Mahallesi, Merkez", 
            "Tarihi Dokuda Dinlenme Alanları, Çocuk Oyun Alanı, Yürüyüş Yolları", 
            "08:00 - 20:00", true, 4.1, 73));
            
        // Yeni Cami Parkı
        parks.add(new Park("27", "Yeni Cami Parkı", null, 
            "Yeni Cami mahallesinde bulunan modern park. Cami çevresinde yer alan yeşil alan.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Cami Mahallesi, Merkez", 
            "Cami Çevresi Dinlenme Alanları, Çocuk Oyun Alanı, Yürüyüş Yolları", 
            "24 Saat Açık", true, 4.0, 65));
            
        // Kırlangıç Parkı
        parks.add(new Park("28", "Kırlangıç Parkı", null, 
            "Kırlangıç mahallesinde bulunan doğal güzellikleri ile öne çıkan park. Kuş gözlem noktaları mevcut.", 
            "Malatya Büyükşehir Belediyesi", "Kırlangıç Mahallesi, Merkez", 
            "Kuş Gözlem Noktaları, Yürüyüş Yolları, Piknik Alanları, Çocuk Oyun Alanı", 
            "24 Saat Açık", true, 4.4, 91));
            
        // Gültepe Parkı
        parks.add(new Park("29", "Gültepe Parkı", null, 
            "Gültepe mahallesinde bulunan modern park. Spor alanları ve çocuk oyun parkları ile donatılmış.", 
            "Malatya Büyükşehir Belediyesi", "Gültepe Mahallesi, Merkez", 
            "Basketbol Sahası, Çocuk Oyun Alanı, Spor Ekipmanları, Yürüyüş Yolları", 
            "06:00 - 22:00", true, 4.1, 69));
            
        // Yeni Mahalle Parkı
        parks.add(new Park("30", "Yeni Mahalle Parkı", null, 
            "Yeni Mahalle'de bulunan kompakt park. Yerel halkın sıkça kullandığı sosyal alan.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Mahalle, Merkez", 
            "Çocuk Oyun Alanı, Dinlenme Bankları, Yeşil Alan", 
            "24 Saat Açık", true, 4.0, 58));
            
        // Çarşıbaşı Parkı
        parks.add(new Park("31", "Çarşıbaşı Parkı", null, 
            "Çarşıbaşı mahallesinde bulunan merkezi konumdaki park. Alışveriş alanlarının yanında yer alır.", 
            "Malatya Büyükşehir Belediyesi", "Çarşıbaşı Mahallesi, Merkez", 
            "Çocuk Oyun Alanı, Dinlenme Bankları, Yeşil Alan, Kafeler", 
            "06:00 - 23:00", true, 4.1, 71));
            
        // Şehit İbrahim Parkı
        parks.add(new Park("32", "Şehit İbrahim Parkı", null, 
            "Şehit İbrahim mahallesinde bulunan anıt park. Şehitlerin anısına yapılmış özel park.", 
            "Malatya Büyükşehir Belediyesi", "Şehit İbrahim Mahallesi, Merkez", 
            "Şehit Anıtı, Çocuk Oyun Alanı, Yürüyüş Yolları, Dinlenme Alanları", 
            "24 Saat Açık", true, 4.6, 98));
            
        // Yeni İnönü Parkı
        parks.add(new Park("33", "Yeni İnönü Parkı", null, 
            "Yeni İnönü mahallesinde bulunan modern park. Spor alanları ve çocuk oyun parkları ile donatılmış.", 
            "Malatya Büyükşehir Belediyesi", "Yeni İnönü Mahallesi, Merkez", 
            "Spor Sahaları, Çocuk Oyun Alanı, Yürüyüş Yolları, Spor Ekipmanları", 
            "06:00 - 22:00", true, 4.2, 75));
            
        // Yeni Fatih Parkı
        parks.add(new Park("34", "Yeni Fatih Parkı", null, 
            "Yeni Fatih mahallesinde bulunan kompakt park. Yerel halkın sıkça kullandığı sosyal alan.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Fatih Mahallesi, Merkez", 
            "Çocuk Oyun Alanı, Dinlenme Bankları, Yeşil Alan", 
            "24 Saat Açık", true, 4.0, 62));
            
        // Yeni Gazi Parkı
        parks.add(new Park("35", "Yeni Gazi Parkı", null, 
            "Yeni Gazi mahallesinde bulunan modern park. Spor alanları ve çocuk oyun parkları ile donatılmış.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Gazi Mahallesi, Merkez", 
            "Basketbol Sahası, Çocuk Oyun Alanı, Spor Ekipmanları, Yürüyüş Yolları", 
            "06:00 - 22:00", true, 4.1, 67));
            
        // Yeni Hürriyet Parkı
        parks.add(new Park("36", "Yeni Hürriyet Parkı", null, 
            "Yeni Hürriyet mahallesinde bulunan modern park. Spor alanları ve çocuk oyun parkları ile donatılmış.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Hürriyet Mahallesi, Merkez", 
            "Spor Sahaları, Çocuk Oyun Alanı, Yürüyüş Yolları, Spor Ekipmanları", 
            "06:00 - 22:00", true, 4.2, 74));
            
        // Yeni Cumhuriyet Parkı
        parks.add(new Park("37", "Yeni Cumhuriyet Parkı", null, 
            "Yeni Cumhuriyet mahallesinde bulunan tarihi park. Tarihi dokunun yanında yer alan yeşil alan.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Cumhuriyet Mahallesi, Merkez", 
            "Tarihi Dokuda Dinlenme Alanları, Çocuk Oyun Alanı, Yürüyüş Yolları", 
            "08:00 - 20:00", true, 4.0, 59));
            
        // Yeni Atatürk Parkı
        parks.add(new Park("38", "Yeni Atatürk Parkı", null, 
            "Yeni Atatürk mahallesinde bulunan büyük park. Anıt ve heykeller ile donatılmış tarihi park.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Atatürk Mahallesi, Merkez", 
            "Anıt ve Heykeller, Çocuk Oyun Alanı, Yürüyüş Yolları, Dinlenme Alanları", 
            "24 Saat Açık", true, 4.3, 83));
            
        // Yeni Çarşı Parkı
        parks.add(new Park("39", "Yeni Çarşı Parkı", null, 
            "Yeni Çarşı mahallesinde bulunan merkezi konumdaki park. Alışveriş merkezlerinin yanında yer alır.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Çarşı Mahallesi, Merkez", 
            "Çocuk Oyun Alanı, Dinlenme Bankları, Yeşil Alan, Kafeler", 
            "06:00 - 23:00", true, 4.1, 70));
            
        // Yeni Orduze Parkı
        parks.add(new Park("40", "Yeni Orduze Parkı", null, 
            "Yeni Orduze mahallesinde bulunan doğal güzellikleri ile öne çıkan park. Piknik alanları mevcut.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Orduze Mahallesi, Merkez", 
            "Piknik Alanları, Çocuk Oyun Alanı, Yürüyüş Yolları, Doğal Güzellikler", 
            "24 Saat Açık", true, 4.4, 86));
            
        // Yeni Çamlıca Parkı
        parks.add(new Park("41", "Yeni Çamlıca Parkı", null, 
            "Yeni Çamlıca mahallesinde bulunan doğal güzellikleri ile öne çıkan park. Çam ağaçları ile kaplı.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Çamlıca Mahallesi, Merkez", 
            "Çam Ağaçları, Yürüyüş Yolları, Piknik Alanları, Çocuk Oyun Alanı", 
            "24 Saat Açık", true, 4.5, 93));
            
        // Yeni Gündüzbey Parkı
        parks.add(new Park("42", "Yeni Gündüzbey Parkı", null, 
            "Yeni Gündüzbey mahallesinde bulunan modern park. Spor alanları ve çocuk oyun parkları ile donatılmış.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Gündüzbey Mahallesi, Merkez", 
            "Basketbol Sahası, Çocuk Oyun Alanı, Spor Ekipmanları, Yürüyüş Yolları", 
            "06:00 - 22:00", true, 4.1, 66));
            
        // Yeni Beydağı Parkı
        parks.add(new Park("43", "Yeni Beydağı Parkı", null, 
            "Yeni Beydağı mahallesinde bulunan doğal güzellikleri ile öne çıkan park. Manzara seyir noktaları mevcut.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Beydağı Mahallesi, Merkez", 
            "Manzara Seyir Noktaları, Yürüyüş Yolları, Piknik Alanları, Çocuk Oyun Alanı", 
            "24 Saat Açık", true, 4.6, 104));
            
        // Yeni Turgut Özal Parkı
        parks.add(new Park("44", "Yeni Turgut Özal Parkı", null, 
            "Yeni Turgut Özal mahallesinde bulunan modern park. Anıt ve heykeller ile donatılmış.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Turgut Özal Mahallesi, Merkez", 
            "Anıt ve Heykeller, Çocuk Oyun Alanı, Yürüyüş Yolları, Dinlenme Alanları", 
            "24 Saat Açık", true, 4.3, 88));
            
        // Yeni Şehit Fevzi Parkı
        parks.add(new Park("45", "Yeni Şehit Fevzi Parkı", null, 
            "Yeni Şehit Fevzi mahallesinde bulunan anıt park. Şehitlerin anısına yapılmış özel park.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Şehit Fevzi Mahallesi, Merkez", 
            "Şehit Anıtı, Çocuk Oyun Alanı, Yürüyüş Yolları, Dinlenme Alanları", 
            "24 Saat Açık", true, 4.7, 111));
            
        // Yeni Emek Parkı
        parks.add(new Park("46", "Yeni Emek Parkı", null, 
            "Yeni Emek mahallesinde bulunan modern park. Spor alanları ve çocuk oyun parkları ile donatılmış.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Emek Mahallesi, Merkez", 
            "Spor Sahaları, Çocuk Oyun Alanı, Yürüyüş Yolları, Spor Ekipmanları", 
            "06:00 - 22:00", true, 4.2, 76));
            
        // Yeni Çilesiz Parkı
        parks.add(new Park("47", "Yeni Çilesiz Parkı", null, 
            "Yeni Çilesiz mahallesinde bulunan kompakt park. Yerel halkın sıkça kullandığı sosyal alan.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Çilesiz Mahallesi, Merkez", 
            "Çocuk Oyun Alanı, Dinlenme Bankları, Yeşil Alan", 
            "24 Saat Açık", true, 4.0, 60));
            
        // Yeni Alacakapı Parkı
        parks.add(new Park("48", "Yeni Alacakapı Parkı", null, 
            "Yeni Alacakapı mahallesinde bulunan tarihi dokunun yanında yer alan park. Tarihi atmosfer ile modern park anlayışını birleştirir.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Alacakapı Mahallesi, Merkez", 
            "Tarihi Dokuda Dinlenme Alanları, Çocuk Oyun Alanı, Yürüyüş Yolları", 
            "08:00 - 20:00", true, 4.1, 72));
            
        // Yeni Yeni Cami Parkı
        parks.add(new Park("49", "Yeni Yeni Cami Parkı", null, 
            "Yeni Yeni Cami mahallesinde bulunan modern park. Cami çevresinde yer alan yeşil alan.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Yeni Cami Mahallesi, Merkez", 
            "Cami Çevresi Dinlenme Alanları, Çocuk Oyun Alanı, Yürüyüş Yolları", 
            "24 Saat Açık", true, 4.0, 64));
            
        // Yeni Kırlangıç Parkı
        parks.add(new Park("50", "Yeni Kırlangıç Parkı", null, 
            "Yeni Kırlangıç mahallesinde bulunan doğal güzellikleri ile öne çıkan park. Kuş gözlem noktaları mevcut.", 
            "Malatya Büyükşehir Belediyesi", "Yeni Kırlangıç Mahallesi, Merkez", 
            "Kuş Gözlem Noktaları, Yürüyüş Yolları, Piknik Alanları, Çocuk Oyun Alanı", 
            "24 Saat Açık", true, 4.4, 90));
        
        return parks;
    }
    
    @Override
    public String toString() {
        return "Park{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", manager='" + manager + '\'' +
                ", isOpen=" + isOpen +
                ", rating=" + rating +
                ", reviewCount=" + reviewCount +
                '}';
    }
} 