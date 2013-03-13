package org.lestaxinomes.les_taxinomes_android.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.lestaxinomes.les_taxinomes_android.views.UpdatableImageView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
/**
 * Loads an image (from an url) in an asynchronous way
 * @author Marie
 *
 */
public class ImageViewLoading extends
		AsyncTask<UpdatableImageView, UpdatableImageView, UpdatableImageView> {

	private UpdatableImageView loadImage(UpdatableImageView imageView) {
		UpdatableImageView i = imageView;
		try {
			Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(
					imageView.getImageUrl()).getContent());
			i.setBitmap(bitmap);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	@Override
	protected void onPostExecute(UpdatableImageView updatableImageView) {
		updatableImageView.update();
	}

	@Override
	protected UpdatableImageView doInBackground(UpdatableImageView... params) {

		UpdatableImageView res = null;
		if (params != null) {
			UpdatableImageView[] uivs = params.clone();
			res= loadImage(uivs[0]);
		}
		return res;
	}

}
