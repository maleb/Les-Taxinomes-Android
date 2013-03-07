package org.lestaxinomes.les_taxinomes_android.adapters;

import java.util.List;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.entities.Author;
import org.lestaxinomes.les_taxinomes_android.entities.Media;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableImageView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Populates the medialist view
 * @author Marie
 *
 */
public class MediaListAdapter extends ArrayAdapter<Media> {

	Context context;

	public MediaListAdapter(Context context, int resourceId, List<Media> items) {
		super(context, resourceId, items);
		this.context = context;
	}

	/* private view holder class */
	private class ViewHolder {
		UpdatableImageView updatableImageView;
		TextView txtTitle;
		TextView author;
	}

	
	//create the view from the media data 
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		
		//get the media from the position
		Media rowItem = getItem(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.media_list_item, null);
			holder = new ViewHolder();
			holder.txtTitle = (TextView) convertView
					.findViewById(R.id.mediaListItemTitle);
			holder.author = (TextView) convertView
					.findViewById(R.id.mediaListItemAuthor);
			holder.updatableImageView = new UpdatableImageView();
			holder.updatableImageView.setImageView(((ImageView) convertView
					.findViewById(R.id.mediaListItemImage)));
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		String names = "";
		if (rowItem.getAuthors() != null) {
			for (Author a : rowItem.getAuthors()) {
				names += " " + a.getName();

			}
		}

		holder.author.setText(names);
		holder.txtTitle.setText(rowItem.getTitre());
		holder.updatableImageView.loadImage(rowItem.getVignetteUrl());

		return convertView;
	}

}