package com.beaconsucc.beaconcity.ui.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import org.altbeacon.beacon.Beacon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beaconsucc.beaconcity.R;

public class BeaconsListAdapter extends BaseAdapter {
	
	String CATEGORIES[] = { "Otro","Bloque UCC", "Museo"};
	String TITLES[] = { "","Bloque M", "Bloque U", "Bloque O" };

	private Context mContext;
	private LayoutInflater mInflater;

	private ArrayList<Beacon> mDataSource;

	public BeaconsListAdapter(Context context) {
		this.mContext = context;
		this.mDataSource = new ArrayList<Beacon>();
		this.mInflater = LayoutInflater.from(context);
	}
	
	public void initAll(Collection<Beacon> newBeacons) {
        this.mDataSource.clear();
        this.mDataSource.addAll(newBeacons);
        
      //Sort 
        Collections.sort(this.mDataSource, new Comparator<Beacon>() {
            @Override
            public int compare(Beacon b1, Beacon b2) {
                String mac1 = b1.getBluetoothAddress();
                String mac2 = b2.getBluetoothAddress();

                return mac1.compareTo(mac2);
            }
        });
        notifyDataSetChanged();
    }

	@Override
	public int getCount() {
		return mDataSource.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.item_beacon, parent, false);
			holder = new ViewHolder();
			holder.labPlace = (TextView) convertView
					.findViewById(R.id.lab_place);
			holder.labCategory = (TextView) convertView
					.findViewById(R.id.lab_category);
			holder.labDistance = (TextView) convertView
					.findViewById(R.id.lab_distance);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Beacon b = mDataSource.get(position);
		if (b != null) {
			int title = b.getId3().toInt();
			if(title<TITLES.length){
				holder.labPlace.setText(TITLES[title]);
			}else{
				holder.labPlace.setText(mContext.getString(R.string.lab_place_unknown));
			}
			
			int cat = b.getId2().toInt();
			if(cat<CATEGORIES.length){
				holder.labCategory.setText(CATEGORIES[cat]);
			}else{
				holder.labCategory.setText(CATEGORIES[0]);
			}
			
			holder.labDistance.setText("Distancia aprox: "+String.format("%.2f", b.getDistance())+" metros");
			// if (category != null) {
			// Picasso.with(mContext)
			// .load(SharedPreferencesHelper
			// .loadString(SharedPreferencesHelper.PARAM_SERVER_URL)
			// + category.getString("IconUrl")).fit()
			// .into(holder.imgCategory);
			// }
		}
		return convertView;
	}

	static class ViewHolder {
		TextView labPlace;
		TextView labCategory;
		TextView labDistance;
	}

}