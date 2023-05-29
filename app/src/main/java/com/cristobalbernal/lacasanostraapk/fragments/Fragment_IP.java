package com.cristobalbernal.lacasanostraapk.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cristobalbernal.lacasanostraapk.MainActivity;
import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

public class Fragment_IP extends Fragment {
    private Button obtener;
    private Button cambiar;
    private EditText ip;
    private SharedPreferences.Editor editor;
    public Fragment_IP(){
        super(R.layout.fragment_ip);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        obtener = view.findViewById(R.id.obtener);
        cambiar = view.findViewById(R.id.cambiar);
        ip = view.findViewById(R.id.ipEdi);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        obtener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("ip", RestClient.DEFAULT_IP);
                editor.apply();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                requireActivity().startActivity(intent);
            }
        });
        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("ip",ip.getText().toString());
                editor.apply();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                requireActivity().startActivity(intent);
            }
        });
    }
}
