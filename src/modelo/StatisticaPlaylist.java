package modelo;

public class StatisticaPlaylist {
	private int idPlaylist, numReproduciones;
	private String titulo;

	public StatisticaPlaylist(int idPlaylist, String titulo,int numReproduciones) {
		this.idPlaylist = idPlaylist;
		this.numReproduciones = numReproduciones;
		this.titulo = titulo;
	}

	@Override
	public String toString() {
		return "StatisticaPlaylist [idPlaylist=" + idPlaylist + ", numReproduciones=" + numReproduciones + ", titulo="
				+ titulo + "]";
	}

	public int getIdPlaylist() {
		return idPlaylist;
	}

	public void setIdPlaylist(int idPlaylist) {
		this.idPlaylist = idPlaylist;
	}

	public int getNumReproduciones() {
		return numReproduciones;
	}

	public void setNumReproduciones(int numReproduciones) {
		this.numReproduciones = numReproduciones;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
