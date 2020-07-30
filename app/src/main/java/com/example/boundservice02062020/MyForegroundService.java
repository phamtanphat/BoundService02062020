package com.example.boundservice02062020;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyForegroundService extends Service {

    String CHANNEL_ID = "mychannel_1";
    NotificationCompat.Builder mBuilder;
    int count = 0;
    IBinder mIBinder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mIBinder = new MyBinder();
        return mIBinder;
    }

    class MyBinder extends Binder{
        MyForegroundService getService(){
            return MyForegroundService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBuilder = createNotification();
        startForeground(1 , mBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                count++;
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(1,createNotification().build());
                new Handler().postDelayed(this, 1000);
            }
        },1000);
        return super.onStartCommand(intent, flags, startId);
    }

    private NotificationCompat.Builder createNotification(){
        Intent intent = new Intent(this , MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        this,
                        123 ,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle("Service running");
        builder.setContentText("Count " + count);
        builder.setStyle(new NotificationCompat.BigTextStyle());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setOngoing(false);
        builder.setShowWhen(true);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel =
                    new NotificationChannel(CHANNEL_ID, "channel", NotificationManager.IMPORTANCE_DEFAULT );
            notificationManager.createNotificationChannel(notificationChannel);
        }
        return builder;
    }
}
