package org.lestaxinomes.les_taxinomes_android.activities;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.utils.LoginUtils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyAccountActivity extends BaseActivity {
	public static final String PREFS_NAME = "LoginPrefs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_account);

		Button pb = (Button) findViewById(R.id.publicationbutton);
		pb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MyAccountActivity.this,
						ImagePickActivity.class);
				startActivity(intent);
			}
		});

		Button b = (Button) findViewById(R.id.logoutbutton);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginUtils.setLoggued(MyAccountActivity.this, false,"","");

				Intent intent = new Intent(MyAccountActivity.this, Login.class);
				startActivity(intent);
			}
		});

	}

}
