package Paneles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Ventanas.VentanaPrincipal;
import controlador.GestorCliente;
import modelo.*;

public class PanelPodcast extends JPanel {

	private VentanaPrincipal ventana;
	private Podcaster podcasterActual;
	private ArrayList<Podcast> podcastsActuales;

	private JList<String> listPodcast;
	private DefaultListModel<String> listModel;

	private JTextArea txtInfo;
	private JLabel lblFoto;

	public PanelPodcast(VentanaPrincipal ventana, cliente clientelogeado, String nombrePodcaster) {
		this.ventana = ventana;
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// ================= PODCASTER =================
		this.podcasterActual = ventana.getControladordb().obtenerPodcasterPorNombre(nombrePodcaster);
		// ================= TOP =================
		JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButton btnPerfil = new JButton("Perfil");
		JButton btnAtras = new JButton("Atrás");

		btnAtras.addActionListener(e -> ventana.cambiarPanel("podcasters"));
		btnPerfil.addActionListener(e -> ventana.cambiarPanel("perfil"));

		top.add(btnAtras);
		top.add(btnPerfil);

		add(top, BorderLayout.NORTH);

		// ================= CENTER =================
		JPanel center = new JPanel(new GridLayout(1, 2, 10, 10));

		// ================= LEFT (PODCAST LIST) =================
		JPanel left = new JPanel(new BorderLayout());
		left.setBorder(BorderFactory.createTitledBorder("Podcast"));

		listModel = new DefaultListModel<>();
		listPodcast = new JList<>(listModel);

		listPodcast.setFont(new Font("Arial", Font.PLAIN, 14));
		listPodcast.setFixedCellHeight(45);
		listPodcast.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// CLICK PODCAST
		listPodcast.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 1) {

					int index = listPodcast.getSelectedIndex();
					if (index < 0 || podcastsActuales == null)
						return;

					Podcast seleccionado = podcastsActuales.get(index);

					ventana.setPodcastSeleccionado(seleccionado);
					ventana.cambiarPanel("reproduccionPodcast");
				}
			}
		});

		left.add(new JScrollPane(listPodcast), BorderLayout.CENTER);

		// informaiones
		JPanel right = new JPanel(new BorderLayout(10, 10));
		right.setBorder(BorderFactory.createTitledBorder("Info Podcaster"));

		lblFoto = new JLabel();
		lblFoto.setHorizontalAlignment(JLabel.CENTER);
		lblFoto.setPreferredSize(new Dimension(200, 200));

		if (podcasterActual != null && podcasterActual.getFoto() != null) {
			ImageIcon icon = new ImageIcon(podcasterActual.getFoto());
			Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			lblFoto.setIcon(new ImageIcon(img));
		} else {
			lblFoto.setText("Sin foto");
		}

		txtInfo = new JTextArea();
		txtInfo.setEditable(false);
		txtInfo.setLineWrap(true);

		if (podcasterActual != null) {
			txtInfo.setText("Nombre: " + podcasterActual.getNombreArt() + "\n" + "Género: "
					+ podcasterActual.getGenero() + "\n\n" + podcasterActual.getDescripcion());
		}

		right.add(lblFoto, BorderLayout.NORTH);
		right.add(new JScrollPane(txtInfo), BorderLayout.CENTER);

		center.add(left);
		center.add(right);

		add(center, BorderLayout.CENTER);

		// caragmos el poscast
		cargarPodcast(nombrePodcaster);
	}

	private void cargarPodcast(String nombrePodcaster) {

		listModel.clear();

		podcastsActuales = ventana.getControladordb().obtenerPodcasts(nombrePodcaster);

		if (podcastsActuales != null && !podcastsActuales.isEmpty()) {

			for (Podcast p : podcastsActuales) {

				listModel.addElement(p.getNombreAudio() + " | " + p.getNumeroParticipantes() + " | "
						+ p.durataConvertida() + " | " + p.getNumRep());
			}

		} else {
			listModel.addElement("No hay podcasts disponibles");
		}
	}
}