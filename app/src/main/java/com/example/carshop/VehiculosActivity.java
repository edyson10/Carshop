package com.example.carshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.carshop.Adapter.Adapter_categoria;
import com.example.carshop.Adapter.Adapter_vehiculo;
import com.example.carshop.Clases.Categorias;
import com.example.carshop.Clases.Vehiculo;

import java.util.ArrayList;

public class VehiculosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Vehiculo> listVehiculo;
    Adapter_vehiculo adapter_vehiculo;
    Button addCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);
        getSupportActionBar().hide();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerCar);
        addCar= (Button) findViewById(R.id.btnAddCar);
        listVehiculo = new ArrayList<>();
        llenarRecycler();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter_vehiculo = new Adapter_vehiculo(listVehiculo);
        recyclerView.setAdapter(adapter_vehiculo);
    }

    public void llenarRecycler() {
        listVehiculo.add(new Vehiculo("BMW i3", 165000000, "Nuevo",
                "23-10-2020", "Eléctrico", 5, "Carga de bateria: 39,7 kWh"));
        listVehiculo.add(new Vehiculo("Volvo FH", 500000000, "Nuevo",
                "23-10-2020", "Camión", 2, "Capacidad máxima: 30000 Kg"));
        listVehiculo.add(new Vehiculo("Hyundai Diesel", 35000000, "Usado",
                "23-10-2020", "Comercial", 2, "Carga útil: 1500Kg"));
    }
}
