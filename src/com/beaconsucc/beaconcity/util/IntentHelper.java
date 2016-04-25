package com.beaconsucc.beaconcity.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.beaconsucc.beaconcity.ui.activities.MainActivity;

public class IntentHelper {

	// concrete methods

	public static void goToMainActivity(Activity activity) {
		launchIntent(activity, MainActivity.class,
				Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
	}
	
//	public static void goToLoginActivity(Activity activity) {
//		launchIntent(activity, LoginActivity.class,
//				Intent.FLAG_ACTIVITY_CLEAR_TOP
//						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
//	}
//	
//	public static void goToRememberActivity(Activity activity) {
//		launchIntent(activity, RememberActivity.class,
//				Intent.FLAG_ACTIVITY_CLEAR_TOP
//						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
//	}
//	
//	public static void goToDataSyncActivity(Activity activity) {
//		launchIntent(activity, DataSyncActivity.class,
//				Intent.FLAG_ACTIVITY_CLEAR_TOP
//						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
//	}
//	
//	public static void goToErrorSyncActivity(Activity activity) {
//		launchIntent(activity, ErrorSyncActivity.class,
//				Intent.FLAG_ACTIVITY_CLEAR_TOP
//						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
//	}
//	
//	public static void goToErrorDetailActivity(Activity activity, SyncError syncError) {
//		Bundle args = new Bundle();
//		args.putSerializable("syncError", syncError);
//		launchIntent(activity, ErrorDetailActivity.class,
//				Intent.FLAG_ACTIVITY_CLEAR_TOP
//						| Intent.FLAG_ACTIVITY_SINGLE_TOP, args);
//	}
//	
//	public static void goToEventDetailActivity(Activity activity, Gang gang, Schedule schedule) {
//		Bundle args = new Bundle();
//		args.putSerializable("gang", gang);
//		args.putSerializable("schedule", schedule);
//		launchIntent(activity, EventDetailActivity.class,
//				Intent.FLAG_ACTIVITY_CLEAR_TOP
//						| Intent.FLAG_ACTIVITY_SINGLE_TOP, args);
//	}
//	
//	public static void goToGangScheduleActivity(Activity activity, Gang gang) {
//		Bundle args = new Bundle();
//		args.putSerializable("gang", gang);
//		launchIntent(activity, SchedulesActivity.class,
//				Intent.FLAG_ACTIVITY_CLEAR_TOP
//						| Intent.FLAG_ACTIVITY_SINGLE_TOP, args);
//	}
//	
//	public static void goToEventScheduleActivity(Activity activity, Gang gang) {
//		Bundle args = new Bundle();
//		args.putSerializable("gang", gang);
//		launchIntent(activity, EventSchedulesActivity.class,
//				Intent.FLAG_ACTIVITY_CLEAR_TOP
//						| Intent.FLAG_ACTIVITY_SINGLE_TOP, args);
//	}
//	
//	public static void goToEventResumeActivity(Activity activity, Gang gang) {
//		Bundle args = new Bundle();
//		args.putSerializable("gang", gang);
//		launchIntent(activity, EventResumeActivity.class,
//				Intent.FLAG_ACTIVITY_CLEAR_TOP
//						| Intent.FLAG_ACTIVITY_SINGLE_TOP, args);
//	}
//
//	public static void goToPasswordChangeActivity(Activity activity) {
//		launchIntent(activity, PasswordChangeActivity.class,
//				Intent.FLAG_ACTIVITY_CLEAR_TOP
//						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
//	}
//
//
	// generic methods

	public static <T> void launchIntent(Activity activity, Class<T> className) {
		launchIntent(activity, className, -1, null);
	}

	public static <T> void launchIntent(Activity activity, Class<T> className,
			int flags) {
		launchIntent(activity, className, flags, null);
	}

	public static <T> void launchIntent(Activity activity, Class<T> className,
			int flags, Bundle extras) {
		Intent intent = new Intent(activity, className);

		if (flags >= 0)
			intent.setFlags(flags);

		if (extras != null)
			intent.putExtras(extras);

		activity.startActivity(intent);
	}

}
