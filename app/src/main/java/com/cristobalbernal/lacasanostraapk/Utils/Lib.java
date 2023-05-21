package com.cristobalbernal.lacasanostraapk.Utils;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Lib {
    public static boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
