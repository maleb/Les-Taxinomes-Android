package org.lestaxinomes.les_taxinomes_android.fragments;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.model.MediaModel;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableMediaView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class MediaFragment extends BaseFragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.media_fragment, container, false);
		
		Integer mediaId = 1;

		MediaModel mediaModel = new MediaModel(mediaId);

		TextView titleView = (TextView) view.findViewById(R.id.mediaTitleView);
		TextView descriptionView = (TextView) view.findViewById(R.id.mediaDescriptionView);
		WebView imageView = (WebView) view.findViewById(R.id.mediaImageView);
		UpdatableMediaView utv = new UpdatableMediaView(mediaModel, titleView, imageView, descriptionView);
		mediaModel.addView(utv);

		mediaModel.readMedia();

		return view;
	}


}
