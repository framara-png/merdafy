package Ventanas;

import java.util.ArrayList;

import javax.swing.*;

import controlador.*;
import modelo.*;
import Paneles.*;

public class VentanaPrincipal extends JFrame {

	private controladorDB controladordb = new controladorDB("merdafy");

	private cliente clientelogeado;

	// oeggetti che mi servono per mantenere costanza tra i pannelli
	private Musico musicoSeleccionado;
	private Podcaster podcasterSeleccionado;
	public ArrayList<Musico> getTodoslosmusicos() {
		return todoslosmusicos;
	}

	public void setTodoslosmusicos(ArrayList<Musico> todoslosmusicos) {
		this.todoslosmusicos = todoslosmusicos;
	}

	public ArrayList<Podcaster> getTodoslospodcasters() {
		return todoslospodcasters;
	}

	public void setTodoslospodcasters(ArrayList<Podcaster> todoslospodcasters) {
		this.todoslospodcasters = todoslospodcasters;
	}

	public ArrayList<Cancion> getTodaslascanciones() {
		return todaslascanciones;
	}

	public void setTodaslascanciones(ArrayList<Cancion> todaslascanciones) {
		this.todaslascanciones = todaslascanciones;
	}

	public ArrayList<Album> getTodoslosAlbumes() {
		return todoslosAlbumes;
	}

	public void setTodoslosAlbumes(ArrayList<Album> todoslosAlbumes) {
		this.todoslosAlbumes = todoslosAlbumes;
	}

	private Album albumSeleccionado;
	private ArrayList<Musico> todoslosmusicos = controladordb.obtenerMusicos();
	private ArrayList<Podcaster> todoslospodcasters;
	private ArrayList<Cancion> todaslascanciones;
	private ArrayList<Album> todoslosAlbumes;
	public controladorDB getControladordb() {
		return controladordb;
	}

	public void setControladordb(controladorDB controladordb) {
		this.controladordb = controladordb;
	}

	public Playlist getPlaylistSelecionada() {
		return playlistSelecionada;
	}

	public void setPlaylistSelecionada(Playlist playlistSelecionada) {
		this.playlistSelecionada = playlistSelecionada;
	}

	private Podcast podcastSeleccionado;
	private Playlist playlistSelecionada;

	public VentanaPrincipal() {
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Ventana Principal");
	}

	// getter setter
	public Musico getMusicoSeleccionado() {
		return musicoSeleccionado;
	}

	public void setMusicoSeleccionado(Musico m) {
		this.musicoSeleccionado = m;
	}

	public Podcaster getPodcasterSeleccionado() {
		return podcasterSeleccionado;
	}

	public void setPodcasterSeleccionado(Podcaster p) {
		this.podcasterSeleccionado = p;
	}

	public Album getAlbumSeleccionado() {
		return albumSeleccionado;
	}

	public void setAlbumSeleccionado(Album a) {
		this.albumSeleccionado = a;
	}

	public Podcast getPodcastSeleccionado() {
		return podcastSeleccionado;
	}

	public void setPodcastSeleccionado(Podcast p) {
		this.podcastSeleccionado = p;
	}

	public cliente getClientelogeado() {
		return clientelogeado;
	}

	public void setClientelogeado(cliente c) {
		this.clientelogeado = c;
	}

	// cambio de paneles
	public void cambiarPanel(String nombrePanel) {

		switch (nombrePanel) {

		case "login":
			setContentPane(new PanelLogin(this));
			break;

		case "cliente":
			setContentPane(new PanelSelecionCliente(this, clientelogeado));
			break;

		case "musicos":
			setContentPane(new PanelMusicos(this, clientelogeado));
			break;

		case "artista":

			if (musicoSeleccionado == null)
				return;

			setContentPane(new PanelArtista(this, clientelogeado, musicoSeleccionado.getNombreArt()));
			break;

		case "podcast":

			if (podcasterSeleccionado == null)
				return;

			setContentPane(new PanelPodcast(this, clientelogeado, podcasterSeleccionado.getNombreArt()));
			break;

		case "album":

			if (albumSeleccionado == null)
				return;

			setContentPane(new PanelAlbum(this, albumSeleccionado.getTitulo(), musicoSeleccionado.getNombreArt()));
			break;
		case "perfil":
			setContentPane(new PanelPerfli(this, clientelogeado));
			break;

		case "registro":
			setContentPane(new PanelRegistro(this));
			break;
		case "podcasters":
			setContentPane(new PanelPodcasters(this, clientelogeado));
			break;
		case "playlists":
			setContentPane(new PanelPlaylist(this, clientelogeado));
			break;
		case "cancionesPlaylist":
			setContentPane(new PanelPlaylistCanciones(this, clientelogeado, playlistSelecionada));
			break;
		case "panelAdmin":
			setContentPane(new PanelSelecionAdmin(this, clientelogeado));
			break;
		
		case "GestionCancion":
			setContentPane(new PanelGestionCanciones(this, clientelogeado));
				break;
		}

		revalidate();
		repaint();
	}

	public void ejecutarVentana() {
		cambiarPanel("login");
		setVisible(true);
	}
}