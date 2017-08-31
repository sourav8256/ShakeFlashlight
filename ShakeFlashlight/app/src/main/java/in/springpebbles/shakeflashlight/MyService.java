package in.springpebbles.shakeflashlight;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import android.widget.Toast;
import android.os.Handler;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by TutorialsPoint7 on 8/23/2016.
 */

public class MyService extends Service implements SensorEventListener{

    private InterstitialAd mInterstitialAd;

    private SensorManager sensorManager;
    private long lastUpdate;




    int time2=0;


    String cameraOwner="unknown";
    Camera camera;
    Camera.Parameters parameters;
    int checked=0;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6249242764892548/2532200518");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        MobileAds.initialize(this, "ca-app-pub-6249242764892548~1055467314");



        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);



        shownotification("Shake To Turn ON/OFF");


        Toast.makeText(this, "Quick Flash Started", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);

        Toast.makeText(this, "Quick Flash Stopped", Toast.LENGTH_SHORT).show();
    }


public void shownotification(String flashStatus) {
    Intent notificationIntent = new Intent(this, MyService.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

    Notification notification = new Notification.Builder(this)
            .setContentTitle(getText(R.string.notification_title))
            .setContentText(flashStatus)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setTicker(getText(R.string.ticker_text))
            .build();

    startForeground(1, notification);
}

    @Override
    public void onCreate() {
        super.onCreate();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis()-2000;

    }



/*
    @Override
    public void onSensorChanged(SensorEvent event) {



        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            times++;

            getAccelerometer(event);
        }

    }

    private void getAccelerometer(SensorEvent event) {

        {

            float[] values = event.values;
            // Movement
            float x = values[0];
            float y = values[1];
            float z = values[2];

            float accelationSquareRoot = (x * x + y * y + z * z)
                    / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
            long actualTime = System.currentTimeMillis();
            if (accelationSquareRoot >= 3) //
            {


                if ((actualTime - lastUpdate) >= 200) {




                    time2++;
                    long timediff = (actualTime - lastUpdate);
                    lastUpdate = actualTime;
                    Toast.makeText(this, "Device was shuffled " + times + "   " + time2 + " time dif " + timediff + " on sensor change", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT).show();

                    try {


                        if (checked == 1) {

                        }


                        if (checked == 0) {

                            //ToDo something
                            camera = Camera.open();
                            Camera.Parameters parameters = camera.getParameters();
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            camera.setParameters(parameters);
                            camera.startPreview();
                            camera.release();
                            checked = 1;

                        } else if (checked == 1) {

                            //ToDo something
                            camera = Camera.open();
                            Camera.Parameters parameters = camera.getParameters();
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                            camera.setParameters(parameters);
                            camera.stopPreview();
                            camera.release();
                            checked = 0;

                        }


                    } catch (Exception e) {
                        Log.d("error", e.getMessage());
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }



                }
            }


        }

        return;

        }
*/


/* Launch activity

    Intent intent = new Intent("android.intent.category.LAUNCHER");
    intent.setClassName("com.your.package", "com.your.package.MainActivity");
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);

 */


    public boolean checkForCamera(){
        Camera camera = null;
        try {
            camera = Camera.open(); // this line will throw exception if camera is not in use.
        }
        catch (Exception e){
            // if exception is thrown, return your boolean value here...
            return true;
        }
        finally {
            if(camera!=null)camera.release();
        }
        return false; // if instance of camera, if it is not available it will return null.
    }



    public void showtoast(String S){
        Toast.makeText(this, S, Toast.LENGTH_SHORT).show();
    }





    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            {

                float[] values = event.values;
                // Movement
                float x = values[0];
                float y = values[1];
                float z = values[2];

                float accelationSquareRoot = (x * x + y * y + z * z)
                        / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
                long actualTime = System.currentTimeMillis();
                if (accelationSquareRoot >= 2) //
                {


                    if ((actualTime - lastUpdate) >= 1400) {


                        time2++;
                        long timediff = (actualTime - lastUpdate);
                        lastUpdate = actualTime;
                        //Toast.makeText(this, "Device was shuffled " + times + "   " + time2 + " time dif " + timediff + " on sensor change", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT)show();

                        try {


                            if (checkForCamera() == false) {
                                camera = Camera.open();
                                parameters = camera.getParameters();
                                cameraOwner = "QuickFlash";
                            }


                            if (cameraOwner == "QuickFlash"){


                                if (checked == 0) {

                                    //ToDo something

                                    parameters = camera.getParameters();
                                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                                    camera.setParameters(parameters);
                                    camera.startPreview();
                                    checked = 1;


                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (checked == 1) {
                                                parameters = camera.getParameters();
                                                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                                                camera.setParameters(parameters);
                                                camera.stopPreview();
                                                camera.release();
                                                checked = 0;
                                                shownotification("Automatic switch OFF after 5 min");
                                                showtoast("Flash OFF");
                                            }
                                        }
                                    }, 300000);


                                    shownotification("Flash ON");
                                    showtoast("Flash ON");


                                    return;

                                } else if (checked == 1) {

                                    //ToDo something

                                    parameters = camera.getParameters();
                                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                                    camera.setParameters(parameters);
                                    camera.stopPreview();
                                    camera.release();
                                    checked = 0;
                                    cameraOwner="unknown";
                                    shownotification("Flash OFF");
                                    showtoast("Flash OFF");


                                    if (mInterstitialAd.isLoaded()) {
                                        mInterstitialAd.show();
                                    /*
                                    Intent intent = new Intent("android.intent.category.LAUNCHER");
                                    intent.setClassName("in.springpebbles.shakeflashlight", "in.springpebbles.shakeflashlight.MainActivity");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    */

                                    } else {
                                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                                    }


                                    return;

                                }


                        } else {
                                Toast.makeText(getApplicationContext(),"Some Other App Like CAMERA Is Already Using Flash You Need To Close That App First! Sorry For The Inconvenience",Toast.LENGTH_LONG).show();
                            }


                        } catch (Exception e) {
                            Log.d("error", e.getMessage());
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                }


            }

            return;

        }
    }













    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }






}