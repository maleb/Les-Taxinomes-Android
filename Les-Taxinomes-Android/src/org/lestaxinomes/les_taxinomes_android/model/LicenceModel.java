package org.lestaxinomes.les_taxinomes_android.model;

import java.util.List;

import org.lestaxinomes.les_taxinomes_android.entities.Licence;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableView;
/**
 * Model for the license list
 * @author Marie
 *
 */
public class LicenceModel extends Model {
	
	private List<Licence> licences;
	

	@Override
	public void updateView(UpdatableView v) {
		v.update();
		
	}

	
	public void loadLicences(){
		getConnexionManager().loadLicences(this);
	}
	
	public List<Licence> getLicences(){
		return licences;
	}


	public void setLicences(List<Licence> licences) {
		this.licences = licences;
	}


}
