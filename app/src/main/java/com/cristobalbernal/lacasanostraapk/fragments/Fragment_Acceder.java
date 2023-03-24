package com.cristobalbernal.lacasanostraapk.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cristobalbernal.lacasanostraapk.R;


public class Fragment_Acceder extends Fragment {
    public Fragment_Acceder(){
        super(R.layout.fragment_acceder);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        view.findViewById(R.id.tvRegistrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Hola",Toast.LENGTH_LONG).show();
            }
        });
    }
}
