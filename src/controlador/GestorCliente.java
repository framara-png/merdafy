package controlador;

import modelo.*;
import java.util.*;

public class GestorCliente {

	public controladorDB controladordb = new controladorDB("merdafy");

	// ================= LOGIN =================
	public cliente login(String user, String password) {
		controladordb.iniciarConexion();
		ArrayList<cliente> clienti = controladordb.obtenerClientes();

		for (cliente c : clienti) {
			if (c.getUsuario().equals(user) && c.getContrasena().equals(password)) {
				controladordb.cerrarConexion();
				return c;
			}
		}

		controladordb.cerrarConexion();
		return null;
	}

	public boolean esAdmin(cliente c) {
		return c != null && c.getUsuario().equals("admin") && c.getContrasena().equals("admin");
	}

	// ================= MUSICI =================
	public ArrayList<Musico> obtenerMusicos() {
		controladordb.iniciarConexion();
		ArrayList<Musico> musicos = controladordb.obtenerMusicos();
		controladordb.cerrarConexion();
		return musicos;
	}

	public Musico obtenerMusicoPorNombre(String nombre) {
		controladordb.iniciarConexion();
		Musico m = controladordb.obtenerMusicoPorNombre(nombre);
		controladordb.cerrarConexion();
		return m;
	}

	// ================= ALBUM =================
	public ArrayList<Album> obtenerAlbum(String nombreArtista) {
		controladordb.iniciarConexion();
		ArrayList<Album> albums = controladordb.obtenerAlbum(nombreArtista);
		controladordb.cerrarConexion();
		return albums;
	}

	public Album obtenerAlbumPorNombre(String titulo) {
		controladordb.iniciarConexion();
		Album a = controladordb.obtenerAlbumPorNombre(titulo);
		controladordb.cerrarConexion();
		return a;
	}

	// ================= CANCIONES =================
	public ArrayList<Cancion> obtenerCancionesAlbum(String nombreAlbum) {
		controladordb.iniciarConexion();
		ArrayList<Cancion> canciones = controladordb.obtenerCanciones(nombreAlbum);
		controladordb.cerrarConexion();
		return canciones;
	}

	// ================= PODCASTERS =================
	public ArrayList<Podcaster> obtenerPodcasters() {
		controladordb.iniciarConexion();
		ArrayList<Podcaster> podcasters = controladordb.obtenerPodcasters();
		controladordb.cerrarConexion();
		return podcasters;
	}

	public Podcaster obtenerPodcasterPorNombre(String nombre) {
		controladordb.iniciarConexion();
		Podcaster p = controladordb.obtenerPodcasterPorNombre(nombre);
		controladordb.cerrarConexion();
		return p;
	}

	// ================= PODCAST =================
	public ArrayList<Podcast> obtenerPodcasts(String nombrePodcaster) {
		controladordb.iniciarConexion();
		ArrayList<Podcast> podcasts = controladordb.obtenerPodcasts(nombrePodcaster);
		controladordb.cerrarConexion();
		return podcasts;
	}

	// ================= PLAYLIST =================
	public ArrayList<Playlist> obtenerPlaylist(cliente c) {
		controladordb.iniciarConexion();
		ArrayList<Playlist> playlists = controladordb.obtenerPlaylists(c.getId());
		controladordb.cerrarConexion();
		return playlists;
	}

	public ArrayList<Cancion> obtenerCancionesPlaylist(String titulo, cliente c) {
		controladordb.iniciarConexion();
		ArrayList<Cancion> canciones = controladordb.obtenerCancionesPlaylist(titulo, c.getId());
		controladordb.cerrarConexion();
		return canciones;
	}

	// ================= PREMIUM =================
	public void pasarPremium(cliente c) {
		c.setEsPremium(true);
		controladordb.actualizarCliente(c);
	}

	public int obtenerRepTotMusico(String NombreMusico) {
		controladordb.iniciarConexion();
		int rep = controladordb.obtenerReproduccionesTotalesMusico(NombreMusico);
		controladordb.cerrarConexion();
		return rep;
	}

	public int obtenerRepTotAlbum(String NombreAlbum, String NombreMusico) {
		controladordb.iniciarConexion();
		int rep = controladordb.obtenerReproduccionesTotalesAlbum(NombreAlbum, NombreMusico);
		controladordb.cerrarConexion();
		return rep;
	}

	public int obtenerRepTotPodcaster(String NombrePodcaster) {
		controladordb.iniciarConexion();
		int rep = controladordb.obtenerReproduccionesTotalPodcaster(NombrePodcaster);
		controladordb.cerrarConexion();
		return rep;
	}
}