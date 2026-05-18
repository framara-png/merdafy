package Ventanas;

import java.util.ArrayList;

import javax.swing.*;
import controlador.*;
import modelo.*;
import Paneles.*;

public class VentanaPrincipal extends JFrame {

	// ================= DB =================
	private controladorDB controladordb;

	// ================= USER =================
	private cliente clientelogeado;

	// ================= SELECTED =================
	private Musico musicoSeleccionado;
	private Podcaster podcasterSeleccionado;
	private Album albumSeleccionado;


	private Podcast podcastSeleccionado;
	private Playlist playlistSelecionada;
	private ArrayList<StastisticaCancion> StatisticaCanciones;

	

	public void setControladordb(controladorDB controladordb) {
		this.controladordb = controladordb;
	}

	public VentanaPrincipal() {

		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Ventana Principal");

		// 🔥 SOLO DB
		controladordb = new controladorDB("merdafy");

		if (!controladordb.iniciarConexion()) {
			JOptionPane.showMessageDialog(this, "Errore connessione DB");
			System.exit(0);
		}
	}

	// ================= GET DB =================
	public controladorDB getControladordb() {
		return controladordb;
	}

	// ================= USER =================
	public cliente getClientelogeado() {
		return clientelogeado;
	}

	public void setClientelogeado(cliente c) {
		this.clientelogeado = c;
	}

	// ================= SELECTED =================
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

	public Playlist getPlaylistSelecionada() {
		return playlistSelecionada;
	}

	public void setPlaylistSelecionada(Playlist p) {
		this.playlistSelecionada = p;
	}

	public ArrayList<StastisticaCancion> getStatisticaCanciones() {
		return StatisticaCanciones;
	}

	public void setStatisticaCanciones(ArrayList<StastisticaCancion> statisticaCanciones) {
		StatisticaCanciones = statisticaCanciones;
	}

	
	
	
	// ================= NAV =================
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

		case "GestionCanciones":
			setContentPane(new PanelGestionCanciones(this, clientelogeado));
			break;

		case "Estatisticas":
			setContentPane(new PanelEstatisticas(this, clientelogeado));
			break;
		
		case "StatCancion":
			setContentPane(new PaneloStatisticaCancion(this, controladordb.StatCancion()));
			break;
		
		case "StatAudio":
			setContentPane(new PaneloStatisticaAudio(this, controladordb.StatAudio()));
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