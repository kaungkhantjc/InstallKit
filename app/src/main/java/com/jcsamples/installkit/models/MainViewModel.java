package com.jcsamples.installkit.models;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jcsamples.installkit.async.AppLoader;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainViewModel extends ViewModel {

     private MutableLiveData<List<AppModel>> appModels;
     private MutableLiveData<Boolean> isScanningFolders;
     private MutableLiveData<LoadingDialogModel> loadingDialogModels;
     private int total_app_count = 0;

    public LiveData<List<AppModel>> getApps() {
        if (appModels == null) {
            appModels = new MutableLiveData<>();
        } 
        return appModels;
    }

    public LiveData<Boolean> isScanningFolders (){
        if (isScanningFolders == null){
            isScanningFolders = new MutableLiveData<>();
            isScanningFolders.setValue(true);
        }
        return isScanningFolders;
    }

    public boolean isScanning(){
        Boolean value = isScanningFolders.getValue();
        if (value == null) return true;
        return value;
    }

    public LiveData<LoadingDialogModel> getLoadingDialogModels(){
        if (loadingDialogModels == null){
            loadingDialogModels = new MutableLiveData<>();
        }
        return loadingDialogModels;
    }

    public int getTotalAppCount(){
        return total_app_count;
    }

    public void setApps (List<AppModel> myAppModels){
        appModels.postValue(myAppModels);
    }

    public void setAppIsScanning (boolean scanning){
        isScanningFolders.postValue(scanning);
    }

    public void setLoadingDialogModels (LoadingDialogModel models){
        loadingDialogModels.postValue(models);
    }

    public void setTotalAppCount (int count){
        this.total_app_count = count;
    }

    public void loadApps(Context context) {
        new AppLoader(new WeakReference<>(context), this).execute();
    }

}
