package com.ax003d.hometag.adapters;

import java.util.ArrayList;
import java.util.List;

import com.ax003d.hometag.R;
import com.ax003d.hometag.models.Device;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DeviceAdapter extends BaseAdapter {

	private List<Device> mDevices = new ArrayList<Device>();

	public DeviceAdapter() {
	}

	@Override
	public int getCount() {
		return mDevices.size();
	}

	@Override
	public Object getItem(int position) {
		return mDevices.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup view = (convertView instanceof ViewGroup) ? (ViewGroup) convertView
				: (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(
						R.layout.item_dev, null);

		TextView tv_mac = (TextView) view.findViewById(R.id.tv_mac);
		TextView tv_val = (TextView) view.findViewById(R.id.tv_val);
		TextView tv_ts = (TextView) view.findViewById(R.id.tv_ts);

		Device device = mDevices.get(position);
		tv_mac.setText(device.getMac());
		tv_val.setText(device.getVal());
		tv_ts.setText(device.getTs());

		return view;
	}

	public void addDevice(Device device) {
		for (Device d : mDevices) {
			if (d.getMac().equals(device.getMac())) {
				return;
			}
		}
		mDevices.add(device);
		notifyDataSetChanged();
	}

	public void setDeviceVal(String mac, String val, String ts) {
		for (Device d : mDevices) {
			if (d.getMac().equals(mac)) {
				d.setVal(val);
				d.setTs(ts);
				notifyDataSetChanged();
				break;
			}
		}

	}

}
