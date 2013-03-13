package org.lestaxinomes.les_taxinomes_android.views;

import org.lestaxinomes.les_taxinomes_android.fragments.MediaListFragment.MediaActivity;
import org.lestaxinomes.les_taxinomes_android.model.CreateMediaModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
/**
 * Redirects to the Detail of the Media, if media creation was OK. or displays an error message.
 * @author Marie
 *
 */
public class UpdatableCreateMediaView implements UpdatableView {

	private View v;
	private CreateMediaModel createMediaModel;
	private ProgressDialog progressDialog;

	public UpdatableCreateMediaView(View v, CreateMediaModel cmm, ProgressDialog progressDialog) {
		this.v = v;
		this.createMediaModel = cmm;
		this.progressDialog = progressDialog;
	}

	@Override
	public void update() {
		if (this.progressDialog!=null){
			this.progressDialog.dismiss();
		}

		if (createMediaModel.getMedia() != null && createMediaModel.getMedia().getId() != null) {
				Activity currentAct = (Activity) this.v.getContext();
				
				

				Intent intent = new Intent(currentAct, MediaActivity.class);
				intent.putExtra("mediaId", createMediaModel.getMedia().getId());
				currentAct.startActivity(intent);
				currentAct.finish();
		}
		else  {
			Toast.makeText(this.v.getContext(), "Erreur lors de la publication", Toast.LENGTH_LONG).show();
		}

	}

}
