package pl.edu.utp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.border.EmptyBorder;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JSpinner;
import javax.swing.JSlider;
import java.awt.GridLayout;
import java.awt.List;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Label;

public class Frame {

	private JFrame frame;
	private ExecutorService executor;
	int radioIndex = 0;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame window = new Frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		});
	
	}

	/**
	 * Create the application.
	 * @throws JavaLayerException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public Frame() throws IOException, JavaLayerException, InterruptedException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws InterruptedException 
	 */
	private void initialize() throws MalformedURLException, IOException, InterruptedException {
		PanelFunctions e = new PanelFunctions();
		e.getNameStation();
		InternetConnectivity.checkConnection(); // sprawdzenie po��czenia z Internetem
		
		executor = Executors.newSingleThreadExecutor(); //utworzenie puli w�tk�w(1 w�tek);
		

		/*
		 * tablica Naszych stacji muzycznych (adresy URL)
		 */
		
		String[] radiotab = new String[10];
		radiotab[0] = "http://poznan5-6.radio.pionier.net.pl:8000/tuba9-1.mp3"; //Z�ote przeboje
		radiotab[1] = "http://stream.radiobaobab.pl:8000/radiobaobab.mp3"; //Radio Baobab
		radiotab[2] = "http://109.123.116.202:8020/stream"; //Venice Classic Radio
		radiotab[3] = "http://150.254.118.250:8000/meteor-mp3"; //Radio Meteor
		radiotab[4] = "http://195.150.20.243:8000/rmf_gamemusic"; //RMG Gamemusic
		radiotab[5] = "http://streaming.radionomy.com/insidedancefloor"; //Radio Dance
		
		/*
		 * tablica nazw stacji muzycznych
		 */
		
		String[] nameTab = new String[10];
		nameTab[0] = "Z�ote Przeboje";
		nameTab[1] = "Radio Baobab";
		nameTab[2] = "Venice Classic Radio";
		nameTab[3] = "Radio Meteor";
		nameTab[4] = "RMF Gamemusic";
		nameTab[5] = "Radio Dance";
		
			
		frame = new JFrame("Music Player");
		frame.setAlwaysOnTop(false);
		frame.setBounds(100, 100, 796, 353);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); //ustawienie �rodkowej pozycji okna
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel aboutMePanel = new JPanel();
		aboutMePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		FlowLayout fl_aboutMePanel = (FlowLayout) aboutMePanel.getLayout();
		fl_aboutMePanel.setVgap(0);
		fl_aboutMePanel.setHgap(0);
		fl_aboutMePanel.setAlignment(FlowLayout.RIGHT);
		frame.getContentPane().add(aboutMePanel, BorderLayout.NORTH);
		
		/*
		 * Utworzenie przycisku "O projekcie" a tak�e dodanie obs�ugi akcji klikni�cia na niego
		 */
		JButton btnNewButton = new JButton("O projekcie");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImageIcon icon = new ImageIcon("C:\\Users\\mdlen\\eclipse-workspace\\Music Player\\resources\\logo.jpg"); 
				JOptionPane.showMessageDialog(null,"Projekt wykona� : Mateusz Didyk "," O Projekcie :", JOptionPane.INFORMATION_MESSAGE, icon);
			}
		});
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		aboutMePanel.add(btnNewButton);
		
		
		
		JPanel floatingPanel = new JPanel();
		floatingPanel.setBorder(new EmptyBorder(50, 0, 0, 0));
		FlowLayout fl_floatingPanel = (FlowLayout) floatingPanel.getLayout();
		fl_floatingPanel.setHgap(0);
		fl_floatingPanel.setVgap(0);
		frame.getContentPane().add(new PanelFunctions(), BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("New label");
		floatingPanel.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_2.getLayout();
		flowLayout_2.setHgap(15);
		flowLayout_2.setVgap(10);
		panel_2.setBorder(new EmptyBorder(35, 0, 40, 0));
		frame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		
		
		/*
		 * Utworznie przycisku "Play" , dodanie ob�ugi klikni�cia ;
		 * Obs�uga akcji zawiera sprawdzenie czy przycisk jest wci�ni�ty czy te� nie
		 * Je�li jest wci�ni�ty zostaje wywo�ana metoda odpowiedzialna za odtwarzanie muzyki z adresu URL, kt�ra
		 * jest wykonywana w osobnym w�tku a tak�e metoda wy�wietlaj�ca Nam przesuwaj�c� si� nazw� stacji radiowej
		 * Je�li przycisk jest odci�ni�ty (Przycisk Play jest przyciskiem ToggleButton) nast�puje zamkni�cie strumienia,
		 * odtwarzanie muzyki zostaje zatrzymane
		 */
		JToggleButton Play = new JToggleButton("");
		Play.setIcon(new ImageIcon("C:\\Users\\mdlen\\eclipse-workspace\\Music Player\\resources\\playIcon24.png"));
		Play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Play.isSelected() == true) {
					startingRun(radiotab[radioIndex]);
					
					PanelFunctions.nameStation= nameTab[radioIndex];
					frame.getContentPane().add(new PanelFunctions(), BorderLayout.CENTER);
				}
				else {
					closeStream();
				}
				System.out.println(radioIndex);
			}
		});
		
		/*
		 * Utworzenie przycisku "Previous" oraz dodanie obs�ugi klikni�cia ;
		 * Obs�uga klikni�cia zawiera zamkni�cie poprzedniego strumienia, poniewa� prze��czamy stacje
		 * oraz zmniejszenie radioindexu odpowiadaj�cego indexowi tablicy adres�w URL.
		 * Sprawdzamy czy przycisk "Play" jest wci�ni�ty i czy radioindex jest wiekszy rowny 0 jesli tak to
		 * wywolujemy metoda odpowiedzialna za odtworzanie muzyki z adresu URL, kt�ra
		 * jest wykonywana w osobnym w�tku a tak�e metoda wy�wietlaj�ca Nam przesuwaj�c� si� nazw� stacji radiowej
		 * W przypadku gdy przycisk "Play" jest odci�ni�ty wy�wietlamy komunikat, �e radio jest wy��czone
		 * W przypadku gdy "dojedziemy" do 1 stacji wy�wietlany jest komunikat, �e nie ma ju� wi�cej stacji
		 */
		
		JButton Previous = new JButton("");
		Previous.setIcon(new ImageIcon("C:\\Users\\mdlen\\eclipse-workspace\\Music Player\\resources\\previousIcon24.png"));
		Previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeStream();
				radioIndex--;
				
				if(Play.isSelected() == true && radioIndex>=0) {
					startingRun(radiotab[radioIndex]);
					PanelFunctions.nameStation= nameTab[radioIndex];
					
				} else if(Play.isSelected() == false) {
					JOptionPane.showMessageDialog(null, "W��cz radio !");
					radioIndex++;
				}
				
				else {
					JOptionPane.showMessageDialog(null, "Nie ma ju� wi�cej stacji !");
					radioIndex=0;
					startingRun(radiotab[radioIndex]);
				}
				System.out.println(radioIndex);
			}
		});
		panel_2.add(Previous);
		panel_2.add(Play);
		

		/*
		 * Utworzenie przycisku "Next" oraz dodanie obs�ugi klikni�cia ;
		 * Obs�uga klikni�cia zawiera zamkni�cie poprzedniego strumienia, poniewa� prze��czamy stacje
		 * oraz zwi�kszenie radioindexu odpowiadaj�cego indexowi tablicy adres�w URL.
		 * Sprawdzamy czy przycisk "Play" jest wci�ni�ty i czy radioindex jest mniejszy rowny 5 jesli tak to
		 * wywolujemy metoda odpowiedzialna za odtworzanie muzyki z adresu URL, kt�ra
		 * jest wykonywana w osobnym w�tku a tak�e metoda wy�wietlaj�ca Nam przesuwaj�c� si� nazw� stacji radiowej
		 * W przypadku gdy przycisk "Play" jest odci�ni�ty wy�wietlamy komunikat, �e radio jest wy��czone
		 * W przypadku gdy "dojedziemy" do ostatniej stacji wy�wietlany jest komunikat, �e nie ma ju� wi�cej stacji
		 */
		
		JButton Next = new JButton("");
		Next.setIcon(new ImageIcon("C:\\Users\\mdlen\\eclipse-workspace\\Music Player\\resources\\nextIcon24.png"));
		Next.addActionListener(new ActionListener() {
			
			
			
			public void actionPerformed(ActionEvent arg0) {
				closeStream();
				radioIndex++;
				if(Play.isSelected() == true && radioIndex<=5 ) {
					startingRun(radiotab[radioIndex]);
					PanelFunctions.nameStation= nameTab[radioIndex];

					
				} else if(Play.isSelected() == false) {
					JOptionPane.showMessageDialog(null, "W��cz radio !");
					radioIndex--;
				}
				else {
						JOptionPane.showMessageDialog(null, "Nie ma ju� wi�cej stacji !");
						radioIndex=5;
						startingRun(radiotab[radioIndex]);;
				}
				System.out.println(radioIndex);
				}
						
		});
		panel_2.add(Next);
	}
	
	
		// metoda odpowiedzialna za zamkni�cie strumienia
		public void closeStream() {
			MyConnector.player.close();
		
	}
		// metoda uruchamiaj�ca Nam w osobnym w�tku odtwarzanie muzyki
		public void startingRun(String arg) {
			Runnable playRadio = () -> {
				try {
					MyConnector.playRadioStream(arg);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (JavaLayerException e1) {
					e1.printStackTrace();
				}
				
			};
			executor.submit(playRadio);
		}
		
		

}
