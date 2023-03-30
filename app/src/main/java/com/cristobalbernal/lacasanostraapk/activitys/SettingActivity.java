package com.cristobalbernal.lacasanostraapk.activitys;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.cristobalbernal.lacasanostraapk.MainActivity;
import com.cristobalbernal.lacasanostraapk.R;
import java.util.Locale;
import java.util.Objects;

public class SettingActivity extends AppCompatActivity {
    private static final String[] lenguaje = {"Selecione idioma!!", "Castellano", "Ingles"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingactivity);
        Spinner spinner = findViewById(R.id.spinner);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(SettingActivity.this, android.R.layout.simple_spinner_item, lenguaje);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String posicion = parent.getItemAtPosition(position).toString();
                if (posicion.equals("Ingles")) {
                    Toast.makeText(getBaseContext(),"You have selected English!!",Toast.LENGTH_LONG).show();
                    setLocal(SettingActivity.this, "en");
                    finish();
                    startActivity(new Intent(SettingActivity.this, MainActivity.class));
                } else if (posicion.equals("Castellano")) {
                    Toast.makeText(getBaseContext(),"Has seleccionado Castellano!!",Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
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
