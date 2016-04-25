package com.beaconsucc.beaconcity.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class Place {

	private String name;
	private String address;
	private String description;
	private double latitude;
	private double longitude;
	private int minor;
	private int categoryId;
	private int cityId;
	private String urlLarge;
	private String urlSmall;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getUrlLarge() {
		return urlLarge;
	}

	public void setUrlLarge(String urlLarge) {
		this.urlLarge = urlLarge;
	}

	public String getUrlSmall() {
		return urlSmall;
	}

	public void setUrlSmall(String urlSmall) {
		this.urlSmall = urlSmall;
	}

	public static Place initWithAttributes(String name, String address,
			String description, double latitude, double longitude, int minor,
			int categoryId, int cityId, String urlLarge, String urlSmall) {
		Place u = new Place();
		u.setName(name);
		u.setAddress(address);
		u.setDescription(description);
		u.setLatitude(latitude);
		u.setLongitude(longitude);
		u.setMinor(minor);
		u.setCategoryId(categoryId);
		u.setCityId(cityId);
		u.setUrlLarge(urlLarge);
		u.setUrlSmall(urlSmall);
		return u;
	}

	public static Place initWithAttributes(JSONObject jo, int major, int minor)
			throws JSONException {
		Place u = new Place();
		u.setName(jo.getString("name"));
		u.setAddress(jo.getString("address"));
		u.setDescription(jo.getString("description"));
		u.setLatitude(jo.getDouble("latitude"));
		u.setLongitude(jo.getDouble("longitude"));
		u.setMinor(minor);
		u.setCategoryId(major);
		u.setCityId(jo.getInt("city_id"));
		u.setUrlLarge(jo.getString("url_large"));
		u.setUrlSmall(jo.getString("url_small"));
		return u;
	}
	
	public static Place initWithAttributes(JSONObject jo)
			throws JSONException {
		Place u = new Place();
		u.setName(jo.getString("name"));
		u.setAddress(jo.getString("address"));
		u.setDescription(jo.getString("description"));
		u.setLatitude(jo.getDouble("latitude"));
		u.setLongitude(jo.getDouble("longitude"));
		u.setCityId(jo.getInt("city_id"));
		u.setUrlLarge(jo.getString("url_large"));
		u.setUrlSmall(jo.getString("url_small"));
		return u;
	}
}
