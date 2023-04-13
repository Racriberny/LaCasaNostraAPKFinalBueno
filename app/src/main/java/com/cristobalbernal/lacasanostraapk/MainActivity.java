package com.cristobalbernal.lacasanostraapk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Admin;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Home;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Carta;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Acceder;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Lista_Reservas;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Mi_Perfil;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Promociones;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Reserva;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Tipo_Producto;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.interfaces.ITipoComida;
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
        ITipoComida,Fragment_Tipo_Producto.IOnAttachListener, Fragment_Reserva.IOnActivoUser,
        Fragment_Lista_Reservas.IOnUsuarioListener {
    private IAPIService iapiService;
    private List<Tipo> tipos;

    private Tipo tipoSeleccionado;
    private Usuario usuario;
    private String userNombre;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iapiService = RestClient.getInstance();
        tipos = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);


    }

    private void cargarUsuarioActivo(){
        Intent intent = getIntent();
        usuario = (Usuario) intent.getSerializableExtra("activo");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userNombre = sharedPreferences.getString("nombreDeUsuario","");
        FragmentManager manager = getSupportFragmentManager();
        int id = item.getItemId();
        if(id == R.id.home) {
            manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.content_frame, Fragment_Home.class, null)
                    .commit();
        } else if(id == R.id.carta) {
            manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.content_frame, Fragment_Carta.class, null)
                    .commit();
        } else if(id == R.id.acceder) {
            if (userNombre.equals("")){
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Acceder.class, null)
                        .commit();
            }else {
                Toast.makeText(getBaseContext(), "Has iniciado sesion con " + userNombre, Toast.LENGTH_LONG).show();
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Mi_Perfil.class, null)
                        .commit();
            }
        } else if (id == R.id.reservas) {
            if (userNombre.equals("")){
                Toast.makeText(getBaseContext(), R.string.inicio_session, Toast.LENGTH_LONG).show();
                manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Acceder.class, null)
                        .commit();
            }else {
                manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Reserva.class, null)
                        .commit();

            }
        } else if (id == R.id.promociones) {
            manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.content_frame, Fragment_Promociones.class, null)
                    .commit();
        } else if (id == R.id.setting) {
            cargarUsuarioActivo();
            if (usuario == null){
                manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Acceder.class, null)
                        .commit();
            }else {
                manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Admin.class, null)
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
    public Usuario usuario() {
        if (usuario == null){
            cargarUsuarioActivo();
        }
        return usuario;
    }

    @Override
    public Usuario getUser() {
        if (usuario == null){
            cargarUsuarioActivo();
        }
        return usuario;
    }
}
