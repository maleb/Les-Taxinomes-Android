package org.lestaxinomes.les_taxinomes_android.model;

import org.lestaxinomes.les_taxinomes_android.entities.Media;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableView;
/**
 * Model for loading a media
 * @author Marie
 *
 */
public class MediaModel extends Model {
	

	private Media media;
	private AuthorModel authorModel;
	
	

	public MediaModel(Integer mediaId) {
		this.authorModel = new AuthorModel();
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
		getConnexionManager().loadMedia(this);
	}

	@Override
	public void updateView(UpdatableView v) {

		v.update();

	}

	public AuthorModel getAuthorModel() {
		return authorModel;
	}

	public void setAuthorModel(AuthorModel authorModel) {
		this.authorModel = authorModel;
	}


}
