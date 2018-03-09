package geogame.proyectoretos.papelera;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import geogame.proyectoretos.Data.entidades.Admin;

/**
 * Created by Santi on 09/03/2018.
 */

public class Internet {
         static String URL;
        RequestQueue requestQueue;

        Context context;
        private List<Admin> listaAdmins;
    public Internet(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);

    }


    private static Internet INSTANCE;



    public static Internet getInternet(final Context context) {
        if (INSTANCE == null) {
            synchronized (Internet.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Internet(context);
                }
            }

        }
        return INSTANCE;
    }


    public List<Admin> listarAdmins(){

        new InternetGetAdmin().execute();

        return listaAdmins;

    }


    private class InternetGetAdmin extends AsyncTask<Void,Void,List<Admin>>{

        List<Admin> mAdmins = new ArrayList<>();

        @Override
        protected List<Admin> doInBackground(Void... voids) {

            URL = "http://geogame.ml/api/lista_admins.php";

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i=0;i<response.length();i++){

                        try {
                            JSONObject o =response.getJSONObject(i);
                            Log.d("DENTRO","RESPONDI");


                            mAdmins.add(new Admin(o.getInt("idAdmin"),o.getString("username"),o.getString("passwd"),o.getInt("superAdmin")));

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


           return mAdmins;
        }


        @Override
        protected void onPostExecute(List<Admin> admins) {
            super.onPostExecute(admins);

            listaAdmins = admins;

        }

        }



}
