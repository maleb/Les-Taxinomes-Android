package org.lestaxinomes.les_taxinomes_android.activities;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.entities.Author;
import org.lestaxinomes.les_taxinomes_android.model.AuthorModel;
import org.lestaxinomes.les_taxinomes_android.utils.LoginUtils;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableAuthorView;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableImageView;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableMyAccountView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAccountActivity extends BasePublicationActivity {
	public static final String PREFS_NAME = "LoginPrefs";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_account);
		

		ImageView avatar = (ImageView) findViewById(R.id.welcomeImageView);
		UpdatableImageView uiv = new UpdatableImageView();
		uiv.setImageView(avatar);

		TextView welcome = (TextView) findViewById(R.id.welcomeTextView);
		
		AuthorModel am = new AuthorModel();
		Author author = new Author();
		author.setId(Integer.parseInt(LoginUtils.getAuthorId(this)));
		am.setAuthor(author);
		
		am.addView(new UpdatableMyAccountView(welcome, uiv, am));
		
		am.loadAuthor();
		
		

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
				LoginUtils
						.setLoggued(MyAccountActivity.this, false, "", "", "");

				Intent intent = new Intent(MyAccountActivity.this, Login.class);
				startActivity(intent);
			}
		});

	}

}
