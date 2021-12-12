package com.example.hw10services;

import static com.example.hw10services.MyService.FILE_DOWNLOAD_PROGRESS_ACTION;
import static com.example.hw10services.MyService.PROGRESS_EXTRA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ProgressBar pbDownloadProgress;

    private BroadcastReceiver intentReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbDownloadProgress = findViewById(R.id.progressBarDownloadProgress);
        pbDownloadProgress.setVisibility(View.GONE);

        intentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("IntentReceiver", ": here");
                if(intent.hasExtra(PROGRESS_EXTRA)){
                    pbDownloadProgress.setProgress(intent.getIntExtra(PROGRESS_EXTRA, 0));
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter(FILE_DOWNLOAD_PROGRESS_ACTION);
//        intentFilter.addAction(FILE_DOWNLOAD_PROGRESS_ACTION);
        //---register the receiver---
        LocalBroadcastManager.getInstance(this).registerReceiver(intentReceiver, intentFilter);

    }

    public void startService(View view) {
        startService(new Intent(getBaseContext(), MyService.class));
        pbDownloadProgress.setVisibility(View.VISIBLE);

    }

    public void stopService(View view) {
        Log.d("Service stopped", "stopService: ");
        stopService(new Intent(getBaseContext(), MyService.class));

    }

}