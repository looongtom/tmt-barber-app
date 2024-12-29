package com.example.myapplication.fragment;

import static com.example.myapplication.model.notification.Notification.BookingType;
import static com.example.myapplication.model.notification.Notification.HairfastType;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.AddBarberActivity;
import com.example.myapplication.BuildConfig;
import com.example.myapplication.ChooseBarberActivity;
import com.example.myapplication.DetailHairFastActivity;
import com.example.myapplication.GenerateHairStyle;
import com.example.myapplication.NotificationActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.BarberRecycleViewAdapter;
import com.example.myapplication.api.ApiAccountService;
import com.example.myapplication.model.account.Account;
import com.example.myapplication.model.account.response.GetListBarberResponse;
import com.example.myapplication.model.booking.response.BookingResponse;
import com.example.myapplication.model.booking.response.TimeSlotResponse;
import com.example.myapplication.model.hairfast.HairFastWS;
import com.example.myapplication.model.notification.Notification;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome  extends Fragment implements BarberRecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    private BarberRecycleViewAdapter adapter;
    private Button btManageBarber,btService,btBooking,btHistory,btGenerateImg;
    private TextView txtName;
    private WebView webView;

    private ImageView imgNoti;
    private HairFastWS hairFastWS;
    private BookingResponse bookingResponse;
    private WebSocket webSocketHairfast;
    private WebSocket webSocketBooking;
    private OkHttpClient client;
    private Queue<HairFastWS> queueHairfast=new LinkedList<>();
    private Queue<BookingResponse> queueBooking=new LinkedList<>();
    private String apiUrl;
    private ArrayList<Notification> notifications;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        notifications=getDummyNotifications();
        hairFastWS = new HairFastWS();

        client = new OkHttpClient();
        apiUrl = BuildConfig.API_BASE_URL;

        String urlHairFast = "ws://"+apiUrl + ":8080/hairfast";
        Request request = new Request.Builder().url(urlHairFast).build();
        EchoWebSocketHairfastListener listener = new EchoWebSocketHairfastListener();
        webSocketHairfast = client.newWebSocket(request, listener);

        String urlBooking = "ws://"+apiUrl + ":8080/booking";
        Request requestBooking = new Request.Builder().url(urlBooking).build();
        EchoWebSocketBookingListener listenerBooking = new EchoWebSocketBookingListener();
        webSocketBooking = client.newWebSocket(requestBooking, listenerBooking);

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

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
        int userId = sharedPreferences.getInt("userId", -1);
        String userName=sharedPreferences.getString("username","");
        txtName.setText(userName);

        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

        if(roleId!= Account.RoleAdmin){
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

        btGenerateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), GenerateHairStyle.class);
                startActivity(intent);
            }
        });

        imgNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(queueHairfast.isEmpty() && queueHairfast.isEmpty()){
                    imgNoti.setImageResource(R.drawable.noti);
//                    notifications.add(new Notification("Notice from backend",queue.peek(),new Date().toString()));
//                    Intent intent = new Intent(getActivity(), NotificationActivity.class);
//                    intent.putExtra("notifications", notifications);
//                    startActivity(intent);
                }
                while(!queueHairfast.isEmpty()){
                        HairFastWS hairFastWS =queueHairfast.peek();
                        notifications.add(new Notification("Đã generate thành công kiểu tóc","Bấm vào để xem chi tiết",new Date().getTime(),HairfastType,hairFastWS));
                        queueHairfast.remove();

//                    Toast.makeText(getContext(), queue.peek(), Toast.LENGTH_SHORT).show();
//                    notifications.add(new Notification("Notice from backend",queue.peek(),new Date().toString()));


//                    Picasso.get().load(hairFastWS.getGeneratedImgCloud()).into(imgResult);
//                    Intent intent=new Intent(getContext(), DetailHairFastActivity.class);
//                    intent.putExtra("hairFastWS",hairFastWS);
//                    startActivity(intent);

                }
                while(!queueBooking.isEmpty()){
                    BookingResponse bookingResponse =queueBooking.peek();
                    String title = bookingResponse.getStatus().equals("Booked")? "Đã đặt lịch thành công":"Đặt lịch thất bại";
                    notifications.add(new Notification(title,"Bấm vào để xem chi tiết",new Date().getTime(),BookingType,bookingResponse));
                    queueBooking.remove();
                }
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                intent.putExtra("notifications", notifications);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });


    }

    private void sendApiGetListBarber(){
        ApiAccountService.API_ACCOUNT_SERVICE.getListBarber().enqueue(new Callback<GetListBarberResponse>() {
            @Override
            public void onResponse(Call<GetListBarberResponse> call, Response<GetListBarberResponse> response) {
                if(response.isSuccessful()){
                    GetListBarberResponse list=response.body();
                    adapter.setList(list.getListBarber());
                }
            }

            @Override
            public void onFailure(Call<GetListBarberResponse> call, Throwable t) {
                Toast.makeText(getContext(), "error get list barber", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class EchoWebSocketHairfastListener extends WebSocketListener {
        @Override
        public void onMessage(WebSocket webSocket, String text) {
            getActivity().runOnUiThread(() -> {
                // Add received message to RecyclerView
                imgNoti.setImageResource(R.drawable.active_noti);
                Gson gson = new Gson();
                hairFastWS = gson.fromJson(text, HairFastWS.class);

                queueHairfast.add(hairFastWS);
                //delay 5s
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                imgNoti.setImageResource(R.drawable.noti);
            });
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
            getActivity().runOnUiThread(() -> {
                // Handle connection failure
                Toast.makeText(getContext(), "WebSocket Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            });
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            getActivity().runOnUiThread(() -> {
                // Handle WebSocket closure
                Toast.makeText(getContext(), "WebSocket Closed: " + reason, Toast.LENGTH_LONG).show();
            });
        }
    }

    private class EchoWebSocketBookingListener extends WebSocketListener {
        @Override
        public void onMessage(WebSocket webSocket, String text) {
            getActivity().runOnUiThread(() -> {
                // Add received message to RecyclerView
                imgNoti.setImageResource(R.drawable.active_noti);
                Gson gson = new Gson();
                bookingResponse = gson.fromJson(text, BookingResponse.class);

                queueBooking.add(bookingResponse);
                //delay 5s
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                imgNoti.setImageResource(R.drawable.noti);
            });
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    // Handle connection failure
                    Toast.makeText(getContext(), "WebSocket Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            getActivity().runOnUiThread(() -> {
                // Handle WebSocket closure
                Toast.makeText(getContext(), "WebSocket Closed: " + reason, Toast.LENGTH_LONG).show();
            });
        }
    }


    private void init(View view) {
        recyclerView=view.findViewById(R.id.rcvStaff);
        btManageBarber=view.findViewById(R.id.btAddBarber);
        txtName=view.findViewById(R.id.txtName);
        webView=view.findViewById(R.id.webView);
        btService=view.findViewById(R.id.btService);
        btBooking=view.findViewById(R.id.btBooking);
        btHistory=view.findViewById(R.id.btHistory);
        btGenerateImg=view.findViewById(R.id.btGenerateImg);
        imgNoti=view.findViewById(R.id.imgNoti);
    }
    private ArrayList<Notification> getDummyNotifications() {
        ArrayList<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification("Title 1", "HairfastType 1", new Long(1732122000),HairfastType,new HairFastWS("https://res.cloudinary.com/dsjuckdxu/image/upload/v1728486066/2024-10-09T22:01:02.jpg","https://res.cloudinary.com/dsjuckdxu/image/upload/v1728486067/2024-10-09T22:01:07.jpg","https://res.cloudinary.com/dsjuckdxu/image/upload/v1728486068/2024-10-09T22:01:08.jpg","https://res.cloudinary.com/dsjuckdxu/image/upload/v1728486342/idteaiucvc5rrf5guimj.png")));
        notifications.add(new Notification("Title 2", "BookingType 2", new Long(1732122000),BookingType, new BookingResponse(1,1,"",1,"",1,1,new TimeSlotResponse(),"Booked",1,1,new Long(1),new Long(1),null)));
        return notifications;
    }

    @Override
    public void onItemCLick(View view, int pos) {

    }

    @Override
    public void onResume() {
        super.onResume();
        sendApiGetListBarber();
//
//        String urlBooking = "ws://"+apiUrl + ":8080/booking";
//        Request requestBooking = new Request.Builder().url(urlBooking).build();
//        EchoWebSocketBookingListener listenerBooking = new EchoWebSocketBookingListener();
//        webSocketBooking = client.newWebSocket(requestBooking, listenerBooking);
    }
}
