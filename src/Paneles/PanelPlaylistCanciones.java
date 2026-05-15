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

	private GestorCliente gestor = new GestorCliente();

	private JPanel panelCanciones;

	private Playlist playlistActual;

	private ArrayList<Cancion> cancionesActuales;

	public PanelPlaylistCanciones(VentanaPrincipal ventana, cliente clienteLogeado, Playlist playlist) {

		this.playlistActual = playlist;

		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// ================= TOP =================
		JPanel top = new JPanel(new BorderLayout());

		JButton btnAtras = new JButton("Atrás");
		JButton btnPerfil = new JButton("Perfil");

		btnAtras.addActionListener(e -> ventana.cambiarPanel("playlist"));
		btnPerfil.addActionListener(e -> ventana.cambiarPanel("perfil"));

		top.add(btnAtras, BorderLayout.WEST);
		top.add(btnPerfil, BorderLayout.EAST);

		add(top, BorderLayout.NORTH);

		// ================= TITULO =================
		JLabel lblTitulo = new JLabel("Playlist: " + playlistActual.getTitulo(), SwingConstants.CENTER);

		lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

		add(lblTitulo, BorderLayout.SOUTH);

		// ================= PANEL CANCIONES =================
		panelCanciones = new JPanel();
		panelCanciones.setLayout(new BoxLayout(panelCanciones, BoxLayout.Y_AXIS));

		JScrollPane scroll = new JScrollPane(panelCanciones);

		add(scroll, BorderLayout.CENTER);

		// ================= LOAD =================
		cargarCanciones(clienteLogeado);
	}

	// ================= CARGAR CANCIONES =================
	private void cargarCanciones(cliente clienteLogeado) {

        panelCanciones.removeAll();

        cancionesActuales = gestor.obtenerCancionesPlaylist(
                playlistActual.getTitulo(),
                clienteLogeado
        );

        if (cancionesActuales == null || cancionesActuales.isEmpty()) {

            JLabel vacio = new JLabel("No hay canciones");
            vacio.setFont(new Font("Arial", Font.BOLD, 18));

            panelCanciones.add(vacio);

        } else {

            for (Cancion c : cancionesActuales) {

                JPanel fila = new JPanel(new BorderLayout());

                fila.setBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                );

                // ================= INFO =================
                JLabel lblInfo = new JLabel(
                        c.getNombreAudio()
                        + " | "
                        + c.durataConvertida()
                );

                lblInfo.setFont(new Font("Arial", Font.PLAIN, 18));

                // ================= BOTON ELIMINAR =================
                JButton btnEliminar = new JButton("-");

                btnEliminar.setFont(new Font("Arial", Font.BOLD, 20));

                btnEliminar.setPreferredSize(new Dimension(50, 40));

                btnEliminar.addActionListener(e -> {

                    // ELIMINAR DE DB
                    gestor.controladordb.iniciarConexion();

                    gestor.controladordb.eliminarCancionDePlaylist(this.playlistActual.getId(), c.getId());
                    gestor.controladordb.cerrarConexion();

                    // ELIMINAR DEL ARRAY
                    cancionesActuales.remove(c);

                    // RECARGAR PANEL
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