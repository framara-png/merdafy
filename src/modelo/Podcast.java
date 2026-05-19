package modelo;

import java.sql.Time;

public class Podcast extends audio {
    private int idPodcaster;
    private int numeroParticipantes;

    public Podcast(int id, String nombreAudio, String archivo, Time durata, int NumRep, int idPodcaster, int numeroParticipantes, String tipo) {
        super(id, nombreAudio, archivo, NumRep, durata, tipo);
        this.idPodcaster = idPodcaster;
        this.numeroParticipantes = numeroParticipantes;
    }

    public int getIdPodcaster() {
        return idPodcaster;
    }

    public void setIdPodcaster(int idPodcaster) {
        this.idPodcaster = idPodcaster;
    }

    public int getNumeroParticipantes() {
        return numeroParticipantes;
    }

    public void setNumeroParticipantes(int numeroParticipantes) {
        this.numeroParticipantes = numeroParticipantes;
    }

    @Override
    public String toString() {
        return "Podcast " + super.toString() + ", numeroParticipantes=" + numeroParticipantes + "]";
    }
}