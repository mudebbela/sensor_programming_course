package com.example.hw8contactmanagementapp3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button btnAddContact;
    ListView lvContacts;
    ActivityResultLauncher mGetContactInformation;
    ArrayAdapter contactsAdapter;
    DBAdapter db;


    ArrayList<String> contactNames = new ArrayList<>();
    HashMap<String, Contact> contacts = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBAdapter(this);

        btnAddContact   = findViewById(R.id.buttonAddContact);
        contactsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,contactNames);

        updateContacts();





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
                updateContacts();

            }
        });

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startCreateContentIntent =  new Intent(getApplicationContext(), CreateContactActivity.class);
                mGetContactInformation.launch(startCreateContentIntent);            }
        });



    }

    private void updateContacts() {

        contactNames.clear();
        contacts.clear();

        db.open();
        Cursor c = db.getAllContacts();

        Log.d(this.getLocalClassName(), "updateContacts: "+ DatabaseUtils.dumpCursorToString(c));

        if(c.moveToFirst()){
            do {
                Contact contact = new Contact();
                Log.d(this.getLocalClassName(), "updateContacts: "+ DatabaseUtils.dumpCurrentRowToString(c));
                contact.setName(c.getString(1));
                contact.setEmail(c.getString(2));
                contact.setPhone(c.getString(3));
                contactNames.add(contact.getName());
                contacts.put(contact.getName(), contact);

            }while(c.moveToNext());
        }
        db.close();

        contactsAdapter.notifyDataSetChanged();

    }
}