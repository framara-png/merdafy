package Paneles;

import javax.swing.*;
import java.awt.*;

import Ventanas.VentanaPrincipal;
import modelo.Podcaster;

public class DialogCrearPodcaster extends JDialog {

	private VentanaPrincipal ventana;

	public DialogCrearPodcaster(VentanaPrincipal ventana) {

		super(ventana, "Crear Podcaster", true);

		this.ventana = ventana;

		setSize(400, 320);
		setLocationRelativeTo(ventana);
		setLayout(new GridLayout(6, 2, 10, 10));

		// ================= CAMPOS =================
		JTextField txtNombre = new JTextField();
		JTextField txtGenero = new JTextField();
		JTextField txtDescripcion = new JTextField();
		JTextField txtFoto = new JTextField();

		// ================= LABELS =================
		add(new JLabel("Nombre podcaster:"));
		add(txtNombre);
		add(new JLabel("Genero:"));
		add(txtGenero);
		add(new JLabel("Descripción:"));
		add(txtDescripcion);
		add(new JLabel("Foto (path):"));
		add(txtFoto);

		JButton btnCrear = new JButton("Crear");

		btnCrear.addActionListener(e -> {

			String nombre = txtNombre.getText().trim();
			String genero = txtGenero.getText().trim();
			String descripcion = txtDescripcion.getText().trim();
			String foto = txtFoto.getText().trim();

			if (nombre.isEmpty() || genero.isEmpty() || descripcion.isEmpty() || foto.isEmpty()) {

				JOptionPane.showMessageDialog(this, "Completa todos los campos");
				return;
			}

			if (!ventana.getGestor().controladorArtistaDoble(nombre)) {

				JOptionPane.showMessageDialog(this, "Ya existe un podcaster con este nombre");
				return;
			}

			Podcaster p = new Podcaster(0, nombre, genero, descripcion, foto);

			ventana.getControladordb().insertarPodcaster(p);

			JOptionPane.showMessageDialog(this, "Podcaster creado");

			dispose();
		});

		add(new JLabel());
		add(btnCrear);
	}
}