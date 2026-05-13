package controlador;

import modelo.*;
import java.util.*;

public class GestorCliente {
	public controladorDB controladordb = new controladorDB("merdafy");

	public boolean Login(String user, String password) {
		controladordb.iniciarConexion();
		ArrayList<cliente> obtenerclientes = controladordb.obtenerClientes();
		for (cliente c : obtenerclientes) {
			if (c.getUsuario().equals(user) && c.getContrasena().equals(password)) {

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

	public void visualizarMusicos() {
		controladordb.iniciarConexion();
		ArrayList<Musico> musicos = controladordb.obtenerMusicos();
		System.out.println("Musicos disponibles");
		for (Musico m : musicos) {
			System.out.println(m.getNombreArt());
		}
	}

public void visualizarAlbum() {
	controladordb.iniciarConexion();
	A
}
}
