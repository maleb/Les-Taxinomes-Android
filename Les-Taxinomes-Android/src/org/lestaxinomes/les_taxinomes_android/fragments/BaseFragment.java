/**
 * 
 */
package org.lestaxinomes.les_taxinomes_android.fragments;

import org.lestaxinomes.les_taxinomes_android.model.Model;

import android.support.v4.app.Fragment;

/**
 * @author Marie
 *
 */
public class BaseFragment extends Fragment{
	
	private Model model ;
	
	public BaseFragment(){
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	

}
