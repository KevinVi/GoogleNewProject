package com.exemple.projetgooglenews.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.exemple.projetgooglenews.R;

import java.lang.reflect.AccessibleObject;

/**
 * Created by kevin on 05/02/2016.
 */
public class WebActivity extends Activity {
    public static final String URL = "link";

    WebView web;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Transparent actionBar Only option menu available...

        setDisplayShowHomeEnabled(false);
        setTitleShowHomeEnabled();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);

        //get intent
        Intent i = getIntent();
        String url = i.getStringExtra(URL);

        // webview id
        web = (WebView) findViewById(R.id.webView);
        //webchromeclient
        web.setWebChromeClient(new WebChromeClient());
        //hide search bar
        web.setWebViewClient(new myWebClient());
        //Enable javascript
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setPluginState(WebSettings.PluginState.ON);
        //Scale video controls
        web.setInitialScale(150);
        // Test

        //web.getSettings().setLoadWithOverviewMode(true);
        //web.getSettings().setUseWideViewPort(true);
        //web.setVerticalScrollbarOverlay(true);
        //web.getSettings().setAllowFileAccess(true);

        //Load url
        web.loadUrl(url);
        //Load file from assets NB: Missing "S" is correct
        // web.loadUrl("file:///android_asset/yt.html");
    }


    //Fullscreen
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
    }

    public void onHideCustomView() {
    }
    //End fullscreen


    private void setTitleShowHomeEnabled() {
    }

    private void setDisplayShowHomeEnabled(boolean b) {
    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;

        }
    }

    //Back button pressed
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //call alertbox method
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit the Live ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        //leave the web activity
                        WebActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }
}
