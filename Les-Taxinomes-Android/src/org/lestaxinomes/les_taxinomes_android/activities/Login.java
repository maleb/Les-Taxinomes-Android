package org.lestaxinomes.les_taxinomes_android.activities;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.model.UserModel;
import org.lestaxinomes.les_taxinomes_android.utils.LoginUtils;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableUserView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Login Form
 * Allows user to log in
 * Redirects to MyAccount if already loggued
 * 
 * @author Marie
 *
 */
public class Login extends BaseActivity {
	public static final String PREFS_NAME = "LoginPrefs";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		//Check if we successfully logged in before. If we did, redirect to MyAccount
		if (LoginUtils.isLoggued(this)) {
			Intent intent = new Intent(Login.this, MyAccountActivity.class);
			startActivity(intent);
		}
		
		//Handle the see/hide password button
		Button seepasswordButton = (Button) findViewById(R.id.seepasswordbutton);		
		seepasswordButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText password = (EditText) findViewById(R.id.password);

				Button seepasswordButton = (Button) v;

				if (seepasswordButton.getText().equals(
						getResources().getString(R.string.seepassword))) {
					// show the password

					password.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
					seepasswordButton.setText("");
				}

			}
		});

		
		//Handle the login button
		Button b = (Button) findViewById(R.id.loginbutton);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText username = (EditText) findViewById(R.id.login);
				EditText password = (EditText) findViewById(R.id.password);

				if (username.getText().toString().length() > 0
						&& password.getText().toString().length() > 0) {
					UserModel um = new UserModel();
					um.setLogin(username.getText().toString());
					um.setPassword(password.getText().toString());
					um.addView(new UpdatableUserView(v.getRootView(), um));
					um.getConnexionManager().authenticate(um);
				} else {
					TextView error = (TextView) v.getRootView().findViewById(
							R.id.error);
					error.setText(v.getResources().getString(R.string.tryagain));
				}

			}
		});
	}
}