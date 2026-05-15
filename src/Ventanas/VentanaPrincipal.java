package Ventanas;

import javax.swing.JFrame;

import Paneles.PanelLogin;
import Paneles.PanelPerfli;
import Paneles.PanelRegistro;
import Paneles.PanelSelecionCliente;
import controlador.*;
import modelo.*;
import java.util.*;
import Paneles.*;

public class VentanaPrincipal extends JFrame {
	private controladorDB controladordb = new controladorDB("merdafy");
	private cliente clientelogeado = new cliente();
	private Musico m = new Musico();
	private Podcaster p = new Podcaster();
	private Album al = new Album();

	public Musico getM() {
		return m;
	}

	public void setM(Musico m) {
		this.m = m;
	}

	public Podcaster getP() {
		return p;
	}

	public void setP(Podcaster p) {
		this.p = p;
	}

	public Album getAl() {
		return al;
	}

	public void setAl(Album al) {
		this.al = al;
	}

	public cliente getClientelogeado() {
		return clientelogeado;
	}

	public void setClientelogeado(cliente clientelogeado) {
		this.clientelogeado = clientelogeado;
	}

	private static final long serialVersionUID = 1L;

	public VentanaPrincipal() {
		controladordb.iniciarConexion();

		setSize(800, 600); // Tamaño
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Operacion de cierre
		setResizable(false); // No redimensionar
		setTitle("Ventana Principal"); // Titulo

	}

	public void cambiarPanel(String nombrePanel) {

		switch (nombrePanel) {
		case "login":
			PanelLogin panelLogin = new PanelLogin(this);
			setContentPane(panelLogin);
			revalidate();
			break;

		case "registro":
			PanelRegistro panelRegistro = new PanelRegistro(this);
			setContentPane(panelRegistro);
			revalidate();
			break;
		case "cliente":
			PanelSelecionCliente panelCliente = new PanelSelecionCliente(this, clientelogeado);
			setContentPane(panelCliente);
			revalidate();
			break;
		case "perfil":
			PanelPerfli panelPerfil = new PanelPerfli(this, clientelogeado);
			setContentPane(panelPerfil);
			revalidate();
			break;
		case "musicos":
			PanelMusicos panelMusicos = new PanelMusicos(this, clientelogeado);
			setContentPane(panelMusicos);
			revalidate();
			break;
		case "artista":
			PanelArtista panelArtista = new PanelArtista(this, clientelogeado, m.getNombreArt());
			setContentPane(panelArtista);
			revalidate();
			break;
		case "album":

			System.out.println("AL DENTRO CAMBIARPANEL = " + al);

			if (al == null) {
				System.out.println("AL NULL");
				return;
			}

			PanelAlbum panelAlbum = new PanelAlbum(this, al.getTitulo(), m.getNombreArt());

			setContentPane(panelAlbum);

			revalidate();

			break;
		}
	}

	public void ejecutarVentana() {
		cambiarPanel("login");
		setVisible(true);
	}

}
