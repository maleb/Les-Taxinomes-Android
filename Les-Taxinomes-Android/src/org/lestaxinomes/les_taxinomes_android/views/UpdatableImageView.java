package org.lestaxinomes.les_taxinomes_android.views;

import org.lestaxinomes.les_taxinomes_android.utils.ImageViewLoading;

import android.graphics.Bitmap;
import android.widget.ImageView;
/**
 * Loads the image from the url
 * @author Marie
 *
 */
public class UpdatableImageView implements UpdatableView{

	private ImageView imageView;
	private String imageUrl;
	private Bitmap bitmap;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	@Override
	public void update() {
		this.getImageView().setImageBitmap(bitmap);
		
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public void loadImage(String imageUrl){
		
		this.setImageUrl(imageUrl);
		new ImageViewLoading().execute(this);
		
	}

}
