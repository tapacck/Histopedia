package eu.histopedia.app.client;

import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class HiWorkingArea extends HLayout {
	public HiWorkingArea() {		
		this.setHeight("100 %");
		this.setBorder(HiApplication.BORDER_STYLE);

		TabSet tTabSet = new TabSet();  
		tTabSet.setTabBarPosition(Side.TOP);  
		tTabSet.setWidth100();
		tTabSet.setHeight100();  
		tTabSet.setMargin(5);

		tTabSet.addTab(new Tab("test"));
		tTabSet.addTab(new Tab("test2"));

		this.addMember(tTabSet);
	}
}