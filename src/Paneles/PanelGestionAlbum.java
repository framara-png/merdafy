package Paneles;

import modelo.*;
import Ventanas.VentanaPrincipal;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class PanelGestionAlbum extends JPanel {

	private VentanaPrincipal ventana;
	private ArrayList<Album> albums;

	private JList<String> listAlbums;
	private DefaultListModel<String> listModel;

	public PanelGestionAlbum(VentanaPrincipal ventana) {

		this.ventana = ventana;
		this.albums = ventana.getControladordb().obtenerTodosAlbum(); // 👈 prende da DB

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
		panelLista.setBorder(BorderFactory.createTitledBorder("Lista Albums"));

		listModel = new DefaultListModel<>();
		listAlbums = new JList<>(listModel);

		listAlbums.setFont(new Font("Arial", Font.BOLD, 16));
		listAlbums.setFixedCellHeight(45);
		listAlbums.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scroll = new JScrollPane(listAlbums);
		panelLista.add(scroll, BorderLayout.CENTER);

		// ================= CLICK =================
		listAlbums.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {

					int index = listAlbums.getSelectedIndex();

					if (index < 0 || albums == null || index >= albums.size())
						return;

					Album a = albums.get(index);

					JOptionPane.showMessageDialog(null, "Album: " + a.getTitulo() + "\nGenero: " + a.getGenero());
				}
			}
		});

		// ================= BOTONES =================
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(3, 1, 10, 20));
		panelBotones.setPreferredSize(new Dimension(250, 300));

		JButton btnAgregar = new JButton("Agregar Album");
		JButton btnModificar = new JButton("Modificar Album");
		JButton btnEliminar = new JButton("Eliminar Album");

		Font font = new Font("Arial", Font.BOLD, 18);

		btnAgregar.setFont(font);
		btnModificar.setFont(font);
		btnEliminar.setFont(font);

		panelBotones.add(btnAgregar);
		panelBotones.add(btnModificar);
		panelBotones.add(btnEliminar);

		// ================= AGREGAR =================
		btnAgregar.addActionListener(e -> {

			DialogCrearAlbum dialog = new DialogCrearAlbum(ventana);
			dialog.setVisible(true);

			cargarAlbums();
		});

		// ================= MODIFICAR =================
		btnModificar.addActionListener(e -> {

			int i = listAlbums.getSelectedIndex();

			if (i < 0) {
				JOptionPane.showMessageDialog(this, "Selecciona un album");
				return;
			}

			Album a = albums.get(i);

			JTextField txtTitulo = new JTextField(a.getTitulo());
			JTextField txtGenero = new JTextField(a.getGenero());
			JTextField txtFoto = new JTextField(a.getFoto());
			JTextField txtFecha = new JTextField(a.getFechaPub());
			JTextField txtIdMusico = new JTextField(String.valueOf(a.getIdMusico()));

			JPanel panel = new JPanel(new GridLayout(0, 1));

			panel.add(new JLabel("Titulo:"));
			panel.add(txtTitulo);
			panel.add(new JLabel("Genero:"));
			panel.add(txtGenero);
			panel.add(new JLabel("Foto:"));
			panel.add(txtFoto);
			panel.add(new JLabel("Fecha:"));
			panel.add(txtFecha);
			panel.add(new JLabel("ID Musico:"));
			panel.add(txtIdMusico);

			int result = JOptionPane.showConfirmDialog(this, panel, "Modificar album", JOptionPane.OK_CANCEL_OPTION);

			if (result == JOptionPane.OK_OPTION) {

				a.setTitulo(txtTitulo.getText().trim());
				a.setGenero(txtGenero.getText().trim());
				a.setFoto(txtFoto.getText().trim());
				a.setFechaPub(txtFecha.getText().trim());

				try {
					a.setIdMusico(Integer.parseInt(txtIdMusico.getText().trim()));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "ID Musico debe ser un número");
					return;
				}

				Musico m = ventana.getControladordb().obtenerMusicoPorId(a.getIdMusico());
				if (m == null) {
					JOptionPane.showMessageDialog(this, "No hay musico con este id");
					return;
				}

				ventana.getControladordb().actualizarAlbum(a);

				cargarAlbums();

				JOptionPane.showMessageDialog(this, "Album modificado");
			}
		});

		// ================= ELIMINAR =================
		btnEliminar.addActionListener(e -> {

			int index = listAlbums.getSelectedIndex();

			if (index < 0) {
				JOptionPane.showMessageDialog(this, "Selecciona un album");
				return;
			}

			Album a = albums.get(index);

			int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar " + a.getTitulo() + "?", "Confirmar",
					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {

				ventana.getControladordb().eliminarAlbum(a.getId());

				cargarAlbums();
			}
		});

		// ================= ADD =================
		center.add(panelLista, BorderLayout.CENTER);
		center.add(panelBotones, BorderLayout.EAST);

		add(center, BorderLayout.CENTER);
		albums = ventana.getControladordb().obtenerTodosAlbum();
		cargarAlbums();
	}

	// ================= LOAD =================
	private void cargarAlbums() {

		listModel.clear();

		if (albums != null && !albums.isEmpty()) {

			for (Album a : albums) {

				listModel.addElement(a.getTitulo() + " | genero: " + a.getGenero() + " | idMusico: " + a.getIdMusico());
			}

		} else {
			listModel.addElement("No hay albums");
		}
	}
}