package com.jcsamples.installkit.async;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.jcsamples.installkit.models.AppModel;
import com.jcsamples.installkit.models.LoadingDialogModel;
import com.jcsamples.installkit.models.MainViewModel;
import com.jcsamples.installkit.utils.JcUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AppLoader extends AsyncTask<String, Integer, String> {
    private WeakReference<Context> contextWeakReference;
    private MainViewModel mainViewModel;
    private List<AppModel> appModels;

    private List<String> apkPaths;

    public AppLoader(WeakReference<Context> contextWeakReference, MainViewModel mainViewModel) {
        this.contextWeakReference = contextWeakReference;
        this.mainViewModel = mainViewModel;
    }

    @Override
    protected String doInBackground(String[] p1) {
        File rootPath = JcUtils.getRootFilePath();

        // scanning folder ...
        mainViewModel.setAppIsScanning(true);

        apkPaths = new ArrayList<>();
        listAndAddApkPaths(rootPath);

        // folder scanning complete . notify to dialog
        mainViewModel.setTotalAppCount(apkPaths.size());
        mainViewModel.setAppIsScanning(false);

        // read apk details (name , version etc)
        appModels = new ArrayList<>();
        for (String path : apkPaths){
            addToAppModels (path);
        }

        return null;
    }

    private void listAndAddApkPaths(File path) {
        File[] files = path.listFiles();
        if (files != null)
            for (File file : files) {
                if (JcUtils.isFileApk(file)) {
                    apkPaths.add(file.getAbsolutePath());

                    File parent = file.getParentFile();
                    String parentFolderName = parent == null ? "null" : parent.getName();
                    mainViewModel.setLoadingDialogModels(new LoadingDialogModel(parentFolderName, apkPaths.size()));
                } else {
                    listAndAddApkPaths(file);
                }
            }
    }

    private void addToAppModels(String apkPath) {
         Context context = contextWeakReference.get();
        if (context == null) return;
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(apkPath, 0);

        if (packageInfo != null) {
            packageInfo.applicationInfo.sourceDir = apkPath;
            packageInfo.applicationInfo.publicSourceDir = apkPath;

            Drawable icon = packageInfo.applicationInfo.loadIcon(packageManager);
            String appName = (String) packageInfo.applicationInfo.loadLabel(packageManager);
            String appVersion = packageInfo.versionName;

            AppModel appModel = new AppModel(appName, appVersion, apkPath, icon);
            appModels.add(appModel);

            mainViewModel.setLoadingDialogModels(new LoadingDialogModel(appName, appModels.size()));
        }
    }

    @Override
    protected void onPostExecute(String s) {
        mainViewModel.setApps(appModels);
    }
}