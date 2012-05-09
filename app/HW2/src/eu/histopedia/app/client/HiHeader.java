package eu.histopedia.app.client;

import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;

public class HiHeader extends HLayout {	
	public HiHeader() {
		this.setHeight(50);
		this.setBorder(HiApplication.BORDER_STYLE);
		
		Label tSpacer = new Label();
		tSpacer.setWidth100();
		this.addMember(tSpacer);
		
        Img tLogo = new Img("histopedia.png", 116, 80);  
        tLogo.setImageType(ImageStyle.NORMAL);  
        this.addMember(tLogo); 
	}
}