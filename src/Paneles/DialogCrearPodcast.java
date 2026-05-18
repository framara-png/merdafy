package Paneles;

import javax.swing.*;
import java.awt.*;
import Ventanas.VentanaPrincipal;
import modelo.Podcast;

public class DialogCrearPodcast extends JDialog {

	private VentanaPrincipal ventana;

	public DialogCrearPodcast(VentanaPrincipal ventana) {
		super(ventana, "Crear Podcast", true);

		this.ventana = ventana;

		setSize(400, 350);
		setLocationRelativeTo(ventana);
		setLayout(new GridLayout(6, 2, 10, 10));

		// ================= CAMPOS =================
		JTextField txtNombre = new JTextField();
		JTextField txtDuracion = new JTextField(); // segundos
		JTextField txtArchivo = new JTextField();
		JTextField txtPodcaster = new JTextField();
		JTextField txtParticipantes = new JTextField();

		// ================= LABELS =================
		add(new JLabel("Nombre podcast:"));
		add(txtNombre);

		add(new JLabel("Duración (segundos):"));
		add(txtDuracion);

		add(new JLabel("Archivo (path):"));
		add(txtArchivo);

		add(new JLabel("ID Podcaster:"));
		add(txtPodcaster);

		add(new JLabel("Participantes:"));
		add(txtParticipantes);

		// ================= BOTÓN =================
		JButton btnCrear = new JButton("Crear");

		btnCrear.addActionListener(e -> {

			try {
				String nombre = txtNombre.getText().trim();
				int duracion = Integer.parseInt(txtDuracion.getText().trim());
				String archivo = txtArchivo.getText().trim();
				int idPodcaster = Integer.parseInt(txtPodcaster.getText().trim());
				int participantes = Integer.parseInt(txtParticipantes.getText().trim());

				if (nombre.isEmpty() || archivo.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Completa todos los campos");
					return;
				}

				Podcast p = new Podcast(0, nombre, archivo, duracion, 0, idPodcaster, participantes, "podcast");

				ventana.getControladordb().insertarPodcast(p);

				JOptionPane.showMessageDialog(this, "Podcast creado correctamente");
				dispose();

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Duración, ID y participantes deben ser números");
			}
		});

		add(new JLabel());
		add(btnCrear);
	}
}