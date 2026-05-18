package Paneles;

import modelo.*;
import Ventanas.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class PanelGestionCanciones extends JPanel {

	public VentanaPrincipal ventana;
	private cliente clienteLogeado;

	private ArrayList<Cancion> canciones;

	private JList<String> listCanciones;
	private DefaultListModel<String> listModel;

	public PanelGestionCanciones(VentanaPrincipal ventana, cliente clienteLogeado) {

		this.ventana = ventana;
		this.clienteLogeado = clienteLogeado;
		this.canciones = ventana.getControladordb().obtenerTodasCanciones();

		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// ================= TOP PANEL =================
		JPanel top = new JPanel(new BorderLayout());

		JButton btnAtras = new JButton("Atrás");
		JButton btnPerfil = new JButton("Perfil");

		btnAtras.addActionListener(e -> ventana.cambiarPanel("panelAdmin"));
		btnPerfil.addActionListener(e -> ventana.cambiarPanel("perfil"));

		top.add(btnAtras, BorderLayout.WEST);
		top.add(btnPerfil, BorderLayout.EAST);

		add(top, BorderLayout.NORTH);

		// ================= CENTER PANEL =================
		JPanel center = new JPanel(new BorderLayout(15, 15));

		// ================= LISTA CANCIONES =================
		JPanel panelLista = new JPanel(new BorderLayout());

		panelLista.setBorder(BorderFactory.createTitledBorder("Lista Canciones"));

		listModel = new DefaultListModel<>();

		listCanciones = new JList<>(listModel);

		listCanciones.setFont(new Font("Arial", Font.BOLD, 16));
		listCanciones.setFixedCellHeight(45);
		listCanciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scroll = new JScrollPane(listCanciones);

		panelLista.add(scroll, BorderLayout.CENTER);

		// ================= DOBLE CLICK =================
		listCanciones.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {

					int index = listCanciones.getSelectedIndex();

					if (index < 0)
						return;

					Cancion seleccionada = canciones.get(index);

					JOptionPane.showMessageDialog(null, "Canción seleccionada: " + seleccionada.getNombreAudio());
				}
			}
		});

		// ================= PANEL BOTONES =================
		JPanel panelBotones = new JPanel();

		panelBotones.setLayout(new GridLayout(3, 1, 10, 20));

		panelBotones.setPreferredSize(new Dimension(250, 300));

		JButton btnAgregar = new JButton("Agregar Canción");
		JButton btnModificar = new JButton("Modificar Canción");
		JButton btnEliminar = new JButton("Eliminar Canción");

		Font fontBotones = new Font("Arial", Font.BOLD, 18);

		btnAgregar.setFont(fontBotones);
		btnModificar.setFont(fontBotones);
		btnEliminar.setFont(fontBotones);

		btnAgregar.setPreferredSize(new Dimension(220, 60));
		btnModificar.setPreferredSize(new Dimension(220, 60));
		btnEliminar.setPreferredSize(new Dimension(220, 60));

		// ================= ACCIONES =================

		// AGREGAR
		btnAgregar.addActionListener(e -> {

			String nombre = JOptionPane.showInputDialog(this, "Nombre de la canción:");

			if (nombre != null && !nombre.trim().isEmpty()) {

				Cancion nueva = null;

				nueva.setNombreAudio(nombre);

				canciones.add(nueva);

				cargarCanciones();

				JOptionPane.showMessageDialog(this, "Canción agregada correctamente");
			}
		});

		// MODIFICAR
		btnModificar.addActionListener(e -> {

			int index = listCanciones.getSelectedIndex();

			if (index < 0) {

				JOptionPane.showMessageDialog(this, "Selecciona una canción");

				return;
			}

			Cancion seleccionada = canciones.get(index);

			String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", seleccionada.getNombreAudio());

			if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {

				seleccionada.setNombreAudio(nuevoNombre);

				cargarCanciones();

				JOptionPane.showMessageDialog(this, "Canción modificada correctamente");
			}
		});

		btnEliminar.addActionListener(e -> {

			int index = listCanciones.getSelectedIndex();

			if (index < 0) {

				JOptionPane.showMessageDialog(this, "Selecciona una canción");
				return;
			}

			Cancion seleccionada = canciones.get(index);

			int confirmacion = JOptionPane.showConfirmDialog(this,
					"¿Eliminar canción " + seleccionada.getNombreAudio() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);

			if (confirmacion == JOptionPane.YES_OPTION) {

				ventana.getControladordb().eliminarAudio(seleccionada.getNombreAudio());

				// ricarico lista dopo delete
				canciones = ventana.getControladordb().obtenerTodasCanciones();
				cargarCanciones();

				JOptionPane.showMessageDialog(this, "Canción eliminada correctamente");
			}
		});
		panelBotones.add(btnAgregar);
		panelBotones.add(btnModificar);
		panelBotones.add(btnEliminar);

		// ================= AGREGAR TODO =================
		center.add(panelLista, BorderLayout.CENTER);
		center.add(panelBotones, BorderLayout.EAST);

		add(center, BorderLayout.CENTER);

		// ================= CARGAR CANCIONES =================
		cargarCanciones();
	}

	// ================= CARGAR CANCIONES =================
	private void cargarCanciones() {

		listModel.clear();

		if (canciones != null && !canciones.isEmpty()) {

			for (Cancion c : canciones) {

				listModel.addElement(c.getNombreAudio() + " | " + c.durataConvertida() + " | "
						+ c.getNombresColaboradores() + " | Reproducciones: " + c.getNumRep());
			}

		} else {

			listModel.addElement("No hay canciones");
		}
	}
}