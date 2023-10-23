package com.example.taskmanagerpro.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.taskmanagerpro.R;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class ActivityTask extends AppCompatActivity {
    private static final int ADD_NOTE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_task_select);


        LinearLayout ree1,ree2,ree3,ree4,ree5;

        ree1=findViewById (R.id.ree1);
        ree2=findViewById (R.id.ree2);
        ree3=findViewById (R.id.ree3);
        ree4=findViewById (R.id.ree4);
        ree5=findViewById (R.id.ree5);


        ree1.setOnClickListener (v -> {
            Intent myintent = new Intent (ActivityTask.this, CreateTaskActivity.class);
            startActivityForResult (myintent, ADD_NOTE_REQUEST);

           // showBrowser ("http://google.com/privacypolicy/?i=1");
        });

        ree2.setOnClickListener (v -> {
            Intent myintent = new Intent (ActivityTask.this, CreateTaskActivity1.class);
            startActivityForResult (myintent, ADD_NOTE_REQUEST);
          //  showBrowser ("http://google.com/contact/?i=1");
        });

        ree3.setOnClickListener (v -> {
            Intent myintent = new Intent (ActivityTask.this, LocationListMain.class);
            myintent.putExtra("status", "1");
            startActivityForResult (myintent, ADD_NOTE_REQUEST);
            //  showBrowser ("http://google.com/contact/?i=1");
        });
        ree4.setOnClickListener (v -> {
            Intent myintent = new Intent (ActivityTask.this, LocationListMain.class);
            myintent.putExtra("status", "2");
            startActivityForResult (myintent, ADD_NOTE_REQUEST);
            //  showBrowser ("http://google.com/contact/?i=1");
        });
        ree5.setOnClickListener (v -> {
            Intent myintent = new Intent (ActivityTask.this, LocationListMain.class);
            myintent.putExtra("status", "3");
            startActivityForResult (myintent, ADD_NOTE_REQUEST);
            //  showBrowser ("http://google.com/contact/?i=1");
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId () == android.R.id.home) {
            //if back button is clicked
            finish ();
            return true;
        }
        return super.onOptionsItemSelected (item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
