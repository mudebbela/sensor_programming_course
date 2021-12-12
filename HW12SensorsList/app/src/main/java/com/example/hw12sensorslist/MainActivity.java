package com.example.hw12sensorslist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        List<Sensor> mSensorList = new ArrayList<Sensor>();



        List<Sensor> mSensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        Sensor s = mSensorList.get(1);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);

        SensorAdapter mSensorAdapter =  new SensorAdapter(getApplicationContext(), mSensorList);
        rv = findViewById(R.id.recyclerViewSensors);
        rv.setHasFixedSize(true);
        rv.setAdapter(mSensorAdapter);
        rv.setLayoutManager(mLinearLayoutManager);

    }
}