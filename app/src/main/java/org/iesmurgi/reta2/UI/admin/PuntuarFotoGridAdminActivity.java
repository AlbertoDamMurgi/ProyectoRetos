package org.iesmurgi.reta2.UI.admin;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.iesmurgi.reta2.R;

public class PuntuarFotoGridAdminActivity extends AppCompatActivity {

    private String nombrereto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuar_foto_grid_admin);

        String reto = getIntent().getExtras().getString("RETO");





    }
}
