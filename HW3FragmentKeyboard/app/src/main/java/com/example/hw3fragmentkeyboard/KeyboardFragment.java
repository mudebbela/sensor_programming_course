package com.example.hw3fragmentkeyboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class KeyboardFragment extends Fragment {

    Button btn1,btn2,btn3,btnbackSpace;
    String keyboardString;
    ButtonPressViewModel bpViewModel;

    public KeyboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn1 = view.findViewById(R.id.button1);
        btn2 =  view.findViewById(R.id.button2);
        btn3 = view.findViewById(R.id.button3);
        btnbackSpace = view.findViewById(R.id.button4);

        bpViewModel = new ViewModelProvider(requireActivity()).get(ButtonPressViewModel.class);
        keyboardString = "";


        btn1.setText("\uD83D\uDE0A"); //😊
        btn2.setText("\uD83D\uDE2F"); //😯
        btn3.setText("\uD83D\uDC4F\uD83C\uDFFF");// 👏🏿 that supposed to be a black hands slapping and not a weird smudge
        btnbackSpace.setText("BackSpace");

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateKeys("\uD83D\uDE0A");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateKeys("\uD83D\uDE2F");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateKeys("\uD83D\uDC4F\uD83C\uDFFF");
            }
        });
        btnbackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getBaseContext(), "backspace", Toast.LENGTH_LONG).show();
                backspace();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keyboard, container, false);
    }

    void updateKeys(String key){
        keyboardString = keyboardString+key;
        bpViewModel.setString(keyboardString);
    }

    void backspace(){
        if(keyboardString.length() >1)
            keyboardString = keyboardString.substring(0, keyboardString.length()-1);
        bpViewModel.setString(keyboardString);

    };
}