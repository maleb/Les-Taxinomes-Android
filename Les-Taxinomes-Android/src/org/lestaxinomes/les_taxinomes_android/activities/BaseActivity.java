package org.lestaxinomes.les_taxinomes_android.activities;

import org.lestaxinomes.les_taxinomes_android.R;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class BaseActivity extends FragmentActivity {

	private int widthOfView;

	public int getWidthOfView(int parentViewId, final int viewId) {
		int res = 0;
		if (findViewById(parentViewId) != null) {
			findViewById(parentViewId).getViewTreeObserver()
					.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
						@Override
						public void onGlobalLayout() {
							if (findViewById(viewId) != null) {
								setWidthOfView(findViewById(viewId).getWidth());
							}
						}
					});
		}
		return res;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_consultation, menu);
		

		return true;
	}

	private void openMapFilter() {
		final Intent intent = new Intent(BaseActivity.this, MapActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, ConsultationActivity.class);
			startActivity(intent);
			return true;
		case R.id.map_filter:
			openMapFilter();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public int getWidthOfView() {
		return widthOfView;
	}

	public void setWidthOfView(int widthOfView) {
		this.widthOfView = widthOfView;
	}

}
