package Paneles;

import javax.swing.*;
import java.awt.*;

import Ventanas.VentanaPrincipal;
import modelo.Album;
import modelo.Musico;

public class DialogCrearAlbum extends JDialog {

	private VentanaPrincipal ventana;

	public DialogCrearAlbum(VentanaPrincipal ventana) {

		super(ventana, "Crear Album", true);

		this.ventana = ventana;

		setSize(450, 400);
		setLocationRelativeTo(ventana);
		setLayout(new GridLayout(7, 2, 10, 10));

		// ================= CAMPOS =================
		JTextField txtTitulo = new JTextField();
		JTextField txtFecha = new JTextField();
		JTextField txtGenero = new JTextField();
		JTextField txtFoto = new JTextField();
		JTextField txtIdMusico = new JTextField();

		// ================= LABELS =================
		add(new JLabel("Titulo album:"));
		add(txtTitulo);

		add(new JLabel("Fecha publicación:"));
		add(txtFecha);

		add(new JLabel("Genero:"));
		add(txtGenero);

		add(new JLabel("Foto (path):"));
		add(txtFoto);

		add(new JLabel("ID Musico:"));
		add(txtIdMusico);

		add(new JLabel());

		// ================= BOTON =================
		JButton btnCrear = new JButton("Crear Album");

		btnCrear.addActionListener(e -> {

			try {

				String titulo = txtTitulo.getText().trim();
				String fecha = txtFecha.getText().trim();
				String genero = txtGenero.getText().trim();
				String foto = txtFoto.getText().trim();

				int idMusico = Integer.parseInt(txtIdMusico.getText().trim());

				if (titulo.isEmpty() || fecha.isEmpty() || genero.isEmpty() || foto.isEmpty()) {

					JOptionPane.showMessageDialog(this, "Completa todos los campos");
					return;
				}

				Musico m = ventana.getControladordb().obtenerMusicoPorId(idMusico);

				if (m == null) {
					JOptionPane.showMessageDialog(this, "no existe un musico con este id");
					return;
				}

				Album a = new Album(0, titulo, fecha, genero, foto, idMusico);

				ventana.getControladordb().insertarAlbum(a);

				JOptionPane.showMessageDialog(this, "Album añadido");

				dispose();

			} catch (NumberFormatException ex) {

				JOptionPane.showMessageDialog(this, "El ID debe ser un número");
			}
		});

		add(new JLabel());
		add(btnCrear);
	}
}