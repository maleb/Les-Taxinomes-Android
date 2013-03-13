package org.lestaxinomes.les_taxinomes_android.activities;

import org.lestaxinomes.les_taxinomes_android.R;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * 
 * Common parent of all the activities of the application
 * Each activity should (maybe indirectly) extends it
 * 
 *  @author Marie
 */
public class BaseActivity extends FragmentActivity {

	/**
	 * Inflate the default menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_consultation, menu);

		return true;
	}

	/**
	 * Handling the menu clicks commun to all the activities
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, ConsultationActivity.class);
			startActivity(intent);
			return true;
		case R.id.medialist:
			Intent intent3 = new Intent(this, ConsultationActivity.class);
			startActivity(intent3);
			return true;
		case R.id.my_account:
			Intent intent2 = new Intent(this, Login.class);
			startActivity(intent2);
			return true;
		case R.id.takephoto:
			Intent intent4 = new Intent(this, ImagePickActivity.class);
			startActivity(intent4);
			return true;
		case R.id.map_filter:
			final Intent intent5 = new Intent(BaseActivity.this,
					MapActivity.class);
			startActivity(intent5);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
