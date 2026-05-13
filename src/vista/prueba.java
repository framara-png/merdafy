package vista;

import modelo.*;
import java.util.*;
import controlador.*;

public class prueba {
	public static GestorCliente gestorCli =  new GestorCliente();
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
public static Musico m = new Musico(0,"p", "r", "cant di pini", "solista", null);
public static cliente c = new cliente(0,"peppe", "vessiccio", "ES", "admin", "admin","1990-05-15","2026-05-12",true);
public static Podcaster p = new Podcaster(0,"porcodio","pop", null, "porcamadonna");
public static void main(String[] args) {
		controladordb.iniciarConexion();
		//clientes = controladordb.obtenerClientes();
/**
		podcasters = controladordb.obtenerPodcasters();
		albums = controladordb.obtenerAlbum(1);
		canciones = controladordb.obtenerCanciones(2);
		podcast = controladordb.obtenerPodcasts(6);
		playlists = controladordb.obtenerPlaylists(1);
		cancionesPlaylist = controladordb.obtenerCancionesPlaylist(1);
		musicos = controladordb.obtenerMusicos();
		System.out.println(musicos.toString());
**/
	/**	controladordb.insertarMusico("pino pino", "rock", "cantante di pini", "solista", null);
		controladordb.insertarAlbum("i pini di pino", "2026-03-02", "sempre pini", null, 7);
		controladordb.insertarCancion("ancora pini per pino", null, 160, 13, null);
		controladordb.insertarPodcaster("Abete abeti", "Podcast abeti", "divulgatore di abeti", null);
		controladordb.insertarPodcast("Abete ep1", null, 1600, 0, 2, 8);
		controladordb.insertarPlaylist("odioJava", 1); **/
	//controladordb.insertarCancoinPlaylist(13, 1);

	//controladordb.insertarClients("zio", "peppo", "en", "porcodio1", "123", "1993-9-13",false);// 
		
		
		//statAudio = controladordb.obtenerstataudio();//
		
gestorCli.visualizarMusicos();	
ArrayList<Album> albums = controladordb.obtenerAlbum("Carlos Music");
	
	System.out.println(albums.toString());
 
		

	}

}
