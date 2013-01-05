package org.lestaxinomes.les_taxinomes_android.views;

import org.lestaxinomes.les_taxinomes_android.model.MediaModel;

import android.webkit.WebView;
import android.widget.TextView;

public class UpdatableMediaView implements UpdatableView {
	

	private MediaModel mediaModel;

	private TextView titleView;
	private WebView imageView;
	private TextView descriptionView;
	

	public UpdatableMediaView(MediaModel mediaModel, TextView titleView, WebView imageView, TextView descriptionView) {
		
		this.mediaModel=mediaModel;
		this.titleView = titleView;
		this.imageView = imageView;
		this.descriptionView = descriptionView;
	}


	@Override
	public void update() {
		titleView.setText(mediaModel.getMedia().getTitre());	
		descriptionView.setText(mediaModel.getMedia().getDescription());	
		imageView.loadUrl(mediaModel.getMedia().getImageUrl());
	}

}
