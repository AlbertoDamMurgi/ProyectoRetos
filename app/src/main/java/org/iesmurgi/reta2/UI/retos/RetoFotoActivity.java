package org.iesmurgi.reta2.UI.retos;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.iesmurgi.reta2.Chat.Chat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.UI.usuario.LoginModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Actividad que permite al usuario realizar el reto de subir una foto
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class RetoFotoActivity extends AppCompatActivity {

    private final String CARPETA_RAIZ="Reta2/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"Imagenes_Reta2";

    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    @BindView(R.id.btn_retoFoto_dialog)
    Button botonCargar;
    @BindView(R.id.img_retoFoto_foto)
    ImageView iw_imagen;

    ProgressDialog progressDialog;
    private StorageReference mStorage;
    private FirebaseAuth mAuth;
    private String nombrepartida;
    private String autor;
    String path;
    File imagen;
    private LoginModel model;
    private String idreto;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto_foto);
        ButterKnife.bind(this);
        model = ViewModelProviders.of(this).get(LoginModel.class);
        nombrepartida = getIntent().getExtras().getString("PARTIDA");
        idreto = getIntent().getExtras().getString("RETO");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        progressDialog = new ProgressDialog(RetoFotoActivity.this);
        mAuth = FirebaseAuth.getInstance();

        mStorage = FirebaseStorage.getInstance().getReference();
        autor = mAuth.getCurrentUser().getDisplayName();
        reference = FirebaseDatabase.getInstance().getReference().child("Imagenes").child(nombrepartida).child(autor).child(idreto);

        if(validaPermisos()){
            botonCargar.setEnabled(true);
        }else{
            botonCargar.setEnabled(false);
        }

    }

    /**
     * Método que sube la foto a la base de datos
     */
    @OnClick(R.id.btn_retoFoto_subirfoto)
    void subirFotoFirebase(){

        if(path!=null) {
            Constraints myConstraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
                    // Many other constraints are available, see the
                    // Constraints.Builder reference
                    .build();
            Data.Builder builder = new Data.Builder();
            builder.putString("path", path);
            builder.putString("nombrepartida", nombrepartida);
            builder.putString("autor", autor);
            builder.putString("idreto", idreto);

            OneTimeWorkRequest subirpic = new OneTimeWorkRequest.Builder(RetoFotoWorker.class).setInputData(builder.build()).setConstraints(myConstraints).build();
            model.mWorkManager.enqueue(subirpic);

            setResult(MapPrincActivity.RESULT_OK);
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"Ninguna foto para enviar!!!!",Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Método que solicita al usuario los permisos de la camara
     * @return true o false
     */
    private boolean validaPermisos() {

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            //cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                botonCargar.setEnabled(true);
            }else{
                solicitarPermisosManual();
            }
        }

    }
    /**
     * Método que solicita al usuario los permisos de la camara
     */
    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(RetoFotoActivity.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    /*
        private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(MainActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCompat.requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }
     */


    public void onclick(View view) {
        cargarImagen();
    }

    /**
     * Método que carga la imagen en el imageview
     */
    private void cargarImagen() {

        String foto = getResources().getString(R.string.take_photo);
        String cancel = getResources().getString(R.string.cancel_photo);
        String option = getResources().getString(R.string.choose_option);

        final CharSequence[] opciones={""+foto,""+cancel};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(RetoFotoActivity.this);
        alertOpciones.setTitle(""+option);
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals(""+foto)){
                    tomarFotografia();
                }else{
                    if (opciones[i].equals(""+cancel)){
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();

    }

    /**
     * Método que crea un archivo con la foto realizada
     */
    private void tomarFotografia() {
        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada=fileImagen.exists();
        String nombreImagen="";
        if(isCreada==false){
            isCreada=fileImagen.mkdirs();
        }

        if(isCreada==true){
            nombreImagen=(System.currentTimeMillis()/1000)+".jpg";
        }


        path=Environment.getExternalStorageDirectory()+
                File.separator+RUTA_IMAGEN+File.separator+nombreImagen;

        imagen=new File(path);
        Log.e("path de tomarfoto",path);

        Intent intent=null;
        intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            String authorities=getApplicationContext().getPackageName()+".provider";
            Uri imageUri=FileProvider.getUriForFile(this,authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){

            switch (requestCode){
                case COD_SELECCIONA:
                    Uri miPath=data.getData();

                    path = miPath.getPath();

                    new Intent().putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(miPath.getPath())));


                    Log.e("path", ""+miPath.getPath());
                    iw_imagen.setImageURI(miPath);
                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento","Path: "+path);
                                }
                            });

                    Bitmap bitmap= BitmapFactory.decodeFile(path);
                    iw_imagen.setImageBitmap(bitmap);
                    Log.v("size antes comprimir" ," "+imagen.length());
                    comprimirFoto();
                    Log.v("size despues comprim" ," "+imagen.length());

                    break;
            }
        }
    }

    /**
     * Método que comprime la foto para que al subirla cueste menos datos y recursos
     */
    public void comprimirFoto(){
        FileOutputStream outputStream;
        int m_inSampleSize = 0;
        int m_compress = 20;

        BitmapFactory.Options bmfOptions = new BitmapFactory.Options();
        bmfOptions.inPurgeable = true;
        bmfOptions.inSampleSize = m_inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmfOptions);
        try {
            outputStream = new FileOutputStream(imagen);
            bitmap.compress(Bitmap.CompressFormat.JPEG, m_compress, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}