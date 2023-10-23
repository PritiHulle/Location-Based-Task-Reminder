package com.example.taskmanagerpro.ui;



import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.taskmanagerpro.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.Timer;
import java.util.TimerTask;



public class MapView1 extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private MapView mMapView;
    private double mLatitude = 37.7749; // Replace with your desired latitude
    private double mLongitude = -122.4194; // Replace with your desired longitude
    private String mMarkerTitle = "Bus Location"; // Replace with your desired marker title
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


//        mToolbar = findViewById(R.id.main_toolbar);
//
//        setSupportActionBar(mToolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);

//        timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                // Call your function here
//                getdata(savedInstanceState);
//
//                System.out.println("Function called every second");
//            }
//        }, 0, 1000);
//
////        Timer.periodic(Duration(seconds: 1), (timer) {
////                // Call your function here
////
////
////         });


        String  lat = getIntent().getStringExtra("lat");
        String  long1 = getIntent().getStringExtra("long");





//        LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(long1));
//        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(mMarkerTitle);
//        mMap.addMarker(markerOptions);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));


        mMapView = findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String  lat = getIntent().getStringExtra("lat");
        String  long1 = getIntent().getStringExtra("long");

        if(lat==null || long1==null || lat.equals("") || long1.equals(""))
        {

        }
        else {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//        mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);


            LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(long1));
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(mMarkerTitle);
            mMap.addMarker(markerOptions);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16)); // Zoom level is set to 10
        }



    }




    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


//
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//
//
//
//
//            case android.R.id.home:{
//
//                //  LocalBroadcastManager.getInstance(this).unregisterReceiver(onDownloadComplete);
//
//                //   startActivity(new Intent(Login.this, LoginDashboard.class));
//                //   finish();
//
//                onBackPressed();
//                return true;
//            }
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

}
