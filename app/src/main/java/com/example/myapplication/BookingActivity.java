package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.adapter.ChooseServiceRecycleViewAdapter;
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.dal.ServiceDataSource;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.Service;
import com.example.myapplication.model.TimeSlot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingActivity extends AppCompatActivity implements ChooseServiceRecycleViewAdapter.ItemListener {
    private TextView tvNameCus, tvNameBarber, tvDate, tvTime;
    private TimeSlot timeSlot;
    private Account account;
    private String queryDate;
    private RecyclerView recyclerView;
    private ChooseServiceRecycleViewAdapter adapter;
    private DatabaseHelper db;
    private List<Service> listService=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        tvNameCus = findViewById(R.id.txtUsername);
        tvNameBarber = findViewById(R.id.txtStaff);
        tvDate = findViewById(R.id.txtBookingTime);
        tvTime = findViewById(R.id.txtSlot);
        recyclerView=findViewById(R.id.rcvService);

        timeSlot= (TimeSlot) getIntent().getSerializableExtra("timeSlot");
        account = (Account) getIntent().getSerializableExtra("account");
        queryDate = getIntent().getStringExtra("queryDate");
        Set<Integer> listIdService=(Set<Integer>) getIntent().getSerializableExtra("listIdService");
        listService=getListService(listIdService);


        tvNameCus.setText("Customer");
        tvNameBarber.setText(account.getName());
        tvDate.setText(queryDate);
        tvTime.setText(timeSlot.getTimeStart());

        adapter = new ChooseServiceRecycleViewAdapter();
        adapter.setChoose(false);

        adapter.setList(listService);
        db=new DatabaseHelper(this);

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }

    private List<Service> getListService(Set<Integer> listIdService) {
        List<Service> listService=new ArrayList<>();
        ServiceDataSource db=new ServiceDataSource(this);
        for (Integer id:listIdService){
            listService.add(db.getById(id));
        }
        return listService;
    }

    @Override
    public void onItemClick(View view, int pos) {

    }
}