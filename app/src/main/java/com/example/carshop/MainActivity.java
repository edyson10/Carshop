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
    TextView tituloToolbar;
    ImageView btn_atras, flecha_atras;
    String resultado;
    int validar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        add = findViewById(R.id.btnAdd);
        bottomBar = findViewById(R.id.bottom_bar);
        recycler = (RecyclerView) findViewById(R.id.recyclerCategoria);
        progressDialog = new ProgressDialog(MainActivity.this);
        tituloToolbar = (TextView) findViewById(R.id.tvTitulo);
        tituloToolbar.setText(R.string.app_name);
        btn_atras = (ImageView) findViewById(R.id.btn_atras_toolbar);
        flecha_atras = (ImageView) findViewById(R.id.ic_flecha_retroceso);
        btn_atras.setVisibility(View.INVISIBLE);
        flecha_atras.setVisibility(View.INVISIBLE);

        listCategoria = new ArrayList<>();
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        cargarCategoriasGET();

        recycler.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new Adapter_categoria(listCategoria, MainActivity.this);
        recycler.setAdapter(adapter);

        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.search:
                    case R.id.account:
                        mostrarToast("Opción no disponible");
                        break;
                }
                return true;
            }
        });

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion = listCategoria.get(recycler.getChildAdapterPosition(v)).getId_categria();
                resultado = Servicios.cargarVehiculoCategoria(posicion);
                validar = Servicios.validarDatosJSON(resultado);
                if (validar < 0) {
                    Toast.makeText(getApplicationContext(), R.string.failVehicule, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), VehiculosActivity.class);
                    intent.putExtra("id_categoria", listCategoria.get(recycler.getChildAdapterPosition(v)).getId_categria());
                    startActivity(intent);
                }
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

    public void cargarCategoriasGET() {
        ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    resultado = Servicios.obtenerCategorias();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            validar = Servicios.validarDatosJSON(resultado);
                            if (validar > 0) {
                                progressDialog.dismiss();
                                llenarRecycler(resultado);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                    //progressDialog.hide();
                }
            };
            thread.start();
        } else {
            Toast.makeText(this, R.string.verificarConexion,Toast.LENGTH_SHORT).show();
        }
    }

    public void llenarRecycler(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                Categorias categoria = new Categorias();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                categoria.setFoto((R.drawable.bmw));
                categoria.setId_categria(Integer.parseInt(jsonObject.getString("id_categoria")));
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
                    Toast.makeText(getApplicationContext(), R.string.completarCampos, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.registerOk, Toast.LENGTH_SHORT).show();
                    Servicios.crearCategoria(getApplicationContext(), nombre.getText().toString());
                    alertDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
