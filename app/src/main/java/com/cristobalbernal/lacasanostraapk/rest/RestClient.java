package com.cristobalbernal.lacasanostraapk.rest;
import android.content.Context;
import android.content.SharedPreferences;

import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static IAPIService instance;
    public static final int PORT = 8080;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String IP_KEY = "ip";

    public static final String DEFAULT_IP = "192.168.1.147";
    private static final String BASE_URL = "http://%s:" + PORT + "/";

    private RestClient() {
        // Constructor privado para evitar instancias directas
    }

    public static synchronized IAPIService getInstance(Context context) {
        if (instance == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String ip = sharedPreferences.getString(IP_KEY, DEFAULT_IP);
            String baseUrl = String.format(BASE_URL, ip);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            instance = retrofit.create(IAPIService.class);
        }
        return instance;
    }
}
