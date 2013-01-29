package org.lestaxinomes.les_taxinomes_android.utils;

import java.util.ArrayList;

import org.lestaxinomes.les_taxinomes_android.activities.MapActivity;
import org.lestaxinomes.les_taxinomes_android.fragments.MediaListFragment.MediaActivity;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.app.Activity;
import android.content.Intent;

public class GISUtils {

	public static void addPOI(final MapView mapView, String mediaId,
			String title, String description, final Double latitude,
			final Double longitude) {

		GeoPoint mediaLocalisation = new GeoPoint(latitude, longitude);

		ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
		OverlayItem item = new OverlayItem(mediaId, title, description,
				mediaLocalisation);
		items.add(item);

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
									intent.putExtra("lon", longitude.toString());
									intent.putExtra("lat", latitude.toString());
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
