package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.AddBarberActivity;
import com.example.myapplication.ChooseBarberActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.BarberRecycleViewAdapter;
import com.example.myapplication.dal.AccountDataSource;
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.model.Account;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class FragmentHome  extends Fragment implements BarberRecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    private BarberRecycleViewAdapter adapter;
    private DatabaseHelper db;
    private Button btManageBarber,btService,btBooking,btHistory;
    private TextView txtName;
    private WebView webView;

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
        webView=view.findViewById(R.id.webView);
        btService=view.findViewById(R.id.btService);
        btBooking=view.findViewById(R.id.btBooking);
        btHistory=view.findViewById(R.id.btHistory);
        String video ="<iframe width=\"400\" height=\"560\"\n" +
                "src=\"https://youtube.com/embed/-LgXdYkR4uQ?si=GkCI8YkxD2b74pXm\"\n" +
                "title=\"YouTube video player\"\n" +
                "frameborder=\"0\"\n" +
                "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\"\n" +
                "allowfullscreen></iframe>";
        webView.loadData(video,"text/html","utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {});
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

        btService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = getActivity().findViewById(R.id.viewPager);
                viewPager.setCurrentItem(2);
            }
        });

        btBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ChooseBarberActivity.class);
                startActivity(intent);
            }
        });

        btHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = getActivity().findViewById(R.id.viewPager);
                viewPager.setCurrentItem(1);
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
