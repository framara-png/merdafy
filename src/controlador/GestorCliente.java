package controlador;

import modelo.*;
import java.util.*;

public class GestorCliente {

	private controladorDB db;

	public GestorCliente(controladorDB db) {
		this.db = db;
	}

	public cliente login(String user, String password) {

		ArrayList<cliente> clientes = db.obtenerClientes();

		for (cliente c : clientes) {
			if (c.getUsuario().equals(user) && c.getContrasena().equals(password)) {

				ArrayList<Playlist> playlists = db.obtenerPlaylists(c.getId());

				c.setPlaylistCliente(playlists);

				return c;
			}
		}

		return null;
	}

	public boolean esAdmin(cliente c) {
		return c != null && c.getUsuario().equals("admin") && c.getContrasena().equals("admin");
	}
}