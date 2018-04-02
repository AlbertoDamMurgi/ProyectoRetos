package org.iesmurgi.reta2.UI.retos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.iesmurgi.reta2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restaurado) {
        if(!restaurado){
            //si no fue restaurado inicia el video
            youTubePlayer.cueVideo("azxDhcKYku4");  //https://www.youtube.com/watch?v=azxDhcKYku4&t=195s , necesita la id del video osea lo que va entre = =

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
}
