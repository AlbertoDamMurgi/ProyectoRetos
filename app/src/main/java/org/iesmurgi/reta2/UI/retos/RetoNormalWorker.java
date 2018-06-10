package org.iesmurgi.reta2.UI.retos;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import androidx.work.Worker;
/**
 * Clase que lanza la tarea de insertar la puntuacion de un reto a la base de datos
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class RetoNormalWorker extends Worker {
    @NonNull
    @Override
    public WorkerResult doWork() {
        final String URL = "http://geogame.ml/api/insertar_puntuacion.php";
        String idUsuario = getInputData().getString("idUsuario",null);
        String idReto = getInputData().getString("idReto",null);
        String tiempo = getInputData().getString("tiempo",null);
        String puntos = getInputData().getString("puntuacion",null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.contains("success")) {

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idUsuario",  idUsuario);
                params.put("idReto",idReto);
                params.put("tiempo", tiempo);
                params.put("puntuacion", ""+puntos);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);





        return WorkerResult.SUCCESS;

    }
}
