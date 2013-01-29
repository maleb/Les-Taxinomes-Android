package org.lestaxinomes.les_taxinomes_android.dataConnexion;

import org.lestaxinomes.les_taxinomes_android.model.AuthorModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaListModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaModel;



public interface ConnexionManager  {


	public void loadMedia(MediaModel mediaModel) ;
	
	public void loadMediaList(MediaListModel mediaListModel) ;
	
	public void loadAuthor(AuthorModel authorModel);


}
