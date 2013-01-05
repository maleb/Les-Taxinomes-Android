package org.lestaxinomes.les_taxinomes_android.fragments;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.model.ConnexionManager;
import org.lestaxinomes.les_taxinomes_android.model.MediaListModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MediaListFragment extends BaseFragment{
	
	
	public MediaListFragment(){
		setModel(new MediaListModel());
	}

	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
	        View view =  inflater.inflate(R.layout.media_list_fragment, container, false);
	        
	        return view;
	    }




}
