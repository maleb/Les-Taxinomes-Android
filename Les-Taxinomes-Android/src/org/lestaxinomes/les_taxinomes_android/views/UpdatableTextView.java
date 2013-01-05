package org.lestaxinomes.les_taxinomes_android.views;

import android.widget.TextView;

public class UpdatableTextView implements UpdatableView {

	private TextView tv;

	public UpdatableTextView(TextView tv) {
		this.tv = tv;
	}

	public void update(String txt) {
		tv.setText(txt);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
