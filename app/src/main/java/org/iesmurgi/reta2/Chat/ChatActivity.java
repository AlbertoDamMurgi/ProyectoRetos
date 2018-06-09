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

/**
 * Clase que usamos para leer y escribir en el chat.
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 * @see ChatAdapter
 * @see ChatAdminActivity
 */

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


        leerFirebase();




    }


    /**
     * Método para conectar con la base de datos Firebase
     */
    public void conectarFirebase() {

        database = FirebaseDatabase.getInstance();
        if(autor.equalsIgnoreCase("administrador")){
            admin = true;

            autor = getIntent().getExtras().getString("USUARIO");
        }else{
            admin = false;
        }
        myRef = database.getReference("chat").child(sala).child(autor);
    }


    /**
     * Método que refresca el chat cuando un cambio ocurre en la base de datos.
     */
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

    /**
     * Método que escribe un mensaje en el historial.
     * @param msg mensaje que se va a insertar en el historial
     */
    public void insertarMensaje(Chat msg) {

        if (msg.getMensaje() != null) {
            historial.append("\n");
            historial.append(msg.getMensaje());
            mChatModel.getTexto().postValue(historial.getText().toString().trim());
            scrollchat.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }


    /**
     * Método que envia el mensaje a la base de datos y borra la entrada de texto
     */
    @OnClick(R.id.enviar)
    public void enviarMensaje() {

        String cadena = entrada.getText().toString().trim();

        limpiarEntrada();

        if(admin){
            Chat msg = new Chat("Administrador: "+cadena);
            guardarMensaje(msg);

        }else {
            Chat msg = new Chat(autor + ": " + cadena);
            guardarMensaje(msg);

        }





    }

    /**
     * Método que borra la entrada de texto.
     */
    public void limpiarEntrada() {
        entrada.setText("");
    }

    /**
     * Método que envia el mensaje a la base de datos
     * @param msg mensaje que se envia a la base de datos.
     */
    public void guardarMensaje(Chat msg) {
        myRef.push().setValue(msg);
    }

}
