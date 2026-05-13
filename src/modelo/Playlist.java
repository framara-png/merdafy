package modelo;

import java.util.*;

public class Playlist {
	private int id, idCliente;
	private String titulo,fechaCreacion;
	

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
