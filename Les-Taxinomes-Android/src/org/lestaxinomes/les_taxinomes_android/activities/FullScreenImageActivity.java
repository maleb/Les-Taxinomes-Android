package org.lestaxinomes.les_taxinomes_android.activities;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.model.MediaModel;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableImageView;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableMediaFullScreenView;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableMediaView;

import android.os.Bundle;
import android.widget.ImageView;

public class FullScreenImageActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_screen_image);
		
		
		MediaModel mediaModel = new MediaModel(Integer.parseInt(getIntent().getStringExtra("mediaId")));

		UpdatableMediaFullScreenView utv = new UpdatableMediaFullScreenView(mediaModel, findViewById(R.id.fullScreenImageView));
		mediaModel.addView(utv);

		mediaModel.loadMedia();

		UpdatableImageView uiv = new UpdatableImageView();

	
		


	}

}
