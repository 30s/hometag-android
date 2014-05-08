package com.ax003d.hometag.services;

import java.util.UUID;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.xtremeprog.sdk.ble.BleGattCharacteristic;
import com.xtremeprog.sdk.ble.BleGattService;
import com.xtremeprog.sdk.ble.BleService;
import com.xtremeprog.sdk.ble.IBle;

public class AcquisitionService extends Service {
	private static final String DEVICE = "88:33:14:DD:8D:63";
	private static final String TAG = "AcquisitionService";
	protected BleService mService;
	private IBle mBle;

	private static final UUID TH_SERVICE = UUID
			.fromString("0000AA20-0000-1000-8000-00805F9B34FB");
	private static final UUID TH_CHARACTERISTIC = UUID
			.fromString("0000AA21-0000-1000-8000-00805F9B34FB");

	private final BroadcastReceiver mBleReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle extras = intent.getExtras();
			String action = intent.getAction();
			if (!DEVICE.equals(extras.getString(BleService.EXTRA_ADDR))) {
				return;
			}

			if (BleService.BLE_GATT_CONNECTED.equals(action)) {
				Log.d(TAG, "connected");
			} else if (BleService.BLE_SERVICE_DISCOVERED.equals(action)) {
				Log.d(TAG, "service discovered");
				BleGattService service = mBle.getService(DEVICE, TH_SERVICE);
				assert (service != null);
				BleGattCharacteristic characteristic = service.getCharacteristic(TH_CHARACTERISTIC);
				assert (characteristic != null);
				mBle.requestCharacteristicNotification(DEVICE, characteristic);
			} else if (BleService.BLE_GATT_DISCONNECTED.equals(action)) {
				Log.d(TAG, "gatt disconnected");
			} else if (BleService.BLE_CHARACTERISTIC_CHANGED.equals(action)) {
				byte[] val = extras.getByteArray(BleService.EXTRA_VALUE);
				Log.d(TAG, val[0] + "c " + val[2] + "%");
			}
		}
	};

	private final ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className,
				IBinder rawBinder) {
			mService = ((BleService.LocalBinder) rawBinder).getService();
			mBle = mService.getBle();
			// TODO： send message to enable bluetooth
			registerReceiver(mBleReceiver, BleService.getIntentFilter());
			mBle.requestConnect(DEVICE);
		}

		@Override
		public void onServiceDisconnected(ComponentName classname) {
			unregisterReceiver(mBleReceiver);
			mService = null;
		}
	};

	@Override
	public void onCreate() {
		Intent bindIntent = new Intent(this, BleService.class);
		bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
		// If we get killed, after returning from here, restart
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
		mBle.disconnect(DEVICE);
	}
}
