package ncku.geo.MileageBattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*try {
            {
                mp = new MediaPlayer();
            }
        }*/


    }

    public void onclick(View V){
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);
        if(V.getId() == R.id.MUSIC){
            it.setType("audio/*");
            startActivityForResult(it, 123);
        }
        else if (V.getId() == R.id.VIDEO){
            it.setType("video/*");
            startActivityForResult(it, 456);
        }

    }
    Uri uri;
    Uri song = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.Kitten);
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_OK){

            if (requestCode == 123)uri = data.getData();
            else if (requestCode == 123);
        }

    }

}