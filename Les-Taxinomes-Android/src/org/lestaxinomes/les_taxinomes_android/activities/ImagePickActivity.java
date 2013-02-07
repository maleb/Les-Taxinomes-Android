package org.lestaxinomes.les_taxinomes_android.activities;

import java.io.File;
import java.io.FileNotFoundException;

import org.lestaxinomes.les_taxinomes_android.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ImagePickActivity extends BaseActivity {

	private static final int REQUEST_CODE = 1;

	// private Bitmap bitmap;
	// private Uri mCapturedImageURI;

	public void pickImage(View View) {
		// String fileName = "temp.jpg";
		// ContentValues values = new ContentValues();
		// values.put(MediaStore.Images.Media.TITLE, fileName);
		// mCapturedImageURI = getContentResolver().insert(
		// MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(
				MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/tmpTaxinomes")));

		startActivityForResult(intent, REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

			Intent intent = new Intent(ImagePickActivity.this,
					PublicationActivity.class);

			File fi = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/tmpTaxinomes");
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

				pickImage(v);
			}
		});

	}

}
