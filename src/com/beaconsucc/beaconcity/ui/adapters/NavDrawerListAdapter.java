package com.beaconsucc.beaconcity.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beaconsucc.beaconcity.R;

public class NavDrawerListAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;

	private String mNavTitles[]; // String Array to store the passed titles
									// Value from MainActivity.java
	private int mIcons[];

	public NavDrawerListAdapter(Context context, String titles[], int icons[]) {
		this.mContext = context;
		this.mNavTitles = titles;
		this.mIcons = icons;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mNavTitles.length;
	}

	@Override
	public Object getItem(int position) {
		return mNavTitles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = mInflater.inflate(R.layout.item_drawer_menu, null);
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.img_icon);
		TextView labTitle = (TextView) convertView.findViewById(R.id.lab_text);

		imgIcon.setImageResource(mIcons[position]);

		labTitle.setText(mNavTitles[position]);

		return convertView;
	}

}