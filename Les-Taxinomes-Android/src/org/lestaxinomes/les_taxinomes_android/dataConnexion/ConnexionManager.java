package org.lestaxinomes.les_taxinomes_android.dataConnexion;

import org.lestaxinomes.les_taxinomes_android.model.AuthorModel;
import org.lestaxinomes.les_taxinomes_android.model.CreateMediaModel;
import org.lestaxinomes.les_taxinomes_android.model.LicenceModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaFullDocumentModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaListModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaModel;
import org.lestaxinomes.les_taxinomes_android.model.UserModel;



public interface ConnexionManager  {


	public void loadMedia(MediaModel mediaModel) ;
	
	public void loadMediaFullDocument(MediaFullDocumentModel mediaFullDocumentModel) ;
	
	public void loadMediaList(MediaListModel mediaListModel) ;
	
	public void loadAuthor(AuthorModel authorModel);
	
	public void authenticate(UserModel userModel);
	
	

	void createMedia(CreateMediaModel createMediaModel);

	public void loadLicences(LicenceModel licenceModel);
	
	


}
