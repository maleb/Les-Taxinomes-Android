package org.lestaxinomes.les_taxinomes_android.dataConnexion;

import org.lestaxinomes.les_taxinomes_android.model.AuthorModel;
import org.lestaxinomes.les_taxinomes_android.model.CreateMediaModel;
import org.lestaxinomes.les_taxinomes_android.model.LicenceModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaListModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaModel;
import org.lestaxinomes.les_taxinomes_android.model.Model;
import org.lestaxinomes.les_taxinomes_android.model.UserModel;

import android.os.AsyncTask;

public class XMLRPCConnexionManager implements ConnexionManager {

	@Override
	public void loadMedia(MediaModel mm) {
		// loading the media information (for author : only the id and name)
		AsyncTask<Model, Integer, Model> mediaAT = new XMLRPCConnexionManagerAsynctask();
		mediaAT.execute(mm);

		

	}

	@Override
	public void loadMediaList(MediaListModel mediaListModel) {
		(new XMLRPCConnexionManagerAsynctask()).execute(mediaListModel);

	}

	@Override
	public void loadAuthor(AuthorModel authorModel) {
		(new XMLRPCConnexionManagerAsynctask()).execute(authorModel);

	}

	@Override
	public void authenticate(UserModel userModel) {
		(new XMLRPCConnexionManagerAsynctask()).execute(userModel);
		
	}

	@Override
	public void createMedia(CreateMediaModel createMediaModel) {
		
		(new XMLRPCConnexionManagerAsynctask()).execute(createMediaModel);
		
	}

	@Override
	public void loadLicences(LicenceModel licenceModel) {
		(new XMLRPCConnexionManagerAsynctask()).execute(licenceModel);
		
	}




}
