package com.example.carshop.Services;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carshop.Clases.Categorias;
import com.example.carshop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Servicios {

    public static final String URL_LOCAL = "http://192.168.1.7/carshop_services/";
    public static final String URL_AWS = "http://54.144.85.160/carshop_services/";
    public static ArrayList<Categorias> listaCategoria;
    private static RequestQueue queue;
    private static ProgressDialog progressDialog;

    public static String obtenerCategorias() {
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = null;
        String url_aws = URL_AWS + "listarCategoria.php";

        try {
            url = new URL(url_aws);
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            respuesta = conection.getResponseCode();
            resul = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = new BufferedInputStream(conection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((linea = reader.readLine()) != null) {
                    resul.append(linea);
                }
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return resul.toString();
    }

    public static void crearCategoria(final Context context, final String nombre) {

        queue = Volley.newRequestQueue(context);
        String urlEnvio = URL_AWS + "crearCategoria.php";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nombre", nombre);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlEnvio,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, R.string.registerOk, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("nombre", nombre);
                return parametros;
            }
        };
        queue.add(stringRequest);
    }

    public static String cargarVehiculoCategoria (int categoria) {
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = null;
        String url_local = URL_LOCAL + "listarVehiculoCategoria.php?";
        String url_aws = URL_AWS + "listarVehiculoCategoria.php?";

        try {
            url = new URL(url_aws + "categoria=" + categoria);
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            respuesta = conection.getResponseCode();
            resul = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = new BufferedInputStream(conection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((linea = reader.readLine()) != null) {
                    resul.append(linea);
                }
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return resul.toString();
    }

    public static String obtenerDatosVehiculoGET(int id_vehiculo) {
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = null;
        String url_local = URL_LOCAL + "cargarVehiculo.php?";
        String url_aws = URL_AWS + "cargarVehiculo.php?";

        try {
            url = new URL(url_aws + "id_vehiculo=" + id_vehiculo);
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            respuesta = conection.getResponseCode();
            resul = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = new BufferedInputStream(conection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((linea = reader.readLine()) != null) {
                    resul.append(linea);
                }
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return resul.toString();
    }


    public static String actualizarVehiculoPUT (int id_vehiculo, String marca, String modelo, int precio, int asientos, String estado
            , String fecha_publicacion, String tipo, String cantidad) {
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = null;
        String url_local = URL_LOCAL + "actualizarVehiculo.php?";
        String url_aws = URL_AWS + "actualizarVehiculo.php?";
        String mod = modelo.replace(" ", "%20");
        String can = cantidad.replace(" ", "%20");

        try {
            url = new URL(url_aws + "id_vehiculo=" + id_vehiculo + "&marca=" + marca + "&modelo=" + mod + "&precio=" + precio
                    + "&asientos=" + asientos + "&estado=" + estado + "&fecha=" + fecha_publicacion + "&tipo=" + tipo + "&cantidad=" + can);
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            respuesta = conection.getResponseCode();
            resul = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = new BufferedInputStream(conection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((linea = reader.readLine()) != null) {
                    resul.append(linea);
                }
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return resul.toString();
    }

    public static int validarDatosJSON(String response) {
        int res = 0;
        try {
            JSONArray jsonArray = new JSONArray(response);
            if (jsonArray.length() > 0) {
                res = 1;
            }
        } catch (Exception e) {
        }
        return res;
    }
}
