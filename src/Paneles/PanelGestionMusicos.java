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

	private JList<String> listMusicos;
	private DefaultListModel<String> listModel;

	public PanelGestionMusicos(VentanaPrincipal ventana, ArrayList<Musico> musicos) {
		this.ventana = ventana;
		this.musicos = musicos;

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
		panelLista.setBorder(BorderFactory.createTitledBorder("Lista Musicos"));

		listModel = new DefaultListModel<>();
		listMusicos = new JList<>(listModel);

		listMusicos.setFont(new Font("Arial", Font.BOLD, 16));
		listMusicos.setFixedCellHeight(45);
		listMusicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scroll = new JScrollPane(listMusicos);
		panelLista.add(scroll, BorderLayout.CENTER);

		// ================= CLICK =================
		listMusicos.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {

					int index = listMusicos.getSelectedIndex();
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

			DialogCrearMusico dialog = new DialogCrearMusico(ventana);
			dialog.setVisible(true);

			// refresh dopo creazione

			cargarMusicos();
		});

		// ================= MODIFICAR =================
		btnModificar.addActionListener(e -> {

			int i = listMusicos.getSelectedIndex();

			if (i < 0) {
				JOptionPane.showMessageDialog(this, "Selecciona un musico");
				return;
			}

			Musico m = musicos.get(i);

			JTextField txtNombre = new JTextField(m.getNombreArt());
			JTextField txtGenero = new JTextField(m.getGenero());
			JTextField txtDescripcion = new JTextField(m.getDescripcion());
			JTextField txtFoto = new JTextField(m.getFoto());

			JComboBox<String> combo = new JComboBox<>();
			combo.addItem("solista");
			combo.addItem("grupo");
			combo.setSelectedItem(m.getComposicion());

			JPanel panel = new JPanel(new GridLayout(0, 1));
			panel.add(new JLabel("Nombre:"));
			panel.add(txtNombre);
			panel.add(new JLabel("Genero:"));
			panel.add(txtGenero);
			panel.add(new JLabel("Descripcion:"));
			panel.add(txtDescripcion);
			panel.add(new JLabel("Foto:"));
			panel.add(txtFoto);
			panel.add(new JLabel("Composición:"));
			panel.add(combo);

			int result = JOptionPane.showConfirmDialog(this, panel, "Modificar músico", JOptionPane.OK_CANCEL_OPTION);

			if (result == JOptionPane.OK_OPTION) {
				String nuevoNombre = txtNombre.getText().trim();
				if (!nuevoNombre.equalsIgnoreCase(m.getNombreArt())
						&& !ventana.getGestor().controladorArtistaDoble(nuevoNombre)) {

					JOptionPane.showMessageDialog(this, "Ya existe un músico con este nombre");
					return;
				}

				m.setNombreArt(txtNombre.getText().trim());
				m.setGenero(txtGenero.getText().trim());
				m.setDescripcion(txtDescripcion.getText().trim());
				m.setFoto(txtFoto.getText().trim());
				m.setComposicion(combo.getSelectedItem().toString());

				ventana.getControladordb().actualizarMusico(m);

				cargarMusicos();
			}
		});
		// ================= ELIMINAR =================
		btnEliminar.addActionListener(e -> {

			int index = listMusicos.getSelectedIndex();

			if (index < 0) {
				JOptionPane.showMessageDialog(this, "Selecciona un musico");
				return;
			}

			Musico m = musicos.get(index);

			int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar " + m.getNombreArt() + "?", "Confirmar",
					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {

				ventana.getControladordb().eliminarArtista(m.getId());
				cargarMusicos();
				ventana.cambiarPanel("GestionMusicos");
			}
		});

		// ================= ADD =================
		center.add(panelLista, BorderLayout.CENTER);
		center.add(panelBotones, BorderLayout.EAST);

		add(center, BorderLayout.CENTER);

		cargarMusicos();
	}

	// ================= LOAD =================
	private void cargarMusicos() {

		listModel.clear();

		if (musicos != null && !musicos.isEmpty()) {

			for (Musico m : musicos) {

				listModel.addElement(
						m.getNombreArt() + " | genero: " + m.getGenero() + " | Composicion: " + m.getComposicion());
			}

		} else {
			listModel.addElement("No hay podcasts");
		}
	}

}
