package com.jcsamples.installkit;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jcsamples.installkit.adapters.AppListAdapter;
import com.jcsamples.installkit.models.AppModel;
import com.jcsamples.installkit.models.LoadingDialogModel;
import com.jcsamples.installkit.models.MainViewModel;
import com.jcsamples.installkit.utils.JcUtils;
import com.jcsamples.installkit.view.AppLoadingDialog;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_READ_PERMISSION = 123;

    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MainViewModel mainViewModel;
    private AppListAdapter adapter;
    private AppLoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dialog = new AppLoadingDialog(this);

        listView = findViewById(R.id.mainListView1);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, factory);
        mainViewModel = viewModelProvider.get(MainViewModel.class);

        mainViewModel.getApps().observe(this, new Observer<List<AppModel>>() {
            @Override
            public void onChanged(List<AppModel> appModels) {
                swipeRefreshLayout.setRefreshing(false);
                adapter = new AppListAdapter(getApplicationContext(), appModels);
                listView.setAdapter(adapter);
                dialog.dismissDialog();
            }
        });

        mainViewModel.isScanningFolders().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isScanning) {
                if (!isScanning){
                    dialog.setMax(mainViewModel.getTotalAppCount());
                    dialog.setIntermediate(false);
                } else {
                    dialog.setIntermediate(true);
                }
            }
        });

        mainViewModel.getLoadingDialogModels().observe(this, new Observer<LoadingDialogModel>() {
            @Override
            public void onChanged(LoadingDialogModel loadingDialogModel) {

                if (mainViewModel.isScanning()) {
                    dialog.setText(String.format(Locale.getDefault(),"scanning folders : %s\nfound : %d",
                            loadingDialogModel.getName(), loadingDialogModel.getCount()));
                } else {
                    dialog.setProgress(loadingDialogModel.getCount());
                    dialog.setText(loadingDialogModel.getName());
                }
            }
        });

        setupListeners();

        /* request runtime permission */
        if (JcUtils.isReadPermissionGranted(this)) {
            loadApps();
        } else {
            JcUtils.requestReadStoragePermission(this);
        }
    }

    private void loadApps(){
        swipeRefreshLayout.setRefreshing(true);
        dialog.show();
        mainViewModel.loadApps(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_READ_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadApps();
            } else {
                JcUtils.requestReadStoragePermission(this);
            }
        }
    }

    private void setupListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() { loadApps(); }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              handleListViewItemClick (position);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) { }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listIsAtTop(firstVisibleItem) && !swipeRefreshLayout.isEnabled()) {
                    swipeRefreshLayout.setEnabled(true);
                } else if (swipeRefreshLayout.isEnabled()){
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });
    }

    private void handleListViewItemClick(int position) {
        List<AppModel> appModels = mainViewModel.getApps().getValue();
        if (appModels != null) {
            JcUtils.installApk(MainActivity.this, appModels.get(position).getPath());
        }
    }

    private boolean listIsAtTop(int firstVisibleItem)   {
        if(firstVisibleItem == 0 && listView.getChildCount() == 0) return true;
        return firstVisibleItem == 0 && listView.getChildAt(0).getTop() == 0;
    }


}
