package org.lestaxinomes.les_taxinomes_android.dataConnexion;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lestaxinomes.les_taxinomes_android.entities.Author;
import org.lestaxinomes.les_taxinomes_android.entities.GIS;
import org.lestaxinomes.les_taxinomes_android.entities.Media;
import org.lestaxinomes.les_taxinomes_android.model.AuthorModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaListModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaModel;
import org.lestaxinomes.les_taxinomes_android.model.Model;

import android.os.AsyncTask;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;

public class XMLRPCConnexionManagerAsynctask extends
		AsyncTask<Model, Integer, Model> {

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

	private Media createMediaFromCallResult(Map<String, Object> callResult,
			MediaModel mm) {
		Media media = new Media();

		if (callResult != null) {
			media.setId(Integer.parseInt((String) callResult.get("id_article")));
			media.setTitre((String) callResult.get("titre"));
			media.setDescription((String) callResult.get("texte"));
			media.setImageUrl((String) callResult.get("vignette"));
			media.setVisites(Integer.parseInt((String) callResult
					.get("visites")));

			Date modifDate = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				modifDate = sdf.parse((String) callResult.get("date_modif"));
				media.setModificationDate(modifDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Object[] gisArray = (Object[]) callResult.get("gis");
			for (Object a : gisArray) {
				Map<String, Object> mapGIS = (Map<String, Object>) a;

				GIS gis = new GIS();
				gis.setLatitude(Double.parseDouble((String) mapGIS.get("lat")));
				gis.setLongitude(Double.parseDouble((String) mapGIS.get("lon")));

				media.setGis(gis);
			}

			List<Author> authors = new ArrayList<Author>();

			Object[] auteurs = (Object[]) callResult.get("auteurs");
			if (auteurs != null) {
				for (Object a : auteurs) {
					Map<String, Object> mapAuthor = (Map<String, Object>) a;
					Author author = new Author();

					// for (Entry<String, Object> i : mapAuthor.entrySet()) {
					// System.out.println(i.getKey() + " : "
					// + i.getValue().toString());
					// }
					author.setId(Integer.parseInt((String) mapAuthor
							.get("id_auteur")));
					author.setName((String) mapAuthor.get("nom"));

					authors.add(author);

					if (mm != null) {
						mm.getAuthorModel().setAuthor(author);

						// (new XMLRPCConnexionManager()).loadAuthor(mm
						// .getAuthorModel());
						//
						// authors.add(mm.getAuthorModel().getAuthor());
					}
					//
					// System.out.println( "idAuteur="+mapAuthor.get("id"));
					// System.out.println( "nomAuteur="+mapAuthor.get("nom"));
				}
			}
			media.setAuthors(authors);
			// Map<String,Object>callResult.get("auteurs");
			// media.setAuthor(author);
		}
		return media;

	}

	@SuppressWarnings("unchecked")
	private Media readMedia(MediaModel mm) {
		Map<String, Integer> criteres = new HashMap<String, Integer>();
		criteres.put("id_article", mm.getMedia().getId());
		criteres.put("vignette_largeur", 400);
		// WindowManager wm = (WindowManager)
		// .getSystemService(Context.WINDOW_SERVICE);
		// Display display = wm.getDefaultDisplay();

		HashMap<String, Object> callResult = (HashMap<String, Object>) XMLRPCCall(
				"geodiv.lire_media", criteres);
		return createMediaFromCallResult(callResult, mm);

	}

	@SuppressWarnings("unchecked")
	private List<Media> getMediaListByCriteria(Map<String, String> criteria) {
		Object b = XMLRPCCall("geodiv.liste_medias", criteria);
		Object[] c = (Object[]) b;
		List<Media> listMedia = new ArrayList<Media>();
		if (c != null) {
			for (Object o : c) {
				Map<String, Object> m = (Map<String, Object>) o;
				listMedia.add(createMediaFromCallResult(m, null));

			}
		}
		return listMedia;

	}

	public void loadMedia(MediaModel mm) {
		// loading the media information (for author : only the id and name)
		this.execute(mm);

	}

	@Override
	protected void onPostExecute(Model model) {
		model.update();

		if (model instanceof MediaModel) {

			AsyncTask<Model, Integer, Model> authorAT = new XMLRPCConnexionManagerAsynctask();
			authorAT.execute(((MediaModel) model).getAuthorModel());
		}

	}

	@Override
	protected Model doInBackground(Model... models) {
		Model res = null;
		if (models != null) {
			Model[] mods = models.clone();
			Model mod = mods[0];
			if (mod instanceof MediaModel) {
				((MediaModel) mod).setMedia(readMedia(((MediaModel) mod)));
				res = mod;
			} else if (mod instanceof MediaListModel) {

				((MediaListModel) mod)
						.setMediaList(getMediaListByCriteria(((MediaListModel) mod)
								.getCriteres()));
				res = mod;
			} else if (mod instanceof AuthorModel) {

				Author loadedAuthor = getAuthorById(((AuthorModel) mod)
						.getAuthor().getId());

				((AuthorModel) mod).setAuthor(loadedAuthor);
				res = mod;
			}
		}
		return res;
	}

	private Author getAuthorById(Integer id) {
		Map<String, Integer> criteres = new HashMap<String, Integer>();
		criteres.put("id_auteur", id);
		// WindowManager wm = (WindowManager)
		// .getSystemService(Context.WINDOW_SERVICE);
		// Display display = wm.getDefaultDisplay();

		@SuppressWarnings("unchecked")
		HashMap<String, Object> callResult = (HashMap<String, Object>) XMLRPCCall(
				"spip.lire_auteur", criteres);
		return createAuthorFromCallResult(callResult);
	}

	private Author createAuthorFromCallResult(Map<String, Object> callResult) {
		Author author = new Author();

		if (callResult != null) {
			author.setId(Integer.parseInt((String) callResult.get("id_auteur")));
			author.setName((String) callResult.get("nom"));
			author.setLogoUrl((String) callResult.get("logo"));

		}
		return author;

	}

	public void loadMediaList(MediaListModel mediaListModel) {
		this.execute(mediaListModel);

	}

	public void loadAuthor(AuthorModel authorModel) {
		this.execute(authorModel);

	}

}
