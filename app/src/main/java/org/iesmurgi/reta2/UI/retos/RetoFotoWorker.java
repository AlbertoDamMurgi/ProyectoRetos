package org.iesmurgi.reta2.UI.retos;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.iesmurgi.reta2.Chat.Chat;

import java.io.File;

import androidx.work.Worker;
/**
 * Clase que lanza la tarea de subir una foto a la base de datos
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class RetoFotoWorker extends Worker {
    @NonNull
    @Override
    public WorkerResult doWork() {
        String path = getInputData().getString("path",null);
        String nombrepartida = getInputData().getString("nombrepartida",null);
        String autor = getInputData().getString("autor",null);
        String idreto = getInputData().getString("idreto",null);
        StorageReference mStorage = FirebaseStorage.getInstance().getReference();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Imagenes").child(nombrepartida).child(autor).child(idreto);

            Uri file = Uri.fromFile(new File(path));
            //  StorageReference riversRef = mStorage.child("Imagenes").child(nombrepartida).child(autor);
            StorageReference riversRef = mStorage.child("Imagenes").child(nombrepartida).child(autor).child(idreto).child(file.getLastPathSegment());
           // progressDialog.setMessage("Subiendo imagen");
           // progressDialog.show();
            riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            reference.push().setValue(new Chat(taskSnapshot.getDownloadUrl().getPath()));

                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {


                        }
                    });


        return WorkerResult.SUCCESS;
    }
}
