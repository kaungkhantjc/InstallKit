package com.jcsamples.installkit.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.jcsamples.installkit.MainActivity;
import java.io.File;

public class JcUtils {

	public static boolean isReadPermissionGranted (Activity activity){
		return ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
			== PackageManager.PERMISSION_GRANTED;
	}

	public static void requestReadStoragePermission (Activity activity){
		ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, MainActivity.REQUEST_CODE_READ_PERMISSION);
	}

	public static File getRootFilePath (){
		//noinspection deprecation
		return Environment.getExternalStorageDirectory();
	}

	public static boolean isFileApk (File f) {
		return f.getName().toLowerCase().endsWith(".apk");
	}

	public static void installApk (Activity activity, String path){
		File apkFile = new File (path);
		Uri fileUri = Uri.fromFile(apkFile);
		if (Build.VERSION.SDK_INT >= 24) {
			fileUri = FileProvider.getUriForFile(activity, activity. getPackageName() + ".provider", apkFile);
		}
		Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
		intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
		intent.setDataAndType(fileUri, "application/vnd.android" + ".package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		activity.	startActivity(intent);
	}
}
