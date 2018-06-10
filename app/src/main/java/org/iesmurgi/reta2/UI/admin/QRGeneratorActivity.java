package org.iesmurgi.reta2.UI.admin;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.iesmurgi.reta2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Actividad que crea un codigo QR con los datos de la partida
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class QRGeneratorActivity extends AppCompatActivity {

    @BindView(R.id.iw_qrGenerator_imagenQR)
    ImageView imagenQR;
    @BindView(R.id.txt_qrgenerator_codigoAux)
    TextView txt_codigoAux;


    private String cadenaParaQR="mensaje prueba";
    private AdminModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerator);
        ButterKnife.bind(this);
        model = ViewModelProviders.of(this).get(AdminModel.class);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        if(model.getCodigoqr()==null) {
            cadenaParaQR = getIntent().getStringExtra("codigoqr");
            model.setCodigoqr(cadenaParaQR);
        }else{
            cadenaParaQR=model.getCodigoqr();
        }
        generarCodigoQR();
        txt_codigoAux.setText(""+cadenaParaQR);

    }

    /**
     * Método que genera el codigo QR y lo añade a un imageview
     */
    void generarCodigoQR(){


        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {

            BitMatrix bitMatrix = multiFormatWriter.encode(cadenaParaQR, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imagenQR.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
