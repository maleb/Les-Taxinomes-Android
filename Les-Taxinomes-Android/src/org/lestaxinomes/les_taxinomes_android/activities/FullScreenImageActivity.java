package org.lestaxinomes.les_taxinomes_android.activities;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.model.MediaModel;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableImageView;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableMediaFullScreenView;

import android.os.Bundle;

/**
 * Activity showing the media in full screen <br/>
 * Needs an Intent with the StringExtra "mediaId"
 * 
 * @author Marie
 * 
 */
public class FullScreenImageActivity extends BaseActivity {

	/**
	 * Get the mediaId from the intent and load the corresponding image
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_screen_image);

		MediaModel mediaModel = new MediaModel(Integer.parseInt(getIntent()
				.getStringExtra("mediaId")));

		UpdatableMediaFullScreenView utv = new UpdatableMediaFullScreenView(
				mediaModel, findViewById(R.id.fullScreenImageView));
		mediaModel.addView(utv);

		mediaModel.loadMedia();

	}

}
