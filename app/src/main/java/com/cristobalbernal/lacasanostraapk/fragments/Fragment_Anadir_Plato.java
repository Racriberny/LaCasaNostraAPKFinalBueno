package com.cristobalbernal.lacasanostraapk.fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.Utils.EncodingImg;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.modelos.Tipo;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Anadir_Plato extends Fragment {
    private ActivityResultLauncher<PickVisualMediaRequest> pickImage;
    private ImageView photo;
    private Button guardar;
    private String base64;
    private IAPIService iapiService;
    private EditText añadirPlato;
    private EditText descripcion;
    public Fragment_Anadir_Plato(){
        super(R.layout.fragment_anadir_plato);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iapiService = RestClient.getInstance(requireContext());
        añadirPlato = view.findViewById(R.id.nombreTipo);
        descripcion = view.findViewById(R.id.descripcionTipo);
        photo = view.findViewById(R.id.imagenPlato);
        guardar = view.findViewById(R.id.guardarPlatos);
        pickImage = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                try {
                    base64 = EncodingImg.getBase64FromUri(requireContext(),uri);
                    photo.setImageBitmap(EncodingImg.decode(base64));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage.launch(new PickVisualMediaRequest());
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plato = añadirPlato.getText().toString();
                String descripcionTip = descripcion.getText().toString();
                if (plato.isEmpty()){
                    añadirPlato.setError("Es necesario escribir en este campo.");
                    añadirPlato.requestFocus();
                    return;
                }
                if (descripcionTip.isEmpty()){
                    descripcion.setError("Es necesario escribir en este campo.");
                    descripcion.requestFocus();
                    return;
                }
                if (base64 == null){
                    Toast.makeText(getContext(), "Por favor, añade una foto", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    iapiService.addTipo(new Tipo(plato,descripcionTip,base64)).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (Boolean.TRUE.equals(response.body())){
                                Toast.makeText(getContext(),R.string.anadido,Toast.LENGTH_SHORT).show();
                                FragmentManager manager = getParentFragmentManager();
                                manager.beginTransaction()
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .replace(R.id.content_frame,Fragment_Home.class,null)
                                        .commit();
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.d("Error","Error");
                        }
                    });
                }
            }
        });

    }
}
