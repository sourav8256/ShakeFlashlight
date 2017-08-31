package in.springpebbles.shakeflashlight;


//material design colors lighter=#5A5A5C darker=#303030

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6249242764892548/2532200518");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });


        Button buttonstop = (Button)findViewById(R.id.button);
        buttonstop.setEnabled(false);



    }



    public boolean checkForCamera(){
        Camera camera = null;
        try {
            camera = Camera.open(); // this line will throw exception if camera is not in use.
        }
        catch (Exception e){

            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

            // if exception is thrown, return your boolean value here...
            return true;
        }
        finally {
            if(camera!=null)camera.release();
        }
        return false; // if instance of camera, if it is not available it will return null.
    }



    public void startService(View view) {
        if(checkForCamera()==false) {
            startService(new Intent(getBaseContext(), MyService.class));
            Button buttonstart = (Button) findViewById(R.id.button2);
            Button buttonstop = (Button) findViewById(R.id.button);
            buttonstart.setEnabled(false);
            buttonstop.setEnabled(true);
        } else {
            Toast.makeText(getApplicationContext(),"Some Other App Like CAMERA Is Already Using Flash You Need To Close That App First! Sorry For The Inconvenience",Toast.LENGTH_LONG).show();
        }

        //TextView textView = (TextView)findViewById(R.id.textView2);
        //textView.setText("Quick Flash Active \n Shake The Device To Turn Flash On/Off");

    }




    // Method to stop the service
    public void stopService(View view) {
        if(checkForCamera()==false) {
            stopService(new Intent(getBaseContext(), MyService.class));
            Button buttonstart = (Button) findViewById(R.id.button2);
            Button buttonstop = (Button) findViewById(R.id.button);
            buttonstart.setEnabled(true);
            buttonstop.setEnabled(false);
        }else {
            Toast.makeText(getApplicationContext(),"You Can't Stop Quick Flash Before Turning OFF The Flash! Sorry For The Inconvenience",Toast.LENGTH_LONG).show();
        }
        //TextView textView = (TextView)findViewById(R.id.textView2);
        //textView.setText("Quick Flash InActive");
    }



}