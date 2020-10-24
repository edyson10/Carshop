package com.example.carshop;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carshop.Adapter.Adapter_categoria;
import com.example.carshop.Clases.Categorias;
import com.example.carshop.Services.Servicios;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomBar;
    ArrayList<Categorias> listCategoria;
    RecyclerView recycler;
    Adapter_categoria adapter;
    Button add;
    private SwipeRefreshLayout swipeRefreshLayout;
    TextView tituloToolbar;
    ImageView btn_atras, flecha_atras;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();

        add = findViewById(R.id.btnAdd);
        bottomBar = findViewById(R.id.bottom_bar);
        recycler = (RecyclerView) findViewById(R.id.recyclerCategoria);
        progressDialog = new ProgressDialog(MainActivity.this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        /*
        tituloToolbar = (TextView) findViewById(R.id.tvTitulo);
        tituloToolbar.setText("CarShop");
        btn_atras = (ImageView) findViewById(R.id.btn_atras_toolbar);
        flecha_atras = (ImageView) findViewById(R.id.ic_flecha_retroceso);
        btn_atras.setVisibility(View.INVISIBLE);
        flecha_atras.setVisibility(View.INVISIBLE);
         */

        swipeRefreshLayout.setColorSchemeResources(R.color.fondo_gradiente);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                llenarRecycler();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        listCategoria = new ArrayList<>();
        llenarRecycler();

        recycler.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new Adapter_categoria(listCategoria, MainActivity.this);
        recycler.setAdapter(adapter);

        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        mostrarToast("Pulsada opción 1");
                        break;
                    case R.id.search:
                        mostrarToast("Pulsada opción 2");
                        break;
                    case R.id.account:
                        mostrarToast("Pulsada opción 3");
                        break;
                }
                return true;
            }
        });

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Le di clic a " +
                //       listCategoria.get(recycler.getChildAdapterPosition(v)).getNombre(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), VehiculosActivity.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                agregarCategoria();
            }
        });
    }

    private void mostrarToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    public void llenarRecycler() {
        ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    progressDialog.setMessage("Cargando...");
                    final String resultado = Servicios.obtenerCategorias();
                    Log.e("TAGGGGGG ", resultado);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int r = Servicios.validarDatosJSON(resultado);
                            if (r > 0) {
                                cargarCategorias(resultado);
                                adapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }
                        }
                    });
                    progressDialog.hide();
                }
            };
            thread.start();
        } else {
            Toast.makeText(this, "¡Verifique su conexión a internet!",Toast.LENGTH_SHORT).show();
        }
    }

    public void cargarCategorias(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                Categorias categoria = new Categorias();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                categoria.setFoto((R.color.black));
                categoria.setNombre(jsonObject.getString("nombre"));
                listCategoria.add(categoria);
            }
        } catch (Exception e) { }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void agregarCategoria() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(R.layout.dialog_categoria);
        alert.setCancelable(false);
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();

        final EditText nombre;
        Button guardar;
        TextView cancelar;

        nombre = alertDialog.findViewById(R.id.txtNomCategory);
        cancelar = alertDialog.findViewById(R.id.btnCancel);
        guardar = alertDialog.findViewById(R.id.btnAddCategory);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombre.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Complete los campos", Toast.LENGTH_SHORT).show();
                } else {
                    //listCustomer.add(new Customers(nombre.getText().toString(), documento.getText().toString(), R.drawable.person));
                    Toast.makeText(getApplicationContext(), "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                    Servicios.crearCategoria(getApplicationContext(), nombre.getText().toString());
                    alertDialog.dismiss();
                }
            }
        });
    }
}
