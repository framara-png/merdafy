package modelo;

import java.util.*;

public class Podcaster extends artista {
    private ArrayList<Podcast> podcasts;

    public Podcaster(int id, String nombreArt, String genero, String descripcion, String foto) {
        super(id, nombreArt, genero, descripcion, foto);
        this.podcasts = new ArrayList<>();
    }

    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(ArrayList<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    @Override
    public String toString() {
        return "Podcaster [" + super.toString() + ", podcasts=" + podcasts + "]";
    }
}