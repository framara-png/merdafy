package Paneles;

import javax.swing.JButton;
import javax.swing.JPanel;
import Ventanas.VentanaPrincipal;
import modelo.*;
import controlador.*;
public class PanelSelecionCliente extends JPanel {

	private static final long serialVersionUID = 1L;

	public PanelSelecionCliente(VentanaPrincipal ventana, cliente clientelogeado) {
		setLayout(null);
		
		// BOTONI PICCOLI IN ALTO
		JButton btnProfilo = new JButton("perfil");
		JButton btnAtras = new JButton("atras");
		
		btnProfilo.setBounds(10, 10, 80, 30);    // alto a sinistra
		btnAtras.setBounds(710, 10, 80, 30);     // alto a destra
		
		// 3 BOTONI GRANDI CENTRATI
		JButton btnMusicos = new JButton("visualizar musicos");
		JButton btnPodcasters = new JButton("visualizar podcasters");
		JButton btnPlaylists = new JButton("visualizar playlists");
		
		btnMusicos.setBounds(150, 150, 500, 50);
		btnPodcasters.setBounds(150, 230, 500, 50);
		btnPlaylists.setBounds(150, 310, 500, 50);
		
		// AÑADIR AL PANEL
		this.add(btnProfilo);
		this.add(btnAtras);
		this.add(btnMusicos);
		this.add(btnPodcasters);
		this.add(btnPlaylists);
		
		// LISTENERS
		btnAtras.addActionListener(e -> ventana.cambiarPanel("login"));
		btnProfilo.addActionListener(e -> ventana.cambiarPanel("perfil"));
		btnMusicos.addActionListener(e -> ventana.cambiarPanel("musicos"));
		btnPodcasters.addActionListener(e -> ventana.cambiarPanel("podcasters"));
		btnPlaylists.addActionListener(e -> ventana.cambiarPanel("playlists"));
	}
}
