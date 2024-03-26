package com.example.myapplication.fragment;

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

import com.example.myapplication.R;
import com.example.myapplication.adapter.BarberRecycleViewAdapter;
import com.example.myapplication.dal.AccountDataSource;
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.model.Account;

import java.util.List;

public class FragmentHome  extends Fragment implements BarberRecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    private BarberRecycleViewAdapter adapter;
    private DatabaseHelper db;
    private Button btManageBarber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.rcvStaff);
        btManageBarber=view.findViewById(R.id.btAddBarber);
        adapter=new BarberRecycleViewAdapter(getContext());
        db=new DatabaseHelper(getContext());

        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

        btManageBarber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Click to add barber", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemCLick(View view, int pos) {
        Toast.makeText(getContext(), "Click on barber", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        AccountDataSource accountDataSource=new AccountDataSource(getContext());
        List<Account> list=(List<Account>) accountDataSource.selectAccountsRoleStaff(getContext());
        adapter.setList(list);
    }
}
