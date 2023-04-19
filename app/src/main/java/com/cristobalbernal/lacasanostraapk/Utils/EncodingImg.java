package com.cristobalbernal.lacasanostraapk.Utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class EncodingImg {
    public static Bitmap decode(String sImage){
        byte[] bytes = Base64.decode(sImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
    //Pasar la imagen de Uri a Base64
    private String getBase64FromUri(Uri uri) throws IOException {
        @SuppressLint("Recycle") InputStream inputStream = getContentResolver().openInputStream(uri);
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
