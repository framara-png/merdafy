package Paneles;
import javax.swing.JButton;
import javax.swing.JPanel;
import Ventanas.VentanaPrincipal;
import modelo.cliente;

public class PanelEstatisticas extends JPanel {
	private static final long serialVersionUID = 1L;
	public PanelEstatisticas(VentanaPrincipal ventana, cliente clientelogeado) {
		setLayout(null);

		// BOTONI PICCOLI IN ALTO
		JButton btnProfilo = new JButton("perfil");
		JButton btnAtras = new JButton("atras");

		btnProfilo.setBounds(10, 10, 80, 30); // alto a sinistra
		btnAtras.setBounds(710, 10, 80, 30); // alto a destra

		// 3 BOTONI GRANDI CENTRATI
		JButton btnStatAudio = new JButton("Audios mas escuchados");
		JButton btnStatCanciones = new JButton("Canciones Favoritas");
		JButton btnStatPodcast = new JButton("Podcast Favoritos");
		JButton btnStatPlaylist = new JButton("Playlist favoritas");


		btnStatAudio.setBounds(150, 150, 500, 50);
		btnStatCanciones.setBounds(150, 230, 500, 50);
		btnStatPodcast.setBounds(150, 310, 500, 50);
		btnStatPlaylist.setBounds(150, 390, 500, 50);
			
		// AÑADIR AL PANEL
		this.add(btnProfilo);
		this.add(btnAtras);
		this.add(btnStatAudio);
		this.add(btnStatCanciones);
		this.add(btnStatPodcast);
		this.add(btnStatPlaylist);
	

		// LISTENERS
		btnAtras.addActionListener(e -> ventana.cambiarPanel("login"));
		btnProfilo.addActionListener(e -> ventana.cambiarPanel("perfil"));
		btnStatAudio.addActionListener(e -> ventana.cambiarPanel("StatAudio"));
		btnStatCanciones.addActionListener(e -> ventana.cambiarPanel("StatCancion"));
		btnStatPodcast.addActionListener(e -> ventana.cambiarPanel("StatPodcast"));
		btnStatPlaylist.addActionListener(e -> ventana.cambiarPanel("StatPlaylist"));
		
		
	}





}
