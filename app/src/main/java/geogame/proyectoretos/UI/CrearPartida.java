package geogame.proyectoretos.UI;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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

import butterknife.ButterKnife;
import geogame.proyectoretos.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
    @BindView(R.id.bt_crearpartida_numeroderetos)
    EditText txt_numeroretos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_partida);
        ButterKnife.bind(this);

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


                  Intent i = new Intent(getApplicationContext(),CrearRetoActivity.class);
                   i.putExtra("partidaNombre",txt_nombre.getText().toString());
                    i.putExtra("partidaContra",txt_contra.getText().toString());
                    i.putExtra("partidaNumerosRetos",txt_numeroretos.getText().toString());

                    startActivity(i);
                }else {
                    Toast.makeText(getApplicationContext(), "Nombre ya esta en uso, ponga otro.", Toast.LENGTH_LONG).show();

                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
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
