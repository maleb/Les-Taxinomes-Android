package org.lestaxinomes.les_taxinomes_android.views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.activities.MapActivity;
import org.lestaxinomes.les_taxinomes_android.entities.Media;
import org.lestaxinomes.les_taxinomes_android.model.MediaModel;
import org.lestaxinomes.les_taxinomes_android.utils.GISUtils;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.xmlpull.v1.XmlPullParser;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UpdatableMediaView implements UpdatableView {

	private MediaModel mediaModel;

	private TextView titleView;
	private UpdatableImageView imageView;
	private TextView descriptionView;
	private UpdatableAuthorView authorView;
	private TextView dateView;
	private TextView visitesView;
	private MapView mapView;

	public UpdatableMediaView(MediaModel mediaModel, View view) {

		// basic views
		titleView = (TextView) view.findViewById(R.id.mediaTitleView);
		descriptionView = (TextView) view
				.findViewById(R.id.mediaDescriptionView);
		dateView = (TextView) view.findViewById(R.id.mediaDateView);
		visitesView = (TextView) view.findViewById(R.id.mediaVisitesView);

		// mapView
		mapView = (MapView) view.findViewById(R.id.mediaMapView);
		mapView.getController().setZoom(10);

		// updatable image views
		this.imageView = new UpdatableImageView();
		this.imageView.setImageView(((ImageView) view
				.findViewById(R.id.mediaImageView)));

		// others updatable views
		UpdatableImageView avatarView = new UpdatableImageView();
		avatarView.setImageView((ImageView) view
				.findViewById(R.id.mediaAvatarView));
		TextView authorNameView = (TextView) view
				.findViewById(R.id.mediaAuthorView);
		this.authorView = new UpdatableAuthorView(authorNameView, avatarView,
				mediaModel.getAuthorModel());

		mediaModel.getAuthorModel().addView(this.authorView);

		this.mediaModel = mediaModel;
	}

	@Override
	public void update() {
		titleView.setText(mediaModel.getMedia().getTitre());
		descriptionView.setText(mediaModel.getMedia().getDescription());

		Integer nbVisites = mediaModel.getMedia().getVisites();

		if (nbVisites != null) {
			String vue = " vues";
			if (nbVisites.equals(1)) {
				vue = " vue";
			}
			visitesView.setText(nbVisites.toString() + vue);
		}

		if (mediaModel.getMedia().getGis() != null) {

			GeoPoint mediaLocalisation = new GeoPoint(mediaModel.getMedia()
					.getGis().getLatitude(), mediaModel.getMedia().getGis()
					.getLongitude());

			mapView.getController().setCenter(mediaLocalisation);

			List<Media> mediaList = new ArrayList<Media>();
			mediaList.add(mediaModel.getMedia());
			GISUtils.setMediaListOnMapView(mediaList, mapView);

			// XmlPullParser parser =
			// this.mapView.getContext().getResources().getXml(R.id.mediaMapView);
			// AttributeSet attributes = Xml.asAttributeSet(parser);

			// GISUtils.addPOI(mapView,
			// mediaModel.getMedia().getId().toString(),
			// mediaModel.getMedia().getTitre(), mediaModel
			// .getMedia().getDescription(), mediaModel.getMedia().getGis()
			// .getLatitude(), mediaModel.getMedia().getGis().getLongitude());

			mapView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					final Intent intent = new Intent(mapView.getContext(),
							MapActivity.class);
					intent.putExtra("lon", mediaModel.getMedia().getGis()
							.getLongitude().toString());
					intent.putExtra("lat", mediaModel.getMedia().getGis()
							.getLatitude().toString());
					((Activity) mapView.getContext()).startActivity(intent);
					((Activity) mapView.getContext()).finish();

				}
			});

			// changing the width to full size
			android.view.ViewGroup.LayoutParams params = this.mapView
					.getLayoutParams();
			params.width = LayoutParams.MATCH_PARENT;
			this.mapView.setLayoutParams(params);
			//reload view
			this.mapView.invalidate();
		}

		Date modifDate = mediaModel.getMedia().getModificationDate();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = sdf.format(modifDate);
		dateView.setText("le " + dateString);
		this.imageView.loadImage(mediaModel.getMedia().getImageUrl());
		this.authorView.update();

	}

}
