package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.adapter.BarberRecycleViewAdapter;
import com.example.myapplication.adapter.ChooseBarberRecycleViewAdapter;
import com.example.myapplication.api.ApiAccountService;
import com.example.myapplication.dal.AccountDataSource;
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.model.account.Account;
import com.example.myapplication.model.account.response.GetListBarberResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseBarberActivity extends AppCompatActivity implements ChooseBarberRecycleViewAdapter.ItemListener {
    private ChooseBarberRecycleViewAdapter adapter;
    private RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_barber);

        recyclerView=findViewById(R.id.rcvBarber);
        adapter = new ChooseBarberRecycleViewAdapter(this);

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
        sendApiGetListBarber();
    }

    private void sendApiGetListBarber(){
        ApiAccountService.API_ACCOUNT_SERVICE.getListBarber().enqueue(new Callback<GetListBarberResponse>() {
            @Override
            public void onResponse(Call<GetListBarberResponse> call, Response<GetListBarberResponse> response) {
                if(response.isSuccessful()){
                    GetListBarberResponse list=response.body();
                    adapter.setList(list.getListBarber());

                }
            }

            @Override
            public void onFailure(Call<GetListBarberResponse> call, Throwable t) {
                Toast.makeText(ChooseBarberActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}