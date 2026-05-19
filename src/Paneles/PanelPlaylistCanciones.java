package Paneles;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import Ventanas.VentanaPrincipal;
import controlador.GestorCliente;
import modelo.Cancion;
import modelo.Playlist;
import modelo.cliente;

public class PanelPlaylistCanciones extends JPanel {

	private VentanaPrincipal ventana;
	private JPanel panelCanciones;

	private Playlist playlistActual;

	private ArrayList<Cancion> cancionesActuales;

	public PanelPlaylistCanciones(VentanaPrincipal ventana, cliente clienteLogeado, Playlist playlist) {

		this.playlistActual = playlist;
		this.ventana = ventana;
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// head panel
		JPanel top = new JPanel(new BorderLayout());

		JButton btnAtras = new JButton("Atrás");
		JButton btnPerfil = new JButton("Perfil");

		btnAtras.addActionListener(e -> ventana.cambiarPanel("playlist"));
		btnPerfil.addActionListener(e -> ventana.cambiarPanel("perfil"));

		top.add(btnAtras, BorderLayout.WEST);
		top.add(btnPerfil, BorderLayout.EAST);

		add(top, BorderLayout.NORTH);

		// titulo de la playlist
		JLabel lblTitulo = new JLabel("Playlist: " + playlistActual.getTitulo(), SwingConstants.CENTER);

		lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

		add(lblTitulo, BorderLayout.SOUTH);

		// lista canzione playlist
		panelCanciones = new JPanel();
		panelCanciones.setLayout(new BoxLayout(panelCanciones, BoxLayout.Y_AXIS));

		JScrollPane scroll = new JScrollPane(panelCanciones);

		add(scroll, BorderLayout.CENTER);

		// cargo las canciones de la playlist
		cargarCanciones(clienteLogeado);
	}

	// cargo las canciones con un ciclo for que genere un boton por cada cancion asi
	// a secunda de que playlisrt sea y de cuanta
	// canciones tenga tendre tantos botones
	private void cargarCanciones(cliente clienteLogeado) {

		panelCanciones.removeAll();

		cancionesActuales = ventana.getControladordb().obtenerCancionesPlaylist(playlistActual.getTitulo(),
				clienteLogeado.getId());

		if (cancionesActuales == null || cancionesActuales.isEmpty()) {

			JLabel vacio = new JLabel("No hay canciones");
			vacio.setFont(new Font("Arial", Font.BOLD, 18));

			panelCanciones.add(vacio);

		} else {

			for (Cancion c : cancionesActuales) {

				JPanel fila = new JPanel(new BorderLayout());

				fila.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

				// info canziones
				JLabel lblInfo = new JLabel(c.getNombreAudio() + " | " + c.getDurata() + c.getNumRep()

				);

				lblInfo.setFont(new Font("Arial", Font.PLAIN, 18));

				// eleimianr
				JButton btnEliminar = new JButton("-");

				btnEliminar.setFont(new Font("Arial", Font.BOLD, 20));

				btnEliminar.setPreferredSize(new Dimension(50, 40));

				btnEliminar.addActionListener(e -> {

					ventana.getControladordb().eliminarCancionDePlaylist(playlistActual.getId(), c.getId());

					cancionesActuales.remove(c);
					cargarCanciones(clienteLogeado);
				});

				fila.add(lblInfo, BorderLayout.CENTER);
				fila.add(btnEliminar, BorderLayout.EAST);

				panelCanciones.add(fila);
			}
		}

		panelCanciones.revalidate();
		panelCanciones.repaint();
	}
}