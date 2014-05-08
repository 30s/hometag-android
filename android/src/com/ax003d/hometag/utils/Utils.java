package com.ax003d.hometag.utils;

import com.squareup.otto.Bus;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class Utils {

	private static Bus bus;

	public static boolean isServiceRunning(Context context, String serviceName) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceName.equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	public static Bus getBus() {
		if (bus == null) {
			bus = new Bus();
		}
		return bus;
	}
}
