package com.example.hw6contactmanager2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateContactActivity extends AppCompatActivity {
    Button btnFinish;
    EditText etPersonName, etEmailAddress, etPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        btnFinish       =  findViewById(R.id.buttonFinish);
        etPersonName    = findViewById(R.id.editTextTextPersonName);
        etEmailAddress  = findViewById(R.id.editTextTextEmailAddress);
        etPhone         = findViewById(R.id.editTextPhone);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnContact =  new Intent();
                returnContact.putExtra(ContactConstants.CONTACT_NAME, etPersonName.getText().toString());
                returnContact.putExtra(ContactConstants.CONTACT_EMAIL, etEmailAddress.getText().toString());
                returnContact.putExtra(ContactConstants.CONTACT_PHONE, etPhone.getText().toString());
                setResult(RESULT_OK, returnContact);
                finish();
            }
        });
    }
}