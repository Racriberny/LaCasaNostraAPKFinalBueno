package com.cristobalbernal.lacasanostraapk;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import com.cristobalbernal.lacasanostraapk.activitys.SettingActivity;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Home;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Carta;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Acceder;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Producto;
import com.cristobalbernal.lacasanostraapk.fragments.Fragment_Tipo_Producto;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.interfaces.IProductoComida;
import com.cristobalbernal.lacasanostraapk.interfaces.ITipoComida;
import com.cristobalbernal.lacasanostraapk.modelos.Producto;
import com.cristobalbernal.lacasanostraapk.modelos.Tipo;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ITipoComida,Fragment_Tipo_Producto.IOnAttachListener, IProductoComida, Fragment_Producto.IOnAttachListener {
    private IAPIService iapiService;
    private List<Tipo> tipos;
    private List<Producto> productos;
    private Tipo tipoSeleccionado;
    private Producto productoSeleccionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iapiService = RestClient.getInstance();
        tipos = new ArrayList<>();
        productos = new ArrayList<>();
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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.home) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.content_frame, Fragment_Home.class, null)
                    .commit();
        } else if(id == R.id.carta) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.content_frame, Fragment_Carta.class, null)
                    .commit();
        } else if(id == R.id.acceder) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.content_frame, Fragment_Acceder.class, null)
                    .commit();
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
    public void onProductoSeleccionada(int id) {
        iapiService.getProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(@NonNull Call<List<Producto>> call, @NonNull Response<List<Producto>> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    productos.addAll(response.body());
                    productoSeleccionado = productos.get(id);

                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .replace(R.id.content_frame, Fragment_Producto.class, null)
                            .commit();
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {

            }
        });
    }

    @Override
    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }
}
