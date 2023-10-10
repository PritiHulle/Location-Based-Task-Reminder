package com.example.taskmanagerpro.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.taskmanagerpro.fragments.CompletedTaskFragment;
import com.example.taskmanagerpro.R;
import com.example.taskmanagerpro.fragments.HomeFragment;
import com.example.taskmanagerpro.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hotchemi.android.rate.AppRate;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById (R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);



        //show rate app prompt
//        AppRate.with (this)
//                .setInstallDays (1)
//                .setLaunchTimes (3)
//                .setRemindInterval (2)
//                .monitor ();
//
//        AppRate.showRateDialogIfMeetsConditions (this);




        getfirstlocation();




        //this checks to see if there's any savedInstance,if null, then it replaces the fragment container
        // with home fragment.
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,
                    new HomeFragment ()).commit();
        }

    }

    private void getfirstlocation()
    {

            FirebaseDatabase mDatabase;
            mDatabase = FirebaseDatabase.getInstance();

            DatabaseReference reff, reff1, reff2, reff3, reff4;
            // reff3 = mDatabase.getReference("Admin").child("vender_admin").child("history_order");
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance ();
            FirebaseUser user = firebaseAuth.getCurrentUser ();
            String uid=user.getUid();
            reff3 = mDatabase.getReference("customer_page").child("Customer_location").child(uid);
           List<LocationList> LocationList;
           LocationList = new ArrayList<>();


        String MyPREFERENCES = "UserData";
        final SharedPreferences[] sharedpreferences = new SharedPreferences[1];
        String lat = "lat";
        String long1 = "long1";
        String desc = "desc";



            reff3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    boolean check=true;
                    if (!dataSnapshot.exists()) {
                    //    Toast.makeText (LocationListMain.this,"No Data",Toast.LENGTH_SHORT).show ();
                    } else {

                        for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {




                            String value1 = npsnapshot.child("uid").getValue(String.class);
                            String value2 = npsnapshot.child("date").getValue(String.class);
                            String value3 = npsnapshot.child("time").getValue(String.class);
                            String value4 = npsnapshot.child("title").getValue(String.class);

                            String value5 = npsnapshot.child("lat").getValue(String.class);
                            String value6 = npsnapshot.child("long1").getValue(String.class);
                            String value7 = npsnapshot.child("address").getValue(String.class);
                            String value8 = npsnapshot.child("description").getValue(String.class);

                            String value9 = npsnapshot.child("push").getValue(String.class);
                            String value10 = npsnapshot.child("status").getValue(String.class);

                            if (value1 == null || value2 == null || value3 == null || value4 == null || value6 == null || value7 == null
                                    || value8 == null || value9 == null|| value10 == null) {

                            } else {

                                if(value10.equals("1"))
                                {


                                    LocationList l = new LocationList(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10);
                                    LocationList.add(l);

                                }




                            }
                        }

                        if(!LocationList.isEmpty())
                        {
                            sharedpreferences[0] = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            final SharedPreferences.Editor editor = sharedpreferences[0].edit();
                            editor.putString(lat, LocationList.get(0).getLat());
                            editor.putString(long1,  LocationList.get(0).getLong1());
                            editor.putString(desc,  LocationList.get(0).getDescription());
                            editor.commit();
                            Log.i("MyActivity1","501");
                            Log.i("MyActivity1",LocationList.get(0).getLat());
                            Log.i("MyActivity1",LocationList.get(0).getLong1());

                            get_call_location();




                        }








                    }


                }
                @Override public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }


            });








    }

    private  void get_call_location()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("MyActivity1","Permission accept1");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);



        } else {
            // Start the LocationTracker service
            Log.i("MyActivity1","Permission accept2");
            Intent serviceIntent = new Intent(this, LocationTracker.class);
            startService(serviceIntent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Start the LocationTracker service
         Log.i("MyActivity1","Permission accept");
//            Intent serviceIntent = new Intent(this, LocationTracker.class);
//            startService(serviceIntent);
        } else {
            Log.i("MyActivity1","Permission Denied");
            // Permission denied, handle the situation accordingly
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            menuItem -> {
                Fragment selectedfragment=null;
                switch (menuItem.getItemId())
                {
                    case R.id.home:
                        selectedfragment=new HomeFragment();
                        break;

                    case R.id.completed:
                        selectedfragment=new CompletedTaskFragment ();
                        break;
                    case R.id.profile:
                        selectedfragment=new ProfileFragment ();
                        break;
                }
                if (selectedfragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,
                            selectedfragment).commit();
                }

                return true;
            };
    @Override
    public void onBackPressed() {
        FragmentManager fm=getFragmentManager ();
        if (fm.getBackStackEntryCount ()>0) {
            fm.popBackStack ();
        }
           else {
               super.onBackPressed ();
           }
        }
        // handles on hard ware backpressed..



}


