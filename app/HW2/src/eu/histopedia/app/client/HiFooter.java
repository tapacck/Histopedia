package eu.histopedia.app.client;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;

public class HiFooter extends HLayout {
	public HiFooter() {
		this.setHeight(40);
		this.setBorder(HiApplication.BORDER_STYLE);
		
		mCopyright = new Label("Copyright &copy; 2011 - 2012 Michael Beier. All rights reserved.");
		mCopyright.setAlign(Alignment.CENTER);
		mCopyright.setWidth100();
		this.addMember(mCopyright);
	}
	
	public void setStatusMessage(String pMessage) {
		mCopyright.setTitle(pMessage);
		// TODO timer-based removal, switch back to original text
	}
	
	private Label mCopyright;
}