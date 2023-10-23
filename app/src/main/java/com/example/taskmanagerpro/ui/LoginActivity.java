package com.example.taskmanagerpro.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagerpro.R;
import com.example.taskmanagerpro.fragments.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText mname,mpassword;
    Button Login;
    TextView mSignup,forgotPassword;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        mname=findViewById(R.id.name);
        mpassword=findViewById(R.id.pass);
        mSignup=findViewById(R.id.signupText);
        fAuth= FirebaseAuth.getInstance();
        Login=findViewById(R.id.LoginButton);
        forgotPassword=findViewById (R.id.forgotPass);

        LoadingProgressDialog progressDialog=new LoadingProgressDialog (this);

        Login.setOnClickListener(v -> {
            String email, password;
            email = mname.getText().toString().trim();
            password = mpassword.getText().toString().trim();


            if (TextUtils.isEmpty(email)) {
                mname.setError("please input email");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                mpassword.setError("please input password");
                return;
            }

            if (password.length() < 6) {
                mpassword.setError("password must be >=6 characters");
                return;
            }

             // checks for internet connection
            if(HomeFragment.HasActiveNetworkConnection (this)){

                progressDialog.startAlertDialog ();
            }
            else{
                Toast.makeText (this,
                        "no network connection",Toast.LENGTH_SHORT).show ();
                return;
            }


            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    progressDialog.dismissDialog ();
                    Intent myintent = new Intent(LoginActivity.this, Home1.class);
                    ToastMessage ("Successfully login");
                    LoginActivity.this.startActivity(myintent);
                    LoginActivity.this.finish();


                }
                else {
                    Toast.makeText (LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.dismissDialog ();
                }
            });

        });

        mSignup.setOnClickListener(v -> {
            LoginActivity.this.startActivity (new Intent (LoginActivity.this.getApplicationContext (), SignupActivity.class));
            finish ();
        });

        forgotPassword.setOnClickListener (v -> {
            showRecoverPasswordDialog();
        });

    }


    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder (this);
        builder.setTitle ("Recover Password");
        LinearLayout layout=new LinearLayout (this);
        final EditText editTextEmail=new EditText (this);
        editTextEmail.setHint ("Email");
        editTextEmail.setInputType (InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        layout.addView (editTextEmail);
        layout.setPadding (10,10,10,10);
        editTextEmail.setMinEms (17);
        LoadingProgressDialog progressDialog=new LoadingProgressDialog (this);

        builder.setView (layout);
        builder.setCancelable (false);

        builder.setPositiveButton ("Recover", (dialog, which) -> {
            String email=editTextEmail.getText ().toString ().trim ();
            if(TextUtils.isEmpty (email)){
                Toast.makeText (getApplicationContext (),"please input your email",Toast.LENGTH_SHORT).show ();
                return;
            }
            if(HomeFragment.HasActiveNetworkConnection (this)){
                progressDialog.startAlertDialog ();
                beginPasswordRecovery(email,progressDialog);



            }
            else{
                Toast.makeText (this,
                        "no network connection",Toast.LENGTH_SHORT).show ();
            }
        });

        builder.setNegativeButton ("Cancel", (dialog, which) -> {
            dialog.dismiss ();
        });
        builder.create ().show ();

    }

    private void beginPasswordRecovery(String email,LoadingProgressDialog dialog) {
        fAuth.sendPasswordResetEmail (email).addOnCompleteListener (task -> {
            if(task.isSuccessful ()){
                ToastMessage ("please check your email, a link has been sent");
            }
        }).addOnFailureListener (e -> {
            Toast.makeText (this,e.getMessage (),Toast.LENGTH_SHORT).show ();
            dialog.dismissDialog ();
        });
    }

    private void ToastMessage(String Message){
        Toast.makeText (LoginActivity.this,Message,Toast.LENGTH_SHORT).show ();
    }
}




