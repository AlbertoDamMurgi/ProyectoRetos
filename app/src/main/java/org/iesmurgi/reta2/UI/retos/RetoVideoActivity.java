package org.iesmurgi.reta2.UI.retos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.iesmurgi.reta2.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Clase que permite al usuario ver un video de youtube en el reto
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class RetoVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener , YouTubePlayer.PlaybackEventListener{

    private String claveAPI = "AIzaSyBWtLJ5YAfriw0Iw_Rx4Zz00-_ersN0KMQ";
    @BindView(R.id.YTplayer)
    YouTubePlayerView ytPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto_video);
        ButterKnife.bind(this);
        ytPlayer.initialize(claveAPI, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restaurado) {
        if(!restaurado){
            //si no fue restaurado inicia el video

            //capturamos el enlace desde el intent y extraemos su id

            String enlaceVideo = getIntent().getExtras().getString("enlaceVideo");
            Log.v("enlacevideo", enlaceVideo);
            String idVideo = extractYTId(enlaceVideo);
            Log.v("idvideo", ""+idVideo);

            youTubePlayer.cueVideo(idVideo);  //https://www.youtube.com/watch?v=azxDhcKYku4&t=195s , necesita la id del video osea lo que va entre = =

        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if (youTubeInitializationResult.isUserRecoverableError()){
            //si hubo error al inicializar nos dira cual fue segun YouTube
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        }else {
            //si youtube no reconoce error nos muestra en toast
            Toast.makeText(getApplicationContext(), youTubeInitializationResult.toString(), Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (resultCode==1){

            getYouTubePlayerProvider().initialize(claveAPI, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider(){

        return ytPlayer;
    }


    //eventos del YTPlayer
    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }

    /**
     * Método que permite sacar la id del video desde la url
     * @param ytUrl url de youtube
     * @return id del video
     */
    public static String extractYTId(String ytUrl) {
        if (TextUtils.isEmpty(ytUrl)) {
            return "";
        }
        String video_id = "";

        String expression = "^.*((youtu.be" + "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*"; // var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
        CharSequence input = ytUrl;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String groupIndex1 = matcher.group(7);
            if (groupIndex1 != null && groupIndex1.length() == 11)
                video_id = groupIndex1;
        }
        if (TextUtils.isEmpty(video_id)) {
            if (ytUrl.contains("youtu.be/")  ) {
                String spl = ytUrl.split("youtu.be/")[1];
                if (spl.contains("\\?")) {
                    video_id = spl.split("\\?")[0];
                }else {
                    video_id =spl;
                }

            }
        }

        return video_id;
    }
}
