package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.example.myapplication.AddServiceActivity;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.UpdateDeleteServiceActivity;
import com.example.myapplication.adapter.CategoryRecycleViewAdapter;
import com.example.myapplication.api.ApiServicingService;
import com.example.myapplication.auth.TokenManager;
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.dal.ServiceDataSource;
import com.example.myapplication.model.Service;
import com.example.myapplication.model.category.Category;
import com.example.myapplication.model.category.response.GetListCategoryResponse;
import com.example.myapplication.model.service.Servicing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentService extends Fragment implements CategoryRecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    private CategoryRecycleViewAdapter adapter;
    private Button btAdd;
    private TokenManager tokenManager ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_service,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recyleService);
        btAdd=view.findViewById(R.id.btAddService);
        adapter=new CategoryRecycleViewAdapter(getContext());
        tokenManager = new TokenManager(getContext());


        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);

        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        if(roleId!=1) {
            btAdd.setVisibility(View.GONE);
        }
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), AddServiceActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sendApiGetListCategory();

//        ServiceDataSource serviceDataSource=new ServiceDataSource(getContext());
//        List<Service> list=(List<Service>) serviceDataSource.selectAllService(getContext());
//        adapter.setList(list);
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
                Toast.makeText(getContext(), "error get list category", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
