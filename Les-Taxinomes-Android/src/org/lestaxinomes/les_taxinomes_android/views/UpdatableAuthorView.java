package org.lestaxinomes.les_taxinomes_android.views;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.model.AuthorModel;

import android.widget.TextView;
/**
 * Displays the author data in the screen "Details of a media"
 * @author Marie
 *
 */
public class UpdatableAuthorView implements UpdatableView {

	private AuthorModel authorModel;

	private TextView authorNameView;
	private UpdatableImageView avatarView;

	public UpdatableAuthorView(TextView authorNameView,
			UpdatableImageView avatarView, AuthorModel authorModel) {
		this.authorNameView = authorNameView;
		this.avatarView = avatarView;
		this.authorModel = authorModel;
	}

	public AuthorModel getAuthorModel() {
		return authorModel;
	}

	public void setAuthorModel(AuthorModel authorModel) {
		this.authorModel = authorModel;
	}

	public TextView getAuthorView() {
		return authorNameView;
	}

	public void setAuthorView(TextView authorView) {
		this.authorNameView = authorView;
	}

	public UpdatableImageView getAvatarView() {
		return avatarView;
	}

	public void setAvatarView(UpdatableImageView avatarView) {
		this.avatarView = avatarView;
	}

	@Override
	public void update() {
		if (authorModel.getAuthor().getAvatarUrl() != null
				&& !authorModel.getAuthor().getAvatarUrl().trim().equals("")) {
			this.avatarView.loadImage(authorModel.getAuthor().getAvatarUrl());
		}

		authorNameView.setText(this.authorNameView.getContext().getResources()
				.getString(R.string.publishby)
				+ " " + authorModel.getAuthor().getName());
	}

}
