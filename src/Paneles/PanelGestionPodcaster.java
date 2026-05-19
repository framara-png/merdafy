package Paneles;

import modelo.*;
import Ventanas.VentanaPrincipal;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class PanelGestionPodcaster extends JPanel {
	public VentanaPrincipal ventana;
	private cliente clienteLogeado;

	private ArrayList<Podcaster> podcasters;

	private JList<String> listPodcasters;
	private DefaultListModel<String> listModel;

	public PanelGestionPodcaster(VentanaPrincipal ventana, ArrayList<Podcaster> podcasters) {
		this.podcasters = podcasters;
		this.ventana = ventana;

		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// ================= TOP =================
		JPanel top = new JPanel(new BorderLayout());

		JButton btnAtras = new JButton("Atrás");

		btnAtras.addActionListener(e -> ventana.cambiarPanel("panelAdmin"));

		top.add(btnAtras, BorderLayout.WEST);

		add(top, BorderLayout.NORTH);

		// ================= CENTER =================
		JPanel center = new JPanel(new BorderLayout(15, 15));

		// ================= LISTA =================
		JPanel panelLista = new JPanel(new BorderLayout());
		panelLista.setBorder(BorderFactory.createTitledBorder("Lista Podcast"));

		listModel = new DefaultListModel<>();
		listPodcasters = new JList<>(listModel);

		listPodcasters.setFont(new Font("Arial", Font.BOLD, 16));
		listPodcasters.setFixedCellHeight(45);
		listPodcasters.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scroll = new JScrollPane(listPodcasters);
		panelLista.add(scroll, BorderLayout.CENTER);

		// ================= CLICK =================
		listPodcasters.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {

					int index = listPodcasters.getSelectedIndex();
					if (index < 0 || podcasters == null || index >= podcasters.size())
						return;

					Podcaster seleccionado = podcasters.get(index);

					JOptionPane.showMessageDialog(null, "Podcaster: " + seleccionado.getNombreArt());
				}
			}
		});

		// ================= BOTONES =================
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(3, 1, 10, 20));
		panelBotones.setPreferredSize(new Dimension(250, 300));

		JButton btnAgregar = new JButton("Agregar Podcaster");
		JButton btnModificar = new JButton("Modificar Podcaster");
		JButton btnEliminar = new JButton("Eliminar Podcaster");

		Font font = new Font("Arial", Font.BOLD, 18);

		btnAgregar.setFont(font);
		btnModificar.setFont(font);
		btnEliminar.setFont(font);

		panelBotones.add(btnAgregar);
		panelBotones.add(btnModificar);
		panelBotones.add(btnEliminar);

		// ================= AGREGAR (CON DIALOG) =================
		btnAgregar.addActionListener(e -> {

			DialogCrearPodcaster dialog = new DialogCrearPodcaster(ventana);
			dialog.setVisible(true);

			// refresh dopo creazione

			cargarPodcasters();
		});

		// ================= MODIFICAR =================

		btnModificar.addActionListener(e -> {

			int index = listPodcasters.getSelectedIndex();

			if (index < 0) {
				JOptionPane.showMessageDialog(this, "Selecciona un podcaster");
				return;
			}

			Podcaster p = podcasters.get(index);

			JTextField txtNombre = new JTextField(p.getNombreArt());
			JTextField txtGenero = new JTextField(p.getGenero());
			JTextField txtDescripcion = new JTextField(p.getDescripcion());
			JTextField txtFoto = new JTextField(p.getFoto());

			JPanel panel = new JPanel(new GridLayout(0, 1));
			panel.add(new JLabel("Nombre:"));
			panel.add(txtNombre);
			panel.add(new JLabel("Genero:"));
			panel.add(txtGenero);
			panel.add(new JLabel("Descripcion:"));
			panel.add(txtDescripcion);
			panel.add(new JLabel("Foto:"));
			panel.add(txtFoto);

			int result = JOptionPane.showConfirmDialog(this, panel, "Modificar podcaster",
					JOptionPane.OK_CANCEL_OPTION);

			if (result == JOptionPane.OK_OPTION) {

				String nuevoNombre = txtNombre.getText().trim();

				if (!nuevoNombre.equalsIgnoreCase(p.getNombreArt())
						&& !ventana.getGestor().controladorArtistaDoble(nuevoNombre)) {

					JOptionPane.showMessageDialog(this, "Ya existe un podcaster con este nombre");
					return;
				}

				p.setNombreArt(nuevoNombre);
				p.setGenero(txtGenero.getText().trim());
				p.setDescripcion(txtDescripcion.getText().trim());
				p.setFoto(txtFoto.getText().trim());

				ventana.getControladordb().actualizarPodcaster(p);

				cargarPodcasters();

				JOptionPane.showMessageDialog(this, "Podcaster modificado correctamente");
			}
		});

		// ================= ELIMINAR =================
		btnEliminar.addActionListener(e -> {

			int index = listPodcasters.getSelectedIndex();

			if (index < 0) {
				JOptionPane.showMessageDialog(this, "Selecciona un podcaster");
				return;
			}

			Podcaster p = podcasters.get(index);

			int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar " + p.getNombreArt() + "?", "Confirmar",
					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {

				ventana.getControladordb().eliminarAudio(p.getId());

				cargarPodcasters();
			}
		});

		// ================= ADD =================
		center.add(panelLista, BorderLayout.CENTER);
		center.add(panelBotones, BorderLayout.EAST);

		add(center, BorderLayout.CENTER);

		cargarPodcasters();
	}

	// ================= LOAD =================
	private void cargarPodcasters() {

		listModel.clear();

		if (podcasters != null && !podcasters.isEmpty()) {

			for (Podcaster p : podcasters) {

				listModel.addElement(p.getNombreArt() + " | genero: " + p.getGenero());
			}

		} else {
			listModel.addElement("No hay podcasts");
		}
	}

}
