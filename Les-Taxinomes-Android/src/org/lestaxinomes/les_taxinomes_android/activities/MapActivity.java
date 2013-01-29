package org.lestaxinomes.les_taxinomes_android.activities;

import org.lestaxinomes.les_taxinomes_android.utils.GISUtils;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.content.Intent;
import android.os.Bundle;

public class MapActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MapView mapView = new MapView(this, 256);
		mapView.setClickable(true);
		mapView.setMultiTouchControls(true);

		mapView.getController().setZoom(50);

		// by default : brest
		Double latitude = 48.3928;
		Double longitude = -4.445702;
		Integer mediaId = 1;

		Intent intent = getIntent();

		if (intent.getStringExtra("lat") != null
				&& intent.getStringExtra("lon") != null) {
			latitude = Double.parseDouble(intent.getStringExtra("lat"));
			longitude = Double.parseDouble(intent.getStringExtra("lon"));
		}

		if (intent.getStringExtra("mediaId") != null) {
			mediaId = Integer.parseInt(intent.getStringExtra("mediaId"));
		}

		mapView.getController().setCenter(new GeoPoint(latitude, longitude));

		GISUtils.addPOI(mapView, mediaId.toString(), "", "", latitude,
				longitude);

		setContentView(mapView);

	}

}
