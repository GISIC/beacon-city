package com.beaconsucc.beaconcity.ui.fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BleNotAvailableException;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import android.app.AlertDialog;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beaconsucc.beaconcity.R;
import com.beaconsucc.beaconcity.ui.adapters.BeaconsListAdapter;

public class ScanningFragment extends Fragment implements BeaconConsumer, OnItemClickListener {

	protected static final String TAG = "ScanningFragment";

	public static Region mRegion = new Region("BeaconCity",
			Identifier.parse("2F234454-CF6D-4A0F-ADF2-F4911BA9FFA6"), null,
			null);
	private BeaconManager mBeaconManager;

//	private TextView mLabBeacon;
	private TextView mLabScanning;
	private BeaconsListAdapter mAdapter;
	private ArrayList<Beacon> mDataSource;
	private ListView mListView;

//	private int minor;
//	private int major;

	public static ScanningFragment newInstance() {
		ScanningFragment f = new ScanningFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mBeaconManager = BeaconManager.getInstanceForApplication(getActivity());
		
		//BEACON PARSER
        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
		
		mAdapter = new BeaconsListAdapter(getActivity());
	}

	private void verifyBluetooth() {
		try {
			if (!mBeaconManager.checkAvailability()) {
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
		} catch (BleNotAvailableException e) {
			mLabScanning.setText("");
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

		mLabScanning = (TextView) rootView.findViewById(R.id.lab_scanning);
		mListView = (ListView) rootView.findViewById(R.id.list_places);
		mListView.setOnItemClickListener(this);
		
		mDataSource = new ArrayList<Beacon>();
		
		//Set Adapter
        mListView.setAdapter(mAdapter);

//		minor = 0;
//		major = 0;

//		mLabBeacon = (TextView) rootView.findViewById(R.id.lab_scanning);

		// Check for bluetooth and Scan for Beacon
		verifyBluetooth();

		// Start Monitoring and Ranging
		mBeaconManager.bind(this);

		return rootView;
	}

	@Override
	public void onBeaconServiceConnect() {
		try {
			// Scan lasts for SCAN_PERIOD time
			mBeaconManager.setForegroundScanPeriod(5000l);
			// mBeaconManager.setBackgroundScanPeriod(0l);
			// Wait every SCAN_PERIOD_INBETWEEN time
			mBeaconManager.setForegroundBetweenScanPeriod(0l);
			// Update default time with the new one
			mBeaconManager.updateScanPeriods();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		//Set Monitoring
        mBeaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.d("TEST", "ENTERED beacon region");
                //Start Raning as soon as you detect a beacon
                try {
                    mBeaconManager.startRangingBeaconsInRegion(mRegion);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didExitRegion(Region region) {
                Log.d("TEST", "EXITED beacon region");
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.d("TEST", "SWITCHED from seeing/not seeing beacon to state " + state);
            }
        });

		// Set Ranging
		mBeaconManager.setRangeNotifier(new RangeNotifier() {
			@Override
			public void didRangeBeaconsInRegion(
					final Collection<Beacon> beacons, Region region) {
				if (beacons != null && beacons.size() > 0) {
					Log.d("BEEEEEAAAACOOOONS", "# " + beacons.size());
					// here you check the value of getActivity() and break up if needed
			        if(getActivity() == null)
			            return;
					showBeaconList(beacons);
//					mDataSource = new ArrayList<Beacon>(beacons);
//					mAdapter = new BeaconsListAdapter(getActivity(), beacons);
//					showBeaconList();
				}
			}
		});

		try {
			//Start Monitoring
            mBeaconManager.startMonitoringBeaconsInRegion(mRegion);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
	
	protected void showBeaconList(final Collection<Beacon> beacons) {
		getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	mDataSource.clear();
                mDataSource.addAll(beacons);
              //Sort 
                Collections.sort(mDataSource, new Comparator<Beacon>() {
                    @Override
                    public int compare(Beacon b1, Beacon b2) {
                        String mac1 = b1.getBluetoothAddress();
                        String mac2 = b2.getBluetoothAddress();

                        return mac1.compareTo(mac2);
                    }
                });
                mAdapter.initAll(beacons);
            }
        });
	}


	protected void showBeaconDetails(final Beacon closestBeacon) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				getActivity()
						.getFragmentManager()
						.beginTransaction()
						.replace(R.id.content_frame,
								DetailFragment.newInstance(closestBeacon))
						.addToBackStack(null).commit();

			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		// if (mBeaconManager.isBound(this))
		// mBeaconManager.setBackgroundMode(true);
		if (mBeaconManager.checkAvailability()) {
			try {
				mBeaconManager.unbind(this);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		// if (mBeaconManager.isBound(this))
		// mBeaconManager.setBackgroundMode(false);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mBeaconManager.checkAvailability()) {
			try {
				mBeaconManager.unbind(this);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Override
	public Context getApplicationContext() {
		return getActivity().getApplicationContext();
	}

	@Override
	public void unbindService(ServiceConnection serviceConnection) {
		getActivity().unbindService(serviceConnection);
	}

	@Override
	public boolean bindService(Intent intent,
			ServiceConnection serviceConnection, int mode) {
		return getActivity().bindService(intent, serviceConnection, mode);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		showBeaconDetails(mDataSource.get(position));
	}

}
