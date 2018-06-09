package org.iesmurgi.reta2.UI.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.iesmurgi.reta2.Chat.Chat;
import org.iesmurgi.reta2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Actividad que muestra la foto que ha hecho un equipo en un reto determinado y permite que el administrador pueda puntuarla
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class AdminPuntuarFotoTransicion extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    ProgressDialog progressDialog;
    private String partida, usuario, nombrereto;
    private int idpartida;
    private ArrayList<String> urisString = new ArrayList<>();
    StorageReference storageReference;
    @BindView(R.id.imagen_descargada_admin)
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_puntuar_foto_transicion);
        ButterKnife.bind(this);
        try {
            usuario = getIntent().getExtras().getString("USUARIO");
            partida = getIntent().getExtras().getString("PARTIDA");
            nombrereto = getIntent().getExtras().getString("NOMBRERETO");
            idpartida = getIntent().getExtras().getInt("IDPARTIDA");

        }catch (NullPointerException ex){

        }

        storageReference = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        progressDialog = new ProgressDialog(AdminPuntuarFotoTransicion.this);

        myRef.child("Imagenes").child(partida).child(usuario).child(nombrereto).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Chat aux = data.getValue(Chat.class);

                    urisString.add(aux.getMensaje().substring(aux.getMensaje().lastIndexOf("/") + 1, aux.getMensaje().length()));

                }
                try {
                    StorageReference reference = storageReference.child("Imagenes").child(partida).child(usuario).child(nombrereto).child(urisString.get(0));
                    Glide.with(getApplicationContext()).using(new FirebaseImageLoader()).load(reference).into(imagen);
                }catch (IndexOutOfBoundsException ex){

                    Log.e("Error array uriString",""+ex.getMessage());

                }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * Método que valida la foto y suma los puntos al equipo.
     */
    @OnClick(R.id.btn_aceptarfoto_admin)
    void aceptarFoto() {

        progressDialog.setMessage("Aceptando foto ...");
        progressDialog.show();

        final String URL = "http://geogame.ml/api/update_puntuacion.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {

                    myRef.child("Imagenes").child(partida).child(usuario).child(nombrereto).removeValue();
                    Toast.makeText(getApplicationContext(), "Foto validada, puntos añadidos con exito.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), AdministrarPartidaAdminActivity.class).putExtra("ID", idpartida).putExtra("PARTIDA", partida));
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Fallo al aceptar ", Toast.LENGTH_LONG).show();
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
                params.put("nombreUsuario", usuario);
                params.put("nombreReto",nombrereto);
                params.put("idPartida", ""+idpartida);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    /**
     * Método que rechaza la foto, la cual no puntua nada a ese equipo.
     */
    @OnClick(R.id.btn_rechazarfoto_admin)
    void rechazarFoto() {
        myRef.child("Imagenes").child(partida).child(usuario).child(nombrereto).removeValue();
        Toast.makeText(this, "Foto rechazada, no se puntuará ese reto.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), AdministrarPartidaAdminActivity.class).putExtra("ID", idpartida).putExtra("PARTIDA", partida));

    }


}
