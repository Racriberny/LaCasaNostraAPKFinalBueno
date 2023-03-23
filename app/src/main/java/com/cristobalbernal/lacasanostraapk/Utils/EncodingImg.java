package com.cristobalbernal.lacasanostraapk.Utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class EncodingImg {
    public static Bitmap Decode(String sImage){
        byte[] bytes = Base64.decode(sImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}
