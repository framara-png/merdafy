package Paneles;

import javax.swing.*;
import java.awt.*;

import Ventanas.VentanaPrincipal;
import modelo.Musico;

public class DialogCrearMusico extends JDialog {

	private VentanaPrincipal ventana;

	public DialogCrearMusico(VentanaPrincipal ventana) {

		super(ventana, "Crear Musico", true);

		this.ventana = ventana;

		setSize(400, 350);
		setLocationRelativeTo(ventana);
		setLayout(new GridLayout(7, 2, 10, 10));

		// ================= CAMPOS =================
		JTextField txtNombre = new JTextField();
		JTextField txtGenero = new JTextField();
		JTextField txtDescripcion = new JTextField();
		JTextField txtFoto = new JTextField();
		JTextField txtComposicion = new JTextField();
		JComboBox<String> Jcomposicion = new JComboBox<>();
		Jcomposicion.addItem("solista");
		Jcomposicion.addItem("grupo");


		// ================= LABELS =================
		add(new JLabel("Nombre músico:"));
		add(txtNombre);

		add(new JLabel("Genero:"));
		add(txtGenero);

		add(new JLabel("Descripción:"));
		add(txtDescripcion);

		add(new JLabel("Foto (path):"));
		add(txtFoto);
		
		add(new JLabel("Composición:"));
		add(Jcomposicion);
		

		// ================= BOTON =================
		JButton btnCrear = new JButton("Crear");
		
		btnCrear.addActionListener(e -> {

			String nombre = txtNombre.getText().trim();
			String genero = txtGenero.getText().trim();
			String descripcion = txtDescripcion.getText().trim();
			String foto = txtFoto.getText().trim();
	
			String composicion = Jcomposicion.getSelectedItem().toString();

			if (nombre.isEmpty() || genero.isEmpty() || descripcion.isEmpty() || foto.isEmpty()
					|| composicion.isEmpty()) {

				JOptionPane.showMessageDialog(this, "Completa todos los campos");
				return;
			}

			if (!ventana.getGestor().controladorArtistaDoble(nombre)) {

				JOptionPane.showMessageDialog(this, "Ya existe un músico con este nombre");
				return;
			}

			Musico m = new Musico(0, nombre, genero, descripcion, foto, composicion);

			ventana.getControladordb().insertarMusico(m);

			JOptionPane.showMessageDialog(this, "Músico creado");
			ventana.cambiarPanel("GestionMusicos");
			dispose();
		});

		add(new JLabel());
		add(btnCrear);
	}
}