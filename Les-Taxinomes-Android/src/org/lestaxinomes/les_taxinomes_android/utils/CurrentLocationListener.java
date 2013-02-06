package org.lestaxinomes.les_taxinomes_android.utils;

import java.util.ArrayList;

import org.lestaxinomes.les_taxinomes_android.entities.GIS;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class CurrentLocationListener implements LocationListener {
	MapView mapView;
	MapController myMapController;
	MyLocationOverlay location = null;
	Double lat;
	Double lng;
	LocationManager lm;
	LocationListener ll;

	ArrayList<OverlayItem> anotherOverlayItemArray;
	String[] sources;
	

	public GIS getCurrentLocation(Context ctx) {

		lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		ll = new Myll();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, ll);
		
		GIS res = new GIS();
		res.setLatitude(lat);
		res.setLongitude(lng);
		
		return res;

	}

	private class Myll implements LocationListener {

		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			lat = location.getLatitude();
			lng = location.getLongitude();
			run();
		}

		public void run() {
			// TODO Auto-generated method stub
			location.runOnFirstFix(new Runnable() {
				public void run() {
					mapView.getController().animateTo(location.getMyLocation());
				}
			});
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
