package tw.com.csie.chiu.ch11_player;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity {

    Uri uri;
    TextView txvName,txvUri;
    boolean isVideo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txvName = (TextView)findViewById(R.id.txvName);
        txvUri = (TextView)findViewById(R.id.txvUri);

        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.fly);

        txvName.setText("fly.mp3");
        txvUri.setText("程式內的歌曲" + uri.toString());
    }


    public void onPick(View v){
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);

        if (v.getId() == R.id.btnPickAudio){
            it.setType("audio/*");
            startActivityForResult(it,100);
        }
        else{
            it.setType("video/*");
            startActivityForResult(it,101);
        }

    }

    protected void onActivityResult(int resquestCode,int resultCode,Intent data){
        super.onActivityResult(resquestCode,resultCode,data);

        if (resultCode == Activity.RESULT_OK){
            isVideo = (resquestCode == 101);

            uri = convertUri(data.getData());

            txvName.setText(uri.getLastPathSegment());

            txvUri.setText("檔案位置 : " + uri.getPath());
        }
    }

    Uri convertUri(Uri uri){

        if(uri.toString().substring(0,7).equals("content")){

            String[] colName = {MediaStore.MediaColumns.DATA};

            Cursor cursor = getContentResolver().query(uri,colName,null,null,null);

            cursor.moveToFirst();
            uri = Uri.parse("file://" + cursor.getString(0));
            cursor.close();
        }
        return uri;
    }


}