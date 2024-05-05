package com.example.myapplication.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;

import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    private String CHANNEL_ID="201";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("MyAction")){
            String time=intent.getStringExtra("time");
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                        "Channel 1",
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("This is Channel 1");
                notificationManager.createNotificationChannel(channel);
            }
            NotificationCompat.Builder builder=new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setContentTitle("Bạn có lịch hẹn lúc: "+time)
                    .setContentText("Hãy chuẩn bị và đến đúng giờ hẹn")
                    .setSmallIcon(R.drawable.noti)
                    .setColor(Color.YELLOW)
                    .setCategory(NotificationCompat.CATEGORY_ALARM);
            notificationManager.notify(getNotification(),builder.build());
        }
    }
    private int getNotification(){
        int time=(int) new Date().getTime();
        return time;
    }
}
