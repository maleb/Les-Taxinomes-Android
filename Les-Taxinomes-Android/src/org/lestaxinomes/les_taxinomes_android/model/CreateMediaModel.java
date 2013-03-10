package org.lestaxinomes.les_taxinomes_android.model;

import org.lestaxinomes.les_taxinomes_android.entities.Media;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableView;
/**
 * Model for creating a new media in the database
 * @author Marie
 *
 */
public class CreateMediaModel extends Model {
	

	private Media media;
	private String localMediaUri;
	private String login;
	private String password;
	private Integer idAuthor;
	
	


	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}


	@Override
	public void updateView(UpdatableView v) {

		v.update();

	}

	public String getLocalMediaUri() {
		return localMediaUri;
	}

	public void setLocalMediaUri(String localMediaUri) {
		this.localMediaUri = localMediaUri;
	}
	
	
	public void createMedia() {
		getConnexionManager().createMedia(this);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIdAuthor() {
		return idAuthor;
	}

	public void setIdAuthor(Integer idAuthor) {
		this.idAuthor = idAuthor;
	}



}
