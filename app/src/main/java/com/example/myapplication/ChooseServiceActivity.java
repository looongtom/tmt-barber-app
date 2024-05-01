package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.ChooseServiceRecycleViewAdapter;
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.dal.ServiceDataSource;
import com.example.myapplication.dal.TimeSlotDataSource;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.Service;
import com.example.myapplication.model.TimeSlot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChooseServiceActivity extends AppCompatActivity implements ChooseServiceRecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    private ChooseServiceRecycleViewAdapter adapter;
    private Button btNext;
    private TextView tvBarberInfo,tvQuantityService;
    private DatabaseHelper db;
    private Account account;
    private Set<Integer> listIdService=new HashSet<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_service);

        recyclerView = findViewById(R.id.recyleService);
        btNext = findViewById(R.id.btNext);
        tvBarberInfo = findViewById(R.id.txtBarberInfo);
        tvQuantityService = findViewById(R.id.tvQuantityService);
        adapter = new ChooseServiceRecycleViewAdapter();
        db = new DatabaseHelper(this);

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listIdService.size()==0){
                    Toast.makeText(ChooseServiceActivity.this,"Please choose at least one service",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent= new Intent(ChooseServiceActivity.this,ChooseTimeSlotActivity.class);
                intent.putExtra("account",account);
                intent.putExtra("listIdService",new HashSet<>(listIdService));

                //get current date
                String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

                TimeSlotDataSource timeSlotDataSource = new TimeSlotDataSource(ChooseServiceActivity.this);

                List<TimeSlot> list=  timeSlotDataSource.getTimeSlotByBarberIdAndDate(account.getId(),date);
                if(list==null){
                    list=timeSlotDataSource.insertTimeSlotForDate(date,account.getId());
                }
                intent.putExtra("listTimeSlot",new ArrayList<>(list));
                startActivity(intent);
            }
        });

        account = (Account) getIntent().getSerializableExtra("account");
        tvBarberInfo.setText("Barber: "+account.getName());

    }

    @Override
    public void onItemClick(View view, int pos) {
        Service service=adapter.getItem(pos);
        //set check box for view
        CheckBox checkBox=view.findViewById(R.id.cbChoose);
        if(listIdService.contains(service.getId())){
            checkBox.setChecked(!checkBox.isChecked());
            listIdService.remove(service.getId());
        }else{
            checkBox.setChecked(!checkBox.isChecked());
            listIdService.add(service.getId());
        }
        tvQuantityService.setText(listIdService.size()+" services selected");
    }
    @Override
    public void onResume() {
        super.onResume();
        ServiceDataSource serviceDataSource=new ServiceDataSource(this);
        List<Service> list=(List<Service>) serviceDataSource.selectAllService(this);
        adapter.setList(list);
    }
}