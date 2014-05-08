package com.ax003d.hometag;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.ax003d.hometag.adapters.DeviceAdapter;
import com.ax003d.hometag.events.Scan;
import com.ax003d.hometag.services.AcquisitionService;
import com.ax003d.hometag.utils.Utils;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

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
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		private OnClickListener onClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.btn_control_service) {
					Intent intent = new Intent(getActivity(),
							AcquisitionService.class);
					if (Utils.isServiceRunning(getActivity(),
							AcquisitionService.class.getName())) {
						getActivity().stopService(intent);
						btn_control_service.setText("Start");
					} else {
						getActivity().startService(intent);
						btn_control_service.setText("Stop");
					}
					return;
				}
				
				if (v.getId() == R.id.btn_scan) {
					Utils.getBus().post(new Scan());
				}
			}
		};
		private Button btn_control_service;
		private ListView lst_dev;
		private DeviceAdapter adapter;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			btn_control_service = (Button) rootView
					.findViewById(R.id.btn_control_service);
			btn_control_service.setOnClickListener(onClickListener);
			if (Utils.isServiceRunning(getActivity(),
					AcquisitionService.class.getName())) {
				btn_control_service.setText("Stop");
			} else {
				btn_control_service.setText("Start");
			}
			
			rootView.findViewById(R.id.btn_scan).setOnClickListener(onClickListener);
			
			lst_dev = (ListView) rootView.findViewById(R.id.lst_dev);
			adapter = new DeviceAdapter();
			lst_dev.setAdapter(adapter);
			
			return rootView;
		}
		
		@Override
		public void onResume() {
			super.onResume();
			Utils.getBus().register(this);
		}
		
		@Override
		public void onPause() {
			super.onPause();
			Utils.getBus().unregister(this);
		}
	}

}
