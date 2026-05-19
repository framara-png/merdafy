package controlador;

import modelo.*;
import java.util.*;

public class GestorAmdin {
	private controladorDB db;

	public GestorAmdin(controladorDB db) {
		this.db = db;
	}

	public boolean controladorAudioDobles(String nombre) {

		ArrayList<Cancion> canciones = db.obtenerTodasCanciones();
		ArrayList<Podcast> podcasts = db.obtenerTodosPodcasts();

		for (int i = 0; i < canciones.size(); i++) {
			if (canciones.get(i).getNombreAudio().trim().equalsIgnoreCase(nombre)) {
				return false;
			}
		}

		for (int i = 0; i < podcasts.size(); i++) {
			if (podcasts.get(i).getNombreAudio().trim().equalsIgnoreCase(nombre)) {
				return false;
			}
		}

		return true;
	}

	public boolean controladorArtistaDoble(String nombre) {
		ArrayList<Musico> musicos = db.obtenerMusicos();
		ArrayList<Podcaster> podcasters = db.obtenerPodcasters();
		for (int i = 0; i < musicos.size(); i++) {
			if (musicos.get(i).getNombreArt().equalsIgnoreCase(nombre)) {
				return false;
			}
		}
		for (int i = 0; i < podcasters.size(); i++) {
			if (podcasters.get(i).getNombreArt().equalsIgnoreCase(nombre)) {
				return false;
			}
		}
		return true;
	}

}