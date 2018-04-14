package zad1;

import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Wikipedia {

	WebView wView;
	WebEngine wEngine;
	
	public Scene getWebsite(String strona) {
		wView = new WebView();
		wView.setContextMenuEnabled(false);
		wView.setZoom(0.8);
		wEngine = wView.getEngine();
		wEngine.load("https://en.wikipedia.org/wiki/"+strona);
		
		return new Scene(wView);
	}
	
}
