package zad1;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="tabela_kursow")
public class Tabela_kursow {
	private String numer_tabeli;
	private String data_publikacji;
	private List<Pozycja> lista = new ArrayList<Pozycja>();
	
	@XmlAttribute(name="numer_tabeli")
	public String getnumer_tabeli() {
        return numer_tabeli;
    }
	
    public void setnumer_tabeli(String numer_tabeli) {
        this.numer_tabeli = numer_tabeli;
    }
    
    @XmlAttribute(name="data_publikacji")
	public String getdata_publikacji() {
        return data_publikacji;
    }
	
    public void setdata_publikacji(String data_publikacji) {
        this.data_publikacji = data_publikacji;
    }
	
	 @XmlElements(@XmlElement(name="pozycja"))
	 public List<Pozycja> getlista() {
	        return lista;
	 }
	 
	 public void setlista(List<Pozycja> lista) {
		 this.lista = lista;
	 }
	
}
