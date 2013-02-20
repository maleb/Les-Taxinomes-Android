package org.lestaxinomes.les_taxinomes_android.utils;

import java.util.ArrayList;
import java.util.List;

import org.lestaxinomes.les_taxinomes_android.activities.MapActivity;
import org.lestaxinomes.les_taxinomes_android.entities.Media;
import org.lestaxinomes.les_taxinomes_android.fragments.MediaListFragment.MediaActivity;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

public class GISUtils {

//	private static void askForGPSIfNeeded(final Context ctx) {
//
//		// This verification should be done during onStart() because the system
//		// calls
//		// this method when the user returns to the activity, which ensures the
//		// desired
//		// location provider is enabled each time the activity resumes from the
//		// stopped state.
//		LocationManager locationManager = (LocationManager) ctx
//				.getSystemService(Context.LOCATION_SERVICE);
//		final boolean gpsEnabled = locationManager
//				.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//		if (!gpsEnabled) {
//			// Build an alert dialog here that requests that the user enable
//			// the location services, then when the user clicks the "OK" button,
//			// call enableLocationSettings()
//			AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(ctx);
//			myAlertDialog.setTitle("Activation du GPS");
//			myAlertDialog
//					.setMessage("Pour améliorer votre positionnement, veuillez activer votre GPS");
//			myAlertDialog.setPositiveButton("OK",
//					new DialogInterface.OnClickListener() {
//
//						public void onClick(DialogInterface arg0, int arg1) {
//							// do something when the OK button is clicked
//							enableLocationSettings(ctx);
//						}
//					});
//			myAlertDialog.setNegativeButton("Annuler",
//					new DialogInterface.OnClickListener() {
//
//						public void onClick(DialogInterface arg0, int arg1) {
//							// do something when the Cancel button is clicked
//						}
//					});
//			myAlertDialog.show();
//
//		}
//	}
//
//	private static void enableLocationSettings(Context ctx) {
//		Intent settingsIntent = new Intent(
//				Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//		ctx.startActivity(settingsIntent);
//	}
//
//	public static GeoPoint getCurrentLocation(
//			MyLocationOverlay locationOverlay, MapView mapView) {
//		askForGPSIfNeeded(mapView.getContext());
//		locationOverlay = new MyLocationOverlay(mapView.getContext()
//				.getApplicationContext(), mapView);
//
//		// ajouter la loc
//		mapView.getOverlays().add(locationOverlay);
//		// afficher
//		locationOverlay.enableMyLocation();
//		
//
//		return new GeoPoint(
//				locationOverlay.getMyLocation().getLatitudeE6() * 1E-6,
//				locationOverlay.getMyLocation().getLongitudeE6() * 1E-6);
//
//	}

	public static void removePOIs(final MapView mapView) {

		mapView.getOverlayManager().clear();

	}

	public static void setMediaListOnMapView(List<Media> mediaList,
			final MapView mapView) {

		ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();

		if (mediaList != null) {
			for (Media media : mediaList) {
				GeoPoint mediaLocalisation = new GeoPoint(media.getGis()
						.getLatitude(), media.getGis().getLongitude());
				String id = (media.getId() == null ? "" : media.getId()
						.toString());
				OverlayItem item = new OverlayItem(id, media.getTitre(),
						media.getDescription(), mediaLocalisation);
				items.add(item);
			}
		}

		ResourceProxy resourceProxy = new DefaultResourceProxyImpl(
				mapView.getContext());

		mapView.getOverlays()
				.add(new ItemizedIconOverlay<OverlayItem>(
						items,
						new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
							@Override
							public boolean onItemSingleTapUp(final int index,
									final OverlayItem item) {

								// if MapActivity : go to Media
								if (((Activity) mapView.getContext()) instanceof MapActivity) {
									final Intent intent = new Intent(mapView
											.getContext(), MediaActivity.class);
									intent.putExtra("mediaId", item.mUid);
									((Activity) mapView.getContext())
											.startActivity(intent);
									((Activity) mapView.getContext()).finish();
								}

								// else : go to MapActivity
								else {
									final Intent intent = new Intent(mapView
											.getContext(), MapActivity.class);
									intent.putExtra("lon",
											item.mGeoPoint.getLongitudeE6());
									intent.putExtra("lat",
											item.mGeoPoint.getLatitudeE6());
									((Activity) mapView.getContext())
											.startActivity(intent);
									((Activity) mapView.getContext()).finish();
								}

								return true; // We 'handled' this event.
							}

							@Override
							public boolean onItemLongPress(int arg0,
									OverlayItem arg1) {
								// TODO Auto-generated method stub
								return false;
							}
						}, resourceProxy));

	}

}
