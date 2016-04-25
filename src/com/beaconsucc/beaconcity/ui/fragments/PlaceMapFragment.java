package com.beaconsucc.beaconcity.ui.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beaconsucc.beaconcity.domain.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlaceMapFragment extends MapFragment {

	private double currentLatitude;
	private double currentLongitude;
	private ArrayList<Place> places;

	private GoogleMap mMap;

	// private LatLng mTheaterLocation;
	// private Theater mTheater;

	public PlaceMapFragment() {
	}

	public static PlaceMapFragment newInstance(double latitude,
			double longitude, ArrayList<Place> places) {
		PlaceMapFragment f = new PlaceMapFragment();
		Bundle args = new Bundle();
		f.currentLatitude = latitude;
		f.currentLongitude = longitude;
		f.places = places;
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState == null) {
			// First incarnation of this activity.
			setRetainInstance(true);
		} else {
			mMap = getMap();
		}
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			mMap = getMap();

			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				mMap.setMyLocationEnabled(true);
				mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

				setupPlaceLocation();
			}
		}
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

	// private void setupTheaterLocation() {
	// if (mTheater != null) {
	// if (mTheaterLocation == null) {
	// mTheaterLocation = new LatLng(Double.parseDouble(mTheater
	// .getLatitude()), Double.parseDouble(mTheater
	// .getLongitude()));
	// }
	//
	// mMap.addMarker(new MarkerOptions().position(mTheaterLocation)
	// .snippet(mTheater.getAddress()).title(mTheater.getName()));
	//
	// if (mMap.getMyLocation() != null) {
	//
	// LatLng userLocation = new LatLng(mMap.getMyLocation()
	// .getLatitude(), mMap.getMyLocation().getLongitude());
	// LatLng midPoint = Util.getMidPoint(userLocation,
	// mTheaterLocation);
	//
	// if (midPoint != null) {
	// CameraPosition cameraPosition = new CameraPosition.Builder()
	// .target(midPoint).zoom(14).bearing(90).tilt(30)
	// .build();
	// mMap.animateCamera(CameraUpdateFactory
	// .newCameraPosition(cameraPosition));
	// }
	// } else {
	// CameraPosition cameraPosition = new CameraPosition.Builder()
	// .target(mTheaterLocation).zoom(14).bearing(90).tilt(30)
	// .build();
	// mMap.animateCamera(CameraUpdateFactory
	// .newCameraPosition(cameraPosition));
	// }
	// }
	// }
}
