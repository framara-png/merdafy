package modelo;

import java.util.Arrays;

public abstract class audio {
	private int id, NumRep, duratasecondi;
	private String archivo;
	private String nombreAudio,tipo;

	

	public audio(int id, String nombreAudio, String archivo ,int NumRep, int duratasecondi,String tipo) {
		this.id = id;
		this.nombreAudio = nombreAudio;
		this.NumRep = NumRep;
		this.duratasecondi = duratasecondi;
		this.archivo = archivo;
		this.tipo= tipo;

	}

	


	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	@Override
	public String toString() {
		return "audio [id=" + id + ", NumRep=" + NumRep + ", durata" + durataConvertida() + ", archivo="
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

	public int getDuratasecondi() {
		return duratasecondi;
	}

	public void setDuratasecondi(int duratasecondi) {
		this.duratasecondi = duratasecondi;
	}

	public String getNombreAudio() {
		return nombreAudio;
	}

	public void setNombreAudio(String nombreAudio) {
		this.nombreAudio = nombreAudio;
	}

//pensar en hacer un clase de utils donde poner los varios convertidores//
	public String durataConvertida() {
		int minuti = duratasecondi / 60;
		int secondi = duratasecondi % 60;
		return String.format("%d:%02d", minuti, secondi);
	}

}
