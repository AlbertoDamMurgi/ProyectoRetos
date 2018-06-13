/*

Reta2  Copyright (C) 2018  Alberto Fernández Fernández, Santiago Álvarez Fernández, Joaquín Pérez Rodríguez

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program. If not, see http://www.gnu.org/licenses/.


Contact us:

fernandez.fernandez.angel@gmail.com
santiago.alvarez.dam@gmail.com
perezrodriguezjoaquin0@gmail.com

*/

/*package org.iesmurgi.proyectoretos.papelera;

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

import org.iesmurgi.proyectoretos.Data.entidades.Admin;

/**
 * Created by Santi on 09/03/2018.
 */
/*
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

import org.iesmurgi.proyectoretos.Data.entidades.Admin;

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


    private class InternetGetAdmin extends AsyncTask<Void,Void,List<Admin>> {

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
*/