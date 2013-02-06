package org.lestaxinomes.les_taxinomes_android.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.entities.GIS;
import org.lestaxinomes.les_taxinomes_android.model.MediaListModel;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableGISListView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MapActivity extends BaseActivity {

	MapController myMapController;
	MyLocationOverlay location = null;
	Double currentLat;
	Double currentLon;
	LocationManager lm;
	LocationListener ll;
	boolean currentLocFirstLoadAlreadyDone=false;


	final int LOAD_MORE_INCREMENT = 7;
	Integer limit = 1;
	MapView mapView;
	GIS center;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_map, menu);

		return true;
	}

	private void loadmore() {
		// TODO loadmore around the current map center (and not the original
		// center)
		center.setLatitude(mapView.getMapCenter().getLatitudeE6() * 1E-6);
		center.setLongitude(mapView.getMapCenter().getLongitudeE6() * 1E-6);

		Map<String, String> criteres = new HashMap<String, String>();
		criteres.put("limite", limit.toString());
		criteres.put("tri", "distance");
		criteres.put("lat", center.getLatitude().toString());
		criteres.put("lon", center.getLongitude().toString());
		MediaListModel mlm = new MediaListModel(criteres);

		UpdatableGISListView uglv = new UpdatableGISListView(mlm, mapView,
				center);
		mlm.addView(uglv);

		mlm.readMediaList();

		limit += LOAD_MORE_INCREMENT;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.map_loadmore:
			loadmore();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mapView = new MapView(this, 256);
		mapView.setClickable(true);
		mapView.setMultiTouchControls(true);

		mapView.getController().setZoom(50);

		// by default : brest
		Double latitude = 48.3928;
		Double longitude = -4.445702;
		Integer mediaId = 1;

		// // if gps enabled : by default = current location
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		ll = new Myll();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, ll);
		location = new MyLocationOverlay(getApplicationContext(), mapView);
		// ajouter la loc
		mapView.getOverlays().add(location);
		// afficher
		location.enableMyLocation();

		if (currentLat != null && currentLon != null) {
			latitude = currentLat;
			longitude = currentLon;
		}

		// GIS currentLocation = new
		// CurrentLocationListener().getCurrentLocation(this);
		// latitude=currentLocation.getLatitude();
		// longitude=currentLocation.getLongitude();

		Intent intent = getIntent();

		if (intent.getStringExtra("lat") != null
				&& intent.getStringExtra("lon") != null) {
			latitude = Double.parseDouble(intent.getStringExtra("lat"));
			longitude = Double.parseDouble(intent.getStringExtra("lon"));
		}

		if (intent.getStringExtra("mediaId") != null) {
			mediaId = Integer.parseInt(intent.getStringExtra("mediaId"));
		}

		center = new GIS();
		center.setLatitude(latitude);
		center.setLongitude(longitude);

		// center the map - done only when creating the view, not at each
		// "loadmore"
		mapView.getController().setCenter(
				new GeoPoint(center.getLatitude(), center.getLongitude()));

		loadmore();

		setContentView(mapView);

	}

	private class Myll implements LocationListener {

		public void onLocationChanged(Location location) {
			currentLat = location.getLatitude();
			currentLon = location.getLongitude();
			
			//we animate the mapview to the currentLocation the first time, but not always.
			//(the user may want to center the mapview on another point as the currentLocation)
			if (!currentLocFirstLoadAlreadyDone){
			run();
			currentLocFirstLoadAlreadyDone=true;
			}
		}

		public void run() {
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

}
