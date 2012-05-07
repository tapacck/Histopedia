package eu.histopedia.app.client;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class HW2App implements EntryPoint {

	public void onModuleLoad() {
		
		IButton tButton = new IButton("Hello World");
		tButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				SC.say("Hello World from SmartGWT");
			}
		});
		
		tButton.draw();
		
		//or
		//RootPanel.get().add(button);
	}
}