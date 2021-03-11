package com.genesis.apps.comm.hybrid;


import android.content.Intent;
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
import androidx.databinding.ViewDataBinding;

import com.genesis.apps.R;
import com.genesis.apps.comm.hybrid.core.WebViewFragment;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.main.MainActivity;
import com.genesis.apps.ui.myg.MyGEntranceActivity;


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

//        if(getActivity() instanceof MainActivity) {
//            if(((MainActivity) getActivity()).loginChk(((MainActivity) getActivity()).getCustGbCd())) {
//                MiddleDialog.dialogCommonTwoButton(getActivity(), R.string.comm_word_3, message, () -> res.confirm(), () -> res.cancel());
//            } else {
//                MiddleDialog.dialogLogin(getActivity(), () -> res.confirm(), () -> res.cancel());
//            }
//        } else {
            MiddleDialog.dialogCommonTwoButton(getActivity(), R.string.comm_word_3, message, () -> res.confirm(), () -> res.cancel());
//        }

        return true;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message,
                             JsResult result) {
        if(getActivity() == null) return false;

        final JsResult res = result;
        MiddleDialog.dialogCommonOneButton(getActivity(), R.string.comm_word_3, message, () -> res.confirm());

        return true;
    }

    @Override
    public void showProgress(final boolean show) {
        Log.v(LOG_TAG, "showProgress [" + show + "]");
        if(getActivity() == null) return;

        ((SubActivity<ViewDataBinding>) getActivity()).showProgressDialog(show);
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
