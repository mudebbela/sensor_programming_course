package com.example.hw3fragmentkeyboard;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class KeysPressedFragment extends Fragment {

    ButtonPressViewModel buttonPressViewModel;
    TextView tvString;


    public static KeysPressedFragment newInstance() {
        return new KeysPressedFragment();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvString =  view.findViewById(R.id.textViewString);

        buttonPressViewModel =  new ViewModelProvider(requireActivity()).get(ButtonPressViewModel.class);

        buttonPressViewModel.getStringData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity().getBaseContext(), s, Toast.LENGTH_LONG).show();
                tvString.setText(s);
            }
        });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.keys_pressed_fragment, container, false);

        return view;
    }
}