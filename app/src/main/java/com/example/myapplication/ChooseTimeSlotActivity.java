package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.dal.TimeSlotDataSource;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.TimeSlot;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class ChooseTimeSlotActivity extends AppCompatActivity implements ChooseTimeSlotRecycleViewAdapter.ItemListener {

    private ChooseTimeSlotRecycleViewAdapter adapter;
    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private EditText edtDate;
    private Button btNext;
    private TextView tvBarberInfo,tvQuantityService;


    private Boolean isChosen = false;

    private List<CardView> cardViewList=new ArrayList<>();
    private CardView choosenCardView=null;
    private List<TimeSlot> timeSlotList=new ArrayList<>();
    private TimeSlot choosenTimeSlot=null;
    private int barberId;
    private String queryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time_slot);

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
        tvBarberInfo.setText("Barber: "+barber.getName());
        tvQuantityService.setText(listIdService.size()+" services selected");

        timeSlotList= getIntent().getSerializableExtra("timeSlotList") == null ? new ArrayList<>() : (List<TimeSlot>) getIntent().getSerializableExtra("timeSlotList");


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
                            date = dayOfMonth+ "-" + (month + 1)+ "-"+year;
                        } else {
                            date = dayOfMonth + "-0" + (month + 1)+ "-"+year;
                        }
                        edtDate.setText(date);
                        queryDate = date;
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

                timeSlotList =   timeSlotDataSource.getTimeSlotByBarberIdAndDate(barberId,edtDate.getText().toString());
                adapter.setList(timeSlotList);
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choosenTimeSlot==null){
                    Toast.makeText(ChooseTimeSlotActivity.this, "Please choose a time slot", Toast.LENGTH_SHORT).show();
                    return;
                }
                TimeSlotDataSource timeSlotDataSource = new TimeSlotDataSource(ChooseTimeSlotActivity.this);
                timeSlotDataSource.chooseTimeSlot(choosenTimeSlot.getId());
                Intent intent = new Intent(ChooseTimeSlotActivity.this, BookingActivity.class);
                intent.putExtra("timeSlot",choosenTimeSlot);
                intent.putExtra("account",barber);
                intent.putExtra("listIdService", (Serializable) listIdService);
                intent.putExtra("queryDate",queryDate);
                startActivity(intent);
            }
        });

    }

    private String getToday() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String date = "";
        if (mMonth > 8) {
            date = mDay+ "-" + (mMonth + 1)+ "-"+mYear;
        } else {
            date = mDay + "-0" + (mMonth + 1)+ "-"+mYear;
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
        List<TimeSlot> list = (List<TimeSlot>) timeSlotDataSource.getTimeSlotByBarberIdAndDate(barberId,queryDate);
        adapter.setList(list);
    }
}