package com.jcsamples.installkit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.jcsamples.installkit.R;
import com.jcsamples.installkit.models.AppModel;

import java.util.List;

public class AppListAdapter extends BaseAdapter
{
	private Context context;
	private List<AppModel> appModels;
	
	public AppListAdapter (Context context, List<AppModel> appModels){
		this.context = context;
		this.appModels = appModels;
	}
	
	@Override
	public int getCount()
	{
		return appModels.size();
	}

	@Override
	public Object getItem(int item)
	{	
		return item;
	}

	@Override
	public long getItemId(int id)
	{
		return id;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent)
	{
		if (view == null){
			view = LayoutInflater.from(context).inflate(R.layout.item_app, parent, false);
		}
		
		AppModel appModel = appModels.get(position);
		
		AppCompatImageView ivIcon = view.findViewById(R.id.itemappImageView1);
		AppCompatTextView tvName = view.findViewById(R.id.itemappTvName);
		AppCompatTextView tvVersion = view.findViewById(R.id.itemappTvVersion);

		ivIcon.setImageDrawable(appModel.getIcon());
		tvName.setText(appModel.getAppName());
		tvVersion.setText(appModel.getAppVersion());
		return view;
	}

}
