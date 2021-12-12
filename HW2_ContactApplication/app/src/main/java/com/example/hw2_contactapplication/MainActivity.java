package com.example.hw2_contactapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tvName, tvPhone, tvEmail ;
    Button btnCreateContact;
    ActivityResultLauncher<Intent> mGetContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateContact    = findViewById(R.id.buttonCreateContact);
        tvName              = findViewById(R.id.textViewName);
        tvPhone             = findViewById(R.id.textViewPhone);
        tvEmail             = findViewById(R.id.textViewEmail);

        mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Toast.makeText(getApplicationContext(), "returned" , Toast.LENGTH_LONG).show();
                tvName.setText(result.getData().getStringExtra(ContactConstants.CONTACT_NAME));
                tvPhone.setText(result.getData().getStringExtra(ContactConstants.CONTACT_PHONE));
                tvEmail.setText(result.getData().getStringExtra(ContactConstants.CONTACT_EMAIL));

            }
        });

        btnCreateContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startCreateContentIntent =  new Intent(getApplicationContext(), CreateContactActivity.class);
                mGetContent.launch(startCreateContentIntent);
            }
        });

    }
}