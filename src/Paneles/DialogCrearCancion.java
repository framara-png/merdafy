package Paneles;

import javax.swing.*;
import java.awt.*;
import Ventanas.VentanaPrincipal;
import modelo.Cancion;

public class DialogCrearCancion extends JDialog {

	private VentanaPrincipal ventana;

	public DialogCrearCancion(VentanaPrincipal ventana) {

		super(ventana, "Crear Canción", true);

		this.ventana = ventana;

		setSize(450, 400);
		setLocationRelativeTo(ventana);
		setLayout(new GridLayout(7, 2, 10, 10));

		// ================= CAMPOS =================
		JTextField txtNombre = new JTextField();
		JTextField txtDuracion = new JTextField(); // segundos
		JTextField txtArchivo = new JTextField();
		JTextField txtAlbum = new JTextField();
		JTextField txtColaboradores = new JTextField();

		// ================= LABELS =================
		add(new JLabel("Nombre canción:"));
		add(txtNombre);

		add(new JLabel("Duración (segundos):"));
		add(txtDuracion);

		add(new JLabel("Archivo (path):"));
		add(txtArchivo);

		add(new JLabel("ID Álbum:"));
		add(txtAlbum);

		add(new JLabel("Colaboradores (texto):"));
		add(txtColaboradores);

		// spacer
		add(new JLabel());

		JButton btnCrear = new JButton("Crear Canción");

		btnCrear.addActionListener(e -> {

			try {
				String nombre = txtNombre.getText().trim();
				int duracion = Integer.parseInt(txtDuracion.getText().trim());
				String archivo = txtArchivo.getText().trim();
				int idAlbum = Integer.parseInt(txtAlbum.getText().trim());
				String colaboradores = txtColaboradores.getText().trim();

				if (nombre.isEmpty() || archivo.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Completa todos los campos");
					return;
				}

				// ================= OBJETO COMPLETO =================
				Cancion c = new Cancion(0, // id
						nombre, // nombreAudio
						archivo, // archivo
						duracion, // duratasecondi
						0, // NumRep
						idAlbum, // idAlbum
						colaboradores, // nombresColaboradores
						"cancion" // tipo
				);
				// ================= DB INSERT =================

				ventana.getControladordb().insertarCancion(c);

				JOptionPane.showMessageDialog(this, "Canción creada correctamente");

				dispose();

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Duración e ID deben ser números");
			}
		});

		add(new JLabel());
		add(btnCrear);
	}
}