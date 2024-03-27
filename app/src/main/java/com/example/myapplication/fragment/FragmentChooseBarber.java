package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class FragmentChooseBarber  extends Fragment implements BarberRecycleViewAdapter.ItemListener{
    private BarberRecycleViewAdapter adapter;
    private DatabaseHelper db;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_barber, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.rcvBarber);
        adapter = new BarberRecycleViewAdapter(getContext());
        db = new DatabaseHelper(getContext());

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
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
