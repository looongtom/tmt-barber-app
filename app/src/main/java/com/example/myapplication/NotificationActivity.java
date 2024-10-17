package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.adapter.NotificationsAdapter;
import com.example.myapplication.model.hairfast.HairFastWS;
import com.example.myapplication.model.notification.Notification;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity implements NotificationsAdapter.ItemListener {

    private RecyclerView notificationsRecyclerView;
    private NotificationsAdapter adapter;
    private List<Notification> notifications=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //get intent notification
        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy data or load actual notifications here
        notifications = (ArrayList<Notification>) getIntent().getSerializableExtra("notifications");

        if (notifications != null) {
            adapter = new NotificationsAdapter(notifications);
            notificationsRecyclerView.setAdapter(adapter);
        }
        adapter.setItemListener(this);
        adapter.setNotifications(notifications);
    }

    @Override
    public void onCLick(View view, int position) {
        Notification notification=adapter.getItem(position);
        if (notification.getType().equals(Notification.HairfastType)){
            Toast.makeText(this, "Hairfast: "+notification.getHairFastWS().toString(), Toast.LENGTH_SHORT).show();
            HairFastWS hairFastWS = (HairFastWS) notification.getHairFastWS() ;

//            Picasso.get().load(hairFastWS.getGeneratedImgCloud()).into(resultImg);
            Intent intent=new Intent(this, DetailHairFastActivity.class);
            intent.putExtra("hairFastWS",hairFastWS);
            startActivity(intent);

        }else if (notification.getType().equals(Notification.BookingType)){
            Toast.makeText(this, "Booking: "+notification.getBookingResponse().toString(), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Unknown: "+notification.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
