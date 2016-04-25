package com.beaconsucc.beaconcity.ui.activities;

import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.Toast;

import com.beaconsucc.beaconcity.R;
import com.beaconsucc.beaconcity.ui.activities.base.DrawerMenuActivity;
import com.beaconsucc.beaconcity.ui.fragments.AboutFragment;
import com.beaconsucc.beaconcity.ui.fragments.MyLocationFragment;
import com.beaconsucc.beaconcity.ui.fragments.ScanningFragment;
import com.beaconsucc.beaconcity.util.GPSTracker;

public class MainActivity extends DrawerMenuActivity {

	private Fragment mSelectedFragment;

	private double latitude;
	private double longitude;
//	private ArrayList<Place> places;

	public static FragmentManager fragmentManager;

	// GPSTracker class
	GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		Place p1 = Place
//				.initWithAttributes(
//						"Bloque M",
//						"Universidad Catolica de Colombia",
//						"Fundado por Francisco de Paula Santander en el año de 1823 es el museo más antiguo de Colombia; dese 1948 el museo viene ocupando el edificio conocido antiguamente como Panóptico de Cundinamarca que fue construido en 1875 como cárcel por el arquitecto Thomas Reed.",
//						4.63503, -74.0686, 2, 1, 1, "", "");
//		Place p2 = Place
//				.initWithAttributes(
//						"Bloque U",
//						"Universidad Catolica de Colombia",
//						"El Museo del Oro del Banco de la República de Colombia es una institución abierta al público cuya finalidad es la adquisición, conservación y exposición de piezas de orfebrería y alfarería del periodo precolombino de culturas en el territorio de la actual Colombia. ",
//						4.63505, -74.06873, 2, 1, 1, "", "");
//		places = new ArrayList<Place>();
//		places.add(p1);
//		places.add(p2);

		mSelectedFragment = ScanningFragment.newInstance();
		fragmentManager = getSupportFragmentManager();

		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mSelectedFragment)
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
		getLocation();
	}

	@Override
	public void selectItem(int idDrawerMenuOption) {
		super.selectItem(idDrawerMenuOption);

		Fragment fragment = null;
		switch (idDrawerMenuOption) {
		case 0:
			fragment = ScanningFragment.newInstance();
			break;

		case 1:
			fragment = MyLocationFragment.newInstance(latitude, longitude);
			break;
		case 2:
			fragment = AboutFragment.newInstance();
			break;

		default:
			break;
		}
		if (fragment != null) {
			getFragmentManager().beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
		}
	}

	private void getLocation() {

		gps = new GPSTracker(this);
		// check if GPS enabled
		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	private void disableBluetooth() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		if (mBluetoothAdapter != null) {
			if (mBluetoothAdapter.isEnabled()) {
				mBluetoothAdapter.disable();
				Toast.makeText(this, "El Bluetooth ha sido desactivado",
						Toast.LENGTH_LONG).show();
			}
		}

	}
}
