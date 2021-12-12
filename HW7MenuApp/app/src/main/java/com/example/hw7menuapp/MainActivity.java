package com.example.hw7menuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> bookmarks;

    EditText etUrl;
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookmarks = new ArrayList();

        etUrl = findViewById(R.id.editTextUrl);

        Button btnMenu = findViewById(R.id.buttonMenu);
        btnMenu.setOnCreateContextMenuListener(this);

        wv = findViewById(R.id.webview);
        WebSettings webSettings = wv.getSettings();
        webSettings.setBuiltInZoomControls(true);
        wv.loadUrl(" https://www.futurity.org/wp/wp-content/uploads/2019/02/viceroy-butterfly_1600.jpg ");
        wv.setWebViewClient(new WebViewClient());

        Button btnBookmark =  findViewById(R.id.buttonBookmark);
        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = etUrl.getText().toString();

                if(!input.isEmpty()){
                    bookmarks.add(input);
                    invalidateOptionsMenu();
                }

            }
        });

        Button btnSubmit = findViewById(R.id.buttonSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = etUrl.getText().toString();
                if(!input.isEmpty())
                    wv.loadUrl(input);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        createMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return menuChoice(item);
    }

    private void createMenu(Menu menu) {
        menu.clear();

        for (int i = 0; i < bookmarks.size(); i++) {
            menu.add(0,i,i,bookmarks.get(i));
        }
    }

    private boolean menuChoice(MenuItem item) {
        String stringUri = bookmarks.get(item.getItemId());

        if(!stringUri.isEmpty()){
            etUrl.setText(stringUri);
            wv.loadUrl(stringUri);
            return true;
        }

        return false;
    }
}
