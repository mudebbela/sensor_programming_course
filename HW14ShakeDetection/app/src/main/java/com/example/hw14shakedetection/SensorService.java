package com.example.hw14shakedetection;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.security.Provider;

public class SensorService extends Service implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mSensor;
    private MediaPlayer mMediaPlayer;
    private boolean isPlaying = false;
    private float mThreshold = 2.5f;

    private Uri mediaPathCosha ;
    private Uri mediaPathSales;
    private Uri mediaPathGta;

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSensorManager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        mediaPathCosha = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cosha);
        mediaPathSales = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sales);
        mediaPathGta = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.gta);

        mMediaPlayer = MediaPlayer.create(getApplicationContext(), mediaPathCosha);



        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp) {
                isPlaying = false;
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double rateOfRotation = Math.sqrt(Math.pow(event.values[0],2) + 	Math.pow(event.values[1], 2) + Math.pow(event.values[2], 2));
        if(rateOfRotation > mThreshold) {
            try {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
                double random = Math.random()*3;
                if(random<1){
                    mMediaPlayer.setDataSource(getApplicationContext(),mediaPathCosha);
                    mMediaPlayer.prepare();
                } else if(random< 2){
                    mMediaPlayer.setDataSource(getApplicationContext(),mediaPathGta);
                    mMediaPlayer.prepare();
                } else {
                    mMediaPlayer.setDataSource(getApplicationContext(), mediaPathSales);
                    mMediaPlayer.prepare();

                }
                mMediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
