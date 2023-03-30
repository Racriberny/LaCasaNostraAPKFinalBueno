package com.cristobalbernal.lacasanostraapk.interfaces;
import com.cristobalbernal.lacasanostraapk.modelos.Producto;
import com.cristobalbernal.lacasanostraapk.modelos.Reservas;
import com.cristobalbernal.lacasanostraapk.modelos.Tipo;
import com.cristobalbernal.lacasanostraapk.modelos.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


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


    @POST("usuario/login")
    Call<Usuario> logUsuario (
            @Body Usuario user
    );
    /*
    @POST("frase/add")
    Call<Boolean> addFrase(@Body Frase frase);

    @GET("autor/all")
    Call<List<Autor>> getAutor();

    @POST("autor/add")
    Call<Boolean> addAutor(@Body Autor autor);

    @GET("categoria/all")
    Call<List<Categoria>> getCategoria();

    @POST("categoria/add")
    Call<Boolean> addCategoria(@Body Categoria categoria);

    @GET("usuario/all")
    Call<List<Usuario>> getUsuario();

    @POST("usuario/add")
    Call<Boolean> addUsuario(@Body Usuario usuario);


    @POST("usuario/login")
    Call<Boolean> logUsuario (
            @Body Usuario user
    );

    @PUT("categoria/update")
    Call<Boolean> modificarCategoria(
            @Body Categoria categoria
    );
    @PUT("autor/update")
    Call<Boolean> modificarAutores(
            @Body Autor autor
    );
    @PUT("frase/update")
    Call<Boolean> modificarFrases(
            @Body Frase frase
    );
    @POST("frase/addValues")
    @FormUrlEncoded
    Call<Boolean> addFraseValues(@Field("texto") String texto,
                                 @Field("fechaProgramada") Date fechaProgramada,
                                 @Field("idAutor") int idAutor,
                                 @Field("idCategoria")int idCategoria);

     */
}
