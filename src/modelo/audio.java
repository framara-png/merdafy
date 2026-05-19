package modelo;

import java.sql.Time;
import java.util.Arrays;

public abstract class audio {
	private int id, NumRep;
	private String archivo;
	private String nombreAudio,tipo;
     private Time durata;
	

	public audio(int id, String nombreAudio, String archivo ,int NumRep, Time durata,String tipo) {
		this.id = id;
		this.nombreAudio = nombreAudio;
		this.NumRep = NumRep;
		this.durata = durata;
		this.archivo = archivo;
		this.tipo= tipo;

	}

	


	public Time getDurata() {
		return durata;
	}




	public void setDurata(Time durata) {
		this.durata = durata;
	}




	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	@Override
	public String toString() {
		return "audio [id=" + id + ", NumRep=" + NumRep + ", durata" + durata + ", archivo="
				+ archivo + ", nombreAudio=" + nombreAudio + ", tipo=" + tipo + "]";
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumRep() {
		return NumRep;
	}

	public void setNumRep(int numRep) {
		NumRep = numRep;
	}



	public String getNombreAudio() {
		return nombreAudio;
	}

	public void setNombreAudio(String nombreAudio) {
		this.nombreAudio = nombreAudio;
	}

//pen

}
