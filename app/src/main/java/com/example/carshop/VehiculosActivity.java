package com.example.carshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carshop.Adapter.Adapter_vehiculo;
import com.example.carshop.Clases.Vehiculo;
import com.example.carshop.Services.Servicios;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VehiculosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Vehiculo> listVehiculo;
    Adapter_vehiculo adapter_vehiculo;
    Button addCar;
    TextView tituloToolbar;
    ImageView btn_atras, flecha_atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);
        getSupportActionBar().hide();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerCar);
        addCar = (Button) findViewById(R.id.btnAddCar);
        tituloToolbar = (TextView) findViewById(R.id.tvTitulo);
        tituloToolbar.setText(R.string.listVehicule);
        btn_atras = (ImageView) findViewById(R.id.btn_atras_toolbar);
        flecha_atras = (ImageView) findViewById(R.id.ic_flecha_retroceso);

        listVehiculo = new ArrayList<>();
        obtenerDatosGET();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter_vehiculo = new Adapter_vehiculo(listVehiculo, this);
        recyclerView.setAdapter(adapter_vehiculo);

        adapter_vehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoria = listVehiculo.get(recyclerView.getChildAdapterPosition(v)).getCategoria();
                if (!categoria.contains("Electrico")) {
                    Intent intent= new Intent(getApplicationContext(), UpdateCarActivity.class);
                    intent.putExtra("id_vehiculo", listVehiculo.get(recyclerView.getChildAdapterPosition(v)).getId_vehiculo());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.modifyFail, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.noDisponible, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void obtenerDatosGET() {
        ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    Bundle bundle = getIntent().getExtras();
                    int id_categoria = bundle.getInt("id_categoria");
                    final String resultado = Servicios.cargarVehiculoCategoria(id_categoria);
                    runOnUiThread(new Runnable() {
                        @Override
                            public void run() {
                            int r = Servicios.validarDatosJSON(resultado);
                            if (r > 0) {
                                llenarRecycler(resultado);
                                adapter_vehiculo.notifyDataSetChanged();
                                //progressDialog.dismiss();
                            }
                        }
                    });
                    //progressDialog.hide();
                }
            };
            thread.start();
        } else {
            Toast.makeText(this, R.string.verificarConexion, Toast.LENGTH_SHORT).show();
        }
    }

    public void llenarRecycler(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                Vehiculo vehiculo = new Vehiculo();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                vehiculo.setId_vehiculo(Integer.parseInt(jsonObject.getString("id_carro")));
                vehiculo.setModelo(jsonObject.getString("marca") + " " + jsonObject.getString("modelo"));
                vehiculo.setPrecio(Integer.parseInt(jsonObject.getString("precio")));
                vehiculo.setAsientos(Integer.parseInt(jsonObject.getString("asientos")));
                vehiculo.setEstado(jsonObject.getString("estado"));
                vehiculo.setFecha_publicacion(jsonObject.getString("fecha_publicacion"));
                vehiculo.setCategoria(jsonObject.getString("categoria"));
                vehiculo.setTipo(jsonObject.getString("tipo"));
                vehiculo.setCantidad(jsonObject.getString("cantidad"));
                listVehiculo.add(vehiculo);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
