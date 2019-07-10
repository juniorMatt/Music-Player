package pl.edu.utp;

import java.io.IOException;

import javax.swing.JOptionPane;

public class InternetConnectivity {
	
	
	// metoda odpowiedzialna za sprawdzenie po³¹czenia z sieci¹ ;
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
            JOptionPane.showMessageDialog(null, "Po³¹cz siê z Internetem aby s³uchaæ muzyki !");
        }
	}


}
