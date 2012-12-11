package org.lestaxinomes.les_taxinomes_android.activities;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.model.ConnexionManager;
import org.lestaxinomes.les_taxinomes_android.model.XMLRPCConnexionManager;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ConnexionManager cM = new ConnexionManager(this);
		cM.execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void updateTextView(int viewId, String newText) {
		textView = (TextView) findViewById(viewId);
		textView.setText(newText);

	}

}
