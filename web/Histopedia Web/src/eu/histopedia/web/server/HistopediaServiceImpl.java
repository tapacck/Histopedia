package eu.histopedia.web.server;

import eu.histopedia.web.client.HistopediaService;
import eu.histopedia.web.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class HistopediaServiceImpl extends RemoteServiceServlet implements
HistopediaService {

	public String checkServer(String pInput) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(pInput)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String tServerInfo = getServletContext().getServerInfo();
		String tUserAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		pInput = escapeHtml(pInput);
		tUserAgent = escapeHtml(tUserAgent);

		return "Hello, "+pInput+"!<br><br>I am running "+tServerInfo
				+".<br><br>It looks like you are using:<br>"+tUserAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String tHtml) {
		if (tHtml == null) {
			return null;
		}
		return tHtml.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
