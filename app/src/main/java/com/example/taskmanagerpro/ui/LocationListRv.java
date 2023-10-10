package com.example.taskmanagerpro.ui;

import androidx.annotation.NonNull;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.taskmanagerpro.R;
import com.example.taskmanagerpro.fragments.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class LocationListRv extends RecyclerView.Adapter<LocationListRv.ViewHolder>  {

    private List<LocationList> LocationList;
    private Context mContext1;
    public static final String MyPREFERENCES = "BBSP" ;
    SharedPreferences sharedpreferences;
    public static final String provider_id = "provider_id";
    public static final String provider_name = "provider_name";
    public static final String service_id = "service_id";
    public static final String service_name = "service_name";
    public static final String provider_icon = "provider_icon";

    private static  Integer a=0;

    private static final int EDIT_NOTE_REQUEST = 2;
    private List<LocationList> exampleListFull;

    private FragmentManager supportFragmentManager;
    String type1;
    public LocationListRv(Context context, List<LocationList> LocationList,String type) {
        this.LocationList = LocationList;
        mContext1 = context;
     
        exampleListFull = new ArrayList<>(LocationList);
        type1 = type;
    }





    @NonNull

    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.location_rv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final LocationList ld= LocationList.get(position);
        holder.setIsRecyclable(false);

        holder.titleTask.setText(ld.getTitle());
        holder.description.setText(ld.getDescription());
        holder.Address.setText(ld.getAddress());
        holder.date.setText(ld.getDate()+" "+ld.getTime());





        holder.l1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(type1.equals("Customer_location")) {
                            Log.i("MyActivity_33", ld.getLat());
                            Log.i("MyActivity_33", ld.getLong1());

                            Intent intent1 = new Intent(mContext1, MapView1.class);//start about app activity

                            intent1.putExtra("lat", ld.getLat());
                            intent1.putExtra("long", ld.getLong1());


                            mContext1.startActivity(intent1);

                        }
                    }
                }
        );



        if(!ld.getStatus().equals("1"))
        {
            holder.b1.setVisibility(View.GONE);
            holder.b2.setVisibility(View.GONE);
            holder.b3.setVisibility(View.GONE);
        }


        holder.b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseReference reff1,reff2;
                        FirebaseDatabase mDatabase;
                        mDatabase = FirebaseDatabase.getInstance();

                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance ();
                        FirebaseUser user = firebaseAuth.getCurrentUser ();
                        String uid=user.getUid();

                        reff2 = mDatabase.getReference("customer_page").child(type1).child(uid);



                        String push_key = ld.getPush();
                        String e222=push_key;
                        reff2.child(e222).child("status").setValue("2");


                        Intent intent1 = new Intent (mContext1, Home1.class);//start about app activity


                        mContext1.startActivity(intent1);


                    }
                }
        );

        holder.b2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        DatabaseReference reff1,reff2;
                        FirebaseDatabase mDatabase;
                        mDatabase = FirebaseDatabase.getInstance();

                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance ();
                        FirebaseUser user = firebaseAuth.getCurrentUser ();
                        String uid=user.getUid();

                        reff2 = mDatabase.getReference("customer_page").child(type1).child(uid);



                        String push_key = ld.getPush();
                        String e222=push_key;
                        reff2.child(e222).child("status").setValue("3");


                        Intent intent1 = new Intent (mContext1, Home1.class);//start about app activity


                        mContext1.startActivity(intent1);


                    }
                }
        );

        holder.b3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent1 = new Intent (mContext1, CreateTaskActivity1.class);
                        if(type1.equals("Customer_location"))
                        {
                            intent1 = new Intent (mContext1, CreateTaskActivity1.class);
                            intent1.putExtra (CreateTaskActivity1.EXTRA_ID, ld.getPush());
                            intent1.putExtra (CreateTaskActivity1.EXTRA_TITLE, ld.getTitle());
                            intent1.putExtra (CreateTaskActivity1.EXTRA_DESC, ld.getDescription());
                            intent1.putExtra (CreateTaskActivity1.EXTRA_TIME, ld.getAddress());
                            mContext1.startActivity(intent1);
                        }
                        if(type1.equals("Customer_remainder"))
                        {
                            intent1 = new Intent (mContext1, CreateTaskActivity.class);
                            intent1.putExtra (CreateTaskActivity.EXTRA_ID, ld.getPush());
                            intent1.putExtra (CreateTaskActivity.EXTRA_TITLE, ld.getTitle());
                            intent1.putExtra (CreateTaskActivity.EXTRA_DESC, ld.getDescription());
                            intent1.putExtra (CreateTaskActivity.EXTRA_TIME, ld.getTime());
                            mContext1.startActivity(intent1);
                        }
                        if(type1.equals("Customer_work"))
                        {
                            intent1 = new Intent (mContext1, CreateTaskActivity2.class);
                            intent1.putExtra (CreateTaskActivity2.EXTRA_ID, ld.getPush());
                            intent1.putExtra (CreateTaskActivity2.EXTRA_TITLE, ld.getTitle());
                            intent1.putExtra (CreateTaskActivity2.EXTRA_DESC, ld.getDescription());
                            intent1.putExtra (CreateTaskActivity2.EXTRA_TIME, ld.getAddress());
                            mContext1.startActivity(intent1);
                        }




                    //    mContext1.startActivityForResult(intent1, EDIT_NOTE_REQUEST);


                    }
                }
        );


    }






    @Override
    public int getItemCount() {
        return LocationList.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView titleTask,description,Address,date;


        LinearLayout l1;
        Button b1,b2,b3;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTask = itemView.findViewById(R.id.titleTask);
            description = itemView.findViewById(R.id.description);
            Address = itemView.findViewById(R.id.Address);
            date = itemView.findViewById(R.id.date);
            l1 = itemView.findViewById(R.id.l1);
            b1 = itemView.findViewById(R.id.b1);
            b2 = itemView.findViewById(R.id.b2);
            b3 = itemView.findViewById(R.id.b3);







        }

    }















}