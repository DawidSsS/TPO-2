/**
 *
 *  @author Surma Dawid S15063
 *
 */

package zad1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Service {
	
	public String country;
	public String code;
	
	
	public Service(String _country) {
		country = _country;
		
		try {
			Object obj = new JSONParser().parse(new FileReader("DB.json"));
			JSONObject json = (JSONObject) obj;
			JSONArray jsonarray= (JSONArray) json.get("Kraje");
			
			
			Iterator ls = jsonarray.iterator();
			while (ls.hasNext()) {
				json = (JSONObject) ls.next();
				if (json.get("nazwa").toString().equals(_country))
					code = json.get("kod").toString();
			}
				
			//System.out.println(code);
			
		} catch (Exception e) { 
			System.out.println(e);
		}
		
		
	}
	
	public String getWeather(String miasto) {
		
		String ms = miasto;
		
		JSONObject ret =  readJsonFromURL("http://api.openweathermap.org/data/2.5/weather?q="+ms+","+code+"&appid=1be60b7eae101d2c54c0ebd43fad6e99&units=metric");
		
		
		return ret.toString();
	}
	
	public static String getWeather(String miasto, String kod) {
		
		String ms = miasto;
		
		
		JSONObject ret =  readJsonFromURL("http://api.openweathermap.org/data/2.5/weather?q="+ms+","+kod+"&appid=1be60b7eae101d2c54c0ebd43fad6e99&units=metric");
		JSONObject temperatura = (JSONObject) ret.get("main");
		
		return temperatura.get("temp").toString();
	}
	
	
	
	public static JSONObject readJsonFromURL(String url) {
		try {
			InputStream is = new URL(url).openStream();
			BufferedReader bfr = new BufferedReader(new InputStreamReader(is));
			String st = "";
			String bfs;
			while ((bfs = bfr.readLine()) != null) {
				st += bfs;
			}
			
			
			return (JSONObject) new JSONParser().parse(st);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getCurrency(String kraj) {
		//readJsonFromURL("http://data.fixer.io/api/latest?access_key=API_KEY&base=US");
		String waluta = "EUR";
		try {
			JSONObject waluty = (JSONObject) new JSONParser().parse(new FileReader("kraj-waluta.json"));
			waluta = (String) waluty.get(kraj);
			
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		
		return waluta;
		
	}
	
	public Double getRateFor(String waluta) {
		
		String walutaKraju = getCurrency(country);
		JSONObject obj = (JSONObject) readJsonFromURL("http://data.fixer.io/api/latest?access_key=2d449b9d504ab24adafa1c23287be47d&format=1");
		double walutaKrajuwEuro = Double.parseDouble(((JSONObject) obj.get("rates")).get(walutaKraju).toString());
		double walutawEuro = Double.parseDouble(((JSONObject) obj.get("rates")).get(waluta).toString());
		
		return walutaKrajuwEuro/walutawEuro;
	}
	
	public static String getRateFor(String waluta, String kraj) {
		
		String walutaKraju = getCurrency(kraj);
		JSONObject obj = (JSONObject) readJsonFromURL("http://data.fixer.io/api/latest?access_key=2d449b9d504ab24adafa1c23287be47d&format=1");
		double walutaKrajuwEuro = Double.parseDouble(((JSONObject) obj.get("rates")).get(walutaKraju).toString());
		double walutawEuro = Double.parseDouble(((JSONObject) obj.get("rates")).get(waluta).toString());
		
		return walutaKrajuwEuro/walutawEuro + " ("+walutaKraju+"/"+waluta+")";
	}
	
	public Double getNBPRate() {
		try {
			XMLInputFactory xmlif = XMLInputFactory.newInstance();
			InputStream is = new URL("http://www.nbp.pl/kursy/xml/a065z180403.xml").openStream();
			XMLEventReader xmler = xmlif.createXMLEventReader(is);
			Tabela_kursow tabela = null;
			
			while (xmler.hasNext()) {
				XMLEvent event = xmler.nextEvent();
				System.out.println(event);
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return 5.3;
	}
	
	
	
}  
