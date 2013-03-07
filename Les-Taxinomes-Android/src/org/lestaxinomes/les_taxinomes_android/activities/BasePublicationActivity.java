package org.lestaxinomes.les_taxinomes_android.activities;

import org.lestaxinomes.les_taxinomes_android.utils.LoginUtils;

import android.content.Intent;
import android.os.Bundle;

/**
 * 
 * Common parent of all the activities of the "Publication" part
 * 
 * @author Marie
 */
public class BasePublicationActivity extends BaseActivity {

	/**
	 * When creating the activity, check if the user is loggued. If not, go to
	 * the Login screen
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// if no authorId in Preferences : logout properly and go to the login
		// screen
		if (LoginUtils.getAuthorId(this) == null
				|| LoginUtils.getAuthorId(this).trim().equals("")) {
			LoginUtils.setLoggued(this, false, "", "", "");
			Intent intent = new Intent(this, Login.class);
			startActivity(intent);
			finish();

		}

	}

}
