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
import com.example.myapplication.adapter.ServiceRecycleViewAdapter;
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.dal.ServiceDataSource;
import com.example.myapplication.model.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentService extends Fragment implements ServiceRecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    ServiceRecycleViewAdapter adapter;
    private Button btAdd;
    private DatabaseHelper db;

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
        adapter=new ServiceRecycleViewAdapter();
        db=new DatabaseHelper(getContext());

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);

        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

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
    public void onItemClick(View view, int pos) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
        if(roleId!=1){
            return;
        }else{
            Service service=adapter.getItem(pos);
            Intent intent=new Intent(getContext(), UpdateDeleteServiceActivity.class);
            intent.putExtra("service",service);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ServiceDataSource serviceDataSource=new ServiceDataSource(getContext());
        List<Service> list=(List<Service>) serviceDataSource.selectAllService(getContext());
        for(Service s: list){
            System.out.println(s.getName());
        }
        adapter.setList(list);
    }
}
