package modelo;

public class StatisticaPlaylist {
	private int numReproduciones;
	private String user, titulo;

	public StatisticaPlaylist(String user,String titulo,int numReproduciones) {
		this.user = user;
		this.numReproduciones = numReproduciones;
		this.titulo = titulo;
	}

	

	@Override
	public String toString() {
		return "StatisticaPlaylist [numReproduciones=" + numReproduciones + ", user=" + user + ", titulo=" + titulo
				+ "]";
	}



	public String getUser() {
		return user;
	}



	public void setUser(String user) {
		this.user = user;
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
