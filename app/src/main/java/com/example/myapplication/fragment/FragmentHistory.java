package com.example.myapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.myapplication.adapter.BookingAdapter;
import com.example.myapplication.dal.BookingDataSource;
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.model.Booking;

import java.util.List;

public class FragmentHistory  extends Fragment {
    BookingAdapter adapter;
    private RecyclerView recyclerView;
    private DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.rcvListBooking);
        adapter=new BookingAdapter(getContext());
        db=new DatabaseHelper(getContext());

        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


    }


    @Override
    public void onResume() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
        int userId = sharedPreferences.getInt("userId", -1);
        String userName=sharedPreferences.getString("username","");

        super.onResume();
        BookingDataSource bookingDataSource = new BookingDataSource(getContext());
        List<Booking> bookings = bookingDataSource.getBookingByUserId(getContext(),userId);
        adapter.setData(bookings);
    }
}
