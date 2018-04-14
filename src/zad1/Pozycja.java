package zad1;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="pozycja")
public class Pozycja {
	private String nazwa_waluty;
	private int przelicznik;
	private String kod_waluty;
	private double kurs_sredni;
	
	@XmlAttribute(name="nazwa_waluty")
	public String getnazwa_waluty() {
        return nazwa_waluty;
    }
	
    public void setnazwa_waluty(String nazwa_waluty) {
        this.nazwa_waluty = nazwa_waluty;
    }
	
	@XmlAttribute(name="przelicznik")
	public int getprzelicznik() {
        return przelicznik;
    }
	
    public void setprzelicznik(String przelicznik) {
        this.przelicznik = Integer.parseInt(przelicznik);
    }

	@XmlAttribute(name="kod_waluty")
	public String getkod_waluty() {
        return kod_waluty;
    }
	
    public void setkod_waluty(String kod_waluty) {
        this.kod_waluty = kod_waluty;
    }
	
	@XmlAttribute(name="kurs_sredni")
	public double getkurs_sredni() {
        return kurs_sredni;
    }
	
    public void setkurs_sredni(String kurs_sredni) {
        this.kurs_sredni = Double.parseDouble(kurs_sredni);
    }
	
}
