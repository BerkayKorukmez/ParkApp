package com.example.parkapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * ComplaintAdapter - Şikayet listesi için RecyclerView adapter'ı
 * 
 * Bu sınıf, şikayet listesini RecyclerView'da göstermek için kullanılır.
 * Her şikayet için detaylı bilgileri görüntüler ve durum göstergelerini yönetir.
 * 
 * @author Berkay Körükmez
 * @version 1.0
 */
public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {
    
    // Constants
    private static final String TAG = "ComplaintAdapter";
    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";
    
    // Status constants
    private static final String STATUS_PENDING = "Beklemede";
    private static final String STATUS_IN_PROGRESS = "İşleme Alındı";
    private static final String STATUS_RESOLVED = "Çözüldü";
    
    // Data
    private final List<Complaint> complaints;
    
    /**
     * ComplaintAdapter constructor
     * 
     * @param complaints Şikayet listesi
     */
    public ComplaintAdapter(@NonNull List<Complaint> complaints) {
        this.complaints = complaints;
    }
    
    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_complaint, parent, false);
        return new ComplaintViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        Complaint complaint = complaints.get(position);
        holder.bind(complaint);
    }
    
    @Override
    public int getItemCount() {
        return complaints.size();
    }
    
    /**
     * Şikayet listesini günceller
     * 
     * @param newComplaints Yeni şikayet listesi
     */
    public void updateComplaints(@NonNull List<Complaint> newComplaints) {
        complaints.clear();
        complaints.addAll(newComplaints);
        notifyDataSetChanged();
    }
    
    /**
     * ComplaintViewHolder - Şikayet item'ları için ViewHolder
     */
    static class ComplaintViewHolder extends RecyclerView.ViewHolder {
        
        // UI Components
        private final TextView parkName;
        private final TextView issueType;
        private final TextView description;
        private final TextView status;
        private final TextView date;
        private final View statusIndicator;
        
        // Date formatter
        private final SimpleDateFormat dateFormatter;
        
        /**
         * ComplaintViewHolder constructor
         * 
         * @param itemView Item view
         */
        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            
            // Initialize UI components
            parkName = itemView.findViewById(R.id.complaintParkName);
            issueType = itemView.findViewById(R.id.complaintIssueType);
            description = itemView.findViewById(R.id.complaintDescription);
            status = itemView.findViewById(R.id.complaintStatus);
            date = itemView.findViewById(R.id.complaintDate);
            statusIndicator = itemView.findViewById(R.id.complaintStatusIndicator);
            
            // Initialize date formatter
            dateFormatter = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        }
        
        /**
         * Şikayet verilerini view'a bağlar
         * 
         * @param complaint Bağlanacak şikayet
         */
        public void bind(@NonNull Complaint complaint) {
            // Set basic information
            parkName.setText(complaint.getParkName());
            issueType.setText(complaint.getIssueType());
            description.setText(complaint.getDescription());
            status.setText(complaint.getStatus());
            
            // Set formatted date
            date.setText(dateFormatter.format(complaint.getReportDate()));
            
            // Set status indicator
            setStatusIndicator(complaint.getStatus());
        }
        
        /**
         * Durum göstergesini ayarlar
         * 
         * @param status Şikayet durumu
         */
        private void setStatusIndicator(@NonNull String status) {
            switch (status) {
                case STATUS_PENDING:
                    statusIndicator.setBackgroundResource(R.drawable.status_pending);
                    break;
                case STATUS_IN_PROGRESS:
                    statusIndicator.setBackgroundResource(R.drawable.status_in_progress);
                    break;
                case STATUS_RESOLVED:
                    statusIndicator.setBackgroundResource(R.drawable.status_resolved);
                    break;
                default:
                    statusIndicator.setBackgroundResource(R.drawable.status_pending);
                    break;
            }
        }
    }
} 