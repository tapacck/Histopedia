package eu.histopedia.web.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface HistopediaServiceAsync {
	void checkServer(String pInput, AsyncCallback<String> pCallback)
			throws IllegalArgumentException;
}
