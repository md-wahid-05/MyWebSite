package com.example.mywebsite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    ProgressDialog pd;
    private ProgressBar progressBar;
    private LottieAnimationView animationView;
    private RelativeLayout splashScreen;
    private Button retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.myweb_view);
//        progressBar = findViewById(R.id.progressBar); // Instance create of progressDialog
        pd = new ProgressDialog(this);  // Instance create of progressDialog
        pd.setMessage("Loading the sites ...........!!!!"); // set the message
        /**
         * if any one click any where the dialog will not go
         * when the site load then it will auto go
         */
        pd.setCanceledOnTouchOutside(false);

        /**
         * call there checkNetwork()
         */
        checkNetwork();


    }
// TODO ----------------- WebView Processing ------------------------------
    private void startWebVview() {


        pd.show();
        /**
         * set the website link in this url
         */
        webView.loadUrl(ConstantUrl.web_url);
        /**
         * Enable javascript
         */
        webView.getSettings().setJavaScriptEnabled(true);
        /**
         * This app doesnot open any browser fpr this using webClient
         */
        webView.setWebViewClient(new WebViewClient());
/**
 * When the website will load
 * then the dialog will finish
 * by this process
 */
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });

    }
// TODO ----------------- CheckNetWork Processing --------------------------
    private void checkNetwork() {
        final ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final NetworkInfo mobile_data = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnected() || mobile_data.isConnected()) {
            /**
             * if the wifi or data is connect
             * then the sites load
             */
            startWebVview();
        } else {
            /**
             * OtherWise
             * show this dialog
             */
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.warn_layout);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            retry = dialog.findViewById(R.id.try_btn);


            ((View) retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * when we click the try button
                     * then the sites will load
                     * when the connection is back
                     */
                    recreate();
                }
            });
            dialog.show();
        }
    }


    /**
     * When you will click back button
     * then AlertDialog will show
     * then the yes exit from the app
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Are You Want to Exit ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }
}