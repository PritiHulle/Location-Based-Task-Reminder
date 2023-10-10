package com.example.taskmanagerpro.ui;


import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.taskmanagerpro.R;

import java.util.Locale;

public class LocationTracker extends Service implements LocationListener, TextToSpeech.OnInitListener{
    private static final String TAG = "LocationTracker";
    private LocationManager locationManager;
    private MediaPlayer alarmSound;
    private TextToSpeech textToSpeech;
    // Define your target location's latitude and longitude
    private double targetLatitude = 37.7749; // Example latitude
    private double targetLongitude = -122.4194; // Example longitude

    private CountDownTimer countDownTimer;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MyActivity1","601");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        alarmSound = MediaPlayer.create(this, R.raw.alarm_sound);
        textToSpeech = new TextToSpeech(this, this);
       // onLocationChanged(locationManager);
    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Set the language of the TextToSpeech engine
            int result = textToSpeech.setLanguage(Locale.US);


            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language not supported, handle the situation accordingly
            }
        } else {
            // TextToSpeech initialization failed, handle the situation accordingly
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop location updates and release resources
        locationManager.removeUpdates(this);
        alarmSound.release();

        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i("MyActivity1","402");


        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        Log.i("MyActivity1",currentLatitude+"");
        Log.i("MyActivity1",currentLongitude+"");
        // Calculate the distance between the current location and the target location
        float[] distance = new float[1];
        SharedPreferences p_type = getSharedPreferences("UserData", MODE_PRIVATE);
        String s1 = (p_type.getString("lat", ""));
        String s2 = (p_type.getString("long1", ""));
        String s3 = (p_type.getString("desc", ""));
        Log.i("MyActivity1","403");
        Log.i("MyActivity1",s1+"");
        Log.i("MyActivity1",s2+"");
        Log.i("MyActivity1",s3+"");

        targetLatitude=Double.parseDouble(s1);
        targetLongitude=Double.parseDouble(s2);



        Location.distanceBetween(currentLatitude, currentLongitude, targetLatitude, targetLongitude, distance);
        float distanceInMeters = distance[0];

        // Adjust the threshold as needed (in meters)
        float threshold = 10000;

        if (distanceInMeters <= threshold) {

            // You have reached the target location, play the alarm sound
            //alarmSound.start();
            speakText(s3);
//            countDownTimer = new CountDownTimer(12000, 12000) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//                    // This code will run every second during the delay
//                }
//
//                @Override
//                public void onFinish() {
//                    Log.i("MyActivity2",s3+"");
//                    // This code will run after the delay has elapsed
//
//                }
//            };
//
//            countDownTimer.start();

        }
    }
    private void speakText(String text) {
        // Speak the provided text
        textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, null);
        if(!textToSpeech.isSpeaking()) {
            textToSpeech = new TextToSpeech(this, this);
            System.out.println("tts restarted");
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
