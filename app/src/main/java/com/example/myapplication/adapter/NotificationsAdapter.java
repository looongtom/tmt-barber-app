package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.notification.Notification;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {

    private List<Notification> notifications;
    private ItemListener itemListener;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public NotificationsAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public Notification getItem(int position) {
        return notifications.get(position);
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.titleTextView.setText(notification.getTitle());
        holder.messageTextView.setText(notification.getMessage());
        holder.timestampTextView.setText(notification.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView, messageTextView, timestampTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener != null){
                itemListener.onCLick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener{
        void onCLick(View view, int position);
    }
}

