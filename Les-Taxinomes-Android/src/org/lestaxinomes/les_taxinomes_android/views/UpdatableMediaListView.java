package org.lestaxinomes.les_taxinomes_android.views;

import java.util.List;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.adapters.MediaListAdapter;
import org.lestaxinomes.les_taxinomes_android.entities.Media;
import org.lestaxinomes.les_taxinomes_android.model.MediaListModel;

import android.view.View;
import android.widget.ListView;

public class UpdatableMediaListView implements UpdatableView {

	private MediaListModel mediaListModel;
	private ListView listView;

	public UpdatableMediaListView(MediaListModel mediaListModel, View view) {

		this.mediaListModel = mediaListModel;
		this.listView = (ListView) view.findViewById(android.R.id.list);
		
	}

	@Override
	public void update() {
		List<Media> mediaList = mediaListModel.getMediaList();
		MediaListAdapter adapter = new MediaListAdapter(listView.getContext(),
                R.layout.media_list_item, mediaList);
        listView.setAdapter(adapter);



		
	}
}
