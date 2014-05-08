package com.ax003d.hometag.events;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DataRead {
	public String mac;
	public String val;
	public String ts;

	public DataRead(String mac, String val) {
		this.mac = mac;
		this.val = val;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA);
		this.ts = format.format(c.getTime());
	}
}
