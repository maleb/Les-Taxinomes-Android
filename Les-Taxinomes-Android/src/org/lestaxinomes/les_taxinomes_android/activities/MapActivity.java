package org.lestaxinomes.les_taxinomes_android.activities;

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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Displays a map
 * <ul>
 * <li>if "mediaId" StringExtra exists, centered on the media location</li>
 * <li>else if GPS activated, centered on the current location of the user</li>
 * <li>else centered on Brest</li>
 * </ul>
 * 
 * and allows to load the closest medias of the center of the map (7 by 7)
 * 
 * @author Marie
 * 
 */
public class MapActivity extends BaseActivity {

	MapController myMapController;
	MyLocationOverlay location = null;
	Double currentLat;
	Double currentLon;
	LocationManager lm;
	LocationListener ll;
	boolean currentLocFirstLoadAlreadyDone = false;

	final int LOAD_MORE_INCREMENT = 7;
	Integer limit = 1;
	MapView mapView;
	GIS center;
	private MyLocationOverlay locationOverlay;

	/**
	 * loads a specific menu (with "load more" button etc)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_map, menu);

		return true;
	}

	/**
	 * displays the "limit" closest medias of the map center on the map and
	 * increase the limit
	 */
	private void loadmore() {
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

	/**
	 * Handles the specific button clicks Other buttons (= the default menu) are
	 * handled by the parent
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.map_loadmore:
			loadmore();
			return true;
		case R.id.center_to_current_location:
			centerToCurrentLocation();
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

		mapView.getController().setZoom(10);

		// by default : brest
		Double latitude = 48.3928;
		Double longitude = -4.445702;

		boolean oneMediaToDisplay = false;

		Intent intent = getIntent();

		if (intent.getStringExtra("lat") != null
				&& intent.getStringExtra("lon") != null) {
			oneMediaToDisplay = true;

			// there is 1 media to display
			latitude = Double.parseDouble(intent.getStringExtra("lat"));
			longitude = Double.parseDouble(intent.getStringExtra("lon"));
		}

		center = new GIS();
		center.setLatitude(latitude);
		center.setLongitude(longitude);

		// center the map - done only when creating the view, not at each
		// "loadmore"
		mapView.getController().setCenter(
				new GeoPoint(center.getLatitude(), center.getLongitude()));

		if (!oneMediaToDisplay) {

			// no localisation to center -> center to current location of the
			// user
			centerToCurrentLocation();

		}

		if (oneMediaToDisplay) {
			// display media markers when creating the view : only if we come
			// from a media Detail -> load 1 marker -the one of the media)
			loadmore();
		} else {
			// when the user will click on "load more", he wants to see 7 medias
			// -not just one)
			limit += LOAD_MORE_INCREMENT;
		}

		setContentView(mapView);

	}

	/**
	 * If GPS disabled, open a dialog asking to enable GPS
	 */
	private void askForGPSIfNeeded() {

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		final boolean gpsEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (!gpsEnabled) {
			// Build an alert dialog here that requests that the user enable
			// the location services, then when the user clicks the "OK" button,
			// call enableLocationSettings()
			AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
					MapActivity.this);
			myAlertDialog.setTitle("Activation du GPS");
			myAlertDialog
					.setMessage("Pour améliorer votre positionnement, veuillez activer votre GPS");
			myAlertDialog.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0, int arg1) {
							// when the OK button is clicked
							enableLocationSettings();
						}
					});
			myAlertDialog.setNegativeButton("Annuler",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0, int arg1) {
							// do something when the Cancel button is clicked
						}
					});
			myAlertDialog.show();

		}
	}

	/**
	 * Opens the native "Settings" screen
	 */
	private void enableLocationSettings() {
		Intent settingsIntent = new Intent(
				Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(settingsIntent);
	}

	/**
	 * Ask for enabling GPS if not enabled And center the map to the current
	 * location of the user
	 */
	private void centerToCurrentLocation() {

		askForGPSIfNeeded();
		locationOverlay = new MyLocationOverlay(getApplicationContext(),
				mapView);

		// ajouter la loc
		mapView.getOverlays().add(locationOverlay);
		// afficher
		locationOverlay.enableMyLocation();

		locationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				mapView.getController().animateTo(
						locationOverlay.getMyLocation());

				center.setLatitude(locationOverlay.getMyLocation()
						.getLatitudeE6() * 1E-6);
				center.setLongitude(locationOverlay.getMyLocation()
						.getLongitudeE6() * 1E-6);
			}
		});
	}

}
