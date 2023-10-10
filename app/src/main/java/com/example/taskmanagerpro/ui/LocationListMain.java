package com.example.taskmanagerpro.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;
import android.widget.ProgressBar;


import com.example.taskmanagerpro.R;
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







public class LocationListMain extends AppCompatActivity {

    private RecyclerView rv;

    private List<LocationList> LocationList;

    private LocationListRv adapter;
    private EditText searchView;
    private ProgressBar l2;
    private CardView l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_main);




        LocationList = new ArrayList<>();


        rv = (RecyclerView) findViewById(R.id.rv);


        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));




        getdata();


    }


    private void getdata()
    {
        FirebaseDatabase mDatabase;
        mDatabase = FirebaseDatabase.getInstance();

        DatabaseReference reff, reff1, reff2, reff3, reff4;
       // reff3 = mDatabase.getReference("Admin").child("vender_admin").child("history_order");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance ();
        FirebaseUser user = firebaseAuth.getCurrentUser ();
        String uid=user.getUid();
        reff3 = mDatabase.getReference("customer_page").child("Customer_location").child(uid);



        reff3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                boolean check=true;
                if (!dataSnapshot.exists()) {
                  //  Toast.makeText (LocationListMain.this,"No Data",Toast.LENGTH_SHORT).show ();
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
                            String  status = getIntent().getStringExtra("status");
                            if(value10.equals(status))
                            {
                                LocationList l = new LocationList(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10);
                                LocationList.add(l);

                            }




                        }
                    }


                    if(LocationList.isEmpty())
                    {
                       
                    }
                    else {
                      
                        
                        adapter = new LocationListRv(LocationListMain.this, LocationList,"11");
                        rv.setAdapter(adapter);
                    }
                }


            }
            @Override public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }


        });






    }









}