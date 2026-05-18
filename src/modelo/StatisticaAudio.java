package modelo;
 
public class StatisticaAudio {
private int  reproducioines;
private String nombreAudio, nombreArtista;
 


public StatisticaAudio(String nombreAudio, String nombreArtista,int reproduciones) {
	this.nombreArtista = nombreArtista;
	this.reproducioines = reproduciones;
	this.nombreAudio = nombreAudio;
	
	
}



@Override
public String toString() {
	return "StatisticaAudio [reproducioines=" + reproducioines + ", nombreAudio=" + nombreAudio + ", nombreArtista="
			+ nombreArtista + "]";
}



public String getNombreArtista() {
	return nombreArtista;
}



public void setNombreArtista(String nombreArtista) {
	this.nombreArtista = nombreArtista;
}



public int getReproducioines() {
	return reproducioines;
}

public void setReproducioines(int reproducioines) {
	this.reproducioines = reproducioines;
}

public String getNombreAudio() {
	return nombreAudio;
}

public void setNombreAudio(String nombreAudio) {
	this.nombreAudio = nombreAudio;
}

}