package org.lestaxinomes.les_taxinomes_android.model;

import org.lestaxinomes.les_taxinomes_android.views.UpdatableView;

public class MediaModel extends Model {

	private Media media;

	public MediaModel(Integer mediaId) {
		media = new Media();
		media.setId(mediaId);
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public void readMedia() {
		getConnexionManager().readMedia(this);
	}

	@Override
	public void updateView(UpdatableView v) {

		v.update();

	}

}
