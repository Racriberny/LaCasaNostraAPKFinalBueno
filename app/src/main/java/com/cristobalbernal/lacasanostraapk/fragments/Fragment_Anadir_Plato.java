package com.cristobalbernal.lacasanostraapk.fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.Utils.EncodingImg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Fragment_Anadir_Plato extends Fragment {
    private ActivityResultLauncher<PickVisualMediaRequest> pickImage;
    private ImageView photo;
    private Button guardar;
    private String base64;
    public Fragment_Anadir_Plato(){
        super(R.layout.fragment_anadir_plato);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        photo = view.findViewById(R.id.imagenPlato);
        guardar = view.findViewById(R.id.guardarPlatos);
        pickImage = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                try {
                    base64 = getBase64FromUri(uri);
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
                Toast.makeText(getContext(),String.valueOf(base64.length()),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getBase64FromUri(Uri uri) throws IOException {
        @SuppressLint("Recycle")
        InputStream inputStream = requireActivity().getContentResolver().openInputStream(uri);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[100000];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }

        byte[] imageBytes = outputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}
