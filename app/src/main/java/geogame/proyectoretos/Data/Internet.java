package geogame.proyectoretos.Data;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import geogame.proyectoretos.Data.DAOS.AdminDao;
import geogame.proyectoretos.Data.entidades.Admin;
import geogame.proyectoretos.R;
import geogame.proyectoretos.UI.PruebaModel;

/**
 * Created by Santi on 09/03/2018.
 */

public class Internet {
         static String URL;
        RequestQueue requestQueue;

        Context context;

    public Internet(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }
/*


    void insertarAdmin(String user, String contra){

            URL= "http://geogame.ml/api/insertar_admin.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.contains("success")) {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }

            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", user);
                    params.put("passwd", contra);
                    return params;
                }
            };

            requestQueue.add(stringRequest);

        }
*/


     public void listarAdmins(){
         final AdminDao mAdminDao=null;
        URL = "http://geogame.ml/api/lista_admins.php";

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i=0;i<response.length();i++){

                        try {
                            JSONObject o =response.getJSONObject(i);
                            Log.d("DENTRO","RESPONDI");
                            mAdminDao.insertarAdmins(new Admin(o.getInt("idAdmin"),o.getString("username"),o.getString("passwd"),o.getInt("superAdmin")));

                        } catch (JSONException e) {
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });


        requestQueue.add(request);

        }



}
