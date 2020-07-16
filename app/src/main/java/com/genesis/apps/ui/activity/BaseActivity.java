package com.genesis.apps.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.excutor.ExecutorService;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BaseActivity extends AppCompatActivity {
    @Inject
    public ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutDownExcutor();
    }

    public final void showProgressDialog(final boolean show, final View view) {
        try {
            if (view != null)
                runOnUiThread(() -> view.setVisibility(show ? View.VISIBLE : View.GONE));
        } catch (Exception ignore) {

        }
    }

    public final void showProgressDialog(final boolean show) {
        try {
            if (findViewById(R.id.l_progress) != null)
                runOnUiThread(() -> findViewById(R.id.l_progress).setVisibility(show ? View.VISIBLE : View.GONE));
        } catch (Exception e) {

        }
    }

    public void startActivitySingleTop(Intent intent, int flag) {
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(flag==0)
            startActivity(intent);
        else
            startActivityForResult(intent, flag);
    }

    public void alert(String title, String msg, DialogInterface.OnClickListener okListener) {
        if(TextUtils.isEmpty(title)) title = getString(R.string.comm_word_3);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.comm_word_1, okListener);
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }
}
