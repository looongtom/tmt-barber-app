package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import androidx.appcompat.widget.SearchView;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.adapter.NotificationsAdapter;
import com.example.myapplication.api.ApiNotificationService;
import com.example.myapplication.auth.TokenManager;
import com.example.myapplication.model.booking.response.BookingResponse;
import com.example.myapplication.model.hairfast.HairFastWS;
import com.example.myapplication.model.notification.Notification;
import com.example.myapplication.model.notification.request.FindNotificationRequest;
import com.example.myapplication.model.notification.response.GetListNotification;
import com.example.myapplication.model.notification.response.NotificationResponse;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class NotificationActivity extends AppCompatActivity implements NotificationsAdapter.ItemListener {
    public static final String TAG = NotificationActivity.class.getName();

    private RecyclerView notificationsRecyclerView;
    private NotificationsAdapter adapter;
    private ArrayAdapter<String> queryAdapter;
    private List<Notification> notifications = new ArrayList<>();
    private ImageView searchView;
    private Long queryTime;
    private TokenManager tokenManager;
    private String[] notiQueries ;
    private Spinner spinner;
    private Integer userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        notiQueries = getResources().getStringArray(R.array.noti_query_options);

        setContentView(R.layout.activity_notification);
        spinner = findViewById(R.id.spinner);
        tokenManager = new TokenManager(this);

        //get intent notification
        searchView = findViewById(R.id.search);
        searchView.setFocusable(false);
        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        queryAdapter=new ArrayAdapter<String>(NotificationActivity.this, android.R.layout.simple_spinner_item, notiQueries);
        queryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(queryAdapter);

        String notiQuery = sharedPreferences.getString("gender", "");
        if(notiQuery.equals("Thông báo hôm nay")) spinner.setSelection(1);
        else if(notiQuery.equals("Thông báo tuần này")) spinner.setSelection(2);
        else if(notiQuery.equals("Thông báo tháng này")) spinner.setSelection(3);
        else spinner.setSelection(0);

        // Dummy data or load actual notifications here
        notifications = (ArrayList<Notification>) getIntent().getSerializableExtra("notifications");

        if (notifications != null) {
            adapter = new NotificationsAdapter(notifications);
            notificationsRecyclerView.setAdapter(adapter);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        sendApiGetNotification(new FindNotificationRequest(userId, 0L, getEndToday()));
                        break;
                    case 1:
                        sendApiGetNotification(new FindNotificationRequest(userId, getToday(), getEndToday()));
                        break;
                    case 2:
                        sendApiGetNotification(new FindNotificationRequest(userId, getStartWeek(), getEndToday()));
                        break;
                    case 3:
                        sendApiGetNotification(new FindNotificationRequest(userId, getStartMonth(), getEndToday()));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                sendApiGetNotification(new FindNotificationRequest(userId, 0L, getEndToday()));
            }

        });
        adapter.setItemListener(this);
        adapter.setNotifications(notifications);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(NotificationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = "";
                        if (month > 8) {
                            date = dayOfMonth + "-" + (month + 1) + "-" + year;
                        } else {
                            date = dayOfMonth + "-0" + (month + 1) + "-" + year;
                        }
//                    convert date to Long
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date mDate = sdf.parse(date);
                            long timeInMilliseconds = mDate.getTime() / 1000;
                            queryTime = timeInMilliseconds;
                            Log.d(TAG, "userId: " + userId);
                            Log.d(TAG, "from: " + getStartTimestamp(mDate));
                            Log.d(TAG, "to: " + getEndTimestamp(mDate));
                            sendApiGetNotification(new FindNotificationRequest(userId, getStartTimestamp(mDate), getEndTimestamp(mDate)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onCLick(View view, int position) {
        Notification notification = adapter.getItem(position);
        if (notification.getType().equals(Notification.HairfastType)) {
            Toast.makeText(this, "Hairfast: " + notification.getHairFastWS().toString(), Toast.LENGTH_SHORT).show();
            HairFastWS hairFastWS = (HairFastWS) notification.getHairFastWS();

//            Picasso.get().load(hairFastWS.getGeneratedImgCloud()).into(resultImg);
            Intent intent = new Intent(this, DetailHairFastActivity.class);
            intent.putExtra("hairFastWS", hairFastWS);
            startActivity(intent);

        } else if (notification.getType().equals(Notification.BookingType)) {
            Toast.makeText(this, "Booking: " + notification.getBookingResponse().toString(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ViewBookingFromNoti.class);
            intent.putExtra("booking", notification.getBookingResponse());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Unknown: " + notification.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void sendApiGetNotification(FindNotificationRequest findNotificationRequest) {
        String accessToken = tokenManager.getAccessToken();
        ApiNotificationService.API_NOTIFICATION_SERVICE.findNotification(accessToken, findNotificationRequest).enqueue((new Callback<GetListNotification>() {
            @Override
            public void onResponse(Call<GetListNotification> call, Response<GetListNotification> response) {
                if (response.isSuccessful()) {
                    List<NotificationResponse> getListNotification = response.body().getData();
                    if (getListNotification != null) {
                        List<Notification> notifications = new ArrayList<>();
                        for (NotificationResponse notificationResponse : getListNotification) {
                            Notification notification = new Notification(notificationResponse.getTitle(), notificationResponse.getMessage(), notificationResponse.getTimestamp(), notificationResponse.getType());
                            Gson gson = new Gson();
                            switch (notificationResponse.getType()) {
                                case Notification.HairfastType:
                                    String rawData = notificationResponse.getRawData().toString();

                                    rawData = rawData.replace("=", ":");

                                    rawData = rawData.replaceAll("(:)(https?://[^,}]+)", "$1\"$2\"");

                                    HairFastWS hairFastWS = gson.fromJson(rawData, HairFastWS.class);
                                    notification.setHairFastWS(hairFastWS);
                                    break;
                                case Notification.BookingType:
                                    BookingResponse bookingResponse = gson.fromJson(notificationResponse.getRawData().toString(), BookingResponse.class);
                                    notification.setBookingResponse(bookingResponse);
                                    break;
                            }
                            notifications.add(notification);
                        }
                        adapter.setNotifications(notifications);
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(NotificationActivity.this, "No data", Toast.LENGTH_SHORT).show();
                        List<Notification> notifications = new ArrayList<>();
                        adapter.setNotifications(notifications);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(NotificationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NotificationActivity.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<GetListNotification> call, Throwable t) {
                Toast.makeText(NotificationActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "userId: " + userId);
        Log.d(TAG, "from: " + getToday());
        Log.d(TAG, "to: " + getEndToday());
//        sendApiGetNotification(new FindNotificationRequest(userId, getToday(), getEndToday()));
    }

    private Long getStartWeek() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date date = c.getTime();
        return date.getTime()/1000;
    }

    private Long getStartMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date date = c.getTime();
        return date.getTime()/1000;
    }

    private Long getToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(new Date());
        try {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();
            return timeInMilliseconds/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    private Long getEndToday(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(new Date());
        try {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();
            return (timeInMilliseconds + 86400000)/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    private Long getStartTimestamp(Date date) {
        // Create a Calendar instance and set the date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Set the time to 00:00:00
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Return the updated Date
        return calendar.getTime().getTime() / 1000;
    }

    private Long getEndTimestamp(Date date) {
        // Create a Calendar instance and set the date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Set the time to 23:59:59
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        // Return the updated Date
        return calendar.getTime().getTime() / 1000;

    }

}
