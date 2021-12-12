package com.example.hw12sensorslist;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.MyViewHolder> {
    public static String SENSOR_TYPE_CONSTANT = "sensor_type";
    List<Sensor> mSensorList;
    Context ctx;
    public SensorAdapter(Context ctx,List<Sensor> mSensorList){
        Log.d("TAG", "SensorAdapter: ");
        this.mSensorList = mSensorList;
        this.ctx = ctx;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TAG", "onCreateViewHolder: ");
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sensor_single_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("TAG", "onBindViewHolder: bindingview");
        Sensor s = mSensorList.get(position);
        holder.setName(s.getName());
        holder.setType(s.getType());
        holder.initListener();
    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "getItemCount: "+mSensorList.size());
        return mSensorList.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{
        View itemView;
        int type;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

        }

        public void setName(String sensorName) {
            Log.d("TAG", "setName: setting name");
            ((TextView)itemView.findViewById(R.id.textViewSensorName)).setText(sensorName);
        }

        public void setType(int type) {
            this.type =  type;
        }

        private void initListener(){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ctx, "Clicked", Toast.LENGTH_LONG).show();
                    Log.d("TAG", "onClick: Type " + type);
                    Intent startSensorOverviewIntent =  new Intent(v.getContext(), SensorOverviewActivity.class);
                    //TODO getCurrent sensor type
                    startSensorOverviewIntent.putExtra(SENSOR_TYPE_CONSTANT, type);
                    v.getContext().startActivity(startSensorOverviewIntent);

                }
            });

        };


    }
}
