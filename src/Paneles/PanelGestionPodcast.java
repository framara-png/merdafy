package Paneles;

import modelo.*;
import Ventanas.VentanaPrincipal;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.*;

public class PanelGestionPodcast extends JPanel {

	public VentanaPrincipal ventana;
	private cliente clienteLogeado;

	private ArrayList<Podcast> podcasts;

	private JList<String> listPodcasts;
	private DefaultListModel<String> listModel;

	public PanelGestionPodcast(VentanaPrincipal ventana, cliente clienteLogeado) {

		this.ventana = ventana;
		this.clienteLogeado = clienteLogeado;

		this.podcasts = ventana.getControladordb().obtenerTodosPodcasts();

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
					if (index < 0 || podcasts == null || index >= podcasts.size())
						return;

					Podcast seleccionado = podcasts.get(index);

					JOptionPane.showMessageDialog(null, "Podcast: " + seleccionado.getNombreAudio());
				}
			}
		});

		// ================= BOTONES =================
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(3, 1, 10, 20));
		panelBotones.setPreferredSize(new Dimension(250, 300));

		JButton btnAgregar = new JButton("Agregar Podcast");
		JButton btnModificar = new JButton("Modificar Podcast");
		JButton btnEliminar = new JButton("Eliminar Podcast");

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
			podcasts = ventana.getControladordb().obtenerTodosPodcasts();
			cargarPodcasts();
		});

		// ================= MODIFICAR =================
		btnModificar.addActionListener(e -> {

			int index = listPodcasts.getSelectedIndex();

			if (index < 0) {
				JOptionPane.showMessageDialog(this, "Selecciona un podcast");
				return;
			}

			Podcast p = podcasts.get(index);

			JTextField txtNombre = new JTextField(p.getNombreAudio());
			JTextField txtArchivo = new JTextField(p.getArchivo());
			JTextField txtDuracion = new JTextField(p.getDurata().toString());
			JTextField txtParticipantes = new JTextField(String.valueOf(p.getNumeroParticipantes()));
			JTextField txtIdPodcaster = new JTextField(p.getIdPodcaster());

			JPanel panel = new JPanel(new GridLayout(0, 1));
			panel.add(new JLabel("Nombre:"));
			panel.add(txtNombre);
			panel.add(new JLabel("Archivo:"));
			panel.add(txtArchivo);
			panel.add(new JLabel("Duración (HH:MM:SS):"));
			panel.add(txtDuracion);
			panel.add(new JLabel("Participantes:"));
			panel.add(txtParticipantes);
			panel.add(new JLabel("IdPodcaster:"));
			panel.add(txtIdPodcaster);

			int result = JOptionPane.showConfirmDialog(this, panel, "Modificar podcast", JOptionPane.OK_CANCEL_OPTION);

			if (result == JOptionPane.OK_OPTION) {

				String nuevoNombre = txtNombre.getText().trim();
				int IdPodcaster = Integer.parseInt(txtIdPodcaster.getText());
				if (ventana.getControladordb().obtenerPodcasterPorId(IdPodcaster)) {
					JOptionPane.showMessageDialog(this, "No hay podcaster con este id");
					return;
				}

				if (!nuevoNombre.equalsIgnoreCase(p.getNombreAudio())
						&& !ventana.getGestor().controladorAudioDobles(nuevoNombre)) {

					JOptionPane.showMessageDialog(this, "Ya existe un podcast con este nombre");
					return;
				}

				p.setNombreAudio(nuevoNombre);
				p.setArchivo(txtArchivo.getText().trim());
				p.setDurata(Time.valueOf(txtDuracion.getText().trim()));
				p.setNumeroParticipantes(Integer.parseInt(txtParticipantes.getText().trim()));

				ventana.getControladordb().actualizarPodcast(p);

				podcasts = ventana.getControladordb().obtenerTodosPodcasts();
				cargarPodcasts();

				JOptionPane.showMessageDialog(this, "Podcast modificado correctamente");
			}
		});
		// ================= ELIMINAR =================
		btnEliminar.addActionListener(e -> {

			int index = listPodcasts.getSelectedIndex();

			if (index < 0) {
				JOptionPane.showMessageDialog(this, "Selecciona un podcast");
				return;
			}

			Podcast p = podcasts.get(index);

			int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar " + p.getNombreAudio() + "?", "Confirmar",
					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {

				ventana.getControladordb().eliminarAudio(p.getId());

				podcasts = ventana.getControladordb().obtenerTodosPodcasts();
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

		if (podcasts != null && !podcasts.isEmpty()) {

			for (Podcast p : podcasts) {

				listModel.addElement(p.getNombreAudio() + " | Participantes: " + p.getNumeroParticipantes()
						+ " | Reproducciones: " + p.getNumRep());
			}

		} else {
			listModel.addElement("No hay podcasts");
		}
	}

}