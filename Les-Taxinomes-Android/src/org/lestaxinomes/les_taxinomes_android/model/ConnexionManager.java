package org.lestaxinomes.les_taxinomes_android.model;

import java.util.List;
import java.util.Map;


public interface ConnexionManager  {


	public void readMedia(MediaModel mediaModel) ;
	
	public List<Media> getMediaList(Map criteres) ;


}
