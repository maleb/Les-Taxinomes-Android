package org.lestaxinomes.les_taxinomes_android.activities;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableLicenceSingletonView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Displays a picture during 1 second at each launch of the application
 * 
 * @author Marie
 * 
 */
public class SplashActivity extends Activity {
	private static final int STOPSPLASH = 0;

	/**
	 * Default duration for the splash screen (milliseconds)
	 */
	private static final long SPLASHTIME = 1000;

	/**
	 * Handler to close this activity and to start automatically
	 * {@link ConsultationActivity} after <code>SPLASHTIME</code> seconds.
	 */
	private final transient Handler splashHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == STOPSPLASH) {
				final Intent intent = new Intent(SplashActivity.this,
						ConsultationActivity.class);
				startActivity(intent);
				finish();
			}

			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		// load all the utils in the first activity
		UpdatableLicenceSingletonView.getInstance().getLicences();

		final Message msg = new Message();
		msg.what = STOPSPLASH;
		splashHandler.sendMessageDelayed(msg, SPLASHTIME);
	}

}