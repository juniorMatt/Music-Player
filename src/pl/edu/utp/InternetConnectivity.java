package pl.edu.utp;

import java.io.IOException;

import javax.swing.JOptionPane;

public class InternetConnectivity {
	
	
	// metoda odpowiedzialna za sprawdzenie po��czenia z sieci� ;
	// Pingujemy google
	public static void checkConnection() throws IOException, InterruptedException {
		Process process = java.lang.Runtime.getRuntime().exec("ping www.google.pl"); 
        int x = process.waitFor(); 
        if (x == 0) { 
            System.out.println("Connection Successful, "
                               + "Output was " + x); 
        } 
        else { 
            System.out.println("Internet Not Connected, "
                               + "Output was " + x); 
            JOptionPane.showMessageDialog(null, "Po��cz si� z Internetem aby s�ucha� muzyki !");
        }
	}


}
