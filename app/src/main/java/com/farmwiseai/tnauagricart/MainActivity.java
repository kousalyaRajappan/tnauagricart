package com.farmwiseai.tnauagricart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new HelloWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());

        // Enable JavaScript and DOM Storage
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        // Accept cookies
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        webView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_DOWN) {
                if (!v.hasFocus()) {
                    v.requestFocus();
                }
            }
            return false;
        });

        // Manually show the keyboard when the WebView gains focus
        webView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        // Load the URL
        //   https://agricart-dev.farmwiseai.com/
        webView.loadUrl("https://www.tnauagricart.com/");
        //  webView.loadUrl(" https://agricart-dev.farmwiseai.com/");

        // Restore state if available
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("----", url);
            if (Uri.parse(url).getHost().equals("tnauagricart.com")) {
                // This is my web site, so do not override; let my WebView load the page
                view.loadUrl(url);
                return true;
            } else {
                // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // Inject JavaScript to focus on the first input field in the page
            webView.loadUrl("javascript:(function(){ var input = document.querySelector('input'); if (input) { input.focus(); } })()");
        }
    }
/*
    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, android.os.Message resultMsg) {
            WebView newWebView = new WebView(MainActivity.this);
            newWebView.setWebViewClient(new WebViewClient());
            // Setup any additional settings for new WebView here if needed

            // Show the new WebView in a dialog or activity
            // For simplicity, this code snippet does not include the implementation for displaying the new WebView.
            // You can implement this based on your requirements.

            return true;
        }

        @Override
        public void onConsoleMessage(String message, int lineNumber, String sourceID) {
            Log.d("WebView", message + " -- From line " + lineNumber + " of " + sourceID);
        }
    }
*/

    /*
        private class MyWebChromeClient extends WebChromeClient {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, android.os.Message resultMsg) {
                WebView newWebView = new WebView(MainActivity.this);
                newWebView.setWebViewClient(new WebViewClient());

                // Create a dialog to show the new WebView
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(newWebView);
                builder.setTitle("Popup");

                AlertDialog dialog = builder.create();
                dialog.show();

                // Load the URL that triggered the popup
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.sendToTarget();
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();

                return true;
            }
        }
    */
    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, android.os.Message resultMsg) {
            WebView newWebView = new WebView(MainActivity.this);
            newWebView.setWebViewClient(new WebViewClient());
            WebSettings webSettings = newWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setSupportMultipleWindows(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            newWebView.setFocusable(true);
           /* LinearLayout container = findViewById(R.id.container);
            container.addView(newWebView, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            ))*/;

            newWebView.setFocusableInTouchMode(true);
            // Create a dialog to show the new WebView
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(newWebView);
            builder.setTitle("TNAU AGRICART");

            AlertDialog dialog = builder.create();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            newWebView.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!v.hasFocus()) {
                        v.requestFocus();
                    }
                }
                return false;
            });

            // Manually show the keyboard when the WebView gains focus
            newWebView.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                }
            });

            // Display the popup dialog and ensure it properly handles keyboard visibility
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            dialog.show();
          /*  dialog.setOnDismissListener(dialogs -> {
                view.reload();
            });*/

            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(newWebView);
            resultMsg.sendToTarget();
            newWebView.requestFocus();
            newWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    newWebView.loadUrl("javascript:(function(){ var input = document.querySelector('input'); if (input) { input.focus(); } })()");

                    Log.e("---url---", url);
                    // Optionally, automatically close the popup after the action completes (e.g., form submission, page load)
                    if (url.contains("callbackResponse") || url.contains("https://www.tnauagricart.com/emptyPage")) {  // Replace with actual URL logic for action completion
                        dialog.dismiss();  // Close the popup dialog
                    }
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    String url = request.getUrl().toString();
                    if (Uri.parse(url).getHost().endsWith("tnauagricart.com")) {
                        return false;  // Let WebView load the URL inside the popup
                    }
                    view.loadUrl(request.getUrl().toString());
                    return false;

                }
            });
            return true;
        }
    }

}

/*
public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                Log.d("WebView", message + " -- From line "
                        + lineNumber + " of "
                        + sourceID);
            }
        });

        // Enable JavaScript and DOM Storage
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true); // Enable local storage
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        // Accept cookies
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        // Load the URL
        webView.loadUrl("https://www.tnauagricart.com/");

        // Restore state if available
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
*/


