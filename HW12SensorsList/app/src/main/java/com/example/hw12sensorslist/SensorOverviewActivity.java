package com.example.hw12sensorslist;

import static com.example.hw12sensorslist.SensorAdapter.SENSOR_TYPE_CONSTANT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SensorOverviewActivity extends AppCompatActivity {
    TextView tvName, tvRange, tvMinDelay, tvPower, tvResolution, tvVendor, tvVersion;

    Button btGetSensorValues;
    int sensorType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_overview);

        sensorType = getIntent().getIntExtra(SENSOR_TYPE_CONSTANT, -1);

        Toast.makeText(getApplicationContext(), "Sensor: " + sensorType, Toast.LENGTH_LONG).show();

        tvName = findViewById(R.id.textViewSensorOverviewName);
        tvRange = findViewById(R.id.textViewSensorOverviewRange);
        tvMinDelay = findViewById(R.id.textViewSensorOverviewMinDelay);
        tvPower = findViewById(R.id.textViewSensorOverviewPower);
        tvResolution = findViewById(R.id.textViewSensorOverviewResolution);
        tvVendor = findViewById(R.id.textViewSensorOverviewVendor);
        tvVersion = findViewById(R.id.textViewSensorOverviewVersion);

        btGetSensorValues = findViewById(R.id.buttonGetSensorValues);

        btGetSensorValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        SensorValuesActivity.class);
                intent.putExtra(SENSOR_TYPE_CONSTANT, sensorType);
                startActivity(intent);
                finish();
            }
        });

        SensorManager mSensorManager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        Sensor mSensor = mSensorManager.getDefaultSensor(sensorType);

        try {
            tvName.setText(mSensor.getName());
        }catch (Resources.NotFoundException notFoundException){

        }
        try {
            tvRange.setText(""+mSensor.getMaximumRange());
        }catch (Resources.NotFoundException notFoundException){

        }

        try {
            tvMinDelay.setText(mSensor.getMinDelay());
        }catch (Resources.NotFoundException notFoundException){

        }

        try {
            tvPower.setText(""+mSensor.getPower());
        }catch (Resources.NotFoundException ignored){

        }

        try {
            tvResolution.setText(""+mSensor.getResolution());
        }catch (Resources.NotFoundException notFoundException){

        }

        try {
            tvVendor.setText(mSensor.getVendor());
        }catch (Resources.NotFoundException notFoundException){

        }

        try {
            tvVersion.setText(mSensor.getVersion());
        }catch (Resources.NotFoundException notFoundException){

        }




    }
}