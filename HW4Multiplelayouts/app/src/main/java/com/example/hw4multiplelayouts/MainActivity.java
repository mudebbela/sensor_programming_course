package com.example.hw4multiplelayouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {
    Button btnOpenTableLayout,btnOpenRelativeLayout,btnOpenLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpenLinearLayout     = findViewById(R.id.buttonLinearLayout);
        btnOpenRelativeLayout   = findViewById(R.id.buttonRelativelayout);
        btnOpenTableLayout      = findViewById(R.id.buttonTabletLayout);

        btnOpenTableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TableLayoutActivity.class);
                startActivity(intent);
            }
        });

        btnOpenRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RelativeLayoutActivity.class);
                startActivity(intent);

            }
        });
        btnOpenLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), LinearLayoutActivity.class);
                startActivity(intent);
            }
        });
    }
}