package org.lestaxinomes.les_taxinomes_android.activities;

import org.lestaxinomes.les_taxinomes_android.R;

import android.os.Bundle;

/**
 * Activity composed by :
 * <ul>
 * <li>only the media list, if portrait</li>
 * <li>the media list and the detail of the selected media, if landscape.</li>
 * </ul>
 * 
 * @author Marie
 * 
 */
public class ConsultationActivity extends BaseConsultationActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consultation);

	}

}
