package com.cristobalbernal.lacasanostraapk.rest;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static IAPIService instance;
    public static final int PORT = 8080;

    public static final String CASA = "192.168.1.141";
    public static final String INSTI = "192.168.21.129";
    public static final String DASS = "192.168.5.7";
    private static final String BASE_URL = "http://" + CASA + ":" +  PORT + "/";

    /* Lo hacemos privado para evitar que se puedan crear instancias de esta forma */
    private RestClient() {

    }

    public static synchronized IAPIService getInstance() {
        if(instance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            instance = retrofit.create(IAPIService.class);
        }
        return instance;
    }
}
