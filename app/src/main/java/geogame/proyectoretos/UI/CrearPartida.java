package geogame.proyectoretos.UI;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

import butterknife.BindView;
import butterknife.OnClick;
import geogame.proyectoretos.R;

public class CrearPartida extends AppCompatActivity {
    ProgressDialog progressDialog;
    @BindView(R.id.txt_crearPartida_nombre)
    EditText txt_nombre;
    @BindView(R.id.txt_crearPartida_contra)
    EditText txt_contra;
    @BindView(R.id.txt_crearPartida_duracion)
    EditText txt_duracion;
    @BindView(R.id.bt_crearpartida_continuar)
    Button bt_continuar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_partida);
        progressDialog = new ProgressDialog(this);

    }


    @OnClick(R.id.bt_crearpartida_continuar)
    void BottonInsertarPartida() {
        Log.i("LOOOOOOOOOOOG","ENTRE EN CLIK");
        progressDialog.setMessage("Creando partida...");
        progressDialog.show();

        final String URL = "http://geogame.ml/api/insertar_partida.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", txt_nombre.getText().toString());
                params.put("passwd", txt_contra.getText().toString());
                params.put("maxDuracion", txt_duracion.getText().toString());
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
