package controlador;

import modelo.*;
import java.util.*;

public class GestorCliente {
	public controladorDB controladordb = new controladorDB("merdafy");

	// pensar un return diferente, o otro metodo per aceso admin? //
	public boolean Login(String user, String password) {
		controladordb.iniciarConexion();
		ArrayList<cliente> obtenerclientes = controladordb.obtenerClientes();
		for (cliente c : obtenerclientes) {
			if (c.getUsuario().equals(user) && c.getContrasena().equals(password)) {
				cliente clientelog = new cliente(c.getId(), c.getNombre(), c.getApellido(), c.getIdioma(),
						c.getUsuario(), c.getContrasena(), c.getFecNac(), c.getFecReg(), c.isEsPremium());
				c.setPlaylistCliente(controladordb.obtenerPlaylists(c.getId()));
				System.out.println("Login...");
				return true;
//acesso panel cliente//
			} else if (user.equals("admin") && password.equals("admin")) {
				return true;
				// aceso panel admin//
			}
		}
		return false;
	}

	// cambiar print por return//
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

//cambiar print por return//
	public void visualizarCanciones(String nombreAlbum) {
		controladordb.iniciarConexion();
		ArrayList<Cancion> canciones = controladordb.obtenerCanciones(nombreAlbum);
		for (Cancion c : canciones) {
			System.out.println(c.getNombreAudio() + " " + c.durataConvertida());
		}
	}

//cambiare print con return//
	public void visualizarPodcaster() {
		controladordb.iniciarConexion();
		ArrayList<Podcaster> podcasters = controladordb.obtenerPodcasters();
		for (Podcaster p : podcasters) {
			System.out.println(p.getNombreArt());
		}
	}

//cambiare print con return//
	public void visualizarPodcasts(String NombrePodcaster) {
		controladordb.iniciarConexion();
		ArrayList<Podcast> podcasts = controladordb.obtenerPodcasts(NombrePodcaster);
		for (Podcast p : podcasts) {
			System.out.println(p.getNombreAudio() + " " + p.durataConvertida());
		}

	}

	public void visualizarPlatlists(cliente c) {

	}
}
