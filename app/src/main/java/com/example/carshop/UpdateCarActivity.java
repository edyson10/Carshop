package com.example.carshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.carshop.Clases.Vehiculo;
import com.example.carshop.Services.Servicios;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class UpdateCarActivity extends AppCompatActivity {

    Button actualizar;
    EditText marca, modelo, precio, asientos, estado, fecha, categoria, tipo, cantidad;
    TextView cancelar;
    Bundle bundle;
    TextView tituloToolbar;
    ImageView btn_atras, flecha_atras;

    Calendar mCurrentDate;
    int dia, mes, anio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car);
        getSupportActionBar().hide();

        tituloToolbar = (TextView) findViewById(R.id.tvTitulo);
        tituloToolbar.setText(R.string.updateCar);
        btn_atras = (ImageView) findViewById(R.id.btn_atras_toolbar);
        flecha_atras = (ImageView) findViewById(R.id.ic_flecha_retroceso);
        marca = (EditText) findViewById(R.id.txtMarcaUpdate);
        modelo = (EditText) findViewById(R.id.txtModeloUpdate);
        precio = (EditText) findViewById(R.id.txtPrecioUpdate);
        asientos = (EditText) findViewById(R.id.txtAsientosUpdate);
        estado = (EditText) findViewById(R.id.txtEstadoUpdate);
        fecha = (EditText) findViewById(R.id.txtFechaUpdate);
        categoria = (EditText) findViewById(R.id.txtCategoriaUpdate);
        tipo = (EditText) findViewById(R.id.txtTipoUpdate);
        cantidad = (EditText) findViewById(R.id.txtCantidadUpdate);
        cancelar = (TextView) findViewById(R.id.btnCancelarUpdate);
        actualizar = (Button) findViewById(R.id.btnActualizarCarro);

        bundle = getIntent().getExtras();
        final int id_vehiculo = bundle.getInt("id_vehiculo");
        Thread thread = new Thread() {
            @Override
            public void run() {
                final String resultado = Servicios.obtenerDatosVehiculoGET(id_vehiculo);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int validar = Servicios.validarDatosJSON(resultado);
                        if (validar < 0) {
                            Toast.makeText(getApplicationContext(), R.string.failDataVehicule, Toast.LENGTH_SHORT).show();
                        } else {
                            llenarDatos(resultado);
                        }
                    }
                });
            }
        };
        thread.start();

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarVehiculo();
            }
        });

        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    public void actualizarVehiculo() {
        final String marca = this.marca.getText().toString();
        final String modelo = this.modelo.getText().toString();
        final String precio = this.precio.getText().toString();
        final String asientos = this.asientos.getText().toString();
        final String estado = this.estado.getText().toString();
        final String fecha = this.fecha.getText().toString();
        String categoria = this.categoria.getText().toString();
        final String tipo = this.tipo.getText().toString();
        final String cantidad = this.cantidad.getText().toString();

        final int id_vehiculo = bundle.getInt("id_vehiculo");

        if (marca.isEmpty() || modelo.isEmpty() || precio.isEmpty() || asientos.isEmpty() || estado.isEmpty() || fecha.isEmpty()
                || categoria.isEmpty() || tipo.isEmpty() || cantidad.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.completarCampos, Toast.LENGTH_SHORT).show();
        } else {
            new AlertDialog.Builder(UpdateCarActivity.this)
                    .setIcon(R.drawable.online_shop)
                    .setTitle(R.string.update)
                    .setMessage(R.string.sureUpdate)
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                           enviarDatosGET(id_vehiculo, marca, modelo, Integer.parseInt(precio), Integer.parseInt(asientos), estado, fecha, tipo, cantidad);
                        }
                    }).show();
        }
    }

    private void llenarDatos(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                marca.setText(jsonObject.getString("marca"));
                modelo.setText(jsonObject.getString("modelo"));
                precio.setText(jsonObject.getString("precio"));
                asientos.setText(jsonObject.getString("asientos"));
                estado.setText(jsonObject.getString("estado"));
                fecha.setText(jsonObject.getString("fecha_publicacion"));
                categoria.setText(jsonObject.getString("categoria"));
                tipo.setText(jsonObject.getString("tipo"));
                cantidad.setText(jsonObject.getString("cantidad"));
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void enviarDatosGET(final int id_vehiculo, final String marca, final String modelo, final int precio, final int asientos
            , final String estado, final String fecha, final String tipo, final String cantidad) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                final String resultado = Servicios.actualizarVehiculoPUT(id_vehiculo, marca, modelo, precio, asientos, estado, fecha, tipo, cantidad);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int validar = Servicios.validarDatosJSON(resultado);
                        if (validar > 0) {
                            Toast.makeText(getApplicationContext(), R.string.updateOk, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.updateFail, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        thread.start();
    }
}
