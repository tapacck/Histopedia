package eu.histopedia.web.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("histopedia")
public interface HistopediaService extends RemoteService {
	String checkServer(String name) throws IllegalArgumentException;
}
