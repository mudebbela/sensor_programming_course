package com.example.hw13proximitysensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private boolean isSensorPresent;
    private float distanceFromPhone;
    private TextView myLabel;

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(isSensorPresent)
            mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            isSensorPresent = true;
        } else {
            isSensorPresent = false;
        }

        myLabel = (TextView)findViewById(R.id.label);

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("TAG", "onSensorChanged: ");
        distanceFromPhone = event.values[0];
        if(distanceFromPhone < mSensor.getMaximumRange()) {
            myLabel.setBackgroundColor(Color.GREEN);
            myLabel.setTextColor(Color.WHITE);
        } else {
            myLabel.setBackgroundColor(Color.RED);
            myLabel.setTextColor(Color.BLACK);

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}