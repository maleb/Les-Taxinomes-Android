package org.lestaxinomes.les_taxinomes_android.model;

import java.util.List;
import java.util.Map;

import org.lestaxinomes.les_taxinomes_android.entities.Media;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableView;

/**
 * Model for loading a list of medias
 * @author Marie
 *
 */
public class MediaListModel extends Model{
	
	private List<Media> mediaList;
	private Map<String,String> criteres;

	public MediaListModel(Map<String, String> criteres) {
		this.criteres=criteres;
	}

	@Override
	public void updateView(UpdatableView v) {
		v.update();
		
	}

	public List<Media> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<Media> mediaList) {
		this.mediaList = mediaList;
	}

	public Map<String,String> getCriteres() {
		return criteres;
	}

	public void setCriteres(Map<String,String> criteres) {
		this.criteres = criteres;
	}
	
	public void readMediaList(){
		getConnexionManager().loadMediaList(this);
	}
	
	
	

}
