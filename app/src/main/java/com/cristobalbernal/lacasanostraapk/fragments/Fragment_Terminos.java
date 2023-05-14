package com.cristobalbernal.lacasanostraapk.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cristobalbernal.lacasanostraapk.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

public class Fragment_Terminos extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terminos, container, false);

        TextView termsAndConditionsTextView = view.findViewById(R.id.terms_and_conditions_textview);


        // Establece el texto del TextView con los términos y condiciones leídos
        termsAndConditionsTextView.setText(R.string.texto);

        return view;
    }
}
