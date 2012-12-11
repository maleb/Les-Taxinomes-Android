package org.lestaxinomes.les_taxinomes_android.model;

import java.util.HashMap;
import java.util.Map;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.activities.MainActivity;

import android.os.AsyncTask;

public class ConnexionManager extends AsyncTask<Integer, Integer, String> {

	private MainActivity activity;

	public ConnexionManager(MainActivity activity) {
		this.activity = activity;

	}

	public String readMedia(Integer mediaId) {

		String res = "";
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id_article", mediaId);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> b = (HashMap<String, Object>) XMLRPCConnexionManager
				.XMLRPCCall("geodiv.lire_media", map);

		if (b != null) {
			res = "Mon titre est " + b.get("titre")
					+ " et ma description est : " + b.get("texte");
			// for (Entry<String, Object> o : b.entrySet()) {
			// res += o.getKey() + " : " + o.getValue().toString();
			// }
		} else {
			res = "nothing found";
		}
		return res;

	}

	@Override
	protected void onPostExecute(String result) {
		activity.updateTextView(R.id.mytextview, result);

	}

	@Override
	protected String doInBackground(Integer... mediaId) {
		return readMedia(1);
	}

}
