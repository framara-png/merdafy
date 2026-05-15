package vista;

import modelo.*;
import java.util.*;
import controlador.*;
import Ventanas.*;
import Paneles.*;

public class prueba {
	public static GestorCliente gestorCli = new GestorCliente();
	public static controladorDB controladordb = new controladorDB("merdafy");
	public static ArrayList<cliente> clientes;
	public static ArrayList<Musico> musicos;
	public static ArrayList<Podcaster> podcasters;
	public static ArrayList<Album> albums;
	public static ArrayList<Cancion> canciones;
	public static ArrayList<Podcast> podcast;
	public static ArrayList<Playlist> playlists;
	public static ArrayList<Cancion> cancionesPlaylist;
	public static ArrayList<StatisticaAudio> statAudio;
	public static ArrayList<StastisticaCancion> statCancion;
	public static ArrayList<StatisticaPodcast> statPodcast;
	public static ArrayList<StatisticaPlaylist> statPlaylist;
	public static Musico m = new Musico(0, "p", "r", "cant di pini", "solista", null);
	public static cliente c = new cliente(0, "peppe", "vessiccio", "ES", "admin", "admin", "1990-05-15", "2026-05-12",
			true);
	public static Podcaster p = new Podcaster(0, "porcodio", "pop", null, "porcamadonna");
	public static VentanaPrincipal ventana = new VentanaPrincipal();
	public static PanelLogin panelLog;
	public static void main(String[] args) {
	    controladordb.iniciarConexion();
	    ventana.ejecutarVentana();
	   cliente cli = gestorCli.login("carlos_music", "pass123"); 
	gestorCli.inserireCancionesPlaylist(cli);
	System.out.println(cli.getPlaylistCliente().get(1).getCancionesPlaylist().toString());
	  
	  // Album al =controladordb.obtenerAlbumPorNombre("Inicios");
	   //System.out.println(al.toString());
	}
}