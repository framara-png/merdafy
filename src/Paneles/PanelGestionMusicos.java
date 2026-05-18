package Paneles;

import modelo.*;
import Ventanas.VentanaPrincipal;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class PanelGestionMusicos extends JPanel {
	public VentanaPrincipal ventana;
	private ArrayList<Musico> musicos;

	private JList<String> listPodcasts;
	private DefaultListModel<String> listModel;

	public PanelGestionMusicos(VentanaPrincipal ventana, ArrayList<Musico> musicos) {
		this.ventana = ventana;
		this.musicos = musicos;

		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// ================= TOP =================
		JPanel top = new JPanel(new BorderLayout());

		JButton btnAtras = new JButton("Atrás");
		JButton btnPerfil = new JButton("Perfil");

		btnAtras.addActionListener(e -> ventana.cambiarPanel("panelAdmin"));
		btnPerfil.addActionListener(e -> ventana.cambiarPanel("perfil"));

		top.add(btnAtras, BorderLayout.WEST);
		top.add(btnPerfil, BorderLayout.EAST);

		add(top, BorderLayout.NORTH);

		// ================= CENTER =================
		JPanel center = new JPanel(new BorderLayout(15, 15));

		// ================= LISTA =================
		JPanel panelLista = new JPanel(new BorderLayout());
		panelLista.setBorder(BorderFactory.createTitledBorder("Lista Podcast"));

		listModel = new DefaultListModel<>();
		listPodcasts = new JList<>(listModel);

		listPodcasts.setFont(new Font("Arial", Font.BOLD, 16));
		listPodcasts.setFixedCellHeight(45);
		listPodcasts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scroll = new JScrollPane(listPodcasts);
		panelLista.add(scroll, BorderLayout.CENTER);

		// ================= CLICK =================
		listPodcasts.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {

					int index = listPodcasts.getSelectedIndex();
					if (index < 0 || musicos == null || index >= musicos.size())
						return;

					Musico seleccionado = musicos.get(index);

					JOptionPane.showMessageDialog(null, "Musico: " + seleccionado.getNombreArt());
				}
			}
		});

		// ================= BOTONES =================
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(3, 1, 10, 20));
		panelBotones.setPreferredSize(new Dimension(250, 300));

		JButton btnAgregar = new JButton("Agregar Musico");
		JButton btnModificar = new JButton("Modificar Musico");
		JButton btnEliminar = new JButton("Eliminar Musico");

		Font font = new Font("Arial", Font.BOLD, 18);

		btnAgregar.setFont(font);
		btnModificar.setFont(font);
		btnEliminar.setFont(font);

		panelBotones.add(btnAgregar);
		panelBotones.add(btnModificar);
		panelBotones.add(btnEliminar);

		// ================= AGREGAR (CON DIALOG) =================
		btnAgregar.addActionListener(e -> {

			DialogCrearPodcast dialog = new DialogCrearPodcast(ventana);
			dialog.setVisible(true);

			// refresh dopo creazione

			cargarPodcasts();
		});

		// ================= MODIFICAR =================
		btnModificar.addActionListener(e -> {

			int index = listPodcasts.getSelectedIndex();
			if (index < 0) {
				JOptionPane.showMessageDialog(this, "Selecciona un podcast");
				return;
			}

			Musico m = musicos.get(index);

			String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", m.getNombreArt());

			if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {

				m.setNombreArt(nuevoNombre);

				ventana.getControladordb().actualizarMusico(m);

				cargarPodcasts();
			}
		});

		// ================= ELIMINAR =================
		btnEliminar.addActionListener(e -> {

			int index = listPodcasts.getSelectedIndex();

			if (index < 0) {
				JOptionPane.showMessageDialog(this, "Selecciona un podcast");
				return;
			}

			Musico m = musicos.get(index);

			int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar " + m.getNombreArt() + "?", "Confirmar",
					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {

				ventana.getControladordb().eliminarArtista(m.getNombreArt());

				cargarPodcasts();
			}
		});

		// ================= ADD =================
		center.add(panelLista, BorderLayout.CENTER);
		center.add(panelBotones, BorderLayout.EAST);

		add(center, BorderLayout.CENTER);

		cargarPodcasts();
	}

	// ================= LOAD =================
	private void cargarPodcasts() {

		listModel.clear();

		if (musicos != null && !musicos.isEmpty()) {

			for (Musico m : musicos) {

				listModel.addElement(m.getNombreArt() + " | genero: "
						+ m.getGenero() + " | Composicion: " + m.getComposicion());
			}

		} else {
			listModel.addElement("No hay podcasts");
		}
	}

}
