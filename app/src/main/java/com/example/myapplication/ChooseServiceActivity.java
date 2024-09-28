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

import com.example.myapplication.adapter.CategoryRecycleViewAdapter;
import com.example.myapplication.adapter.ChooseCategoryRecycleViewAdapter;
import com.example.myapplication.adapter.ChooseServiceRecycleViewAdapter;
import com.example.myapplication.api.ApiServicingService;
import com.example.myapplication.auth.TokenManager;
import com.example.myapplication.dal.ServiceDataSource;
import com.example.myapplication.dal.TimeSlotDataSource;
import com.example.myapplication.model.TimeSlot;
import com.example.myapplication.model.account.Account;
import com.example.myapplication.model.category.Category;
import com.example.myapplication.model.category.response.GetListCategoryResponse;
import com.example.myapplication.model.service.Servicing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseServiceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChooseCategoryRecycleViewAdapter adapter;
    private Button btNext;
    private TextView tvBarberInfo,tvQuantityService;
    private Account account;
    private Set<Integer> listIdService=new HashSet<>();
    private TokenManager tokenManager ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

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
        tvBarberInfo.setText("Barber: "+account.getFullName());

    }

//    @Override
//    public void onItemClick(View view, int pos) {
//        Servicing service=adapter.getItem(pos);
//        //set check box for view
//        CheckBox checkBox=view.findViewById(R.id.cbChoose);
//        if(listIdService.contains(service.getId())){
//            checkBox.setChecked(!checkBox.isChecked());
//            listIdService.remove(service.getId());
//        }else{
//            checkBox.setChecked(!checkBox.isChecked());
//            listIdService.add(service.getId());
//        }
//        tvQuantityService.setText(listIdService.size()+" services selected");
//    }

    @Override
    public void onResume() {
        super.onResume();
        sendApiGetListCategory();
    }

    private void sendApiGetListCategory(){
        String accessToken = tokenManager.getAccessToken();
        ApiServicingService.API_SERVICING_SERVICE.getListCategory(accessToken).enqueue(new Callback<GetListCategoryResponse>() {


            @Override
            public void onResponse(Call<GetListCategoryResponse> call, Response<GetListCategoryResponse> response) {
                if(response.isSuccessful()){
                    GetListCategoryResponse getListCategoryResponse=response.body();
                    Map<String, List<Servicing>> serviceMap =getListCategoryResponse.getServiceMap();
                    List<Category> categories =new ArrayList<>();
                    for (Map.Entry<String, List<Servicing>> entry : serviceMap.entrySet()) {
                        categories.add(new Category(entry.getValue().get(0).getCategoryId(),entry.getKey(),entry.getValue()));
                    }

                    adapter.setList(categories);
                }
            }

            @Override
            public void onFailure(Call<GetListCategoryResponse> call, Throwable t) {
                Toast.makeText(ChooseServiceActivity.this,"error when get list category",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){
        setContentView(R.layout.activity_choose_service);
        recyclerView = findViewById(R.id.recyleService);
        btNext = findViewById(R.id.btNext);
        tvBarberInfo = findViewById(R.id.txtBarberInfo);
        tvQuantityService = findViewById(R.id.tvQuantityService);
        adapter = new ChooseCategoryRecycleViewAdapter(this);
        tokenManager = new TokenManager(this);
    }
}