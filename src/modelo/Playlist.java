package modelo;

import java.util.*;

public class Playlist {
	private int id, idCliente;
	private String titulo,fechaCreacion;
	private ArrayList<Cancion> cancionesPlaylist;
	

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public ArrayList<Cancion> getCancionesPlaylist() {
		return cancionesPlaylist;
	}

	public void setCancionesPlaylist(ArrayList<Cancion> cancionesPlaylist) {
		this.cancionesPlaylist = cancionesPlaylist;
	}

	public Playlist() {

	}

	public Playlist(int id, String titulo, String fechaCreacion, int idCliente) {
		this.id = id;
		this.titulo = titulo;
		this.idCliente = idCliente;
		this.fechaCreacion = fechaCreacion;
		

	}

	@Override
	public String toString() {
		return " \n" + titulo + "";
				
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
