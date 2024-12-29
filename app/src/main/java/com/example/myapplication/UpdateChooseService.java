package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.ChooseCategoryRecycleViewAdapter;
import com.example.myapplication.api.ApiBookingService;
import com.example.myapplication.api.ApiServicingService;
import com.example.myapplication.auth.TokenManager;
import com.example.myapplication.model.Account;
import com.example.myapplication.model.booking.request.UpdateBookingRequest;
import com.example.myapplication.model.booking.response.BookingResponse;
import com.example.myapplication.model.booking.response.ServicingResponse;
import com.example.myapplication.model.category.Category;
import com.example.myapplication.model.category.response.GetListCategoryResponse;
import com.example.myapplication.model.service.Servicing;
import com.google.protobuf.Any;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateChooseService extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChooseCategoryRecycleViewAdapter adapter;
    private Button btNext;
    private TextView tvBarberInfo, tvQuantityService;
    private Account account;
    private Set<Integer> listIdService = new HashSet<>();
    private List<Integer> listChoosenServices;
    private List<ServicingResponse> listServicing;
    private TokenManager tokenManager ;
    private BookingResponse booking;
    private Boolean isFirstLoad=true;
    private Integer count=0;

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
        this.isFirstLoad = true;
        booking = (BookingResponse) getIntent().getSerializableExtra("booking");
        listServicing = booking.getListServiceStruct();
        for (ServicingResponse servicing : listServicing) {
            listIdService.add(servicing.getId());
        }
        count=listIdService.size();
        initView();
//        if (isFirstLoad){
//            adapter.setChosenList(listIdService);
//            tvQuantityService.setText(listIdService.size()+" services selected");
//        }

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        tvQuantityService.setText(listIdService.size() + " services selected");

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listIdService.size()<=0){
                    Toast.makeText(UpdateChooseService.this,"Please choose at least 1 service",Toast.LENGTH_SHORT).show();
                    return;
                }
                sendApiUpdateBookingService();
                finish();
            }
        });

    }

    private void initView() {
        recyclerView = findViewById(R.id.recyleService);
        btNext = findViewById(R.id.btNext);
        tvBarberInfo = findViewById(R.id.txtBarberInfo);
        tvQuantityService = findViewById(R.id.tvQuantityService);
        adapter = new ChooseCategoryRecycleViewAdapter(this, new ChooseCategoryRecycleViewAdapter.ItemListener() {
            @Override
            public void onItemClick(int id) {
                Toast.makeText(UpdateChooseService.this,"id: "+id,Toast.LENGTH_SHORT).show();
                if (listIdService.contains(id) && !isFirstLoad){
                    listIdService.remove(id);
                }else if (!listIdService.contains(id) && !isFirstLoad){
                    listIdService.add(id);
                }
                Log.e("listIdService",listIdService.toString());
                tvQuantityService.setText(listIdService.size()+" services selected");
                count-=1;

                if (count ==0) isFirstLoad = false;

            }
        });
        adapter.setChosenList(listIdService);
        tokenManager = new TokenManager(this);
    }

    private void sendApiUpdateBookingService(){
        String accessToken = tokenManager.getAccessToken();
        UpdateBookingRequest updateBookingRequest = new UpdateBookingRequest();
        updateBookingRequest.setId(booking.getId());
        updateBookingRequest.setListService(listIdService);
        ApiBookingService.API_BOOKING_SERVICE.updateBookingService(accessToken, updateBookingRequest).enqueue(new Callback<Any>() {
            @Override
            public void onResponse(Call<Any> call, Response<Any> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UpdateChooseService.this,"Update booking service success",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateChooseService.this, UpdateBookingActivity.class);
                    intent.putExtra("booking",booking);
                    startActivity(intent);
                }else if(response.code()==401) {
                    Toast.makeText(UpdateChooseService.this, "Token is expired", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateChooseService.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Any> call, Throwable t) {
                Toast.makeText(UpdateChooseService.this,"Update booking service fail",Toast.LENGTH_SHORT).show();
            }
        });
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
                }else if(response.code()==401) {
                    Toast.makeText(UpdateChooseService.this, "Token is expired", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateChooseService.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<GetListCategoryResponse> call, Throwable t) {
                Toast.makeText(UpdateChooseService.this,"error when get list category",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sendApiGetListCategory();
    }
}