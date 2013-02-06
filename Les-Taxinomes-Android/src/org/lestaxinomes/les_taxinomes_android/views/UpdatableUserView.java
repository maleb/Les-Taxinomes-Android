package org.lestaxinomes.les_taxinomes_android.views;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.model.UserModel;
import org.lestaxinomes.les_taxinomes_android.utils.LoginUtils;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class UpdatableUserView implements UpdatableView {

	private View v;
	private UserModel usermodel;

	public UpdatableUserView(View v, UserModel um) {
		this.v = v;
		this.usermodel = um;
	}

	@Override
	public void update() {

		Activity currentAct = (Activity) this.v.getContext();

		if (usermodel.getAuthor() == null) {
			// not loggued
			LoginUtils.setLoggued(this.v.getContext(), false, "", "");

			TextView error = (TextView) currentAct.findViewById(R.id.error);
			error.setText("Veuillez réessayer");
		} else {
			
			LoginUtils.setLoggued(this.v.getContext(), true,
					usermodel.getLogin(), usermodel.getPassword());
			// reload the current activity

			currentAct.finish();
			currentAct.startActivity(currentAct.getIntent());

		}

	}

}
