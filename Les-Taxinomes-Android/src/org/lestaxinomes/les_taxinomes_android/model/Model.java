package org.lestaxinomes.les_taxinomes_android.model;

import java.util.ArrayList;
import java.util.List;

import org.lestaxinomes.les_taxinomes_android.dataConnexion.ConnexionManager;
import org.lestaxinomes.les_taxinomes_android.dataConnexion.XMLRPCConnexionManager;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableView;

public abstract class Model {

	List<UpdatableView> views = new ArrayList<UpdatableView>();

	public ConnexionManager getConnexionManager() {
		return new XMLRPCConnexionManager();

	}

	public void update() {
		notifyViews();
	}

	public void notifyViews() {

		for (UpdatableView v : views) {
			updateView(v);
		}

	}

	public abstract void updateView(UpdatableView v);

	public void addView(UpdatableView view) {
		views.add(view);
	}

}
