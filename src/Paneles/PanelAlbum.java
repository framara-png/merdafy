package Paneles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Ventanas.VentanaPrincipal;
import modelo.*;

public class PanelAlbum extends JPanel {

	private VentanaPrincipal ventana;

	private Album album;
	private String nombreMusico;

	private JList<String> listaCanciones;
	private DefaultListModel<String> modeloLista;

	private JLabel lblFoto;
	private JTextArea txtInfoAlbum;

	public PanelAlbum(VentanaPrincipal ventana, String nombreAlbum, String nombreMusico) {

		this.ventana = ventana;
		this.nombreMusico = nombreMusico;
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		album = ventana.getControladordb().obtenerAlbumPorNombre(nombreAlbum);
		if (album != null) {
			ArrayList<Cancion> canciones = ventana.getControladordb().obtenerCanciones(album.getTitulo());

			album.setCancionesAlbum(canciones);
		}

		construirPanel();
	}

	private void construirPanel() {

		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButton btnPerfil = new JButton("Perfil");
		JButton btnAtras = new JButton("Atrás");

		btnAtras.addActionListener(e -> ventana.cambiarPanel("artista"));
		btnPerfil.addActionListener(e -> ventana.cambiarPanel("perfil"));

		panelSuperior.add(btnAtras);
		panelSuperior.add(btnPerfil);

		add(panelSuperior, BorderLayout.NORTH);
		JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));

		// ================= IZQUIERDA =================
		JPanel panelIzquierdo = new JPanel(new BorderLayout());
		panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Canciones del Álbum"));

		modeloLista = new DefaultListModel<>();
		listaCanciones = new JList<>(modeloLista);

		listaCanciones.setFont(new Font("Arial", Font.PLAIN, 14));
		listaCanciones.setFixedCellHeight(50);

		listaCanciones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {

					String sel = listaCanciones.getSelectedValue();

					if (sel != null && !sel.contains("No hay")) {
						System.out.println("Canción: " + sel);
					}
				}
			}
		});

		panelIzquierdo.add(new JScrollPane(listaCanciones), BorderLayout.CENTER);

		// ================= DERECHA =================
		JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));
		panelDerecho.setBorder(BorderFactory.createTitledBorder("Información del Álbum"));

		lblFoto = new JLabel();
		lblFoto.setHorizontalAlignment(JLabel.CENTER);
		lblFoto.setPreferredSize(new Dimension(200, 200));

		if (album != null && album.getFoto() != null && !album.getFoto().isEmpty()) {

			ImageIcon icon = new ImageIcon(album.getFoto());
			Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			lblFoto.setIcon(new ImageIcon(img));

		} else {
			lblFoto.setText("Sin foto");
		}

		txtInfoAlbum = new JTextArea();
		txtInfoAlbum.setEditable(false);
		txtInfoAlbum.setLineWrap(true);

		if (album != null) {

			txtInfoAlbum.setText("Álbum: " + album.getTitulo() + "\n" + "Artista: " + nombreMusico + "\n" + "Género: "
					+ album.getGenero() + "\n" + "Fecha: " + album.getFechaPub() + "\n" + "Canciones: "
					+ (album.getCancionesAlbum() != null ? album.getCancionesAlbum().size() : 0));

		} else {
			txtInfoAlbum.setText("Álbum no encontrado");
		}

		panelDerecho.add(lblFoto, BorderLayout.NORTH);
		panelDerecho.add(new JScrollPane(txtInfoAlbum), BorderLayout.CENTER);
		panelCentral.add(panelIzquierdo);
		panelCentral.add(panelDerecho);
		add(panelCentral, BorderLayout.CENTER);

		cargarCanciones();
	}

	private void cargarCanciones() {

		modeloLista.clear();
		if (album != null && album.getCancionesAlbum() != null && !album.getCancionesAlbum().isEmpty()) {
			for (Cancion c : album.getCancionesAlbum()) {
				modeloLista.addElement(c.getNombreAudio() + " | " + c.durataConvertida() + " | " + c.getNumRep());
			}

		} else {
			modeloLista.addElement("No hay canciones disponibles");
		}
	}
}