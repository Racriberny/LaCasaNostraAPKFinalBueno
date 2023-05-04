package com.cristobalbernal.lacasanostraapk;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cristobalbernal.lacasanostraapk.Utils.EncodingImg;
import com.cristobalbernal.lacasanostraapk.fragments.FragmentDetalle;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Home;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Carta;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Acceder;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Promociones;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Reserva;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Setting;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Tipo_Producto;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.interfaces.IProductoSeleccionado;
import com.cristobalbernal.lacasanostraapk.interfaces.ITipoComida;
import com.cristobalbernal.lacasanostraapk.modelos.Producto;
import com.cristobalbernal.lacasanostraapk.modelos.Tipo;
import com.cristobalbernal.lacasanostraapk.modelos.Usuario;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ITipoComida, IProductoSeleccionado,Fragment_Tipo_Producto.IOnAttachListener,FragmentDetalle.IOnAttachListenerDetalle{
    private IAPIService iapiService;
    private List<Tipo> tipos;
    private List<Producto> productos;
    private List<Usuario> usuarios;

    private Tipo tipoSeleccionado;
    private Tipo tipoSeleccionadoProducto;
    private int productoSeleccionado;
    private SharedPreferences sharedPreferences;
    private static NavigationView navigationView;
    private static View headerView;
    private String userNombre;
    private static DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iapiService = RestClient.getInstance();
        tipos = new ArrayList<>();
        productos = new ArrayList<>();
        usuarios = new ArrayList<>();
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userNombre = sharedPreferences.getString("nombreDeUsuario", "");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        if (userNombre.equals("")){
            cambiarAlNormal(this);

            Button iniciarSesionBtn = headerView.findViewById(R.id.iniciarSesionDra);
            iniciarSesionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .replace(R.id.content_frame, Fragment_Acceder.class, null)
                            .commit();
                    drawer.closeDrawer(GravityCompat.START);

                }
            });
        }else {
            cambiarHeaderNavigationView();
        }


    }

    private void cambiarHeaderNavigationView() {
        // Inflar el nuevo layout del header de navegación
        View nuevoHeaderView = LayoutInflater.from(this).inflate(R.layout.nav_header_main_login, null);
        // Actualizar el header del NavigationView con el nuevo View de header
        navigationView.removeHeaderView(headerView);
        navigationView.addHeaderView(nuevoHeaderView);
        headerView = nuevoHeaderView;
        TextView headerTextView = nuevoHeaderView.findViewById(R.id.header_text);
        ImageView headerImageview = nuevoHeaderView.findViewById(R.id.header_image);


        iapiService.getUsuario().enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                assert response.body() != null;
                usuarios.addAll(response.body());
                for (int i = 0; i <usuarios.size() ; i++) {
                    if (usuarios.get(i).getCorreoElectronico().equals(userNombre)){
                        headerTextView.setText("Bienvenido "  + usuarios.get(i).getNombre());
                        headerImageview.setImageBitmap(EncodingImg.decode(usuarios.get(i).getImagen()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

            }
        });




    }
    public static void cambiarAlNormal(Context context){
        View header = LayoutInflater.from(context).inflate(R.layout.nav_header_main,null);
        navigationView.removeHeaderView(headerView);
        navigationView.addHeaderView(header);
        headerView = header;

        Button iniciarSesionBtn = headerView.findViewById(R.id.iniciarSesionDra);
        iniciarSesionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();

                // Iniciar una transacción para reemplazar el fragment actual
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Crear una instancia del nuevo fragment y reemplazar el actual con el nuevo
                Fragment_Acceder fragment_acceder = new Fragment_Acceder();
                fragmentTransaction.replace(R.id.content_frame, fragment_acceder);
                // Confirmar la transacción
                fragmentTransaction.commit();
                drawer.closeDrawer(GravityCompat.START);

            }
        });



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userNombre = sharedPreferences.getString("nombreDeUsuario", "");
        FragmentManager manager = getSupportFragmentManager();
        int id = item.getItemId();
        if(id == R.id.home) {
            manager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.content_frame, Fragment_Home.class, null)
                    .commit();
        } else if(id == R.id.carta) {
            manager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.content_frame, Fragment_Carta.class, null)
                    .commit();
        } else if (id == R.id.reservas) {
            if (userNombre.equals("")){
                Toast.makeText(getBaseContext(), R.string.inicio_session, Toast.LENGTH_LONG).show();
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Acceder.class, null)
                        .commit();
            }else {
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Reserva.class, null)
                        .commit();

            }

        } else if (id == R.id.promociones) {
            if (userNombre.equals("")){
                Toast.makeText(getBaseContext(), R.string.inicio_session, Toast.LENGTH_LONG).show();
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Acceder.class, null)
                        .commit();
            }else {
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Promociones.class, null)
                        .commit();
            }
        } else if (id == R.id.setting) {
            if (userNombre.equals("")){
                Toast.makeText(getBaseContext(), R.string.inicio_session, Toast.LENGTH_LONG).show();
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Acceder.class, null)
                        .commit();
            }else {
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Setting.class,null)
                        .commit();
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTipoSeleccionada(int id) {
        iapiService.getTipo().enqueue(new Callback<List<Tipo>>() {
            @Override
            public void onResponse(Call<List<Tipo>> call, Response<List<Tipo>> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    tipos.addAll(response.body());
                    tipoSeleccionado = tipos.get(id);
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .replace(R.id.content_frame, Fragment_Tipo_Producto.class, null)
                            .commit();
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Tipo>> call, Throwable t) {

            }
        });
    }

    @Override
    public Tipo getTipoSelecionado() {
        return tipoSeleccionado;
    }

    @Override
    public int ipProducto() {
        return productoSeleccionado;
    }
    @Override
    public void IComidaSeleccionada(int idTipo, int idProducto) {
        iapiService.getProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                assert response.body() != null;
                productos.addAll(response.body());
                productoSeleccionado = idProducto;
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, FragmentDetalle.class, null)
                        .commit();
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {

            }
        });
    }
}
