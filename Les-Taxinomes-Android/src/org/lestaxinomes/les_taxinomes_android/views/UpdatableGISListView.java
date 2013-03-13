package org.lestaxinomes.les_taxinomes_android.views;

import java.util.List;

import org.lestaxinomes.les_taxinomes_android.entities.GIS;
import org.lestaxinomes.les_taxinomes_android.entities.Media;
import org.lestaxinomes.les_taxinomes_android.model.MediaListModel;
import org.lestaxinomes.les_taxinomes_android.utils.GISUtils;
import org.osmdroid.views.MapView;
/**
 * Displays the Points of Interest on the Map
 * @author Marie
 *
 */
public class UpdatableGISListView implements UpdatableView {

	private MediaListModel mediaListModel;
	private MapView mapView;

	public UpdatableGISListView(MediaListModel mediaListModel, MapView view,
			GIS center) {

		this.mediaListModel = mediaListModel;
		this.mapView = view;

	}

	@Override
	public void update() {

		List<Media> mediaList = mediaListModel.getMediaList();

		// add the new POIs
		GISUtils.setMediaListOnMapView(mediaList, mapView);

		mapView.invalidate();

	}
}
