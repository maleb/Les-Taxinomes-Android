package org.lestaxinomes.les_taxinomes_android.views;

import java.util.List;

import org.lestaxinomes.les_taxinomes_android.entities.Licence;
import org.lestaxinomes.les_taxinomes_android.model.LicenceModel;

public class UpdatableLicenceSingletonView implements UpdatableView {

	/** L'instance statique */
	private static UpdatableLicenceSingletonView instance;
	private static List<Licence> licences;
	private static LicenceModel licenceModel;

	public static UpdatableLicenceSingletonView getInstance() {
		if (null == instance) { // Premier appel
			instance = new UpdatableLicenceSingletonView();
			licenceModel = new LicenceModel();
			licenceModel.addView(instance);

			licenceModel.getConnexionManager().loadLicences(licenceModel);

		}
		return instance;
	}

	/**
	 * Constructeur redéfini comme étant privé pour interdire son appel et
	 * forcer à passer par la méthode <link
	 */
	private UpdatableLicenceSingletonView() {
	}

	@Override
	public void update() {
		if (licenceModel.getLicences()!=null){
			this.setLicences(licenceModel.getLicences());
		}

	}

	public static List<Licence> getLicences() {
		return licences;
	}

	private static void setLicences(List<Licence> licences) {
		UpdatableLicenceSingletonView.licences = licences;
	}

}
