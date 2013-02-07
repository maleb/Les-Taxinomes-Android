package org.lestaxinomes.les_taxinomes_android.dataConnexion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lestaxinomes.les_taxinomes_android.entities.Author;
import org.lestaxinomes.les_taxinomes_android.entities.GIS;
import org.lestaxinomes.les_taxinomes_android.entities.Licence;
import org.lestaxinomes.les_taxinomes_android.entities.Media;
import org.lestaxinomes.les_taxinomes_android.model.AuthorModel;
import org.lestaxinomes.les_taxinomes_android.model.CreateMediaModel;
import org.lestaxinomes.les_taxinomes_android.model.LicenceModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaListModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaModel;
import org.lestaxinomes.les_taxinomes_android.model.Model;
import org.lestaxinomes.les_taxinomes_android.model.UserModel;
import org.lestaxinomes.les_taxinomes_android.utils.Base64;

import android.os.AsyncTask;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;

public class XMLRPCConnexionManagerAsynctask extends
		AsyncTask<Model, Integer, Model> {

	// real site
	 private static final String serverURL =
	 "http://www.lestaxinomes.org/spip.php?action=xmlrpc_serveur";

	// test site
//	private static final String serverURL = "http://taxinomes.arscenic.org/spip.php?action=xmlrpc_serveur";

	private static Object XMLRPCCall(String fonction, Object criteres) {
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

	private Media loadMediaFromCallResult(Map<String, Object> callResult,
			MediaModel mm) {
		Media media = new Media();

		if (callResult != null) {
			media.setId(Integer.parseInt((String) callResult.get("id_article")));
			media.setTitre((String) callResult.get("titre"));
			media.setDescription((String) callResult.get("texte"));
			media.setImageUrl((String) callResult.get("document"));
			media.setVignetteUrl((String) callResult.get("vignette"));
			if (callResult.get("visites") != null) {
				media.setVisites(Integer.parseInt((String) callResult
						.get("visites")));
			}

			if (callResult.get("id_licence") != null) {
				media.setLicenceId(Integer.parseInt((String) callResult
						.get("id_licence")));
			}

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
			if (gisArray != null) {
				for (Object a : gisArray) {
					Map<String, Object> mapGIS = (Map<String, Object>) a;

					GIS gis = new GIS();
					gis.setLatitude(Double.parseDouble((String) mapGIS
							.get("lat")));
					gis.setLongitude(Double.parseDouble((String) mapGIS
							.get("lon")));

					media.setGis(gis);
				}
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

	private Media createMedia(CreateMediaModel cmm) {

		Map<String, Object> criteres = new HashMap<String, Object>();
		criteres.put("login", cmm.getLogin());
		criteres.put("pass", cmm.getPassword());
		criteres.put("statut", "publie");
		criteres.put("titre", cmm.getMedia().getTitre());
		criteres.put("texte", cmm.getMedia().getDescription());
		criteres.put("id_licence", cmm.getMedia().getLicenceId().toString());

		if (cmm.getMedia().getGis() != null) {

			Map<String, Object> gis = new HashMap<String, Object>();
			gis.put("lat",
					Float.toString(cmm.getMedia().getGis().getLatitude()
							.floatValue()));
			gis.put("lon",
					Float.toString(cmm.getMedia().getGis().getLongitude()
							.floatValue()));
			criteres.put("gis", gis);
		}

		Map<String, Object> doc = new HashMap<String, Object>();
		doc.put("name", cmm.getMedia().getTitre() + ".jpg");
		doc.put("type", "image/jpeg");

		String encodedImage = null;
		try {

			encodedImage = Base64.encodeFromFile(cmm.getLocalMediaUri());

		} catch (Exception e) {
			return null;
		}

		doc.put("bits", encodedImage);

		criteres.put("document", doc);

		HashMap<String, Object> callResult = (HashMap<String, Object>) XMLRPCCall(
				"geodiv.creer_media", criteres);
		return loadMediaFromCallResult(callResult, null);

	}

	@SuppressWarnings("unchecked")
	private Media readMedia(MediaModel mm) {
		Map<String, Integer> criteres = new HashMap<String, Integer>();
		criteres.put("id_article", mm.getMedia().getId());
		//criteres.put("vignette_largeur", 400);
		// WindowManager wm = (WindowManager)
		// .getSystemService(Context.WINDOW_SERVICE);
		// Display display = wm.getDefaultDisplay();

		HashMap<String, Object> callResult = (HashMap<String, Object>) XMLRPCCall(
				"geodiv.lire_media", criteres);
		return loadMediaFromCallResult(callResult, mm);

	}

	@SuppressWarnings("unchecked")
	private List<Media> getMediaListByCriteria(Map<String, String> criteria) {
		Object b = XMLRPCCall("geodiv.liste_medias", criteria);
		Object[] c = (Object[]) b;
		List<Media> listMedia = new ArrayList<Media>();
		if (c != null) {
			for (Object o : c) {
				Map<String, Object> m = (Map<String, Object>) o;
				listMedia.add(loadMediaFromCallResult(m, null));

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

			}

			else if (mod instanceof CreateMediaModel) {
				((CreateMediaModel) mod)
						.setMedia(createMedia(((CreateMediaModel) mod)));

				res = mod;

			}

			else if (mod instanceof MediaListModel) {

				((MediaListModel) mod)
						.setMediaList(getMediaListByCriteria(((MediaListModel) mod)
								.getCriteres()));
				res = mod;
			} else if (mod instanceof AuthorModel) {

				Author loadedAuthor = getAuthorById(((AuthorModel) mod)
						.getAuthor().getId());

				((AuthorModel) mod).setAuthor(loadedAuthor);
				res = mod;
			} else if (mod instanceof UserModel) {

				Author loadedAuthor = loadAuthorIfSucessfulAuthentification(
						((UserModel) mod).getLogin(),
						((UserModel) mod).getPassword());

				((UserModel) mod).setAuthor(loadedAuthor);
				res = mod;
			} else if (mod instanceof LicenceModel) {

				((LicenceModel) mod).setLicences(loadLicences());

				res = mod;
			}
		}
		return res;
	}

	private List<Licence> loadLicences() {
		HashMap<String, Object> b = (HashMap<String, Object>) XMLRPCCall(
				"spip.liste_licences", new HashMap<String, Object>());

		List<Licence> list = new ArrayList<Licence>();

		if (b != null) {
			for (Entry<String, Object> i : b.entrySet()) {
				HashMap<String, Object> map = (HashMap<String, Object>) i
						.getValue();

				if (map != null) {
					if (map.get("id") != null && map.get("icon") != null
							&& map.get("description") != null
							&& map.get("link") != null
							&& map.get("abbr") != null
							&& map.get("name") != null) {
						Licence licence = new Licence();
						licence.setId(Integer
								.parseInt(map.get("id").toString()));
						licence.setIconUrl(map.get("icon").toString());
						licence.setDescription(map.get("description")
								.toString());
						licence.setLink(map.get("link").toString());
						licence.setAbbr(map.get("abbr").toString());
						licence.setName(map.get("name").toString());

						list.add(licence);
					}
				}

			}
		}
		return list;
	}

	private Author loadAuthorIfSucessfulAuthentification(String login,
			String password) {
		String[] array = new String[2];
		array[0] = login;
		array[1] = password;

		Object res = XMLRPCCall("spip.auth", array);

		if (res instanceof Boolean) {
			if (!(Boolean) res) {
				// authentification failed
				return null;
			}

		} else if (res instanceof Map<?, ?>) {
			@SuppressWarnings("unchecked")
			Map<String, Object> resMap = (Map<String, Object>) res;

			return createAuthorFromCallResult(resMap);

		}

		return null;

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
