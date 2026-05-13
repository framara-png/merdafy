
package modelo;

import java.util.*;

public class Musico extends artista {
    private String composicion;  // SOLO questa, le altre sono già nella superclasse
    private ArrayList<Album> albums;

    public Musico(int id, String NombreArt, String genero, String descripcion, String foto, String composicion) {
        super(id, NombreArt, genero, descripcion, foto);
        this.composicion = composicion;
        this.albums = new ArrayList<>(); 
    }

	public String getComposicion() {
		return composicion;
	}

	public void setComposicion(String composicion) {
		this.composicion = composicion;
	}



	@Override
	public String toString() {
		return "Musico [" + super.toString() + ", composicion=" + composicion + ", albums=" + albums + "]";
	}

	

	public String composicion() {
		return composicion;
	}

	public void setComosicion(String composicion) {
		this.composicion=composicion;
	}

	public ArrayList<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}

}
