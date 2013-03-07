package org.lestaxinomes.les_taxinomes_android.dataConnexion;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
import org.lestaxinomes.les_taxinomes_android.model.MediaFullDocumentModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaListModel;
import org.lestaxinomes.les_taxinomes_android.model.MediaModel;
import org.lestaxinomes.les_taxinomes_android.model.Model;
import org.lestaxinomes.les_taxinomes_android.model.UserModel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import de.timroes.axmlrpc.XMLRPCClient;

/**
 * Connects to the SPIP database using XML-RPC in an asynchrone way
 * 
 * @author Marie
 * 
 */
public class XMLRPCConnexionManagerAsynctask extends
		AsyncTask<Model, Integer, Model> {

	/**
	 * the production website URL
	 */
	private static final String serverURL = "http://www.lestaxinomes.org/spip.php?action=xmlrpc_serveur";

	// /**
	// * the test website URL
	// */
	// private static final String serverURL =
	// "http://taxinomes.arscenic.org/spip.php?action=xmlrpc_serveur";

	/**
	 * Generic way for making a call
	 * 
	 * @param fonction
	 *            the method to call
	 * @param criteres
	 *            the arguments for the method
	 * @param login
	 *            if possible the login of the user
	 * @param password
	 *            if possible the password of the user
	 * @return the response of the server (as an Object)
	 */
	private static Object XMLRPCCall(String fonction, Object criteres,
			String login, String password) {
		Object res = null;
		try {
			XMLRPCClient client = new XMLRPCClient(new URL(serverURL));

			if (login != null && password != null) {
				client.setLoginData(login, password);
			}

			res = client.call(fonction, criteres);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return res;
	}

	/**
	 * Uses the response of the server for updating the views
	 */
	@Override
	protected void onPostExecute(Model model) {
		model.update();

		if (model instanceof MediaModel) {

			AsyncTask<Model, Integer, Model> authorAT = new XMLRPCConnexionManagerAsynctask();
			authorAT.execute(((MediaModel) model).getAuthorModel());
		} else if (model instanceof CreateMediaModel) {
			File fi = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/tmpTaxinomes");
			fi.delete();
		}

	}

	/**
	 * Call the right method depending on the Model asking
	 */
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

			else if (mod instanceof MediaFullDocumentModel) {
				((MediaFullDocumentModel) mod)
						.setMedia(loadMediaFullDocument(((MediaFullDocumentModel) mod)));
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

	/**
	 * Transforms the response of the server in an object Media
	 * 
	 * @param callResult
	 *            the response received from the server
	 * @param mm
	 *            if pertinent : the mediaModel which will receive the loaded
	 *            author
	 * @return the Media
	 */
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

			// date
			Date modifDate = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				modifDate = sdf.parse((String) callResult.get("date_modif"));
				media.setModificationDate(modifDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// geolocalisation
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

			// authors
			List<Author> authors = new ArrayList<Author>();
			Object[] auteurs = (Object[]) callResult.get("auteurs");
			if (auteurs != null) {
				for (Object a : auteurs) {
					Map<String, Object> mapAuthor = (Map<String, Object>) a;
					Author author = new Author();

					author.setId(Integer.parseInt((String) mapAuthor
							.get("id_auteur")));
					author.setName((String) mapAuthor.get("nom"));

					authors.add(author);

					if (mm != null) {
						mm.getAuthorModel().setAuthor(author);

					}
				}
			}
			media.setAuthors(authors);
		}
		return media;

	}

	/**
	 * Create a new media in the database with the media data from the
	 * CreateMediaModel
	 * 
	 * @param cmm
	 *            the createMediaModel containing the media to create
	 * @return the created Media (with the ID given by the database)
	 */
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
			Double latitude = cmm.getMedia().getGis().getLatitude();
			Double longitude = cmm.getMedia().getGis().getLongitude();

			gis.put("lat", Float.toString(latitude.floatValue()));
			gis.put("lon", Float.toString(longitude.floatValue()));
			criteres.put("gis", gis);
		}

		Map<String, Object> doc = new HashMap<String, Object>();
		doc.put("name", cmm.getMedia().getTitre() + ".jpg");
		doc.put("type", "image/jpeg");

		// Encode image in base64 - reduced size for not having Out of memory
		// errors
		String encodedImage = null;
		try {

			Options options = new BitmapFactory.Options();
			options.inSampleSize = 2;
			Bitmap bm = BitmapFactory.decodeFile(cmm.getLocalMediaUri(),
					options);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);
			byte[] b = baos.toByteArray();
			encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		doc.put("bits", encodedImage);

		criteres.put("document", doc);

		HashMap<String, Object> callResult = (HashMap<String, Object>) XMLRPCCall(
				"geodiv.creer_media", criteres, cmm.getLogin(),
				cmm.getPassword());
		return loadMediaFromCallResult(callResult, null);

	}

	/**
	 * Get the media from the database, with its document in full size
	 * 
	 * @param mm
	 *            the MediaFullDocumentModel containing the media with an id
	 * @return the Media
	 */
	@SuppressWarnings("unchecked")
	private Media loadMediaFullDocument(MediaFullDocumentModel mm) {
		Map<String, Integer> criteres = new HashMap<String, Integer>();
		criteres.put("id_article", mm.getMedia().getId());

		HashMap<String, Object> callResult = (HashMap<String, Object>) XMLRPCCall(
				"geodiv.lire_media", criteres, null, null);
		return loadMediaFromCallResult(callResult, null);

	}

	/**
	 * Get the media from the database, with its document in size 600
	 * 
	 * @param mm
	 *            the MediaModel containing the media with an id
	 * @return the Media
	 */
	@SuppressWarnings("unchecked")
	private Media readMedia(MediaModel mm) {
		Map<String, Integer> criteres = new HashMap<String, Integer>();
		criteres.put("id_article", mm.getMedia().getId());
		criteres.put("document_largeur", 600);

		HashMap<String, Object> callResult = (HashMap<String, Object>) XMLRPCCall(
				"geodiv.lire_media", criteres, null, null);
		return loadMediaFromCallResult(callResult, mm);

	}

	/**
	 * Get the list of the medias from the database respecting the criterias
	 * 
	 * @param criteria
	 *            the map of criteria
	 * @return the list of medias
	 */
	@SuppressWarnings("unchecked")
	private List<Media> getMediaListByCriteria(Map<String, String> criteria) {
		Object b = XMLRPCCall("geodiv.liste_medias", criteria, null, null);
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

	/**
	 * Read the license list from the database
	 * 
	 * @return the license list
	 */
	private List<Licence> loadLicences() {
		HashMap<String, Object> b = (HashMap<String, Object>) XMLRPCCall(
				"spip.liste_licences", new HashMap<String, Object>(), null,
				null);

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

	/**
	 * Try to connect with login/password, if successful return the data of the
	 * logged author, or return null
	 * 
	 * @param login
	 *            the login of the user
	 * @param password
	 *            the password of the user
	 * @return the Author if succesfully authenticate, or null
	 */
	private Author loadAuthorIfSucessfulAuthentification(String login,
			String password) {
		String[] array = new String[2];
		array[0] = login;
		array[1] = password;

		Object res = XMLRPCCall("spip.auth", array, null, null);

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

	/**
	 * get the Author from the database by its id
	 * 
	 * @param id
	 *            the authorId
	 * @return the Author
	 */
	private Author getAuthorById(Integer id) {
		Map<String, Integer> criteres = new HashMap<String, Integer>();
		criteres.put("id_auteur", id);

		@SuppressWarnings("unchecked")
		HashMap<String, Object> callResult = (HashMap<String, Object>) XMLRPCCall(
				"spip.lire_auteur", criteres, null, null);
		return createAuthorFromCallResult(callResult);
	}

	/**
	 * Transforms the response of the server in an object Author
	 * 
	 * @param callResult
	 *            th response from the server
	 * @return the Author
	 */
	private Author createAuthorFromCallResult(Map<String, Object> callResult) {
		Author author = new Author();

		if (callResult != null) {
			author.setId(Integer.parseInt((String) callResult.get("id_auteur")));
			author.setName((String) callResult.get("nom"));
			author.setAvatarUrl((String) callResult.get("logo"));

		}
		return author;

	}

}
