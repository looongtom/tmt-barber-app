package com.example.myapplication.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dal.AccountDataSource;
import com.example.myapplication.dal.BookingDetailDataSource;
import com.example.myapplication.dal.ServiceDataSource;
import com.example.myapplication.dal.TimeSlotDataSource;
import com.example.myapplication.fragment.FragmentHistory;
import com.example.myapplication.model.Booking;
import com.example.myapplication.model.BookingDetail;
import com.example.myapplication.model.Service;
import com.example.myapplication.model.TimeSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder>  {
    private List<Booking> list;
    private Context context;
    private ChooseServiceRecycleViewAdapter adapter;



    public BookingAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }


    public void setData(List<Booking> list){
        this.list = list;
        sortData(list);
    }

    public void sortData(List<Booking> list){
        //sort by create time desc with the value like 2021-06-01 12:00:00
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.sort((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
        }
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new BookingViewHolder(view);
    }

    public String getStaffName(int id){
        AccountDataSource db=new AccountDataSource(context);
        return db.getStaffByStaffId(id);
    }

    public String getStartTimeSlot(int id){
        TimeSlotDataSource db=new TimeSlotDataSource(context);
        return db.getTimeSlotById(id).getTimeStart();
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
        int userId = sharedPreferences.getInt("userId", -1);
        String userName=sharedPreferences.getString("username","");


        Booking booking=list.get(position);
        holder.txtUsername.setText(userName);
        holder.txtStaff.setText(getStaffName(booking.getBarberId()));
        holder.txtBookingTime.setText(booking.getCreateTime());
        holder.txtSlot.setText(getStartTimeSlot(booking.getSlotId()) + "   :   " + booking.getTime());
        holder.txtPrice.setText(booking.getPrice().toString());
        holder.txtStatus.setText(booking.getStatus());

        BookingDetailDataSource bookingDetailDataSource = new BookingDetailDataSource(context);
        List<BookingDetail> listBookingDetail = bookingDetailDataSource.getAllBookingDetail();
        List<Integer> listIdServices = bookingDetailDataSource.getListServiceByBookingId(booking.getId());
        List<Service> listService = getListService(listIdServices);

        adapter = new ChooseServiceRecycleViewAdapter();
        adapter.setChoose(false);
        adapter.setList(listService);

        LinearLayoutManager manager = new LinearLayoutManager(holder.recService.getContext(), RecyclerView.VERTICAL, false);
        holder.recService.setLayoutManager(manager);
        holder.recService.setAdapter(adapter);

    }

    private List<Service> getListService(List<Integer> listIdService) {
        List<Service> listService=new ArrayList<>();
        ServiceDataSource db=new ServiceDataSource(context);
        for (Integer id:listIdService){
            listService.add(db.getById(id));
        }
        return listService;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder{
        private TextView txtUsername,txtStaff,txtBookingTime,txtSlot,txtPrice,txtStatus;
        private RecyclerView recService;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsername=itemView.findViewById(R.id.txtUsername);
            txtStaff=itemView.findViewById(R.id.txtStaff);
            txtBookingTime=itemView.findViewById(R.id.txtBookingTime);
            txtSlot=itemView.findViewById(R.id.txtSlot);
            recService=itemView.findViewById(R.id.rcvService);
            txtPrice=itemView.findViewById(R.id.tv6);
            txtStatus=itemView.findViewById(R.id.txtStatus);
        }
    }

}
