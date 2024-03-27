package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.adapter.BarberRecycleViewAdapter;
import com.example.myapplication.adapter.ChooseBarberRecycleViewAdapter;
import com.example.myapplication.dal.AccountDataSource;
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.model.Account;

import java.util.List;

public class ChooseBarberActivity extends AppCompatActivity implements ChooseBarberRecycleViewAdapter.ItemListener {
    private ChooseBarberRecycleViewAdapter adapter;
    private DatabaseHelper db;
    private RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_barber);

        recyclerView=findViewById(R.id.rcvBarber);
        adapter = new ChooseBarberRecycleViewAdapter(this);
        db = new DatabaseHelper(this);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener( this);
    }


    @Override
    public void onItemCLick(View view, int pos) {

    }
    @Override
    public void onResume() {
        super.onResume();
        AccountDataSource accountDataSource=new AccountDataSource(this);
        List<Account> list=(List<Account>) accountDataSource.selectAccountsRoleStaff(this);
        adapter.setList(list);
    }
}