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

public class ScanningDetailFragment extends Fragment {

	ProgressDialog progressBar;

	Place p1 = Place
			.initWithAttributes(
					"Museo Nacional",
					"Carrera Séptima entre calles 28 y 29",
					"Fundado por Francisco de Paula Santander en el año de 1823 es el museo más antiguo de Colombia; dese 1948 el museo viene ocupando el edificio conocido antiguamente como Panóptico de Cundinamarca que fue construido en 1875 como cárcel por el arquitecto Thomas Reed.",
					4.615578400000000000, -74.068411400000000000, 1, 1, 1,
					"http://static.panoramio.com/photos/original/2852541.jpg",
					"");
	Place p2 = Place
			.initWithAttributes(
					"Museo del Oro",
					"Carrera 6 # 15-88",
					"El Museo del Oro del Banco de la República de Colombia es una institución abierta al público cuya finalidad es la adquisición, conservación y exposición de piezas de orfebrería y alfarería del periodo precolombino de culturas en el territorio de la actual Colombia. ",
					4.601646500000000000,
					-74.071975100000030000,
					2,
					1,
					1,
					"http://admin.banrepcultural.org/sites/default/files/fachada-museo-oro_3.jpg",
					"");
	Place p3 = Place
			.initWithAttributes(
					"Museo Botero",
					"Calle 11 No. 4-41",
					"El Museo Botero se creó en el año 2000 gracias al maestro Fernando Botero; pintor y escultor colombiano reconocido internacionalmente por sus obras con la representación de personas y animales corpulentas y obesas, el maestro Botero dono una colección de avaluada en más de 200 millones de dólares.",
					4.596760700000000000,
					-74.073145399999990000,
					3,
					1,
					1,
					"http://upload.wikimedia.org/wikipedia/commons/5/5b/Fachada_del_Museo_Botero,_cl_11_con_kr_4_Bogota.JPG",
					"");

	private TextView mLabName;
	private TextView mLabDescription;
	private TextView mLabAddress;
	private ImageView mImgPlace;

	private int minor;
	private int major;
	private String proximity;

	// GPSTracker class
	GPSTracker gps;

	public static ScanningDetailFragment newInstance(Beacon closestBeacon) {
		ScanningDetailFragment f = new ScanningDetailFragment();
		f.minor = closestBeacon.getId3().toInt();
		f.major = closestBeacon.getId2().toInt();
		Bundle args = new Bundle();
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_detail, container,
				false);

		progressBar = new ProgressDialog(getActivity());
		progressBar.setCancelable(false);
		progressBar.setMessage(getResources().getString(R.string.msg_loading));
		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressBar.show();

		mLabName = (TextView) rootView.findViewById(R.id.lab_name);
		mLabDescription = (TextView) rootView
				.findViewById(R.id.lab_description);
		mLabAddress = (TextView) rootView.findViewById(R.id.lab_address);
		mImgPlace = (ImageView) rootView.findViewById(R.id.img_place);

		getPlaceData();

		return rootView;
	}

	private void getPlaceData() {
		Log.v("RESP", "HOLA");
		BeaconCityApplication
				.getInstance()
				.getRestClient()
				.get(Core.rootUrl + "/places/"+major+"/"+minor+".json",
						new JsonHttpResponseHandler() {
							@Override
							public void onSuccess(int statusCode,
									Header[] headers, JSONObject response) {
								progressBar.dismiss();
								Log.v("RESP", ""+response.toString());
								if (HttpStatus.SC_OK == statusCode) {
									// parse response
									Place u = null;
									try {
										u = Place.initWithAttributes(response,
												major, minor);
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									initViews(u);
								}

							}

							@Override
							public void onFailure(int statusCode,
									Header[] headers, Throwable throwable,
									JSONObject errorResponse) {
								progressBar.dismiss();
								Toast.makeText(getActivity(),
										R.string.msg_connection_server_error+throwable.toString(),
										Toast.LENGTH_LONG).show();
							}

							@Override
							public void onFailure(int statusCode,
									Header[] headers, String responseString,
									Throwable throwable) {
								progressBar.dismiss();
								Toast.makeText(getActivity(),
										R.string.msg_connection_server_error+throwable.toString(),
										Toast.LENGTH_LONG).show();
							}
						});

	}

	private void initViews(Place place) {
		if (place != null) {
			mLabName.setText(place.getName());
			mLabDescription.setText(place.getDescription());
			mLabAddress.setText("Dirección: " + place.getAddress());
			Picasso.with(getActivity())
					.load(getAbsoluteUrl(place.getUrlLarge())).fit()
					.into(mImgPlace);
		} else {
			Toast.makeText(getActivity(), R.string.msg_error_no_info,
					Toast.LENGTH_SHORT).show();
		}

	}

	private String getAbsoluteUrl(String relativeUrl) {
		return Core.serverUrl+relativeUrl;
	}
}
