    package com.example.hw8contactmanagementapp3;

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

        DBAdapter db = new DBAdapter(this);

        btnFinish       =  findViewById(R.id.buttonFinish);
        etPersonName    = findViewById(R.id.editTextTextPersonName);
        etEmailAddress  = findViewById(R.id.editTextTextEmailAddress);
        etPhone         = findViewById(R.id.editTextPhone);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnContact =  new Intent();
                //---add a contact---
                db.open();
                long id = db.insertContact(etPersonName.getText().toString(),
                        etEmailAddress.getText().toString(), etPhone.getText().toString());
//                id = db.insertContact("Oscar Diggs", "oscar@oscardiggs.com");
                db.close();
                setResult(RESULT_OK, returnContact);
                finish();
            }
        });
    }
}