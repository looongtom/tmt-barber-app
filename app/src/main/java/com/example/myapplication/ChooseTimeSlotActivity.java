package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.myapplication.adapter.ChooseTimeSlotRecycleViewAdapter;
import com.example.myapplication.dal.AccountDataSource;
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.dal.TimeSlotDataSource;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.TimeSlot;

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

    private Boolean isChosen = false;

    private List<CardView> cardViewList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time_slot);

        edtDate = findViewById(R.id.eDate);
        edtDate.setText(getToday());
        recyclerView = findViewById(R.id.recyclerTimeSlot);
        btNext = findViewById(R.id.btNext);
        adapter = new ChooseTimeSlotRecycleViewAdapter(this);
        db = new DatabaseHelper(this);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

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
                            date = year + "-" + (month + 1) + "-" + dayOfMonth;
                        } else {
                            date = year + "-0" + (month + 1) + "-" + dayOfMonth;
                        }
                        edtDate.setText(date);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
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
            date = mYear + "-" + (mMonth + 1) + "-" + mDay;
        } else {
            date = mYear + "-0" + (mMonth + 1) + "-" + mDay;
        }
        return date;
    }

    @Override
    public void onItemCLick(View view, int pos) {
        CardView cardView = view.findViewById(R.id.cardView);
        TimeSlot timeSlot = adapter.getItem(pos);
        if (timeSlot.getStatus().equals("Available")) {
            if (isChosen) {
                for (CardView card : cardViewList) {
                    card.setCardBackgroundColor(getResources().getColor(R.color.white));
                }
                cardViewList.clear();
            }
            cardView.setCardBackgroundColor(getResources().getColor(R.color.primary));
            cardViewList.add(cardView);
            isChosen = true;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        TimeSlotDataSource timeSlotDataSource = new TimeSlotDataSource(this);
        List<TimeSlot> list = (List<TimeSlot>) timeSlotDataSource.selectAllTimeSlot(this);
        adapter.setList(list);
    }
}