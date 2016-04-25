package com.beaconsucc.beaconcity.ui.fragments;

import java.util.ArrayList;

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
import android.widget.Toast;

import com.beaconsucc.beaconcity.R;
import com.beaconsucc.beaconcity.application.BeaconCityApplication;
import com.beaconsucc.beaconcity.application.Core;
import com.beaconsucc.beaconcity.domain.Place;
import com.beaconsucc.beaconcity.ui.activities.MainActivity;
import com.beaconsucc.beaconcity.util.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MyLocationFragment extends Fragment {

	ProgressDialog progressBar;
	
	private double currentLatitude;
	private double currentLongitude;
	private ArrayList<Place> places;

	// GPSTracker class
	GPSTracker gps;

	private static GoogleMap mMap;

	public static MyLocationFragment newInstance(double latitude,
			double longitude) {
		MyLocationFragment f = new MyLocationFragment();
		Bundle args = new Bundle();
		f.currentLatitude = latitude;
		f.currentLongitude = longitude;
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_mylocation,
				container, false);
		
		progressBar = new ProgressDialog(getActivity());
		progressBar.setCancelable(false);
		progressBar.setMessage(getResources().getString(R.string.msg_loading));
		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		
		setUpMapIfNeeded();
		getPlaces();

		return rootView;
	}
	
	/***** Sets up the map if it is possible to do so *****/
	public static void setUpMapIfNeeded() {
	    // Do a null check to confirm that we have not already instantiated the map.
	    if (mMap == null) {
	        // Try to obtain the map from the SupportMapFragment.
	        mMap = ((SupportMapFragment) MainActivity.fragmentManager
	                .findFragmentById(R.id.map)).getMap();
	        // Check if we were successful in obtaining the map.
	        if (mMap != null)
	            setUpMap();
	    }
	}
	
	/**
	 * This is where we can add markers or lines, add listeners or move the
	 * camera.
	 * <p>
	 * This should only be called once and when we are sure that {@link #mMap}
	 * is not null.
	 */
	private static void setUpMap() {
	    // For showing a move to my loction button
	    mMap.setMyLocationEnabled(true);
	    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	    // For dropping a marker at a point on the Map
//	    mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("My Home").snippet("Home Address"));
	    // For zooming automatically to the Dropped PIN Location
//	    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
//	            longitude), 12.0f));
	}
	
	private void setupPlaceLocation() {
		if (mMap.getMyLocation() != null) {
			LatLng userLocation = new LatLng(
					mMap.getMyLocation().getLatitude(), mMap.getMyLocation()
							.getLongitude());

			if (userLocation != null) {
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(userLocation).zoom(14).bearing(90).tilt(30)
						.build();
				mMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));

			}
		} else {
			LatLng userLocation = new LatLng(currentLatitude, currentLongitude);
			if (userLocation != null) {
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(userLocation).zoom(14).bearing(90).tilt(30)
						.build();
				mMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));

			}
		}

		if (places != null) {
			for (int i = 0; i < places.size(); i++) {
				LatLng placeLocation = new LatLng(places.get(i).getLatitude(),
						places.get(i).getLongitude());
				mMap.addMarker(new MarkerOptions().position(placeLocation)
						.snippet(places.get(i).getAddress())
						.title(places.get(i).getName()));
			}
		}

	}
	
	private void getPlaces() {
		progressBar.show();
		places = new ArrayList<Place>();
		BeaconCityApplication
				.getInstance()
				.getRestClient()
				.get(Core.rootUrl + "/places.json",
						new JsonHttpResponseHandler() {
							@Override
							public void onSuccess(int statusCode,
									Header[] headers, JSONArray response) {
								progressBar.dismiss();
								Log.v("RESP", ""+response.toString());
								if (HttpStatus.SC_OK == statusCode) {
									// parse response
									try {
										for (int i = 0; i < response.length(); i++) {
											JSONObject jo = response
													.getJSONObject(i);
											Place u = Place.initWithAttributes(jo);
											places.add(u);
										}
									}catch (JSONException e) {
										e.printStackTrace();
									}
									progressBar.dismiss();
									setupPlaceLocation();
								}
							}
							

							@Override
							public void onFailure(int statusCode,
									Header[] headers, Throwable throwable,
									JSONObject errorResponse) {
								progressBar.dismiss();
								Toast.makeText(getActivity(),
										R.string.msg_connection_server_error,
										Toast.LENGTH_LONG).show();
							}

							@Override
							public void onFailure(int statusCode,
									Header[] headers, String responseString,
									Throwable throwable) {
								progressBar.dismiss();
								Toast.makeText(getActivity(),
										R.string.msg_connection_server_error,
										Toast.LENGTH_LONG).show();
							}
						});

	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    if (mMap != null)
	        setUpMap();

	    if (mMap == null) {
	        // Try to obtain the map from the SupportMapFragment.
	        mMap = ((SupportMapFragment) MainActivity.fragmentManager
	                .findFragmentById(R.id.map)).getMap(); // getMap is deprecated
	        // Check if we were successful in obtaining the map.
	        if (mMap != null)
	            setUpMap();
	    }
	}

	/**** The mapfragment's id must be removed from the FragmentManager
	 **** or else if the same it is passed on the next time then 
	 **** app will crash ****/
	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    if (mMap != null) {
	        MainActivity.fragmentManager.beginTransaction()
	            .remove(MainActivity.fragmentManager.findFragmentById(R.id.map)).commit();
	        mMap = null;
	    }
	}
	

}
