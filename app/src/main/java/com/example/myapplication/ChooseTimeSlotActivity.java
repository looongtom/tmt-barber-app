package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.ChooseTimeSlotRecycleViewAdapter;
import com.example.myapplication.dal.AccountDataSource;
import com.example.myapplication.dal.BookingDataSource;
import com.example.myapplication.dal.BookingDetailDataSource;
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.dal.ServiceDataSource;
import com.example.myapplication.dal.TimeSlotDataSource;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.Booking;
import com.example.myapplication.model.BookingDetail;
import com.example.myapplication.model.TimeSlot;
import com.example.myapplication.notification.AlarmReceiver;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ChooseTimeSlotActivity extends AppCompatActivity implements ChooseTimeSlotRecycleViewAdapter.ItemListener {

    private ChooseTimeSlotRecycleViewAdapter adapter;
    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private EditText edtDate;
    private Button btNext;
    private TextView tvBarberInfo, tvQuantityService;


    private Boolean isChosen = false;

    private List<CardView> cardViewList = new ArrayList<>();
    private CardView choosenCardView = null;
    private List<TimeSlot> timeSlotList = new ArrayList<>();
    private TimeSlot choosenTimeSlot = null;
    private int barberId;
    private String queryDate;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time_slot);
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
        int userId = sharedPreferences.getInt("userId", -1);
        String userName = sharedPreferences.getString("username", "");

        edtDate = findViewById(R.id.eDate);
        edtDate.setShowSoftInputOnFocus(false);
        edtDate.setText(getToday());
        recyclerView = findViewById(R.id.recyclerTimeSlot);
        btNext = findViewById(R.id.btNext);
        tvBarberInfo = findViewById(R.id.txtBarberInfo);
        tvQuantityService = findViewById(R.id.tvQuantityService);
        adapter = new ChooseTimeSlotRecycleViewAdapter(this);
        db = new DatabaseHelper(this);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

        Account barber = (Account) getIntent().getSerializableExtra("account");
        Set<Integer> listIdService = (Set<Integer>) getIntent().getSerializableExtra("listIdService");
        barberId = barber.getId();
        tvBarberInfo.setText("Barber: " + barber.getName());
        tvQuantityService.setText(listIdService.size() + " services selected");

        timeSlotList = getIntent().getSerializableExtra("timeSlotList") == null ? new ArrayList<>() : (List<TimeSlot>) getIntent().getSerializableExtra("timeSlotList");


        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ChooseTimeSlotActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = "";
                        if (mMonth > 8) {
                            date = dayOfMonth + "-" + (month + 1) + "-" + year;
                        } else {
                            date = dayOfMonth + "-0" + (month + 1) + "-" + year;
                        }
                        if (compareDate(date, getToday())) {
                            edtDate.setText(date);
                            queryDate = date;
                        } else {
                            Toast.makeText(ChooseTimeSlotActivity.this, "Cannot choose date before today", Toast.LENGTH_SHORT).show();
                            edtDate.setText(getToday());
                            queryDate = getToday();
                        }

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        edtDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                timeSlotList.clear();
                TimeSlotDataSource timeSlotDataSource = new TimeSlotDataSource(ChooseTimeSlotActivity.this);
                timeSlotList = timeSlotDataSource.getTimeSlotByBarberIdAndDate(barberId, edtDate.getText().toString());
                if (timeSlotList == null) {
                    timeSlotList = timeSlotDataSource.insertTimeSlotForDate(edtDate.getText().toString(), barberId);
                }
                adapter.setList(timeSlotList);
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choosenTimeSlot == null) {
                    Toast.makeText(ChooseTimeSlotActivity.this, "Please choose a time slot", Toast.LENGTH_SHORT).show();
                    return;
                }
                TimeSlotDataSource timeSlotDataSource = new TimeSlotDataSource(ChooseTimeSlotActivity.this);
                timeSlotDataSource.chooseTimeSlot(choosenTimeSlot.getId());
                Intent intent = new Intent(ChooseTimeSlotActivity.this, BookingActivity.class);
                intent.putExtra("timeSlot", choosenTimeSlot);
                intent.putExtra("account", barber);
                intent.putExtra("listIdService", (Serializable) listIdService);
                intent.putExtra("queryDate", queryDate);

                BookingDataSource bookingDataSource = new BookingDataSource(ChooseTimeSlotActivity.this);
                Booking booking = new Booking();
                booking.setBarberId(barberId);
                booking.setUserId(userId);
                booking.setTime(queryDate);
                booking.setCreateTime(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
                booking.setSlotId(choosenTimeSlot.getId());
                booking.setPrice(1.0);
                booking.setStatus("Đã đặt");
                Booking insertBooking = bookingDataSource.insertBooking(getApplicationContext(), booking);
                intent.putExtra("idBooking", insertBooking.getId());

                BookingDetailDataSource bookingDetailDataSource = new BookingDetailDataSource(ChooseTimeSlotActivity.this);
                for (Integer idService : listIdService) {
                    BookingDetail bookingDetail = new BookingDetail(insertBooking.getId(), idService);
                    bookingDetailDataSource.insert(bookingDetail);
                }

                setAlarm();

                startActivity(intent);
            }
        });

    }

    public static Calendar convertStringToCalendar(String dateString, String timeString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = format.parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        Date time = formatTime.parse(timeString);
        calendar.set(Calendar.HOUR_OF_DAY, time.getHours());
        calendar.set(Calendar.MINUTE, time.getMinutes() - 30);
        return calendar;
    }

    private void setAlarm() {
        try {
//            Calendar calendar = convertStringToCalendar(choosenTimeSlot.getDate(), choosenTimeSlot.getTimeStart());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.MINUTE, 1);

            Intent intent = new Intent(ChooseTimeSlotActivity.this, AlarmReceiver.class);
            intent.setAction("MyAction");
            intent.putExtra("time", choosenTimeSlot.getTimeStart());

            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            pendingIntent = PendingIntent.getBroadcast(ChooseTimeSlotActivity.this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

        } catch (Exception e) {
            System.out.println("Error parsing date string: " + e.getMessage());
        }
    }

    public boolean compareDate(String str1, String str2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date1 = sdf.parse(str1);
            Date date2 = sdf.parse(str2);

            if (date1.compareTo(date2) < 0) {
                //before
                return false;
            } else {
//                after
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getToday() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String date = "";
        if (mMonth > 8) {
            date = mDay + "-" + (mMonth + 1) + "-" + mYear;
        } else {
            date = mDay + "-0" + (mMonth + 1) + "-" + mYear;
        }
        queryDate = date;
        return date;
    }

    @Override
    public void onItemCLick(View view, int pos) {
        CardView cardView = view.findViewById(R.id.cardView);
        TimeSlot timeSlot = adapter.getItem(pos);
        if (timeSlot.getStatus().equals("Available")) {
            if (isChosen) {
//                for (CardView card : cardViewList) {
//                    card.setCardBackgroundColor(getResources().getColor(R.color.white));
//                }
//                cardViewList.clear();
                choosenCardView.setCardBackgroundColor(getResources().getColor(R.color.white));
            }
            cardView.setCardBackgroundColor(getResources().getColor(R.color.primary));
//            cardViewList.add(cardView);
            choosenCardView = cardView;
            isChosen = true;
            choosenTimeSlot = timeSlot;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        TimeSlotDataSource timeSlotDataSource = new TimeSlotDataSource(this);
        List<TimeSlot> list = (List<TimeSlot>) timeSlotDataSource.getTimeSlotByBarberIdAndDate(barberId, queryDate);
        adapter.setList(list);
    }
}