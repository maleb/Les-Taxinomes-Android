package org.lestaxinomes.les_taxinomes_android.fragments;

import java.util.HashMap;
import java.util.Map;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.activities.BaseActivity;
import org.lestaxinomes.les_taxinomes_android.entities.Media;
import org.lestaxinomes.les_taxinomes_android.model.MediaListModel;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableMediaListView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class MediaListFragment extends BaseListFragment {

	boolean mDualPane;
	int mCurCheckPosition = 0;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Check to see if we have a frame in which to embed the details
		// fragment directly in the containing UI.
		View detailsFrame = getActivity().findViewById(R.id.details);
		mDualPane = detailsFrame != null
				&& detailsFrame.getVisibility() == View.VISIBLE;

		if (savedInstanceState != null) {
			// Restore last state for checked position.
			mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
		}

		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.media_list_fragment, container,
				false);

		Toast.makeText(getActivity(), "chargement des médias", Toast.LENGTH_LONG)
				.show();

		Map<String, String> criteres = new HashMap<String, String>();
		criteres.put("tri", "date DESC");
		criteres.put("vignette_format","carre" );
		MediaListModel mlm = new MediaListModel(criteres);

		UpdatableMediaListView umlv = new UpdatableMediaListView(mlm, view);
		mlm.addView(umlv);

		mlm.readMediaList();
		
		
		if (mDualPane) {
			// In dual-pane mode, the list view highlights the selected item.
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			// Make sure our UI is in the correct state.
			showDetails(mCurCheckPosition);
		}

		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		//Toast.makeText(getActivity(), "onListItemClick", Toast.LENGTH_LONG).show();

		showDetails(position);

	}

	void showDetails(int index) {
		mCurCheckPosition = index;
		 Media selectedMedia = (Media)
				 getListView().getItemAtPosition(index);
				 Integer mediaId = selectedMedia.getId();

		if (mDualPane) {
			// We can display everything in-place with fragments, so update
			// the list to highlight the selected item and show the data.
			getListView().setItemChecked(index, true);

			// Check what fragment is currently shown, replace if needed.
			MediaFragment details = (MediaFragment) getFragmentManager()
					.findFragmentById(R.id.details);
			if (details == null || details.getShownIndex() != index) {
				// Make new fragment to show this selection.
				details = MediaFragment.newInstance(index,mediaId);

				// Execute a transaction, replacing any existing fragment
				// with this one inside the frame.
				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				if (index == 0) {
					ft.replace(R.id.details, details);
				} else {
					// TODO ft.replace(R.id.a_item, details);
					ft.replace(R.id.details, details);
				}
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}

		} else {
			
			// Otherwise we need to launch a new activity to display
			// the dialog fragment with selected text.
			Intent intent = new Intent();
			intent.setClass(getActivity(), MediaActivity.class);
			intent.putExtra("index", index);
			intent.putExtra("mediaId", mediaId);
			startActivity(intent);
		}
	}

	public static class MediaActivity extends BaseActivity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				// If the screen is now in landscape mode, we can show the
				// dialog in-line with the list so we don't need this activity.
				finish();
				return;
			}

			if (savedInstanceState == null) {
				// During initial setup, plug in the details fragment.
				MediaFragment details = new MediaFragment();
				details.setArguments(getIntent().getExtras());
				getSupportFragmentManager().beginTransaction()
						.add(android.R.id.content, details).commit();
			}
		}
	}

}
