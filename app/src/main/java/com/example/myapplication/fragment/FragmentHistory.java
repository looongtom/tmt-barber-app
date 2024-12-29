package com.example.myapplication.fragment;

import static com.example.myapplication.model.account.Account.RoleUser;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ChooseTimeSlotActivity;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.BookingAdapter;
import com.example.myapplication.api.ApiBookingService;
import com.example.myapplication.auth.TokenManager;
import com.example.myapplication.dal.BookingDataSource;
import com.example.myapplication.dal.DatabaseHelper;
import com.example.myapplication.model.booking.Booking;
import com.example.myapplication.model.booking.request.FindBookingRequest;
import com.example.myapplication.model.booking.response.BookingResponse;
import com.example.myapplication.model.booking.response.GetListBookingResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHistory  extends Fragment {
    BookingAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar loadingPB;
    private NestedScrollView nestedSV;
    private SearchView searchView;
    private String queryDate;
    private TokenManager tokenManager ;
    private List<BookingResponse> listBookings;
    private ImageButton btnReset;

    private int page=1,limit=2,total=Integer.MAX_VALUE;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
        int userId = sharedPreferences.getInt("userId", -1);
        String userName=sharedPreferences.getString("username","");

        if(roleId!=RoleUser){
            return inflater.inflate(R.layout.fragment_history_staff,container,false);
        }

        return inflater.inflate(R.layout.fragment_history,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView=view.findViewById(R.id.search);
//        enableSearchView(searchView,false);
        btnReset=view.findViewById(R.id.btnClear);
        recyclerView=view.findViewById(R.id.rcvListBooking);
        loadingPB=view.findViewById(R.id.idPBLoading);
        nestedSV=view.findViewById(R.id.idNestedSV);
        adapter=new BookingAdapter(getContext());
        tokenManager = new TokenManager(getContext());
        listBookings = new ArrayList<>();

        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQueryHint("Search by date");
                queryDate=null;

                resetPagination();
                if (listBookings.size()>0 || !listBookings.isEmpty()){
                    listBookings.clear();
                    adapter.setData(listBookings);
                }
                sendApiGetListBooking(page,limit,new FindBookingRequest(page,limit));
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = "";
                        if (dayOfMonth < 10) {
                            date = "0" + dayOfMonth + "-";
                        } else {
                            date = dayOfMonth + "-";
                        }
                        if (month > 8) {
                            date +=  (month + 1) + "-" + year;
                        } else {
                            date += "0" + (month + 1) + "-" + year;
                        }
                        queryDate = date;
                        searchView.setQueryHint(date);
                        if (listBookings.size()>0 || !listBookings.isEmpty()){
                            listBookings.clear();
                            adapter.setData(listBookings);
                        }
                        sendApiGetListBooking(page, limit,new FindBookingRequest(page,limit,queryDate));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
                    page++;
                    loadingPB.setVisibility(View.VISIBLE);
                    if (queryDate==null || queryDate.isEmpty()) {
                        sendApiGetListBooking(page, limit, new FindBookingRequest(page, limit));
                    } else {
                        sendApiGetListBooking(page, limit, new FindBookingRequest(page, limit, queryDate));
                    }
//                    sendApiGetListBooking(page,limit,new FindBookingRequest(page,limit,queryDate));
                }
            }
        });
    }

    private void enableSearchView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                enableSearchView(child, enabled);
            }
        }
    }



    @Override
    public void onResume() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
        int userId = sharedPreferences.getInt("userId", -1);
        String userName=sharedPreferences.getString("username","");
        super.onResume();

//        if(roleId!=3){
//            BookingDataSource bookingDataSource = new BookingDataSource(getContext());
//            List<Booking> bookings = bookingDataSource.getBookingByStaffId(getContext(),userId);
//            adapter.setData(listBookings);
//            return;
//        }

//        BookingDataSource bookingDataSource = new BookingDataSource(getContext());
//        List<Booking> bookings = bookingDataSource.getBookingByUserId(getContext(),userId);
//        adapter.setData(listBookings);
        resetPagination();
        sendApiGetListBooking(page, limit,new FindBookingRequest(page,limit));
    }

    private void resetPagination(){
        this.page=1;
        this.limit=2;
        this.total=Integer.MAX_VALUE;
    }

    private String getToday() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String date = "";

        if (mDay < 10) {
            date = "0" + mDay + "-";
        } else {
            date = mDay + "-";
        }
        if (mMonth > 8) {
            date +=  (mMonth + 1) + "-" + mYear;
        } else {
            date += "0" + (mMonth + 1) + "-" + mYear;
        }
        return date;
    }

    private void sendApiGetListBooking(int page, int limit,  FindBookingRequest findBookingRequest){
        if (page*limit > total) {
            // checking if the page number is greater than limit.
            // displaying toast message in this case when page>limit.
            Toast.makeText(getContext(), "That's all the data..", Toast.LENGTH_SHORT).show();

            // hiding our progress bar.
            loadingPB.setVisibility(View.GONE);
            return;
        }

        String accessToken = tokenManager.getAccessToken();
        ApiBookingService.API_BOOKING_SERVICE.findBooking(accessToken,findBookingRequest).enqueue(new Callback<GetListBookingResponse>() {
            @Override
            public void onResponse(Call<GetListBookingResponse> call, Response<GetListBookingResponse> response) {
                if(response.isSuccessful() && response.body().getData()!=null){
                    List<BookingResponse> bookings = response.body().getData();
                    int totalRows = response.body().getTotal();
                    total=totalRows;
                    listBookings.addAll(bookings);
                    adapter.setData(listBookings);
                }else if(response.body()==null){
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "There is no data", Toast.LENGTH_SHORT).show();
                }
                else if(response.code()==401) {
                    Toast.makeText(getContext(), "Token is expired", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    getActivity().finish();
                    startActivity(intent);
                }
                else{
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "There is no data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetListBookingResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
