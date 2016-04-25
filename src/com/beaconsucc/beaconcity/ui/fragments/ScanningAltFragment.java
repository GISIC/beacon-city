package com.beaconsucc.beaconcity.ui.fragments;

import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.beaconsucc.beaconcity.R;
import com.beaconsucc.beaconcity.util.IntentHelper;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;
import com.estimote.sdk.utils.L;

public class ScanningAltFragment extends Fragment {

	protected static final String TAG = "ScanningFragment";

	private Region ALL_ESTIMOTE_BEACONS;

	private BeaconManager beaconManager;
	
	private TextView mLabBeacon;
	private RecyclerView mRecyclerView;

	public static ScanningAltFragment newInstance() {
		ScanningAltFragment f = new ScanningAltFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Configure BeaconManager.
		beaconManager = new BeaconManager(getActivity());

		verifyBluetooth();

		// Configure verbose debug logging.
		L.enableDebugLogging(true);

		ALL_ESTIMOTE_BEACONS = new Region("regionId", "B9407F30-F5F8-466E-AFF9-25556B57FE6D", null, null);
	}

	private void verifyBluetooth() {
		try {
			if (beaconManager.hasBluetooth()) {
				if (!beaconManager.isBluetoothEnabled()) {
					final AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle("Bluetooth desactivado");
					builder.setMessage("Desea activar el Bluetooth?");
					builder.setPositiveButton("OK", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							enableBluetooth();
						}
					});
					builder.setNegativeButton("No", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});
					builder.setCancelable(false);
					builder.show();
				}
			} else {
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("Bluetooth LE no disponible!");
				builder.setMessage("Este dispositivo no soporta Bluetooth LE.");
				builder.setPositiveButton(android.R.string.ok,
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
				builder.show();
			}
		} catch (RuntimeException e) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(
					getActivity());
			builder.setTitle("Bluetooth LE no disponible");
			builder.setMessage("Este dispositivo no soporta Bluetooth LE.");
			builder.setPositiveButton(android.R.string.ok,
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});
			builder.show();

		}
	}

	private void enableBluetooth() {
		if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
			BluetoothAdapter.getDefaultAdapter().enable();
			Toast.makeText(getActivity(), "El Bluetooth ha sido activado",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_scanning, container,
				false);
		
		mLabBeacon = (TextView) rootView.findViewById(R.id.lab_scanning);

		if (beaconManager.hasBluetooth()) {
			beaconManager
					.setRangingListener(new BeaconManager.RangingListener() {
						@Override
						public void onBeaconsDiscovered(Region region,
								List<Beacon> beacons) {
							Log.d(TAG, "Found beacons: " + beacons.size());

							if (beacons.size() > 0) {
								Beacon closestBeacon = beacons.get(0);

								switch (Utils.computeProximity(closestBeacon)) {
								case FAR:
									Log.d(TAG, "LEJOS");
									break;

								case NEAR:
									Log.d(TAG, "CERCA");
//									getActivity().getFragmentManager().beginTransaction()
//									.replace(R.id.content_frame, DetailFragment.newInstance(closestBeacon)).commit();
									break;

								case IMMEDIATE:
									Log.d(TAG, "INMEDIATO");		
									break;

								case UNKNOWN:
									Log.d(TAG, "DESCONOCIDO");
									break;

								default:
									break;
								}

							}

						}
					});
		}

		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();

		if(beaconManager.hasBluetooth()){
			beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
				@Override
				public void onServiceReady() {
					try {
						beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
					} catch (RemoteException e) {
						Log.e(TAG, "Cannot start ranging", e);
					}
				}
			});
		}
		
	}

	@Override
	public void onStop() {

		if(beaconManager.hasBluetooth()){
			try {

				beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
			} catch (RemoteException e) {
				Log.e(TAG, "Cannot stop but it does not matter now", e);
			}
		}
		
		super.onStop();
	}

	@Override
	public void onDestroy() {

		if(beaconManager.hasBluetooth()){
			// When no longer needed. Should be invoked in #onDestroy.
			beaconManager.disconnect();

//			if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
//				BluetoothAdapter.getDefaultAdapter().disable();
//				Toast.makeText(getActivity(), "El Bluetooth ha sido desactivado",
//						Toast.LENGTH_SHORT).show();
//			}
		}
		
		super.onDestroy();
	}

}
