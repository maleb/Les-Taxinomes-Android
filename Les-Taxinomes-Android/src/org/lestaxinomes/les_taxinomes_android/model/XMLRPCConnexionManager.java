package org.lestaxinomes.les_taxinomes_android.model;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;

public class XMLRPCConnexionManager extends AsyncTask<Model, Integer, Model>
		implements ConnexionManager {

	private static final String serverURL = "http://www.lestaxinomes.org/spip.php?action=xmlrpc_serveur";

	private static Object XMLRPCCall(String fonction, Map criteres) {
		Object res = null;
		try {
			XMLRPCClient client = new XMLRPCClient(new URL(serverURL));

			res = client.call(fonction, criteres);

		} catch (XMLRPCServerException ex) {
			// The server throw an error.
			ex.printStackTrace();
		} catch (XMLRPCException ex) {
			// An error occured in the client.
			ex.printStackTrace();
		} catch (Exception ex) {
			// Any other exception
			ex.printStackTrace();
		}
		return res;
	}

	private Media readMediaById(Integer mediaId) {
		Map<String, Integer> criteres = new HashMap<String, Integer>();
		criteres.put("id_article", mediaId);
		Media media = new Media();

		HashMap<String, Object> b = (HashMap<String, Object>) XMLRPCCall(
				"geodiv.lire_media", criteres);
		if (b != null) {
			media.setId((Integer) b.get("id"));
			media.setTitre((String) b.get("titre"));
			media.setDescription((String) b.get("texte"));
			media.setImageUrl((String) b.get("vignette"));
		} else {
			media.setId(1);
			media.setTitre("title not found");
			media.setDescription("description not found");
			media.setImageUrl("vignette not found");
		}
		return media;

	}

	@Override
	public void readMedia(MediaModel mm) {
		this.execute(mm);

	}

	@Override
	public List<Media> getMediaList(Map criteres) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onPostExecute(Model model) {
		model.update();
	}

	@Override
	protected Model doInBackground(Model... models) {
		Model res = null;
		if (models != null) {
			Model[] mods = models.clone();
			Model mod = mods[0];
			if (mod instanceof MediaModel) {
				((MediaModel) mod).setMedia(readMediaById(((MediaModel) mod)
						.getMedia().getId()));
				res = mod;
			}
		}
		return res;
	}

}
