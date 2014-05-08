package com.ax003d.hometag.models;

public class Device {
	private String mac;
	private String val;
	private String ts;

	public Device(String mac, String val, String ts) {
		this.setMac(mac);
		this.setVal(val);
		this.setTs(ts);
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
}
