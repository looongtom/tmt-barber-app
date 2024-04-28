package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AddBarberActivity;
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
    private TextView txtName;

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
        txtName=view.findViewById(R.id.txtName);
        adapter=new BarberRecycleViewAdapter(getContext());
        db=new DatabaseHelper(getContext());

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
        int userId = sharedPreferences.getInt("userId", -1);
        String userName=sharedPreferences.getString("username","");
        txtName.setText(userName);

        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

        if(roleId!=1){
            btManageBarber.setVisibility(View.GONE);
        }

        btManageBarber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), AddBarberActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemCLick(View view, int pos) {

    }

    @Override
    public void onResume() {
        super.onResume();
        AccountDataSource accountDataSource=new AccountDataSource(getContext());
        List<Account> list=(List<Account>) accountDataSource.selectAccountsRoleStaff(getContext());
            adapter.setList(list);
    }
}
