package geogame.proyectoretos.Data;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import geogame.proyectoretos.R;

public class ActivityLogin extends AppCompatActivity {






///////////////////////////////////////////////////////////////////////////////////////////////////

    public class ComprobarUsuario extends AsyncTask<Void,Void,ResultSet> {
        String consultaLg="Select * from usuarios where username='"+usuario.getText().toString().trim()+"' and pass='"+ pass.getText().toString().trim()+"'";
        Connection conexLg;
        Statement sentenciaLg;
        android.app.AlertDialog dialog;
        ResultSet resultLg;

        public ComprobarUsuario(String consulta, android.app.AlertDialog dialog){
            this.consultaLg=consulta;
            this.dialog=dialog;
        }

        @Override
        protected ResultSet doInBackground(Void... params) {

            try {
                conexLg = DriverManager.getConnection("jdbc:mysql://" + ActivityLogin.ip + "/base20171", "ubase20171", "pbase20171");
                sentenciaLg = conexLg.createStatement();
                resultLg = null;
                publishProgress();

                resultLg = sentenciaLg.executeQuery(consultaLg);

            } catch (SQLException e) { e.printStackTrace();}

            return resultLg;
        }

        @Override
        protected void onPostExecute(ResultSet resultSet) {
            super.onPostExecute(resultSet);

            try {
                while (resultSet.next()) {
                    USER=new Usuario(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),
                            resultSet.getString(4),resultSet.getString(5),resultSet.getInt(6),resultSet.getInt(7));
                    loginCorrecto=true;
                }

                conexLg.close();
                sentenciaLg.close();
                resultLg.close();


                if (USER==null)
                    Toast.makeText(getApplicationContext(), "Usuario o Contraseña incorrectos",Toast.LENGTH_SHORT).show();
                else
                    lanzarActivity();

            }catch (Exception ex) {     Log.d("Error",""+ex.getMessage());   }
            dialog.dismiss();
        }
    }//Fin AsynTack
}//Fin Acticity