package org.lestaxinomes.les_taxinomes_android.activities;

import java.io.File;

import org.lestaxinomes.les_taxinomes_android.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Allows to choose beetween Taking the picture through camera (done) or Loading
 * an existing picture from the gallerie (not yet available)
 * 
 * @author Marie
 * 
 */
public class ImagePickActivity extends BasePublicationActivity {

	private static final int REQUEST_CODE = 1;

	private String tmpFilePath = "/tmpTaxinomes";

	/**
	 * Open the native camera activity, with the indication of the tmpfilepath
	 * which has to be used to store the picture
	 */
	public void pickImage() {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(
				MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + tmpFilePath)));

		startActivityForResult(intent, REQUEST_CODE);
	}

	/**
	 * Called when the native camera activity has finished. If picture
	 * successfully taken, starts the PublicationForm Activity with the
	 * indication of the path of the taken picture.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

			Intent intent = new Intent(ImagePickActivity.this,
					PublicationFormActivity.class);

			File fi = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + tmpFilePath);
			intent.putExtra("loadedImageUri", fi.getAbsolutePath());

			startActivity(intent);
			finish();
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pick_image);

		Button tpb = (Button) findViewById(R.id.takephotobutton);
		tpb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pickImage();
			}
		});

	}

}
