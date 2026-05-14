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
			int totalRep = controladordb.obtenerReproduccionesTotalesMusico(m.getNombreArt());
			System.out.println(m.getNombreArt() + " NumReproduciones " + totalRep);
		}
	}

	// cambiar print por return//
	public void visualizarAlbum(String nombreArt) {
		controladordb.iniciarConexion();
		ArrayList<Album> albums = controladordb.obtenerAlbum(nombreArt);
		System.out.println("Discografia de" + nombreArt);
		for (Album a : albums) {
			int totalRep = controladordb.obtenerReproduccionesTotalesAlbum(a.getTitulo(), nombreArt);
			System.out.println(a.getTitulo() + " NumReproduciones " + totalRep);
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
			int totalRep = controladordb.obtenerReproduccionesTotalPodcaster(p.getNombreArt());
			System.out.println(p.getNombreArt() + " " + totalRep);
		}
	}

//cambiare print  con return e usar los print para panales//
	public void visualizarPodcasts(String NombrePodcaster) {
		controladordb.iniciarConexion();
		ArrayList<Podcast> podcasts = controladordb.obtenerPodcasts(NombrePodcaster);
		for (Podcast p : podcasts) {
			System.out.println(p.getNombreAudio() + " " + p.durataConvertida() + "" + p.getNumRep());
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
			System.out.println(ca.getNombreAudio() + " " + ca.durataConvertida() + "" + ca.getNumRep());
		}
	}

	public void pasarPremium(cliente c) {
		c.setEsPremium(true);
		controladordb.actualizarCliente(c);

	}

	public String RegistrarCliente(String nombre, String apellido, String idioma, String usuario, String contrasena,
			String fecNac, boolean esPremium) {


		if (nombre == null || nombre.trim().isEmpty())
			return "El nombre es obligatorio";
		if (apellido == null || apellido.trim().isEmpty())
			return "El apellido es obligatorio";
		if (usuario == null || usuario.trim().isEmpty())
			return "El usuario es obligatorio";
		if (contrasena == null || contrasena.trim().isEmpty())
			return "La contraseña es obligatoria";
		if (fecNac == null || fecNac.trim().isEmpty())
			return "La fecha de nacimiento es obligatoria";

		controladordb.iniciarConexion();


		cliente nuevo = new cliente(0, nombre, apellido, idioma, usuario, contrasena, fecNac, null, esPremium);
		nuevo.setEsPremium(esPremium);

		boolean insertado = controladordb.insertarCliente(nuevo);
		controladordb.cerrarConexion();

		if (insertado) {
			return null; 
		} else {
			return "El usuario '" + usuario + "' ya existe. Elige otro nombre de usuario";
		}
	}
}
