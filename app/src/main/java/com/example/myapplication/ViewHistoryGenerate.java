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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.adapter.GeneratedResultAdapter;
import com.example.myapplication.adapter.NotificationsAdapter;
import com.example.myapplication.api.ApiPreviewImgService;
import com.example.myapplication.auth.TokenManager;
import com.example.myapplication.model.hairfast.HairFastWS;
import com.example.myapplication.model.hairfast.request.RequestGetListGeneratedResult;
import com.example.myapplication.model.hairfast.response.DetailHairFastResponse;
import com.example.myapplication.model.hairfast.response.GeneratedResult;
import com.example.myapplication.model.hairfast.response.GetListGeneratedResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewHistoryGenerate extends AppCompatActivity implements GeneratedResultAdapter.ItemListener{

    private RecyclerView recyclerView;
    private GeneratedResultAdapter adapter;
    private List<GeneratedResult> generatedResults=new ArrayList<>();
    private TokenManager tokenManager;
    private Integer userId;
    private Button btBack,btCreate;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history_generate);
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        tokenManager = new TokenManager(this);

        btBack = findViewById(R.id.btBack);
        btCreate = findViewById(R.id.btCreate);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new GeneratedResultAdapter(generatedResults,this);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
        adapter.setGeneratedResults(generatedResults);
        sendApiGetListGeneratedResult();

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewHistoryGenerate.this, GenerateHairStyle.class);
                finish();
                startActivity(intent);
            }
        });
    }

    private void sendApiGetListGeneratedResult() {
        String accessToken = tokenManager.getAccessToken();
        // Call API to get list generated result
        ApiPreviewImgService.apiService.getListGeneratedResult(accessToken,new RequestGetListGeneratedResult(userId)).enqueue(new Callback<GetListGeneratedResult>() {
            @Override
            public void onResponse(Call<GetListGeneratedResult> call, Response<GetListGeneratedResult> response) {
                if (response.isSuccessful()) {
                    GetListGeneratedResult getListGeneratedResult = response.body();
                    if (getListGeneratedResult != null && getListGeneratedResult.getGenerateHairResponseList().size()>0) {
                        generatedResults.clear();
                        generatedResults.addAll(getListGeneratedResult.getGenerateHairResponseList());
                        adapter.setGeneratedResults(generatedResults);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetListGeneratedResult> call, Throwable t) {
                Toast.makeText(ViewHistoryGenerate.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCLick(View view, int position) {
        GeneratedResult generatedResult = adapter.getItem(position);
        HairFastWS hairFastWS = new HairFastWS();
        hairFastWS.setGeneratedImgCloud(generatedResult.getGeneratedImg());
        hairFastWS.setColorImgCloud(generatedResult.getColorImg());
        hairFastWS.setSelfImgCloud(generatedResult.getSelfImg());
        hairFastWS.setShapeImgCloud(generatedResult.getShapeImg());

//                        redirect to DetailHairFastActivity
        Intent intent = new Intent(ViewHistoryGenerate.this, DetailHairFastActivity.class);
        intent.putExtra("hairFastWS", hairFastWS);
        startActivity(intent);
    }
}