package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.myapplication.adapter.ChooseServiceRecycleViewAdapter;
import com.example.myapplication.dal.BookingDetailDataSource;
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.dal.ServiceDataSource;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.BookingDetail;
import com.example.myapplication.model.Service;
import com.example.myapplication.model.service.Servicing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UpdateChooseService extends AppCompatActivity implements ChooseServiceRecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    private ChooseServiceRecycleViewAdapter adapter;
    private Button btNext;
    private TextView tvBarberInfo, tvQuantityService;
    private DatabaseHelper db;
    private Account account;
    private Set<Integer> listIdService = new HashSet<>();
    private List<Integer> listChoosenServices;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getBooleanExtra("RETURN_TO_A", false)) {
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_choose_service);


        initView();
        adapter = new ChooseServiceRecycleViewAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

        int bookingId = getIntent().getIntExtra("bookingId", 0);


        BookingDetailDataSource bookingDetailDataSource = new BookingDetailDataSource(this);
        listChoosenServices = bookingDetailDataSource.getListServiceByBookingId(bookingId);
        listIdService.addAll(listChoosenServices);
        tvQuantityService.setText(listIdService.size() + " services selected");

        adapter.setSetChoosenService(listIdService);

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double total = 0;
                bookingDetailDataSource.deleteByBookingId(bookingId);
                for (int serviceId : listIdService) {
                    BookingDetail bookingDetail = new BookingDetail();
                    bookingDetail.setBookingId(bookingId);
                    bookingDetail.setServiceId(serviceId);
                    bookingDetailDataSource.insert(bookingDetail);

                    ServiceDataSource serviceDataSource = new ServiceDataSource(UpdateChooseService.this);
                    Service service = serviceDataSource.getById(serviceId);
                    total += service.getPrice();
                }
                finish();
            }
        });

    }

    private void initView() {
        recyclerView = findViewById(R.id.recyleService);
        btNext = findViewById(R.id.btNext);
        tvBarberInfo = findViewById(R.id.txtBarberInfo);
        tvQuantityService = findViewById(R.id.tvQuantityService);
        db = new DatabaseHelper(this);
    }

    @Override
    public void onItemClick(View view, int pos) {
        Servicing service = adapter.getItem(pos);
        //set check box for view
        CheckBox checkBox = view.findViewById(R.id.cbChoose);
        if (listIdService.contains(service.getId())) {
            checkBox.setChecked(!checkBox.isChecked());
            listIdService.remove(service.getId());
        } else {
            checkBox.setChecked(!checkBox.isChecked());
            listIdService.add(service.getId());
        }
        tvQuantityService.setText(listIdService.size() + " services selected");
    }


    @Override
    public void onResume() {
        super.onResume();
        ServiceDataSource serviceDataSource = new ServiceDataSource(this);
//        List<Service> list = (List<Service>) serviceDataSource.selectAllService(this);
        List<Servicing> list=null;
        adapter.setList(list);
    }
}