package com.cristobalbernal.lacasanostraapk.notificacion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cristobalbernal.lacasanostraapk.MainActivity;
import com.cristobalbernal.lacasanostraapk.R;

import java.util.Locale;

/**
 * DialogoSeleccion
 *
 * @author Germán Gascón
 * @version 0.2, 2021-03-04
 * @since 0.1, 2019-03-04
 **/
public class DialogoSeleccion extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final String[] items = {"Español", "Inglés"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.seleccionar)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("Diálogos", "Opción elegida: " + items[i]);

                        if (items[i].equalsIgnoreCase("Inglés")){
                            Toast.makeText(getContext(),"You have selected English!!",Toast.LENGTH_LONG).show();
                            setLocal(DialogoSeleccion.this, "en");
                            requireActivity().finish();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        } else if (items[i].equalsIgnoreCase("Español")) {
                            Toast.makeText(getContext(),"Has seleccionado Castellano!!",Toast.LENGTH_LONG).show();
                            System.out.println("Marica!!");
                            setLocal(DialogoSeleccion.this,"es");
                            requireActivity().finish();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        }
                    }
                });
        return builder.create();
    }

    private void setLocal(DialogoSeleccion dialogoSeleccion, String lenguaje) {
        Locale locale = new Locale(lenguaje);
        locale.setDefault(locale);
        Resources resources = dialogoSeleccion.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }
}
