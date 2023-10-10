package com.example.taskmanagerpro.ui;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;

import com.example.taskmanagerpro.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Map1 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker mMarker;
    // creating a variable
    // for search view.
    SearchView searchView;
    private TextView t1;
    private Button SaveTask;
    private String currentLatitude;

    private String currentLongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        // initializing our search view.
        searchView = findViewById(R.id.idSearchView);
        t1 = findViewById(R.id.t1);
        SaveTask = findViewById(R.id.SaveTask);

        SaveTask.setOnClickListener (v -> {


            String  title = getIntent().getStringExtra("title");
            String  description = getIntent().getStringExtra("description");

            if(currentLatitude==null || currentLongitude==null)
            {
                Toast.makeText (getApplicationContext (),"Location Not Found",Toast.LENGTH_SHORT).show ();

            }
            else {
                DatabaseReference reff1,reff2;
                FirebaseDatabase mDatabase;
                mDatabase = FirebaseDatabase.getInstance();

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance ();
                FirebaseUser user = firebaseAuth.getCurrentUser ();
                String uid=user.getUid();

                reff2 = mDatabase.getReference("customer_page").child("Customer_location").child(uid);

                String pattern = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date1 = simpleDateFormat.format(new Date());

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat month1 = new SimpleDateFormat("h:mm a");
                String time1 = month1.format(cal.getTime());




                String  extra_id = getIntent().getStringExtra("extra_id");
                String   push_key = reff2.push().getKey();
                if(extra_id==null || extra_id=="")
                {
                    push_key = reff2.push().getKey();
                }
                else {
                    push_key = extra_id;
                }



                String e222=push_key;
                reff2.child(e222).child("uid").setValue(uid);
                reff2.child(e222).child("date").setValue(date1);
                reff2.child(e222).child("time").setValue(time1);
                reff2.child(e222).child("title").setValue(title);
                reff2.child(e222).child("description").setValue(description);
                reff2.child(e222).child("lat").setValue(currentLatitude);
                reff2.child(e222).child("push").setValue(e222);

                reff2.child(e222).child("long1").setValue(currentLongitude);
                reff2.child(e222).child("address").setValue(t1.getText().toString());
                reff2.child(e222).child("status").setValue("1");



                Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_SHORT).show();


                Intent i4 = new Intent(Map1.this, Home1.class);
                startActivity(i4);
                finish();
            }


        });

        // Obtain the SupportMapFragment and get notified
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // adding on query listener for our search view.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // on below line we are getting the
                // location name from search view.
                String location = searchView.getQuery().toString();

                // location =mumbai

                // below line is to create a list of address
                // where we will store the list of all address.
                List<Address> addressList = null;

                // checking if the entered location is null or not.
                if (location != null || location.equals("")) {
                    // on below line we are creating and initializing a geo coder.
                    Geocoder geocoder = new Geocoder(Map1.this);
                    try {
                        // on below line we are getting location from the
                        // location name and adding that location to address list.
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // on below line we are getting the location
                    // from our list a first position.
                    Address address = addressList.get(0);

                    // on below line we are creating a variable for our location
                    // where we will add our locations latitude and longitude.
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    if(mMarker!=null)
                    {
                        mMarker.remove();
                    }

                    // on below line we are adding marker to that position.
                    mMarker=  mMap.addMarker(new MarkerOptions().position(latLng).title(location));

                    // below line is to animate camera to that position.
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                    //Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    String addressText = "";
                    currentLatitude=address.getLatitude()+"";
                    currentLongitude=address.getLongitude()+"";


                    addressText = address.getAddressLine(0); // Get the full address
                    t1.setText(addressText);

                }
//                else {
//                    Toast.makeText (Map1.this,"Please Enter Some Text",Toast.LENGTH_SHORT).show ();
//                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        // at last we calling our map fragment to update.
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
