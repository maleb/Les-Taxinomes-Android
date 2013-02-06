package org.lestaxinomes.les_taxinomes_android.views;

import java.util.List;

import org.lestaxinomes.les_taxinomes_android.entities.GIS;
import org.lestaxinomes.les_taxinomes_android.entities.Media;
import org.lestaxinomes.les_taxinomes_android.model.MediaListModel;
import org.lestaxinomes.les_taxinomes_android.utils.GISUtils;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class UpdatableGISListView implements UpdatableView {

	private MediaListModel mediaListModel;
	private MapView mapView;
//	private GIS center;

	public UpdatableGISListView(MediaListModel mediaListModel, MapView view,
			GIS center) {

		this.mediaListModel = mediaListModel;
		this.mapView = view;
//		this.center = center;

	}

	@Override
	public void update() {

		List<Media> mediaList = mediaListModel.getMediaList();

		// update the center
//		mapView.getController().setCenter(
//				new GeoPoint(center.getLatitude(), center.getLongitude()));

		// clean the old POIS
		GISUtils.removePOIs(mapView);

		// add the new POIs
		GISUtils.setMediaListOnMapView(mediaList, mapView);
		
		mapView.invalidate();

	}
}
