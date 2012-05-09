package eu.histopedia.app.client;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.widgets.layout.VLayout;

public class HiApplication implements EntryPoint {
	
	public static String BORDER_STYLE = "1px solid #808080";

	public void onModuleLoad() {

		VLayout tMain = new VLayout();  
		tMain.setWidth100();  
		tMain.setHeight100();  
		tMain.setMargin(5);
		
		tMain.addMember(sHeader = new HiHeader());
		tMain.addMember(new HiWorkingArea());
		tMain.addMember(sFooter = new HiFooter());		
		
		tMain.draw();  
	}
	
	static public HiHeader getHeader() {
		return sHeader;
	}
	
	static public HiFooter getFooter() {
		return sFooter;
	}
	
	static private HiHeader sHeader;
	static private HiFooter sFooter;
}