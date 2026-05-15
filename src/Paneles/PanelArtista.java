package Paneles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Ventanas.VentanaPrincipal;
import controlador.GestorCliente;
import modelo.*;

public class PanelArtista extends JPanel {

	private GestorCliente gestor = new GestorCliente();

	private JList<String> listDiscos;
	private DefaultListModel<String> listModel;

	private JTextArea txtInfoArtista;
	private JLabel lblFoto;

	private Musico artistaActual;

	public PanelArtista(VentanaPrincipal ventana, cliente clientelogeado, String nombreArtista) {

		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		this.artistaActual = gestor.obtenerMusicoPorNombre(nombreArtista);

		// ================= TOP =================
		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButton btnPerfil = new JButton("Perfil");
		JButton btnAtras = new JButton("Atrás");

		btnAtras.addActionListener(e -> ventana.cambiarPanel("musicos"));
		btnPerfil.addActionListener(e -> ventana.cambiarPanel("perfil"));

		panelSuperior.add(btnAtras);
		panelSuperior.add(btnPerfil);

		add(panelSuperior, BorderLayout.NORTH);

		// ================= CENTER =================
		JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));

		// ===== LEFT =====
		JPanel panelIzquierdo = new JPanel(new BorderLayout());
		panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Discografía"));

		listModel = new DefaultListModel<>();
		listDiscos = new JList<>(listModel);

		listDiscos.setFont(new Font("Arial", Font.PLAIN, 14));
		listDiscos.setFixedCellHeight(50);
		listDiscos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	
		listDiscos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {

					String seleccionado = listDiscos.getSelectedValue();
					if (seleccionado == null || seleccionado.equals("No hay discos disponibles"))
						return;

					String nombreDisco = seleccionado;

					if (seleccionado.contains("|")) {
						nombreDisco = seleccionado.split("\\|")[0].trim();
					} else if (seleccionado.contains("NumReproducciones")) {
						nombreDisco = seleccionado.split("NumReproducciones")[0].trim();
					}

					System.out.println("NOMBRE DISCO = " + nombreDisco);

					gestor.controladordb.iniciarConexion();

					Album albumSeleccionado = gestor.controladordb.obtenerAlbumPorNombre(nombreDisco);

					gestor.controladordb.cerrarConexion();

					System.out.println("ALBUM = " + albumSeleccionado);

					if (albumSeleccionado == null) {
						System.out.println("Album no encontrado");
						return;
					}

					ventana.setAl(albumSeleccionado);
					ventana.cambiarPanel("album");
				}
			}
		});

		panelIzquierdo.add(new JScrollPane(listDiscos), BorderLayout.CENTER);

		// ===== RIGHT =====
		JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));
		panelDerecho.setBorder(BorderFactory.createTitledBorder("Información del Artista"));

		JPanel panelInfoArtista = new JPanel(new BorderLayout(10, 10));

		// FOTO
		lblFoto = new JLabel();
		lblFoto.setHorizontalAlignment(JLabel.CENTER);
		lblFoto.setPreferredSize(new Dimension(200, 200));
		lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		if (artistaActual != null && artistaActual.getFoto() != null) {
			ImageIcon icon = new ImageIcon(artistaActual.getFoto());
			Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			lblFoto.setIcon(new ImageIcon(img));
		} else {
			lblFoto.setText("Sin foto");
		}

		panelInfoArtista.add(lblFoto, BorderLayout.NORTH);

		// INFO
		txtInfoArtista = new JTextArea();
		txtInfoArtista.setEditable(false);
		txtInfoArtista.setLineWrap(true);
		txtInfoArtista.setWrapStyleWord(true);

		if (artistaActual != null) {
			txtInfoArtista.setText("Nombre: " + artistaActual.getNombreArt() + "\n" + "Género: "
					+ artistaActual.getGenero() + "\n\n" + "Descripción:\n" + artistaActual.getDescripcion());
		}

		panelInfoArtista.add(new JScrollPane(txtInfoArtista), BorderLayout.CENTER);

		panelDerecho.add(panelInfoArtista, BorderLayout.CENTER);

		panelCentral.add(panelIzquierdo);
		panelCentral.add(panelDerecho);

		add(panelCentral, BorderLayout.CENTER);

		// ================= LOAD DISCOS =================
		if (artistaActual != null) {
			cargarDiscos(artistaActual.getNombreArt());
		}
	}

	// ================= LOAD DISCOS =================
	private void cargarDiscos(String nombreArtista) {

		listModel.clear();

		gestor.controladordb.iniciarConexion();
		ArrayList<Album> albums = gestor.controladordb.obtenerAlbum(nombreArtista);
		gestor.controladordb.cerrarConexion();

		if (albums != null && !albums.isEmpty()) {
			for (Album a : albums) {
				listModel.addElement(a.getTitulo() + " | " + a.getFechaPub() + " | " + a.getGenero());
			}
		} else {
			listModel.addElement("No hay discos disponibles");
		}
	}
}