package com.example.hw8contactmanagementapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowContactActivity extends AppCompatActivity {
    TextView tvName, tvPhone, tvEmail ;
    Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);

        Intent contactInformation = getIntent();

        btnReturn = findViewById(R.id.buttonReturn);
        tvName              = findViewById(R.id.textViewName);
        tvPhone             = findViewById(R.id.textViewPhone);
        tvEmail             = findViewById(R.id.textViewEmail);

        tvName.setText(contactInformation.getStringExtra(ContactConstants.CONTACT_NAME));
        tvPhone.setText(contactInformation.getStringExtra(ContactConstants.CONTACT_PHONE));
        tvEmail.setText(contactInformation.getStringExtra(ContactConstants.CONTACT_EMAIL));

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}