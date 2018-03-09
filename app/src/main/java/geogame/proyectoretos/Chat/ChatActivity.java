package geogame.proyectoretos.Chat;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import geogame.proyectoretos.R;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.historial)
    TextView historial;

    @BindView(R.id.enviar)
    Button enviar;

    @BindView(R.id.entrada)
    EditText entrada;


    FirebaseDatabase database;
    DatabaseReference myRef;

    private ChatModel mChatModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // para que funcione el ButterKnife
        ButterKnife.bind(this);

        mChatModel = ViewModelProviders.of(this).get(ChatModel.class);

        if(historial.getText().toString().isEmpty()){

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
        myRef = database.getReference("chat"); // parecido a crear una tabla llamada chat
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
            mChatModel.getTexto().postValue(historial.getText().toString());
        }
    }

    // manejador de enventos para el botón con butterknife ("más easy")
    @OnClick(R.id.enviar)
    public void enviarMensaje() {
        // primero sacar lo que el usuario a escrito en el edittext
        String cadena = entrada.getText().toString();
        // vaciamos el edittext
        limpiarEntrada();
        // instanciamos el objeto que hemos creado con el POI Mensaje
        Chat msg = new Chat(cadena);


        // guardamos en firebase
        guardarMensaje(msg);

    }

    public void limpiarEntrada() {
        entrada.setText("");
    }

    public void guardarMensaje(Chat msg) {
        myRef.push().setValue(msg);
    }

}
