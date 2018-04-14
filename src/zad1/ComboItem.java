package zad1;

import org.json.simple.JSONObject;

public class ComboItem {
	private String text;
	private JSONObject obj;
	
	public ComboItem(JSONObject o) {
		obj = o;
		text = o.get("nazwa").toString();
	}
	
	public JSONObject getValue() {
		return obj;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
