package com.example.hw15pedometerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mSensorListView;
    private ListAdapter mListAdapter;
    private StepsDBHelper mStepsDBHelper;
    ArrayList<DateStepsModel> mStepCountList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 10001);
        }
        mSensorListView = (ListView)findViewById(R.id.steps_list);
        getDataForList();

        mListAdapter = new ListAdapter(mStepCountList,this);
        mSensorListView.setAdapter(mListAdapter);
        Intent stepsIntent = new Intent(getApplicationContext(), StepsService.class);
        startService(stepsIntent);
    }

    public void getDataForList(){
        mStepsDBHelper = new StepsDBHelper(this);
        mStepCountList = mStepsDBHelper.readStepsEntries();
    }


}