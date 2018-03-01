package geogame.proyectoretos.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import geogame.proyectoretos.R;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.et_email)
    EditText email;
    @BindView(R.id.et_pass)
    EditText pass;
    @BindViews({R.id.et_email,R.id.et_pass})
    List<EditText> asd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);



    }


    @OnClick(R.id.btn_registrarse)
    void registrarAdmin() {

        Toast.makeText(getApplicationContext(), "asdasd", Toast.LENGTH_SHORT).show();


    }


}


