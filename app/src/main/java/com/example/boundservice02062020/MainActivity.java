package com.example.boundservice02062020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTvCount;
    Button mBtnStartService,mBtnStopService;
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
}