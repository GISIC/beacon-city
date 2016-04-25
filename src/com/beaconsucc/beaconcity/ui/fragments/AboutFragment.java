package com.beaconsucc.beaconcity.ui.fragments;

import org.altbeacon.beacon.Beacon;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beaconsucc.beaconcity.R;
import com.beaconsucc.beaconcity.application.BeaconCityApplication;
import com.beaconsucc.beaconcity.application.Core;
import com.beaconsucc.beaconcity.domain.Place;
import com.beaconsucc.beaconcity.util.GPSTracker;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

public class AboutFragment extends Fragment {

	private TextView mLabName;
	private TextView mLabDescription;
	private TextView mLabAddress;
	private ImageView mImgPlace;

	public static AboutFragment newInstance() {
		AboutFragment f = new AboutFragment();
		Bundle args = new Bundle();
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_about, container,
				false);

//		mLabName = (TextView) rootView.findViewById(R.id.lab_name);
//		mLabDescription = (TextView) rootView
//				.findViewById(R.id.lab_description);
//		mLabAddress = (TextView) rootView.findViewById(R.id.lab_address);
//		mImgPlace = (ImageView) rootView.findViewById(R.id.img_place);

		return rootView;
	}

}
