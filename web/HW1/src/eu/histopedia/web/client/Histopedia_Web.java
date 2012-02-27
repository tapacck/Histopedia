package eu.histopedia.web.client;

import eu.histopedia.web.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Histopedia_Web implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final HistopediaServiceAsync mHistopediaService = GWT
			.create(HistopediaService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button tSendButton = new Button("Send");
		final TextBox tNameField = new TextBox();
		tNameField.setText("GWT User");
		final Label tErrorLabel = new Label();

		// We can add style names to widgets
		tSendButton.addStyleName("tSendButton");

		// Add the tNameField and tSendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("tNameFieldContainer").add(tNameField);
		RootPanel.get("tSendButtonContainer").add(tSendButton);
		RootPanel.get("tErrorLabelContainer").add(tErrorLabel);

		// Focus the cursor on the name field when the app loads
		tNameField.setFocus(true);
		tNameField.selectAll();

		// Create the popup dialog box
		final DialogBox tDialogBox = new DialogBox();
		tDialogBox.setText("Remote Procedure Call");
		tDialogBox.setAnimationEnabled(true);
		final Button tCloseButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		tCloseButton.getElement().setId("tCloseButton");
		final Label tTextToServerLabel = new Label();
		final HTML tServerResponseLabel = new HTML();
		VerticalPanel tDialogVPanel = new VerticalPanel();
		tDialogVPanel.addStyleName("dialogVPanel");
		tDialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		tDialogVPanel.add(tTextToServerLabel);
		tDialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		tDialogVPanel.add(tServerResponseLabel);
		tDialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		tDialogVPanel.add(tCloseButton);
		tDialogBox.setWidget(tDialogVPanel);

		// Add a handler to close the tDialogBox
		tCloseButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				tDialogBox.hide();
				tSendButton.setEnabled(true);
				tSendButton.setFocus(true);
			}
		});

		// Create a handler for the tSendButton and tNameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the tSendButton.
			 */
			public void onClick(ClickEvent pEvent) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the tNameField.
			 */
			public void onKeyUp(KeyUpEvent pEvent) {
				if (pEvent.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the tNameField to the server and wait for a response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				tErrorLabel.setText("");
				String tTextToServer = tNameField.getText();
				if (!FieldVerifier.isValidName(tTextToServer)) {
					tErrorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				tSendButton.setEnabled(false);
				tTextToServerLabel.setText(tTextToServer);
				tServerResponseLabel.setText("");
				mHistopediaService.checkServer(tTextToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable pCaught) {
								// Show the RPC error message to the user
								tDialogBox
										.setText("Remote Procedure Call - Failure");
								tServerResponseLabel
										.addStyleName("tServerResponseLabelError");
								tServerResponseLabel.setHTML(SERVER_ERROR);
								tDialogBox.center();
								tCloseButton.setFocus(true);
							}

							public void onSuccess(String pResult) {
								tDialogBox.setText("Remote Procedure Call");
								tServerResponseLabel
										.removeStyleName("tServerResponseLabelError");
								tServerResponseLabel.setHTML(pResult);
								tDialogBox.center();
								tCloseButton.setFocus(true);
							}
						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler tHandler = new MyHandler();
		tSendButton.addClickHandler(tHandler);
		tNameField.addKeyUpHandler(tHandler);
	}
}
