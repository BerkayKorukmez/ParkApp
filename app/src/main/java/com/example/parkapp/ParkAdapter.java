package com.example.parkapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * ParkAdapter - Park listesi için RecyclerView adapter'ı
 * 
 * Bu sınıf, park listesini RecyclerView'da göstermek için kullanılır.
 * Her park için detaylı bilgileri görüntüler ve tıklama olaylarını yönetir.
 * 
 * @author Berkay Körükmez
 * @version 1.0
 */
public class ParkAdapter extends RecyclerView.Adapter<ParkAdapter.ParkViewHolder> {
    
    // Constants
    private static final String TAG = "ParkAdapter";
    private static final String REVIEW_COUNT_FORMAT = "(%d değerlendirme)";
    
    // Data
    private final List<Park> parks;
    private final OnParkClickListener listener;
    
    /**
     * Park tıklama olayları için callback interface
     */
    public interface OnParkClickListener {
        /**
         * Park tıklandığında çağrılır
         * 
         * @param park Tıklanan park
         */
        void onParkClick(@NonNull Park park);
    }
    
    /**
     * ParkAdapter constructor
     * 
     * @param parks Park listesi
     * @param listener Tıklama listener'ı
     */
    public ParkAdapter(@NonNull List<Park> parks, @Nullable OnParkClickListener listener) {
        this.parks = parks;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public ParkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_park, parent, false);
        return new ParkViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ParkViewHolder holder, int position) {
        Park park = parks.get(position);
        holder.bind(park);
    }
    
    @Override
    public int getItemCount() {
        return parks.size();
    }
    
    /**
     * Park listesini günceller
     * 
     * @param newParks Yeni park listesi
     */
    public void updateParks(@NonNull List<Park> newParks) {
        parks.clear();
        parks.addAll(newParks);
        notifyDataSetChanged();
    }
    
    /**
     * ParkViewHolder - Park item'ları için ViewHolder
     */
    static class ParkViewHolder extends RecyclerView.ViewHolder {
        
        // UI Components
        private final ImageView parkImage;
        private final TextView parkName;
        private final TextView parkAddress;
        private final TextView parkDescription;
        private final RatingBar parkRating;
        private final TextView reviewCount;
        private final TextView openingHours;
        private final View statusIndicator;
        
        /**
         * ParkViewHolder constructor
         * 
         * @param itemView Item view
         */
        public ParkViewHolder(@NonNull View itemView) {
            super(itemView);
            
            // Initialize UI components
            parkImage = itemView.findViewById(R.id.parkImage);
            parkName = itemView.findViewById(R.id.parkName);
            parkAddress = itemView.findViewById(R.id.parkAddress);
            parkDescription = itemView.findViewById(R.id.parkDescription);
            parkRating = itemView.findViewById(R.id.parkRating);
            reviewCount = itemView.findViewById(R.id.reviewCount);
            openingHours = itemView.findViewById(R.id.openingHours);
            statusIndicator = itemView.findViewById(R.id.statusIndicator);
        }
        
        /**
         * Park verilerini view'a bağlar
         * 
         * @param park Bağlanacak park
         */
        public void bind(@NonNull Park park) {
            // Set basic information
            parkName.setText(park.getName());
            parkAddress.setText(park.getAddress());
            parkDescription.setText(park.getDescription());
            
            // Set rating and review count
            parkRating.setRating((float) park.getRating());
            reviewCount.setText(String.format(REVIEW_COUNT_FORMAT, park.getReviewCount()));
            
            // Set opening hours
            openingHours.setText(park.getOpeningHours());
            
            // Set status indicator
            setStatusIndicator(park.isOpen());
            
            // Set default park image
            parkImage.setImageResource(R.drawable.ic_park_marker);
            
            // Set click listener
            setupClickListener(park);
        }
        
        /**
         * Durum göstergesini ayarlar
         * 
         * @param isOpen Park açık durumu
         */
        private void setStatusIndicator(boolean isOpen) {
            if (isOpen) {
                statusIndicator.setBackgroundResource(R.drawable.status_open);
            } else {
                statusIndicator.setBackgroundResource(R.drawable.status_closed);
            }
        }
        
        /**
         * Tıklama olayını ayarlar
         * 
         * @param park Park objesi
         */
        private void setupClickListener(@NonNull Park park) {
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Get adapter reference through parent
                    ParkAdapter adapter = (ParkAdapter) getBindingAdapter();
                    if (adapter != null && adapter.listener != null) {
                        adapter.listener.onParkClick(park);
                    }
                }
            });
        }
    }
} 