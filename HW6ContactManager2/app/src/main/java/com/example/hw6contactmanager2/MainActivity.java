package com.example.hw6contactmanager2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button btnAddContact;
    ListView lvContacts;
    ActivityResultLauncher mGetContactInformation;
    ArrayAdapter contactsAdapter;


    ArrayList<String> contactNames = new ArrayList<>();
    HashMap<String, Contact> contacts = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAddContact   = findViewById(R.id.buttonAddContact);
        contactsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,contactNames);


        lvContacts      = findViewById(R.id.listViewContacts);
        lvContacts.setAdapter(contactsAdapter);
        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact c = contacts.get(contactNames.get(i));
                //TODO get View Contact

                Intent showContactIntent =  new Intent(getApplicationContext(), ShowContactActivity.class);
                showContactIntent.putExtra(ContactConstants.CONTACT_NAME, c.getName());
                showContactIntent.putExtra(ContactConstants.CONTACT_EMAIL, c.getEmail());
                showContactIntent.putExtra(ContactConstants.CONTACT_PHONE, c.getPhone());

                startActivity(showContactIntent);

            }
        });



        mGetContactInformation = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Contact c =  new Contact();
                c.setName(result.getData().getStringExtra(ContactConstants.CONTACT_NAME));
                c.setPhone(result.getData().getStringExtra(ContactConstants.CONTACT_PHONE));
                c.setEmail(result.getData().getStringExtra(ContactConstants.CONTACT_EMAIL));

                contacts.put(c.getName(), c);
                contactNames.add(c.getName());
                contactsAdapter.notifyDataSetChanged();

            }
        });

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startCreateContentIntent =  new Intent(getApplicationContext(), CreateContactActivity.class);
                mGetContactInformation.launch(startCreateContentIntent);            }
        });
    }

}