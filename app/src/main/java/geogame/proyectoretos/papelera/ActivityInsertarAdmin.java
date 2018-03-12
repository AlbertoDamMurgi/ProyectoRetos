package geogame.proyectoretos.papelera;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import geogame.proyectoretos.Data.DAOS.AdminDao;
import geogame.proyectoretos.R;



public class ActivityInsertarAdmin extends AppCompatActivity {
    EditText txt_user,txt_contra;
    Button bt_insertar;
    ProgressDialog progressDialog;

    AdminDao dao;

    @BindView(R.id.tv_prueba)
    TextView prueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_admin);

        txt_user=findViewById(R.id.txt_passwd);
        txt_contra=findViewById(R.id.txt_username);
        bt_insertar=findViewById(R.id.bt_insertarAdmin);
        progressDialog = new ProgressDialog(ActivityInsertarAdmin.this);





        bt_insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d("pene","aaaaaaaaa");
/*
                progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
                progressDialog.show();

                final String URL = "http://geogame.ml/api/insertar_admin.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("success")) {
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", txt_user.getText().toString());
                        params.put("passwd", txt_contra.getText().toString());
                        return params;
                    }
                };


                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
*/
            }
        });


        findViewById(R.id.bt_listar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Log.e("adad","addwad");
                final String URL = "http://geogame.ml/api/lista_admins.php";

                JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i=0;i<response.length();i++){

                            try {
                                JSONObject o =response.getJSONObject(i);

                               modelo.insertAdministrador( new Admin(o.getInt("idAdmin"),o.getString("username"),o.getString("passwd"),o.getInt("superAdmin")));

                            } catch (JSONException e) {

                            }
                        }

                       Log.e("LISTA AA AAA",response.toString());

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("adad",error.getMessage());
                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
*/




            }
        });







    }
}
