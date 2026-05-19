package modelo;

import java.sql.Time;

public class Cancion extends audio {
    private int idAlbum;
    private String nombresColaboradores;

    public Cancion(int id, String nombreAudio, String archivo, Time durata, int NumRep, int idAlbum, String nombresColaboradores, String tipo) {
        super(id, nombreAudio, archivo, NumRep, durata, tipo);
        this.idAlbum = idAlbum;
        this.nombresColaboradores = nombresColaboradores;
    }

    

	public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getNombresColaboradores() {
        return nombresColaboradores;
    }

    public void setNombresColaboradores(String nombresColaboradores) {
        this.nombresColaboradores = nombresColaboradores;
    }

    @Override
    public String toString() {
        return "Cancion " + super.toString() + ", nombresColaboradores=" + nombresColaboradores + "]";
    }
}