package Paneles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Ventanas.VentanaPrincipal;
import controlador.GestorCliente;
import modelo.Musico;
import modelo.cliente;

public class PanelMusicos extends JPanel {

	private VentanaPrincipal ventana;
	private JList<String> listMusicos;
	private DefaultListModel<String> listModel;

	public PanelMusicos(VentanaPrincipal ventana, cliente clientelogeado) {
		setLayout(null);
		this.ventana = ventana;
		// botones
		JButton btnPerfil = new JButton("Perfil");
		JButton btnAtras = new JButton("Atrás");

		btnPerfil.setBounds(10, 10, 80, 30);
		btnAtras.setBounds(700, 10, 80, 25);

		btnAtras.addActionListener(e -> ventana.cambiarPanel("cliente"));
		btnPerfil.addActionListener(e -> ventana.cambiarPanel("perfil"));

		add(btnAtras);
		add(btnPerfil);

		// datos
		ArrayList<Musico> musicos = ventana.getControladordb().obtenerMusicos();

		listModel = new DefaultListModel<>();

		for (Musico m : musicos) {
			int rep = ventana.getControladordb().obtenerReproduccionesTotalesMusico(m.getNombreArt());

			listModel.addElement(m.getNombreArt() + " - Reproducciones: " + rep);
		}

		listMusicos = new JList<>(listModel);
		listMusicos.setFont(new Font("Arial", Font.BOLD, 18));
		listMusicos.setFixedCellHeight(40);
		listMusicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// ================= CLICK =================
		listMusicos.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 1) {

					int index = listMusicos.getSelectedIndex();

					if (index < 0)
						return;

					Musico m = musicos.get(index);

					ventana.setMusicoSeleccionado(m);
					ventana.cambiarPanel("artista");
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(listMusicos);
		scrollPane.setBounds(50, 50, 700, 450);

		add(scrollPane);
	}
}