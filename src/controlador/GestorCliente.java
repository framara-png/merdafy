package controlador;

import modelo.*;
import java.util.*;

public class GestorCliente {
	public controladorDB controladordb = new controladorDB("merdafy");

	public cliente login(String user, String password) {
		controladordb.iniciarConexion();
		ArrayList<cliente> clienti = controladordb.obtenerClientes();

		for (cliente c : clienti) {
			if (c.getUsuario().equals(user) && c.getContrasena().equals(password)) {
				c.setPlaylistCliente(controladordb.obtenerPlaylists(c.getId()));
				controladordb.cerrarConexion();
				return c;
			}
		}

		controladordb.cerrarConexion();
		return null;
	}

	public boolean esAdmin(cliente c) {
		return c.getUsuario().equals("admin") && c.getContrasena().equals("admin");
	}

	// cambiar print por return e usar los print para panales//
	public void visualizarMusicos() {
		controladordb.iniciarConexion();
		ArrayList<Musico> musicos = controladordb.obtenerMusicos();
		System.out.println("Musicos disponibles");
		for (Musico m : musicos) {
			System.out.println(m.getNombreArt());
		}
	}

	// cambiar print por return//
	public void visualizarAlbum(String nombreArt) {
		controladordb.iniciarConexion();
		ArrayList<Album> albums = controladordb.obtenerAlbum(nombreArt);
		System.out.println("Discografia de" + nombreArt);
		for (Album a : albums) {
			System.out.println(a.getTitulo());
		}

	}

//cambiar print por return e usar los print para panales//
	public void visualizarCancionesAlbum(String nombreAlbum) {
		controladordb.iniciarConexion();
		ArrayList<Cancion> canciones = controladordb.obtenerCanciones(nombreAlbum);
		for (Cancion c : canciones) {
			System.out.println(c.getNombreAudio() + " " + c.durataConvertida());
		}
	}

//cambiare print con return e usar los print para panales//
	public void visualizarPodcaster() {
		controladordb.iniciarConexion();
		ArrayList<Podcaster> podcasters = controladordb.obtenerPodcasters();
		for (Podcaster p : podcasters) {
			System.out.println(p.getNombreArt());
		}
	}

//cambiare print  con return e usar los print para panales//
	public void visualizarPodcasts(String NombrePodcaster) {
		controladordb.iniciarConexion();
		ArrayList<Podcast> podcasts = controladordb.obtenerPodcasts(NombrePodcaster);
		for (Podcast p : podcasts) {
			System.out.println(p.getNombreAudio() + " " + p.durataConvertida());
		}

	}

//cambiar con return//
	public void visualizarPlaylist(cliente c) {
		controladordb.iniciarConexion();
		System.out.println(c.getPlaylistCliente().toString());
	}

//cambiar print con return//
	public void visualizarCancionesPlaylist(String Titulo, cliente c) {
		controladordb.iniciarConexion();
		ArrayList<Cancion> cancionesPlaylist = controladordb.obtenerCancionesPlaylist(Titulo, c.getId());
		for (Cancion ca : cancionesPlaylist) {
			System.out.println(ca.getNombreAudio() + " " + ca.durataConvertida());
		}
	}

	public cliente RegistrarCliente() {
	return null;
	}
}
