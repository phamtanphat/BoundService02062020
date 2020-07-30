package com.example.boundservice02062020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView mTvCount;
    Button mBtnStartService,mBtnStopService;
    MyForegroundService mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvCount = findViewById(R.id.textviewCount);
        mBtnStartService = findViewById(R.id.buttonStartService);
        mBtnStopService = findViewById(R.id.buttonStopService);

        mBtnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyForegroundService.class);
                ContextCompat.startForegroundService(MainActivity.this,intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this,MyForegroundService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyForegroundService.MyBinder myBinder =
                    (MyForegroundService.MyBinder) service;
            mService = myBinder.getService();
            mTvCount.setText(mService.count + "");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}