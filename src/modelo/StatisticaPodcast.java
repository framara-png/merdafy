package modelo;

public class StatisticaPodcast {
	private int  numReproduciones;
	private String nombreArtistico, nombreAudio;

	public StatisticaPodcast(String nombreArtistico, String nombreAudio, int numReproduciones) {
		this.nombreArtistico = nombreArtistico;
		this.nombreAudio = nombreAudio;
		this.numReproduciones = numReproduciones;
	}



	@Override
	public String toString() {
		return "StatisticaPodcast [numReproduciones=" + numReproduciones + ", nombreArtistico=" + nombreArtistico
				+ ", nombreAudio=" + nombreAudio + "]";
	}



	public int getNumReproduciones() {
		return numReproduciones;
	}

	public void setNumReproduciones(int numReproduciones) {
		this.numReproduciones = numReproduciones;
	}

	public String getNombreArtistico() {
		return nombreArtistico;
	}

	public void setNombreArtistico(String nombreArtistico) {
		this.nombreArtistico = nombreArtistico;
	}

	public String getNombreAudio() {
		return nombreAudio;
	}

	public void setNombreAudio(String nombreAudio) {
		this.nombreAudio = nombreAudio;
	}
}
