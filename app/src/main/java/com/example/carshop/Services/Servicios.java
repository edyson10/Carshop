package com.example.carshop.Services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carshop.VehiculosActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Servicios {

    public static final String URL_LOCAL = "http://192.168.0.1/Carshop_services/";
    public static final String URL_AWS = "http://54.144.85.160/Carshop_services/";

    public static void obtenerCategorias(final Context context) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String urlEnvio = URL_LOCAL + "listarCategoria.php";

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Obtiendo Categorias...");
        progressDialog.show();

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlEnvio,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TAG--- ", "entre aqui");
                        try {
                            JSONArray jsonArray = response.getJSONArray("categoria");
                            Log.e("XXXXXXX", jsonArray.getString(2));
                            JSONObject jsonObject = jsonArray.getJSONObject(1); //0 indica el primer objeto dentro del array.
                            Log.e("------> ", jsonObject.getString("nombre")); //Agrega valor de character a TextView.
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.getMessage() != null) {
                            if (!error.getMessage().isEmpty()) {
                                Toast.makeText(context, "Error en el servidor", Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        queue.add(jsonObjRequest);

    }
}
