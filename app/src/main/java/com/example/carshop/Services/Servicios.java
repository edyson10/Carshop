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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carshop.Clases.Categorias;

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

    public static final String URL_LOCAL = "http://192.168.0.6/carshop_services/";
    public static final String URL_AWS = "http://54.144.85.160/carshop_services/";
    public static ArrayList<Categorias> listaCategoria;
    private static RequestQueue queue;
    private static ProgressDialog progressDialog;

    public static String obtenerCategorias() {
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = null;
        //String url_local = "http://192.168.56.1/ServiciosWeb/empleadosBD.php";
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

    public static void crearCategoria(final Context context, final String nombre) {
        queue = Volley.newRequestQueue(context);
        //progressDialog.setMessage("Cargando...");
        //progressDialog.show();

        String urlEnvio = Servicios.URL_AWS + "crearCategoria.php";

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
                        Toast.makeText(context, "Se ha creado correctamente", Toast.LENGTH_SHORT).show();
                        Log.e("XXXXX", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "A ocurrido un error", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", error.toString());
                        progressDialog.dismiss();
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
}
