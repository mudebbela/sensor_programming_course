package com.example.hw1_switchingactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnStartNewActivity;
    TextView tvSwitchingCount;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartNewActivity =  findViewById(R.id.buttonStartNewActivity);
        tvSwitchingCount    =  findViewById(R.id.textViewSwitchingCount);
        count  =  0;

        btnStartNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO how do you start a new activity
                //startActivity();
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this.getLocalClassName(), "onResume: Resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        tvSwitchingCount.setText("Switched Count: " + ++count);
        Log.d(this.getLocalClassName(), "onPause: paused");
    }
}