package com.jcsamples.installkit.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;

import com.jcsamples.installkit.R;

public class AppLoadingDialog {
    private AlertDialog alertDialog;
    private ProgressBar progressBar;
    private AppCompatTextView tvName;

    public AppLoadingDialog(Activity activity) {
        createDialog(activity);
    }

    private void createDialog(Activity activity) {
        View view = inflateView(activity);
        alertDialog = new AlertDialog.Builder(activity)
                .setView(view)
                .setCancelable(false).create();
        alertDialog.show();

        progressBar = view.findViewById(R.id.dialogProgress);
        tvName = view.findViewById(R.id.dialogTvName);
    }

    private View inflateView (Activity activity){
        return LayoutInflater.from(activity).inflate(R.layout.dialog_reading_apps, (ViewGroup) activity.findViewById(android.R.id.content), false);
    }

    public void dismissDialog (){
        if (alertDialog != null && alertDialog.isShowing()){
            alertDialog.dismiss();
        }
    }

    public void setIntermediate (boolean intermediate){
            progressBar.setIndeterminate(intermediate);
    }

    public void setProgress (int progress){
         progressBar.setProgress(progress);
    }

    public void setMax (int max) {
        progressBar.setMax(max);
    }

    public void setText (String s){
        tvName.setText(s);
    }

    public void show(){
        if (alertDialog != null){
            alertDialog.show();
        }
    }
}
