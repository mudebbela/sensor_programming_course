package com.example.hw16enhancedpedometerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView mTotalStepsTextView;
    private TextView mTotalDistanceTextView;
    private TextView mTotalDurationTextView;
    private TextView mAverageSpeedTextView;
    private TextView mAveragFrequencyTextView;
    private TextView mTotalCalorieBurnedTextView;
    private TextView mPhysicalActivityTypeTextView;
    StepsTrackerDBHelper mStepsTrackerDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 10001);
        }


        mTotalStepsTextView = (TextView)findViewById(R.id.total_steps);
        mTotalDistanceTextView = (TextView)findViewById(R.id.total_distance);
        mTotalDurationTextView = (TextView)findViewById(R.id.total_duration);
        mAverageSpeedTextView = (TextView)findViewById(R.id.average_speed);
        mAveragFrequencyTextView = (TextView)findViewById(R.id.average_frequency);
        mTotalCalorieBurnedTextView = (TextView)findViewById(R.id.calories_burned);
        mPhysicalActivityTypeTextView = (TextView)findViewById(R.id.physical_activitytype);
        mStepsTrackerDBHelper = new StepsTrackerDBHelper(this);


        Intent mStepsAnalysisIntent = new Intent(getApplicationContext(), StepTrackerService.class);
        startService(mStepsAnalysisIntent);
        calculateDataMatrix();

        Button btnUpdate =  findViewById(R.id.button);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDataMatrix();
            }
        });
    }

    public void calculateDataMatrix()
    {
        Calendar mCalendar = Calendar.getInstance();
        String todayDate = String.valueOf(mCalendar.get(Calendar.MONTH)+1)+"/" +
                String.valueOf(mCalendar.get(Calendar.DAY_OF_MONTH)) +"/"+ String.valueOf(mCalendar.get(Calendar.YEAR));
        int stepType[] = mStepsTrackerDBHelper.getStepsByDate(todayDate);
        int walkingSteps = stepType[0]; int joggingSteps = stepType[1]; int runningSteps = stepType[2];
        //Calculating total steps
        int totalStepTaken = walkingSteps + joggingSteps + runningSteps;
        mTotalStepsTextView.setText(String.valueOf(totalStepTaken)+ " Steps");


        //Calculating total distance travelled
        float totalDistance = walkingSteps*0.5f +joggingSteps * 1.0f + runningSteps * 1.5f;
        mTotalDistanceTextView.setText(String.valueOf(totalDistance)+" meters");


        //Calculating total duration
        float totalDuration = walkingSteps*1.0f +joggingSteps * 0.75f + runningSteps * 0.5f;
        float hours = totalDuration / 3600;
        float minutes = (totalDuration % 3600) / 60;
        float seconds = totalDuration % 60;
        mTotalDurationTextView.setText(String.format("%.0f",hours) + " hrs " +String.format("%.0f",minutes) + " mins " +
                String.format("%.0f",seconds)+ " secs");
        //Calculating average speed
        if(totalDistance>0)
        {
            mAverageSpeedTextView.setText(String.format("%.2f", totalDistance/totalDuration)+" meter per seconds");
        } else { mAverageSpeedTextView.setText("0 meter per seconds"); }


        //Calculating average step frequency
        if(totalStepTaken>0)
        {
            mAveragFrequencyTextView.setText(String.format("%.0f",totalStepTaken/minutes)+"steps per minute");
        } else {
            mAveragFrequencyTextView.setText("0 steps per minute");
        }

        //Calculating total calories burned
        float totalCaloriesBurned = walkingSteps * 0.05f + joggingSteps * 0.1f + runningSteps * 0.2f;
        mTotalCalorieBurnedTextView.setText(String.format("%.0f",totalCaloriesBurned)+"Calories");

        //Calculating type of physical activity
        mPhysicalActivityTypeTextView.setText(String.valueOf(walkingSteps) + " Walking Steps "+ "\n"+String.valueOf(joggingSteps) +
                "Jogging Steps " + "\n"+String.valueOf(runningSteps)+ "Running Steps");
    }


}