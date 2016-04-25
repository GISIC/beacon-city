package com.beaconsucc.beaconcity.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;

import com.beaconsucc.beaconcity.R;
import com.beaconsucc.beaconcity.util.IntentHelper;
//import android.view.WindowManager;

public class SplashScreenActivity extends ActionBarActivity {

	private static long SPLASH_MILLIS = 1500;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_splash_screen);

		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				IntentHelper.goToMainActivity(SplashScreenActivity.this);
				finish();
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);

			}

		}, SPLASH_MILLIS);
	}
}
