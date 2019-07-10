package pl.edu.utp;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MyConnector {
	
	public static Player player;
	static String[] tab;
	
	
	public static void playRadioStream ( String spec ) throws IOException, JavaLayerException
    {
        // Connection
        URLConnection urlConnection = new URL ( spec ).openConnection ();

        // If you have proxy
        //        Properties systemSettings = System.getProperties ();
        //        systemSettings.put ( "proxySet", true );
        //        systemSettings.put ( "http.proxyHost", "host" );
        //        systemSettings.put ( "http.proxyPort", "port" );
        // If you have proxy auth
        //        BASE64Encoder encoder = new BASE64Encoder ();
        //        String encoded = encoder.encode ( ( "login:pass" ).getBytes () );
        //        urlConnection.setRequestProperty ( "Proxy-Authorization", "Basic " + encoded );

        // Connecting
        urlConnection.connect ();

        // Playing
        player = new Player ( urlConnection.getInputStream () );
        player.play();
    }


}
