package com.jcsamples.installkit.models;

import android.graphics.drawable.Drawable;

public class AppModel {
	private String app_name, app_version, file_path;
	private Drawable icon;
	
	public AppModel (String app_name, String app_version, String file_path, Drawable icon){
		this.app_name = app_name;
		this.app_version = app_version;
		this.file_path = file_path;
		this.icon = icon;
	}
	
	public String getAppName(){
		return app_name;
	}
	
	public String getPath(){
		return file_path;
	}
	
	public Drawable getIcon(){
		return icon;
	}

	public String getAppVersion() {
		return app_version;
	}
}
