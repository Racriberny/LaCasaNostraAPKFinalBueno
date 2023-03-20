package com.cristobalbernal.lacasanostraapk.interfaces;
import com.cristobalbernal.lacasanostraapk.modelos.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface IAPIService {

    @GET("producto/all")
    Call<List<Producto>> getProductos();
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
