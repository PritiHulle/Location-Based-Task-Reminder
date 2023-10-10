package com.example.taskmanagerpro.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagerpro.receiver.AlertReceiver;
import com.example.taskmanagerpro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.Locale;

public class SendToComplete extends AppCompatActivity  implements TextToSpeech.OnInitListener{
  Button CompleteTask;
    String title,des,time,push_id;
    int id;
    Intent intent;
    public static final String TITLE=" com.example.taskmanagerpro.TITLE";
    public static final String DESC=" com.example.taskmanagerpro.DESC";
    public static final String EXTRA_ID = "com.example.taskmanagerpro.EXTRA_ID";
    public static final String TIME=" com.example.taskmanagerpro.TIME";
    private TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_send_to_complete);
        TextView setDesc = findViewById (R.id.setDescription);
        TextView setDate = findViewById (R.id.setTime);
        TextView setTitle = findViewById (R.id.setTitle);
        CompleteTask=findViewById (R.id.CompleteTask);

        textToSpeech = new TextToSpeech(this, this);
        setTitle ("Task Alarm");

        intent=getIntent ();
        title=intent.getStringExtra (AlertReceiver.taskNotificationHelper.TITLE);
        des=intent.getStringExtra (AlertReceiver.taskNotificationHelper.DESC);
        time=intent.getStringExtra (AlertReceiver.taskNotificationHelper.TIME);
        push_id=intent.getStringExtra (AlertReceiver.taskNotificationHelper.EXTRA_ID);

        setTitle.setText (title);
        setDesc.setText (des);
        setDate.setText (time);

        speakText(des);




        CompleteTask.setOnClickListener (v -> {
            SendToComplete.this.showCompleteTaskDialog ();

        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop location updates and release resources


        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
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

    private void showCompleteTaskDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder (this);
        builder.setTitle ("Complete Task?");
        builder.setMessage ("do you wish to complete this task?");
        builder.setCancelable (false);

        builder.setPositiveButton ("Yes", (dialog, which) -> SendToComplete.this.sendToComplete());
        builder.setNegativeButton ("No", (dialog, which) -> {
            //stop ongoing alarm tone
            AlertReceiver.taskRingtone.stop ();
            //cancel vibration
            AlertReceiver.vibrator.cancel ();
            dialog.dismiss ();
            finish ();
            Toast.makeText (getApplicationContext (),"Cancelled",Toast.LENGTH_SHORT).show ();

        });
        builder.create ().show ();
    }
    //this method sends data to mainActivity, then CompletedTaskfragment takes it from there
    private void sendToComplete() {
        //stop ongoing alarm tone
        AlertReceiver.taskRingtone.stop ();

        //cancel vibration
        AlertReceiver.vibrator.cancel ();

//        Intent data = new Intent (this, Home1.class);
//        data.putExtra (TITLE, title);
//        data.putExtra (TIME, time);
//        startActivity (data);
//        finish ();





        DatabaseReference reff1,reff2;
        FirebaseDatabase mDatabase;
        mDatabase = FirebaseDatabase.getInstance();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance ();
        FirebaseUser user = firebaseAuth.getCurrentUser ();
        String uid=user.getUid();

        reff2 = mDatabase.getReference("customer_page").child("Customer_remainder").child(uid);



        String push_key = push_id;
        String e222=push_key;
        reff2.child(e222).child("status").setValue("2");


        Intent intent1 = new Intent (this, Home1.class);//start about app activity


        startActivity(intent1);
        finish();





        Toast.makeText (this,"Task Completed",Toast.LENGTH_SHORT).show ();
    }

}
