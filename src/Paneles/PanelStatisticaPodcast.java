package Paneles;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Ventanas.VentanaPrincipal;
import modelo.StatisticaPodcast;

public class PanelStatisticaPodcast extends JPanel {
	private VentanaPrincipal ventana;
	private JTable tabla;
	private DefaultTableModel modelo;
	private ArrayList<StatisticaPodcast> listaPodcast = new ArrayList<StatisticaPodcast>();

	public PanelStatisticaPodcast(VentanaPrincipal ventana, ArrayList<StatisticaPodcast> listaPodcast) {
		this.ventana = ventana;
		this.listaPodcast = listaPodcast;
		setLayout(null);
		setBackground(Color.WHITE);

		// BOTON ATRAS
		JButton btnAtras = new JButton("Atras");
		btnAtras.setBounds(20, 20, 120, 40);
		add(btnAtras);
		btnAtras.addActionListener(e -> ventana.cambiarPanel("Estatisticas"));
		// BOTONES CENTRALES
		JButton btnMes = new JButton("Mes");
		JButton btnSemana = new JButton("Semana");
		JButton btnAnos = new JButton("Anos");

		btnMes.setBounds(180, 20, 140, 40);
		btnSemana.setBounds(340, 20, 140, 40);
		btnAnos.setBounds(500, 20, 140, 40);

		add(btnMes);
		add(btnSemana);
		add(btnAnos);

		// TABLA
		String[] columnas = { "Podcast", "Podcaster", "Likes" };

		modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnas);

		tabla = new JTable(modelo);
		tabla.setRowHeight(30);
		tabla.setFont(new Font("Arial", Font.PLAIN, 16));

		// CARGAR DATOS
		for (StatisticaPodcast stat : listaPodcast) {

			Object[] fila = { stat.getNombreAudio(), stat.getNombreArtistico(), stat.getNumReproduciones() };

			modelo.addRow(fila);
		}

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(50, 100, 700, 450);

		add(scroll);
	}

//listener 

}