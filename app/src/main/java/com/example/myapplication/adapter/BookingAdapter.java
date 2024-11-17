package com.example.myapplication.adapter;

import static com.example.myapplication.model.account.Account.RoleAdmin;
import static com.example.myapplication.model.account.Account.RoleBarber;
import static com.example.myapplication.model.account.Account.RoleUser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.UpdateBookingActivity;
import com.example.myapplication.UploadImage;
import com.example.myapplication.dal.AccountDataSource;
import com.example.myapplication.dal.BookingDataSource;
import com.example.myapplication.dal.BookingDetailDataSource;
import com.example.myapplication.dal.ResultDataSource;
import com.example.myapplication.dal.ServiceDataSource;
import com.example.myapplication.dal.TimeSlotDataSource;
import com.example.myapplication.model.BookingDetail;
import com.example.myapplication.model.Result;
import com.example.myapplication.model.Service;
import com.example.myapplication.model.booking.Booking;
import com.example.myapplication.model.booking.response.BookingResponse;
import com.example.myapplication.model.booking.response.ServicingResponse;
import com.example.myapplication.model.service.Servicing;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    private List<BookingResponse> list;
    private Context context;
    private ChooseServiceRecycleViewAdapter adapter;


    public BookingAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }


    public void setData(List<BookingResponse> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
        int userId = sharedPreferences.getInt("userId", -1);
        String userName = sharedPreferences.getString("username", "");

        if (roleId != RoleUser) {
            //return inflater.inflate(R.layout.fragment_history_staff,container,false);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_staff, parent, false);
            return new BookingViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new BookingViewHolder(view);
    }

    public void setImageSrc(ImageView img, String src) {
        if (src != null) {
            Picasso.get().load(src).resize(350, 450).into(img);
        }
    }

    private String convertTimestamp(Long bookedTime) {
        return new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date(bookedTime));
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
        int userId = sharedPreferences.getInt("userId", -1);
        int totalPrice=0;
        String userName = sharedPreferences.getString("username", "");


        BookingResponse booking = list.get(position);
        holder.txtUsername.setText(booking.getCustomerName());
        holder.txtStaff.setText(booking.getBarberName());
        holder.txtBookingTime.setText(booking.getTimeSlot().getBookedDate());
        holder.txtSlot.setText(booking.getTimeSlot().getStartTime());
//        holder.txtSlot.setText(getStartTimeSlot(booking.getTimeSlotId()));
        holder.txtPrice.setText(booking.getPrice().toString());
        holder.txtStatus.setText(booking.getStatus());

        for (ServicingResponse servicingResponse : booking.getListServiceStruct()) {
            totalPrice+=servicingResponse.getPrice();
        }
        holder.txtPrice.setText(totalPrice+"");

        Result result = new Result();
        ResultDataSource resultDataSource = new ResultDataSource(context);
        Integer resultId = booking.getResultId();
        if (resultId != null){
            result = resultDataSource.getById(resultId);
        }
        if (booking.getStatus().equals("Canceled")) {
            holder.txtStatus.setTextColor(context.getResources().getColor(R.color.choosen_color));
//            set text style to bold
            holder.txtStatus.setTypeface(null, Typeface.BOLD);
        } else if (booking.getStatus().equals("Đã nhận khách")) {
            holder.txtStatus.setTextColor(context.getResources().getColor(android.R.color.holo_orange_light));
            holder.txtStatus.setTypeface(null, Typeface.BOLD);
        }

        List<ServicingResponse> listService=new ArrayList<>();
        for (ServicingResponse servicingResponse : booking.getListServiceStruct()) {
            listService.add(new ServicingResponse(
                    servicingResponse.getId(),
                    servicingResponse.getName(),
                    servicingResponse.getPrice(),
                    servicingResponse.getDescription(),
                    servicingResponse.getUrl()));
        }
        adapter = new ChooseServiceRecycleViewAdapter();
        adapter.setChoose(false);
        adapter.setList(listService);

        LinearLayoutManager manager = new LinearLayoutManager(holder.recService.getContext(), RecyclerView.VERTICAL, false);
        holder.recService.setLayoutManager(manager);
        holder.recService.setAdapter(adapter);

    }

    private List<Service> getListService(List<Integer> listIdService) {
        List<Service> listService = new ArrayList<>();
        ServiceDataSource db = new ServiceDataSource(context);
        for (Integer id : listIdService) {
            listService.add(db.getById(id));
        }
        return listService;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {
        private TextView txtUsername, txtStaff, txtBookingTime, txtSlot, txtPrice, txtStatus;
        private RecyclerView recService;
        private Button btnReceive;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            int roleId = sharedPreferences.getInt("roleId", -1);
            int userId = sharedPreferences.getInt("userId", -1);
            String userName = sharedPreferences.getString("username", "");

            if (roleId == RoleBarber) {
                btnReceive = itemView.findViewById(R.id.btnReceive);
                btnReceive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //update status
                        BookingResponse booking = list.get(getAdapterPosition());

                        Intent intent = new Intent(context, UpdateBookingActivity.class);
                        intent.putExtra("booking", booking);
                        context.startActivity(intent);
                    }
                });
            }
            else if(roleId == RoleUser){
                btnReceive = itemView.findViewById(R.id.btnReceive);
                btnReceive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //update status
                        BookingResponse booking = list.get(getAdapterPosition());

                        Intent intent = new Intent(context, UpdateBookingActivity.class);
                        intent.putExtra("booking", booking);
                        context.startActivity(intent);
                    }
                });
            }

            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtStaff = itemView.findViewById(R.id.txtStaff);
            txtBookingTime = itemView.findViewById(R.id.txtBookingTime);
            txtSlot = itemView.findViewById(R.id.txtSlot);
            recService = itemView.findViewById(R.id.rcvService);
            txtPrice = itemView.findViewById(R.id.tv6);
            txtStatus = itemView.findViewById(R.id.txtStatus);

        }
    }

}
