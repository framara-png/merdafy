package modelo;

import java.util.*;

public class Album {
	private int id;
	private String titulo;
	private int duracion;
	private String fechaPub, genero;
	private String foto;
	private int idMusico, numerocanciones;
	ArrayList<Cancion> cancionesAlbum;

	public Album() {

	}

	public Album(int id, String titulo, String fechaPub, String genero, String foto, int idMusico) {
		this.id = id;
		this.titulo = titulo;
		this.duracion = 0;
		this.fechaPub = fechaPub;
		this.genero = genero;
		this.foto = foto;
		this.idMusico = idMusico;
		this.cancionesAlbum = new ArrayList<>();
		this.numerocanciones = cancionesAlbum.size() - 1;
	}

	public int getNumerocanciones() {
		return numerocanciones;
	}

	public void setNumerocanciones(int numerocanciones) {
		this.numerocanciones = numerocanciones;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	@Override
	public String toString() {
		return "Album [id=" + id + ", titulo=" + titulo + ", fechaPub=" + fechaPub + ", genero=" + genero + ", foto="
				+ foto + ", idMusico=" + idMusico + ", cancionesAlbum=" + cancionesAlbum + "]";
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getIdMusico() {
		return idMusico;
	}

	public void setIdMusico(int idMusico) {
		this.idMusico = idMusico;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getFechaPub() {
		return fechaPub;
	}

	public void setFechaPub(String fechaPub) {
		this.fechaPub = fechaPub;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public ArrayList<Cancion> getCancionesAlbum() {
		return cancionesAlbum;
	}

	public void setCancionesAlbum(ArrayList<Cancion> cancionesAlbum) {
		this.cancionesAlbum = cancionesAlbum;
	}
}