package org.lestaxinomes.les_taxinomes_android.fragments;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.model.MediaModel;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableMediaView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment displaying the details of a media (get the mediaId from the intent or the arguments).
 * Displayed in each own Activity if portrait.
 * Displayed at the right of the Media List Fragment if landscape (in the ConsultationActivty).
 * @author Marie
 *
 */
public class MediaFragment extends BaseFragment {

	public static MediaFragment newInstance(int index, int mediaId) {
		MediaFragment f = new MediaFragment();
		Bundle args = new Bundle();
		args.putInt("index", index);
		args.putInt("mediaId", mediaId);
		f.setArguments(args);
		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.media_fragment, container, false);

		Integer mediaId = 1;

		Intent intent = getActivity().getIntent();

		if (this.getArguments() != null) {
			if (this.getArguments().get("mediaId") != null
					&& this.getArguments().get("mediaId").toString() != "") {
				mediaId = Integer.parseInt(this.getArguments().get("mediaId")
						.toString());
			}
		}
		if (intent.getStringExtra("mediaId") != null) {
			mediaId = Integer.parseInt(intent.getStringExtra("mediaId"));
		}

		MediaModel mediaModel = new MediaModel(mediaId);

		UpdatableMediaView utv = new UpdatableMediaView(mediaModel, view);
		mediaModel.addView(utv);

		mediaModel.loadMedia();

		return view;
	}

}
