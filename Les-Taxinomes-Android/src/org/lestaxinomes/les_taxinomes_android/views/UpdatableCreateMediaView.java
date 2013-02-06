package org.lestaxinomes.les_taxinomes_android.views;

import org.lestaxinomes.les_taxinomes_android.fragments.MediaListFragment.MediaActivity;
import org.lestaxinomes.les_taxinomes_android.model.CreateMediaModel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class UpdatableCreateMediaView implements UpdatableView {

	private View v;
	private CreateMediaModel createMediaModel;

	public UpdatableCreateMediaView(View v, CreateMediaModel cmm) {
		this.v = v;
		this.createMediaModel = cmm;
	}

	@Override
	public void update() {

		if (createMediaModel.getMedia() != null) {
			if (createMediaModel.getMedia().getId() != null) {
				Activity currentAct = (Activity) this.v.getContext();

				Intent intent = new Intent(currentAct, MediaActivity.class);
				intent.putExtra("mediaId", createMediaModel.getMedia().getId());
				currentAct.startActivity(intent);
				currentAct.finish();
			}
		}

	}

}
