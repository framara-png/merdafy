package modelo;

import java.util.Arrays;

public abstract class artista {
	private int id;
	private String NombreArt, genero, descripcion;
	private String foto;

	public artista() {

	}

	public artista(int id, String NombreArt, String genero, String descripcion, String foto) {
		this.id = id;
		this.NombreArt = NombreArt;
		this.genero = genero;
		this.descripcion = descripcion;
		this.foto = foto;

	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	@Override
	public String toString() {
		return "artista [id=" + id + ", NombreArt=" + NombreArt + ", genero=" + genero + ", descripcion=" + descripcion
				+ ", foto=" + foto + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreArt() {
		return NombreArt;
	}

	public void setNombreArt(String nombreArt) {
		NombreArt = nombreArt;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
}
