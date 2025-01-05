package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.adapter.ChooseGeneratedResultAdapter;
import com.example.myapplication.adapter.ChooseTimeSlotRecycleViewAdapter;
import com.example.myapplication.adapter.GeneratedResultAdapter;
import com.example.myapplication.api.ApiPreviewImgService;
import com.example.myapplication.api.ApiTimeSlotService;
import com.example.myapplication.auth.TokenManager;
import com.example.myapplication.model.account.Account;
import com.example.myapplication.model.hairfast.request.RequestGetListGeneratedResult;
import com.example.myapplication.model.hairfast.response.GeneratedResult;
import com.example.myapplication.model.hairfast.response.GetListGeneratedResult;
import com.example.myapplication.model.timeslot.TimeSlot;
import com.example.myapplication.model.timeslot.request.FindTimeSlotRequest;
import com.example.myapplication.model.timeslot.response.FindTimeSlotResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseGeneratedResult extends AppCompatActivity implements GeneratedResultAdapter.ItemListener{

    private RecyclerView recyclerView;
    private GeneratedResultAdapter adapter;
    private List<GeneratedResult> generatedResults=new ArrayList<>();
    private TokenManager tokenManager ;
    private GeneratedResult chosenGeneratedResult=null;
    private Integer userId;
    private Account barber;
    private Set<Integer> listIdService=new HashSet<>();
    private List<TimeSlot> listTimeSlot=new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_generated_result);
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        barber = (Account) getIntent().getSerializableExtra("account");
        listIdService = (Set<Integer>) getIntent().getSerializableExtra("listIdService");

        tokenManager = new TokenManager(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new GeneratedResultAdapter(generatedResults,this);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
        adapter.setGeneratedResults(generatedResults);
        sendApiGetListGeneratedResult();
    }


    private void sendApiGetListGeneratedResult() {
        String accessToken = tokenManager.getAccessToken();
        // Call API to get list generated result
        ApiPreviewImgService.apiService.getListGeneratedResult(accessToken,new RequestGetListGeneratedResult(userId)).enqueue(new Callback<GetListGeneratedResult>() {
            @Override
            public void onResponse(Call<GetListGeneratedResult> call, Response<GetListGeneratedResult> response) {
                if (response.isSuccessful()) {
                    GetListGeneratedResult getListGeneratedResult = response.body();
                    if (getListGeneratedResult != null) {
                        generatedResults.clear();
                        generatedResults.addAll(getListGeneratedResult.getGenerateHairResponseList());
                        adapter.setGeneratedResults(generatedResults);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetListGeneratedResult> call, Throwable t) {
                Toast.makeText(ChooseGeneratedResult.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendApiGetListTimeslot(FindTimeSlotRequest request){
        ApiTimeSlotService.API_TIME_SLOT_SERVICE.findTimeSlot(request).enqueue(new Callback<FindTimeSlotResponse>() {
            @Override
            public void onResponse(Call<FindTimeSlotResponse> call, Response<FindTimeSlotResponse> response) {
                if (response.isSuccessful()) {
                    FindTimeSlotResponse findTimeSlotResponse = response.body();
                    List<TimeSlot> list =findTimeSlotResponse.getData();
                    Intent intent= new Intent(ChooseGeneratedResult.this,ChooseTimeSlotActivity.class);
                    intent.putExtra("account",barber);
                    intent.putExtra("listIdService",new HashSet<>(listIdService));
                    intent.putExtra("generatedResult",chosenGeneratedResult);

                    //put listTimeSlot in intent
                    listTimeSlot=list;
                    intent.putExtra("listTimeSlot",new ArrayList<>(listTimeSlot));
                    finish();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ChooseGeneratedResult.this,"error when get list time slot",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FindTimeSlotResponse> call, Throwable t) {
                Toast.makeText(ChooseGeneratedResult.this,"error when get list time slot",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onCLick(View view, int position) {
        GeneratedResult generatedResult = generatedResults.get(position);
        chosenGeneratedResult = generatedResult;
        String currentDate= new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        FindTimeSlotRequest request = new FindTimeSlotRequest(barber.getId(),currentDate,null,null);
        sendApiGetListTimeslot(request);
    }
}