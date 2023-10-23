
package com.example.taskmanagerpro.fragments;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerpro.R;
import com.example.taskmanagerpro.ui.LocationList;
import com.example.taskmanagerpro.ui.LocationListRv;
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

public class Fragment3 extends Fragment {

    public Fragment3() {
        // Required empty public constructor
    }
    private RecyclerView rv;

    private List<com.example.taskmanagerpro.ui.LocationList> LocationList;

    private LocationListRv adapter;

    private Button b1,b2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate (R.layout.fragment3_layout, container, false);
        Log.i("MyACtivity","Fragment 11");




        Log.i("MyACtivity","Fragment 12");
        LocationList = new ArrayList<>();


        rv = (RecyclerView) v.findViewById(R.id.rv);

        b1 = v.findViewById(R.id.b1);
        b2 = v.findViewById(R.id.b2);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));



        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int color = ContextCompat.getColor(requireContext(), R.color.red); // Replace R.color.my_color with your desired color resource
                        b1.setBackgroundTintList(ColorStateList.valueOf(color));


                        int color1 = ContextCompat.getColor(requireContext(), R.color.green); // Replace R.color.my_color with your desired color resource
                        b2.setBackgroundTintList(ColorStateList.valueOf(color1));

                        getdata("1");

                    }
                }
        );

        b2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int color = ContextCompat.getColor(requireContext(), R.color.red); // Replace R.color.my_color with your desired color resource
                        b2.setBackgroundTintList(ColorStateList.valueOf(color));


                        int color1 = ContextCompat.getColor(requireContext(), R.color.green); // Replace R.color.my_color with your desired color resource
                        b1.setBackgroundTintList(ColorStateList.valueOf(color1));


                        getdata("2");

                    }
                }
        );
        getdata("1");





        //  return inflater.inflate(R.layout.fragment1_layout, container, false);

        return v;

    }

    private void getdata(String status1)
    {
        FirebaseDatabase mDatabase;
        mDatabase = FirebaseDatabase.getInstance();

        DatabaseReference reff, reff1, reff2, reff3, reff4;
        // reff3 = mDatabase.getReference("Admin").child("vender_admin").child("history_order");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance ();
        FirebaseUser user = firebaseAuth.getCurrentUser ();
        String uid=user.getUid();
        reff3 = mDatabase.getReference("customer_page").child("Customer_work").child(uid);

        LocationList.clear();
        adapter = new LocationListRv(requireContext(), LocationList,"Customer_work");
        rv.setAdapter(adapter);
        Log.i("MyActivity","401");


        reff3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                boolean check=true;
                if (!dataSnapshot.exists()) {
                 //   Toast.makeText (requireContext(),"No Data",Toast.LENGTH_SHORT).show ();
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
                            String  status = status1;
                            Log.i("MyActivity","402");
                            if(value10.equals(status))
                            {
                                Log.i("MyActivity","403");
                                LocationList l = new LocationList(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10);
                                LocationList.add(l);

                            }




                        }
                    }


                    if(LocationList.isEmpty())
                    {

                    }
                    else {


                        adapter = new LocationListRv(requireContext(), LocationList,"Customer_work");
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
