package Paneles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import Ventanas.VentanaPrincipal;
import controlador.GestorCliente;
import modelo.*;

public class PanelAlbum extends JPanel {
	private static final long serialVersionUID = 1L;
	private VentanaPrincipal ventana;
	private GestorCliente gestor;
	private Album album;
	private String nombreMusico;
	private JList<String> listaCanciones;
	private DefaultListModel<String> modeloLista;
	private JLabel lblFoto;
	private JTextArea txtInfoAlbum;

	public PanelAlbum(VentanaPrincipal ventana, String nombreAlbum, String nombreMusico) {

		this.ventana = ventana;
		this.nombreMusico = nombreMusico;
		this.gestor = new GestorCliente();

		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		gestor.controladordb.iniciarConexion();

		this.album = gestor.controladordb.obtenerAlbumPorNombre(nombreAlbum);

		if (album != null) {

			ArrayList<Cancion> canciones = gestor.controladordb.obtenerCanciones(album.getTitulo());

			album.setCancionesAlbum(canciones);
		}

		gestor.controladordb.cerrarConexion();

		construirPanel();
	}

	private void construirPanel() {
		// PANEL SUPERIOR CON BOTONES
		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnPerfil = new JButton("Perfil");
		JButton btnAtras = new JButton("Atrás");
		btnAtras.addActionListener(e -> ventana.cambiarPanel("artista"));
		btnPerfil.addActionListener(e -> ventana.cambiarPanel("perfil"));
		panelSuperior.add(btnAtras);
		panelSuperior.add(btnPerfil);
		add(panelSuperior, BorderLayout.NORTH);

		// PANEL CENTRAL CON DOS COLUMNAS
		JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));

		// ===== COLUMNA IZQUIERDA: LISTA DE CANCIONES =====
		JPanel panelIzquierdo = new JPanel(new BorderLayout());
		panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Canciones del Álbum"));

		modeloLista = new DefaultListModel<>();
		listaCanciones = new JList<>(modeloLista);
		listaCanciones.setFont(new Font("Arial", Font.PLAIN, 14));
		listaCanciones.setFixedCellHeight(50);
		listaCanciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		listaCanciones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					String seleccionado = listaCanciones.getSelectedValue();
					if (seleccionado != null && !seleccionado.equals("No hay canciones disponibles")) {
						System.out.println("Seleccionada: " + seleccionado);
					}
				}
			}
		});

		JScrollPane scrollCanciones = new JScrollPane(listaCanciones);
		panelIzquierdo.add(scrollCanciones, BorderLayout.CENTER);

		// ===== COLUMNA DERECHA: INFORMACIÓN DEL ÁLBUM =====
		JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));
		panelDerecho.setBorder(BorderFactory.createTitledBorder("Información del Álbum"));

		JPanel panelInfoAlbum = new JPanel(new BorderLayout(10, 10));

		// Foto del álbum
		lblFoto = new JLabel();
		lblFoto.setHorizontalAlignment(JLabel.CENTER);
		lblFoto.setPreferredSize(new Dimension(200, 200));
		lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		if (album != null && album.getFoto() != null && !album.getFoto().isEmpty()) {
			ImageIcon fotoIcon = new ImageIcon(album.getFoto());
			Image img = fotoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			lblFoto.setIcon(new ImageIcon(img));
		} else {
			lblFoto.setText("Sin foto");
			lblFoto.setHorizontalAlignment(JLabel.CENTER);
		}
		panelInfoAlbum.add(lblFoto, BorderLayout.NORTH);

		// Información del álbum
		txtInfoAlbum = new JTextArea();
		txtInfoAlbum.setEditable(false);
		txtInfoAlbum.setFont(new Font("Arial", Font.PLAIN, 12));
		txtInfoAlbum.setLineWrap(true);
		txtInfoAlbum.setWrapStyleWord(true);

		if (album != null) {
			StringBuilder info = new StringBuilder();
			info.append("Álbum: ").append(album.getTitulo()).append("\n");
			info.append("Artista: ").append(nombreMusico).append("\n");
			info.append("Género: ").append(album.getGenero()).append("\n");
			info.append("Publicado: ").append(album.getFechaPub()).append("\n");
			int numCanciones = album.getCancionesAlbum() != null ? album.getCancionesAlbum().size() : 0;
			info.append("Canciones: ").append(numCanciones).append("\n");
			txtInfoAlbum.setText(info.toString());
		} else {
			txtInfoAlbum.setText("No hay información disponible del álbum");
		}

		JScrollPane scrollInfo = new JScrollPane(txtInfoAlbum);
		panelInfoAlbum.add(scrollInfo, BorderLayout.CENTER);

		panelDerecho.add(panelInfoAlbum, BorderLayout.CENTER);

		panelCentral.add(panelIzquierdo);
		panelCentral.add(panelDerecho);
		add(panelCentral, BorderLayout.CENTER);

		// CARGAR CANCIONES
		if (album != null) {
			cargarCanciones();
		}
	}

	private void cargarCanciones() {
		modeloLista.clear();

		if (album.getCancionesAlbum() != null && !album.getCancionesAlbum().isEmpty()) {
			for (Cancion c : album.getCancionesAlbum()) {
				String texto = c.getNombreAudio() + " | " + c.durataConvertida() + " | " + c.getNumRep()
						+ " reproducciones";
				modeloLista.addElement(texto);
			}
		} else {
			modeloLista.addElement("No hay canciones disponibles");
		}
	}

}
