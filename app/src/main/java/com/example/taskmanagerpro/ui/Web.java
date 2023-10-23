package com.example.taskmanagerpro.ui;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagerpro.R;


public class Web extends AppCompatActivity {

    ProgressBar splashProgress;
    int SPLASH_TIME = 1000;



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        WebView webView = findViewById(R.id.webview1);

        // loading http://www.google.com url in the WebView.
        webView.loadUrl("www.dreamtechnology.in/instructions.php");

        // this will enable the javascript.
        webView.getSettings().setJavaScriptEnabled(true);

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        webView.setWebViewClient(new WebViewClient());


    }

}