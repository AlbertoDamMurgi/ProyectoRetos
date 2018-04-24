package org.iesmurgi.reta2.Chat;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.UI.usuario.LoginModel;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.historial)
    TextView historial;

    @BindView(R.id.enviar)
    ImageButton enviar;

    @BindView(R.id.entrada)
    EditText entrada;

    @BindView(R.id.scrollchat)
    ScrollView scrollchat;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private ChatModel mChatModel;

    private LoginModel logmodel;
    private FirebaseAuth mAuth;
    private String autor;
    private boolean admin = true;
    private String sala;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // para que funcione el ButterKnife
        ButterKnife.bind(this);

        sala = getIntent().getExtras().getString("SALA");

        mChatModel = ViewModelProviders.of(this).get(ChatModel.class);
        logmodel = ViewModelProviders.of(this).get(LoginModel.class);

        mAuth = FirebaseAuth.getInstance();

        autor = mAuth.getCurrentUser().getDisplayName();



        if(historial.getText().toString().trim().isEmpty()){

            historial.setText(mChatModel.getTexto().getValue());

        }

        conectarFirebase();

        // implementando para leer de la firebase
        leerFirebase();




    }



    public void conectarFirebase() {
        // copiado del asistente de firebase
        // Write a message to the database
        database = FirebaseDatabase.getInstance(); // instancia la base de datos
        if(autor.equalsIgnoreCase("administrador")){
            admin = true;

            autor = getIntent().getExtras().getString("USUARIO");
        }else{
            admin = false;
        }
        myRef = database.getReference("chat").child(sala).child(autor); // parecido a crear una tabla llamada chat
    }


    public void leerFirebase() {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // comprobamos que dataSnapshot sea distinto de null es obligatorio esta comprobacion
                if (dataSnapshot != null ) {
                    try {
                        Chat msg = dataSnapshot.getValue(Chat.class);
                        insertarMensaje(msg);
                        scrollchat.fullScroll(ScrollView.FOCUS_DOWN);
                    } catch (Exception ex) {
                        Log.e("Error", ex.getMessage());
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void insertarMensaje(Chat msg) {// método que añade al textView historial el recargo de pagina y un mensaje.
        // comprobamos que exista mensaje
        if (msg.getMensaje() != null) {
            historial.append("\n");
            historial.append(msg.getMensaje());
            mChatModel.getTexto().postValue(historial.getText().toString().trim());
            scrollchat.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }

    // manejador de enventos para el botón con butterknife ("más easy")
    @OnClick(R.id.enviar)
    public void enviarMensaje() {
        // primero sacar lo que el usuario a escrito en el edittext
        String cadena = entrada.getText().toString().trim();
        // vaciamos el edittext
        limpiarEntrada();
        // instanciamos el objeto que hemos creado con el POI Mensaje
        if(admin){
            Chat msg = new Chat("Administrador: "+cadena);
            guardarMensaje(msg);

        }else {
            Chat msg = new Chat(autor + ": " + cadena);
            guardarMensaje(msg);

        }

        // guardamos en firebase




    }

    public void limpiarEntrada() {
        entrada.setText("");
    }

    public void guardarMensaje(Chat msg) {
        myRef.push().setValue(msg);
    }

}
