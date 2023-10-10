package com.example.taskmanagerpro.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagerpro.fragments.CompletedTaskFragment;
import com.example.taskmanagerpro.R;
import com.example.taskmanagerpro.fragments.HomeFragment;
import com.example.taskmanagerpro.fragments.ProfileFragment;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hotchemi.android.rate.AppRate;

public class ProfileMain extends AppCompatActivity {

    private FirebaseUser user;
    private ImageView avatar;
    private Button upload;
    private Uri imgUrl;
    public static final int CHOOSE_IMAGE = 1;
    private DatabaseReference databaseReference;
    private StorageReference mStorageRef;
    private StorageTask mUpload;
    private TextView Name;
    private TextView Email;
    private static final int ADD_NOTE_REQUEST = 1;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance ();
        user = firebaseAuth.getCurrentUser ();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance ();
        databaseReference = firebaseDatabase.getReference ("Users");

        TextView rateApp = findViewById (R.id.RateApp);
        TextView howtouse = findViewById (R.id.howtouse);
        TextView logout = findViewById (R.id.logout);
        avatar = findViewById (R.id.avatarIv);
        Name = findViewById (R.id.User_name);
        Email = findViewById (R.id.User_email);
        TextView share = findViewById (R.id.shareAndSend);
        upload = findViewById (R.id.saveUpload);
        TextView sendFeedback = findViewById (R.id.sendFeedback);
        TextView aboutAppTv = findViewById (R.id.about);
        mStorageRef = FirebaseStorage.getInstance ().getReference ("uploads");
        TextView time1 = findViewById(R.id.time1);
        TextView time1_history = findViewById(R.id.time1_history);
        TextView time1_complete = findViewById(R.id.time1_complete);
        TextView time1_delete = findViewById(R.id.time1_delete);
        retrieveinfos ();

        time1.setOnClickListener (v16 -> {

            Intent myintent = new Intent (ProfileMain.this, CreateTaskActivity1.class);
            startActivityForResult (myintent, ADD_NOTE_REQUEST);


        });
        time1_history.setOnClickListener (v16 -> {

            Intent myintent = new Intent (ProfileMain.this, LocationListMain.class);
            myintent.putExtra("status", "1");
            startActivityForResult (myintent, ADD_NOTE_REQUEST);


        });



        time1_complete.setOnClickListener (v16 -> {

            Intent myintent = new Intent (ProfileMain.this, LocationListMain.class);
            myintent.putExtra("status", "2");
            startActivityForResult (myintent, ADD_NOTE_REQUEST);


        });
        time1_delete.setOnClickListener (v16 -> {

            Intent myintent = new Intent (ProfileMain.this, LocationListMain.class);
            myintent.putExtra("status", "3");
            startActivityForResult (myintent, ADD_NOTE_REQUEST);


        });


        upload.setOnClickListener (v12 -> {
            if (mUpload != null && mUpload.isInProgress ()) {
                Toast.makeText (Objects.requireNonNull (ProfileMain.this), "upload is in progress", Toast.LENGTH_SHORT);
            } else {
                UploadImage (ProfileMain.this);//method that allows user to upload selected image
                upload.setVisibility (View.GONE);
            }
        });

        aboutAppTv.setOnClickListener (v1 -> {
            Intent intent = new Intent (ProfileMain.this, ActivityAbout.class);//start about app activity
            startActivity (intent);

        });

        //image click
        avatar.setOnClickListener (v13 -> {
            showFileChooser ();
            upload.setVisibility (View.VISIBLE);
        });

        //get current user by email
        Query query = databaseReference.orderByChild ("email").equalTo (user.getEmail ());

        query.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren ()) {
                    //get data
                    String name = "" + ds.child ("username").getValue ();
                    String email = "Email:" + ds.child ("email").getValue ();
                    String urlImage = "" + ds.child ("image").getValue ();
                    Name.setText (name);
                    Email.setText (String.format ("%s", email));
                    try {
                        Picasso.with (ProfileMain.this)
                                .load (urlImage).fit ().into (avatar);
                    } catch (Exception e) {
                        e.printStackTrace ();
                    }
                    try {
                        SharedPreferences sharedPreferences= Objects.requireNonNull (ProfileMain.this)
                                .getPreferences (Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit ();
                        editor.putString ("Username",name);
                        editor.putString ("email",email);
                        editor.putString ("image",urlImage);
                        editor.apply ();
                    } catch (Exception e) {
                        e.printStackTrace ();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        logout.setOnClickListener (v16 -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent (ProfileMain.this, LoginActivity.class);//start about app activity
            startActivity (intent);
            finish();

        });

        howtouse.setOnClickListener (v16 -> {

            Intent intent = new Intent (ProfileMain.this, Web.class);//start about app activity
            startActivity (intent);
          //  finish();

        });


        //rate app function, this will come in the update
        rateApp.setOnClickListener (v16 -> {
            try{
                startActivity (new Intent (Intent.ACTION_VIEW,
                        Uri.parse ("market://details?id=" +ProfileMain.this.getPackageName ())));
            }catch (ActivityNotFoundException e){
                startActivity (new Intent (Intent.ACTION_VIEW,
                        Uri.parse ("http://play.google.com/store/apps/details?id=" +ProfileMain.this.getPackageName ())));
            }
        });

        //share function
        share.setOnClickListener (v14 -> {
            //TODO remember to replace the google play with website link
            Intent a = new Intent (Intent.ACTION_SEND);
            final String appPackageName = ProfileMain.this.getApplicationContext ().getPackageName ();
            String strAppLink;
            try {
                strAppLink = "https://play.google.com/store/apps/details?id" + appPackageName;
            } catch (android.content.ActivityNotFoundException anfe) {
                strAppLink = "https://play.google.com/store/apps/details?id" + appPackageName;
            }
            a.setType ("text/link");
            String sharebOdy = "Hey, Check out Task-it, i use it to manage my Todo's. Get it for free at " + "\n" + "" + strAppLink;
            String shareSub = "APP NAME/TITLE";
            a.putExtra (Intent.EXTRA_SUBJECT, shareSub);
            a.putExtra (Intent.EXTRA_TEXT, sharebOdy);
            startActivity (Intent.createChooser (a, "Share Using"));
        });

        //send Feedback Function
        sendFeedback.setOnClickListener (v15 -> showFeedbackDialog (ProfileMain.this));



    }
    //retrieve save username & current date from sharedpreferences

    private void retrieveinfos(){
        String username= null;
        String email= null;
        String urlImage= null;
        try {
            SharedPreferences sharedPreferences= Objects.requireNonNull (ProfileMain.this).getPreferences (Context.MODE_PRIVATE);
            username = sharedPreferences.getString ("Username","");
            email = sharedPreferences.getString ("email","");
            urlImage = sharedPreferences.getString ("image","");
        } catch (Exception e) {
            e.printStackTrace ();
        }

        Name.setText (username);
        Email.setText (email);
        try {
            Picasso.with (ProfileMain.this)
                    .load (urlImage).fit ().into (avatar);
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent ();
        intent.setType ("image/*");
        intent.setAction (Intent.ACTION_GET_CONTENT);
        startActivityForResult (intent, CHOOSE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            imgUrl = data.getData();
            Picasso.with(ProfileMain.this).load(imgUrl).into(avatar);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver resolver = Objects.requireNonNull (ProfileMain.this).getContentResolver ();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton ();
        return mimeTypeMap.getExtensionFromMimeType (resolver.getType (uri));
    }

    private void UploadImage(Context context) {
        if (imgUrl != null) {
            StorageReference file = mStorageRef.child (System.currentTimeMillis () + "*" + getFileExtension (imgUrl));

            mUpload = file.putFile (imgUrl)
                    .addOnSuccessListener (taskSnapshot -> {
                        Task<Uri> uriTasks = taskSnapshot.getStorage ().getDownloadUrl ();
                        while (!uriTasks.isSuccessful ()) ;
                        Uri downloadUri = uriTasks.getResult ();
                        if (uriTasks.isSuccessful ()) {
                            databaseReference.child (user.getUid ()).child("image").setValue (downloadUri.toString ());
                            Picasso.with (ProfileMain.this).load (imgUrl).into (avatar);

                            Toast.makeText (context, "Upload successfully", Toast.LENGTH_SHORT).show ();
                            upload.setVisibility (View.INVISIBLE);
                        } else {
                            Toast.makeText (context, "Some error occurred", Toast.LENGTH_SHORT).show ();
                            upload.setVisibility (View.INVISIBLE);
                        }
                    })
                    .addOnFailureListener (e -> Toast.makeText (ProfileMain.this, e.getMessage (), Toast.LENGTH_SHORT).show ());
        } else {
            Toast.makeText (ProfileMain.this, "no file selected", Toast.LENGTH_SHORT).show ();
        }
    }

    private void showFeedbackDialog(Context context)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Feedback Form");
        builder.setMessage("please provide us your valuable feedback");
        LayoutInflater inflater = LayoutInflater.from(ProfileMain.this);

        View reg_layout = inflater.inflate(R.layout.feedback, null);
        final TextView tvEmail = reg_layout.findViewById(R.id.emailName);
        tvEmail.setText(user.getEmail());

        final EditText Feedback = reg_layout.findViewById(R.id.Message);
        builder.setView(reg_layout);

        builder.setPositiveButton("SEND", (dialog, which) -> {
            if (TextUtils.isEmpty(Feedback.getText().toString())) {
                Toast.makeText (context,"please input a text",Toast.LENGTH_SHORT).show ();
                return;
            }

            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference myRef=database.getReference();

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Object value=dataSnapshot.getValue();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ProfileMain.this, "failed to read info", Toast.LENGTH_SHORT).show();
                }
            });
            myRef.child("Users").child(user.getUid()).child("Feedback").setValue(Feedback.getText().toString());
            Toast.makeText (context,"thanks for your valuable feedback",Toast.LENGTH_SHORT).show ();
        });
        builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
        builder.setCancelable(false);
        builder.show();
    }




}


