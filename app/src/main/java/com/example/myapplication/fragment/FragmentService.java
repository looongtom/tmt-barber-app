package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ServiceRecycleViewAdapter;
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.dal.ServiceDataSource;
import com.example.myapplication.model.Service;

import java.util.List;

public class FragmentService extends Fragment implements ServiceRecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    ServiceRecycleViewAdapter adapter;
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
        adapter=new ServiceRecycleViewAdapter();
        db=new DatabaseHelper(getContext());

        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int pos) {
        Toast.makeText(getContext(), "Click on service", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        ServiceDataSource serviceDataSource=new ServiceDataSource(getContext());
        List<Service> list=(List<Service>) serviceDataSource.selectAllService(getContext());
        adapter.setList(list);
    }
}
