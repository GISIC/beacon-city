package com.beaconsucc.beaconcity.application;

import android.app.Application;

import com.loopj.android.http.AsyncHttpClient;

public class BeaconCityApplication extends Application {

	private static final int TIME_OUT = 1000 * 10;

	private static BeaconCityApplication sInstance;
	private AsyncHttpClient mRestClient;

	public BeaconCityApplication() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		sInstance = this;
		mRestClient = new AsyncHttpClient();
		mRestClient.setTimeout(TIME_OUT);

	}

	public static BeaconCityApplication getInstance() {
		return sInstance;
	}

	public AsyncHttpClient getRestClient() {
		return mRestClient;
	}
}
