package com.cristobalbernal.lacasanostraapk.activitys;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cristobalbernal.lacasanostraapk.MainActivity;
import com.cristobalbernal.lacasanostraapk.R;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity {
    private static final String[] lenguaje = {"Selecione idioma!!", "Castellano", "Ingles"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingactivity);

        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(SettingActivity.this, android.R.layout.simple_spinner_item, lenguaje);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String posicion = parent.getItemAtPosition(position).toString();
                if (posicion.equals("Ingles")) {
                    setLocal(SettingActivity.this, "en");
                    finish();
                    startActivity(new Intent(SettingActivity.this, MainActivity.class));

                } else if (posicion.equals("Castellano")) {
                    setLocal(SettingActivity.this, "es");
                    finish();
                    startActivity(new Intent(SettingActivity.this, MainActivity.class));
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setLocal(SettingActivity settingActivity, String lenguaje) {
        Locale locale = new Locale(lenguaje);
        locale.setDefault(locale);
        Resources resources = settingActivity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }

}
