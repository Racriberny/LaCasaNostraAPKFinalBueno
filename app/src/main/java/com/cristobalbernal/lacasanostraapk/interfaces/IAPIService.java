package com.cristobalbernal.lacasanostraapk.interfaces;
import com.cristobalbernal.lacasanostraapk.modelos.Producto;
import com.cristobalbernal.lacasanostraapk.modelos.Reservas;
import com.cristobalbernal.lacasanostraapk.modelos.Tipo;
import com.cristobalbernal.lacasanostraapk.modelos.Usuario;
import com.cristobalbernal.lacasanostraapk.modelos.Vista;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface IAPIService {

    @GET("producto/all")
    Call<List<Producto>> getProductos();

    @GET("tipo/all")
    Call<List<Tipo>> getTipo();

    @GET("usuario/all")
    Call<List<Usuario>> getUsuario();

    @GET("reserva/all")
    Call<List<Reservas>> getReservas();

    @POST("usuario/add")
    Call<Boolean> addUsuario(@Body Usuario usuario);

    @POST("reserva/add")
    Call<Boolean> addReserva(@Body Reservas reservas);

    @POST("producto/add")
    Call<Boolean> addProducto(@Body Producto producto);


    @POST("usuario/login")
    Call<Usuario> logUsuario (
            @Body Usuario user
    );

    @DELETE("usuario/{id}")
    Call<Boolean> deleteUser(@Path("id") int id);

    @DELETE("producto/{id}")
    Call<Boolean> deleteProducto(@Path("id") int id);
    @DELETE("tipo/{id}")
    Call<Boolean> deleteTipo(@Path("id") int id);
    @PUT("usuario/update/{id}")
    Call<Usuario> modificarUser(@Path("id") int id, @Body Usuario usuario);

    @GET("vista/all")
    Call<List<Vista>> getVista();

    @POST("tipo/add")
    Call<Boolean> addTipo(@Body Tipo tipo);

    @PUT("tipo/update/{id}")
    Call<Tipo> modificarTipo(@Path("id") int id, @Body Tipo tipo);
    @PUT("producto/update/{id}")
    Call<Producto> modificarProducto(@Path("id") int id, @Body Producto producto);
}
