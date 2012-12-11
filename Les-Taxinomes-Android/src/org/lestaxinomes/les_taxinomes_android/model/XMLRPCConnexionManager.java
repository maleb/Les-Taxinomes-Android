package org.lestaxinomes.les_taxinomes_android.model;

import java.net.URL;
import java.util.Map;

import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;

public class XMLRPCConnexionManager {

	private static final String serverURL = "http://www.lestaxinomes.org/spip.php?action=xmlrpc_serveur";

	public static Object XMLRPCCall(String fonction, Map criteres) {
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

}
