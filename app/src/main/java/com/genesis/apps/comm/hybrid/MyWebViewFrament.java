package com.genesis.apps.comm.hybrid;


import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.hybrid.core.WebViewFragment;
import com.genesis.apps.comm.ui.BaseActivity;


/**
 * webView Fragment
 */
public class MyWebViewFrament extends WebViewFragment {
    private static final String LOG_TAG = MyWebViewFrament.class.getSimpleName();

    private RelativeLayout webViewContainer = null;
    WebViewListener webViewListener = null;

    public interface WebViewListener {
        boolean shouldOverrideUrlLoading(WebView view, String url);
        void onPageFinished(String url);
        boolean onBackPressed();
        void onCloseWindow(WebView window);
    }

    public MyWebViewFrament() {
        Log.v(LOG_TAG, "MyWebViewFrament()");
    }

    public void setWebViewListener(WebViewListener webViewListener) {
        this.webViewListener = webViewListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_webview, null);
        webViewContainer = v.findViewById(R.id.webViewContainer);
        return v;
    }

    @Override
    public ViewGroup getWebViewContainer() {
        return webViewContainer;
    }

    public boolean onBackPressed() {
        if (this.webViewListener != null) {
            if (webViewListener.onBackPressed()) {
                return true;
            }
        }

        if (canGoBack()) {
            goBack();
            return true;
        } else {
            return false;
        }
    }

    public String getUrl() {
        return getCurrentUrl();
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message,
                               JsResult result) {
        if(getActivity() == null) return false;

        final JsResult res = result;
        new AlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.ic_launcher)
//				.setTitle(R.string.popup_title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes,
                        (dialog, which) -> res.confirm())
                .setNegativeButton(android.R.string.no,
                        (dialog, which) -> res.cancel()).show();

        return true;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message,
                             JsResult result) {
        if(getActivity() == null) return false;

        final JsResult res = result;
        new AlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.ic_launcher)
//				.setTitle(R.string.popup_title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok,
                        (dialog, which) -> res.confirm()).show();

        return true;
    }

    @Override
    public void showProgress(final boolean show) {
        Log.v(LOG_TAG, "showProgress [" + show + "]");
        if(getActivity() == null) return;

        ((BaseActivity) getActivity()).showProgressDialog(show);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        Log.d(LOG_TAG, "onReceivedError:" );
        loadUrl("about:blank");
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Log.d(LOG_TAG, "onPageFinished:" + url);
        showProgress(false);
        if (webViewListener != null) {
            webViewListener.onPageFinished(url);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        Log.d(LOG_TAG, "onRestart:" + url);
        showProgress(true);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d(LOG_TAG, "shouldOverrideUrlLoading:" + url);
        if (webViewListener != null) {
            return webViewListener.shouldOverrideUrlLoading(view, url);
        } else {
            return false;
        }
    }

    @Override
    public void onCloseWindow(WebView window) {
        webViewListener.onCloseWindow(window);
        Log.v(LOG_TAG, "onCloseWindow window[" + window + "]");
    }
}
