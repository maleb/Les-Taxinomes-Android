package org.lestaxinomes.les_taxinomes_android.dataConnexion;

import org.lestaxinomes.les_taxinomes_android.model.AuthorModel;
import org.lestaxinomes.les_taxinomes_android.model.CreateMediaModel;
import org.lestaxinomes.les_taxinomes_android.model.LicenceModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaFullDocumentModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaListModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaModel;
import org.lestaxinomes.les_taxinomes_android.model.UserModel;

/**
 * Interface for the connexion to the database
 * 
 * @author Marie
 * 
 */
public interface ConnexionManager {

	/**
	 * Loads the media from the mediaModel.media.id, updates mediaModel.media
	 * with the result, and updates the corresponding views
	 * 
	 * @param mediaModel
	 *            the model containing a media with a mediaId
	 */
	public void loadMedia(MediaModel mediaModel);

	/**
	 * Loads the media with full size document and updates the corresponding
	 * view
	 * 
	 * @param mediaFullDocumentModel
	 *            the model containing a media with a mediaId
	 */
	public void loadMediaFullDocument(
			MediaFullDocumentModel mediaFullDocumentModel);

	/**
	 * Load the mediaList from the criteriaMap and populate the list view
	 * 
	 * @param mediaListModel
	 *            the model containing a map of criteres
	 */
	public void loadMediaList(MediaListModel mediaListModel);

	/**
	 * Load the author from authorModel.author.id, updates authorMode.author
	 * with the result, and updates the corresponding views
	 * 
	 * @param authorModel
	 *            the model containing an Author with an ID
	 */
	public void loadAuthor(AuthorModel authorModel);

	/**
	 * Try to connect with login/password, if successful load the data of the
	 * logged author in userModel.author
	 * 
	 * @param userModel
	 *            the model containing login/password
	 */
	public void authenticate(UserModel userModel);

	/**
	 * Creates a new media in the database from the createMediaModel.media and
	 * updates it with the generated id
	 * 
	 * @param createMediaModel
	 *            the model containing the data of the media to create
	 */
	void createMedia(CreateMediaModel createMediaModel);

	/**
	 * Loads the license list and store it in the model
	 * 
	 * @param licenceModel
	 *            the model containing the license list for storing the result
	 */
	public void loadLicences(LicenceModel licenceModel);

}
