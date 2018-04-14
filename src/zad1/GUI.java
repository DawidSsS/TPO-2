package zad1;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GUI {
	
	JComboBox<ComboItem> wybor_kraj;
	JComboBox<ComboItem> wybor_miasto;
	JComboBox<String> wybor_waluta;
	JSONObject json_pogoda;
	JSONArray jsonarray_kraje;
	JSONObject json_waluty;
	Wikipedia wiki;
	JFXPanel panel_Wikipedia;
	JLabel temperatura;
	JLabel kurs;


	private JFrame frame;

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Border emptyborder = new EmptyBorder(5, 10, 5, 10);
		Border componentborder = new EmptyBorder(0, 5, 0, 5);
		Border wyswietlaczborder = new EmptyBorder(300, 100, 300, 100);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1366, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 2));
		
		JPanel panel_Menu = new JPanel();
		panel.add(panel_Menu);
		panel_Menu.setLayout(new BorderLayout());
		
		JPanel panel_wyboru = new JPanel();
		panel_Menu.add(panel_wyboru, BorderLayout.PAGE_START);
		panel_wyboru.setLayout(new GridLayout(0, 4));
		panel_wyboru.setBorder(emptyborder);
		
		
		wybor_kraj = new JComboBox<ComboItem>();
		panel_wyboru.add(wybor_kraj);
		wybor_kraj.setBorder(componentborder);
		wybor_kraj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				wstawienie_miast();
			}
			
		});
		
		wybor_miasto = new JComboBox<ComboItem>();
		panel_wyboru.add(wybor_miasto);
		wybor_miasto.setBorder(componentborder);
		
		wybor_waluta = new JComboBox<String>();
		panel_wyboru.add(wybor_waluta);
		wybor_waluta.setBorder(componentborder);
		
		JButton Enter = new JButton("Enter");
		panel_wyboru.add(Enter);
		Enter.setBorder(componentborder);
		Enter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				akceptuj();
				
			}
			
		});
		
		JPanel panel_wyswietlacz = new JPanel();
		panel_Menu.add(panel_wyswietlacz, BorderLayout.CENTER);
		panel_wyswietlacz.setLayout(new GridLayout(2, 2));
		panel_wyswietlacz.setBorder(wyswietlaczborder);
		
		JLabel lblTemperatura = new JLabel("Temperatura:        ");
		lblTemperatura.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_wyswietlacz.add(lblTemperatura);
		
		temperatura = new JLabel("0");
		temperatura.setHorizontalAlignment(SwingConstants.LEFT);
		panel_wyswietlacz.add(temperatura);
		
		JLabel lblKurs = new JLabel("Kurs:        ");
		lblKurs.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_wyswietlacz.add(lblKurs);
		
		kurs = new JLabel("0");
		temperatura.setHorizontalAlignment(SwingConstants.LEFT);
		panel_wyswietlacz.add(kurs);
		
		panel_Wikipedia = new JFXPanel();
		panel.add(panel_Wikipedia);
		wiki = new Wikipedia();
		
		wstawienie_krajow();
		wstawienie_miast();
		wstawienie_walut();
		wybor_kraj.setSelectedIndex(19);
		wybor_waluta.setSelectedIndex(4);
		akceptuj();
		
		
	}
	
	public void wstawienie_krajow () {
		
		try {
			Object obj = new JSONParser().parse(new FileReader("DB.json"));
			json_pogoda = (JSONObject) obj;
			jsonarray_kraje= (JSONArray) json_pogoda.get("Kraje");
			
			
			Iterator ls = jsonarray_kraje.iterator();
			while (ls.hasNext()) {
				wybor_kraj.addItem(new ComboItem((JSONObject) ls.next()));
			}
				
			
		} catch (Exception e) { 
			System.out.println(e);
		}
		
	}
	
	public void wstawienie_miast() {
		wybor_miasto.removeAllItems();
		Iterator ls =  ((JSONArray) (((ComboItem) wybor_kraj.getSelectedItem()).getValue().get("miasta"))).iterator();
		while (ls.hasNext()) {
			wybor_miasto.addItem(new ComboItem((JSONObject) ls.next()));
		}
	}
	
	public void wstawienie_walut() {
		try {
			JSONArray obj = (JSONArray) new JSONParser().parse(new FileReader("lista_walut.json"));
			Iterator<JSONObject> ls = obj.iterator();
			
			while (ls.hasNext()) {
				wybor_waluta.addItem(ls.next().get("waluta").toString());
			}
			
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void akceptuj() {
		
		String miasto = ((ComboItem) wybor_miasto.getSelectedItem()).toString();
		String kod = ((ComboItem) wybor_kraj.getSelectedItem()).getValue().get("kod").toString();
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				panel_Wikipedia.setScene(wiki.getWebsite(((ComboItem) wybor_miasto.getSelectedItem()).getValue().get("nazwa").toString()));
				
			}
			
		});
		
		
		temperatura.setText(Service.getWeather(miasto, kod)+"\u00b0C");
		kurs.setText((Service.getRateFor(wybor_waluta.getSelectedItem().toString(), wybor_kraj.getSelectedItem().toString())).toString());
		
	}
	


}
