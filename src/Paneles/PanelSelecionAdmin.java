package Paneles;

import javax.swing.JButton;
import javax.swing.JPanel;

import Ventanas.VentanaPrincipal;
import controlador.GestorAmdin;
import modelo.cliente;

public class PanelSelecionAdmin extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelSelecionAdmin(VentanaPrincipal ventana, cliente clientelogeado, GestorAmdin gestor) {
		setLayout(null);

		JButton btnAtras = new JButton("atras");
		btnAtras.setBounds(710, 10, 80, 30);

		// 3 BOTONI GRANDI CENTRATI
		JButton btnGestionMusicos = new JButton("gestion de musicos");
		JButton btnGestionPodcasters = new JButton("gestion de podcasters");
		JButton btnGestionAlbum = new JButton("gestion de album");
		JButton btnGestionCanciones = new JButton("gestion de Canciones");
		JButton btnGestionPodcast = new JButton("gestion de Podcast");
		JButton btnEstatistica = new JButton("Estatisticas");
		btnGestionMusicos.setBounds(150, 150, 500, 50);
		btnGestionPodcasters.setBounds(150, 230, 500, 50);
		btnGestionAlbum.setBounds(150, 310, 500, 50);
		btnGestionCanciones.setBounds(150, 390, 500, 50);
		btnGestionPodcast.setBounds(150, 470, 500, 50);
		btnEstatistica.setBounds(150, 550, 500, 50);
		// AÑADIR AL PANEL
	
		this.add(btnAtras);
		this.add(btnGestionMusicos);
		this.add(btnGestionPodcasters);
		this.add(btnGestionAlbum);
		this.add(btnGestionPodcast);
		this.add(btnGestionCanciones);
		this.add(btnEstatistica);

		// LISTENERS
		btnAtras.addActionListener(e -> ventana.cambiarPanel("login"));
		btnGestionMusicos.addActionListener(e -> ventana.cambiarPanel("GestionMusicos"));
		btnGestionPodcasters.addActionListener(e -> ventana.cambiarPanel("GestionPodcasters"));
		btnGestionAlbum.addActionListener(e -> ventana.cambiarPanel("GestionAlbum"));
		btnGestionCanciones.addActionListener(e -> ventana.cambiarPanel("GestionCanciones"));
		btnGestionPodcast.addActionListener(e -> ventana.cambiarPanel("GestionPodcast"));
		btnEstatistica.addActionListener(e -> ventana.cambiarPanel("Estatisticas"));
	}
}
