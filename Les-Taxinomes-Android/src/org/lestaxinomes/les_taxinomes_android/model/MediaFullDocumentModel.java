package org.lestaxinomes.les_taxinomes_android.model;

import org.lestaxinomes.les_taxinomes_android.entities.Media;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableView;
/**
 * Model for loading a media with its full-size document
 * @author Marie
 *
 */
public class MediaFullDocumentModel extends Model {

	private Media media;

	public MediaFullDocumentModel(Integer mediaId) {
		media = new Media();
		media.setId(mediaId);
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public void loadMedia() {
		getConnexionManager().loadMediaFullDocument(this);
	}

	@Override
	public void updateView(UpdatableView v) {

		v.update();

	}

}
