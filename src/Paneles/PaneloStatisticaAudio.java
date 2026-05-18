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
import modelo.StastisticaCancion;
import modelo.StatisticaAudio;

public class PaneloStatisticaAudio extends JPanel {
	private VentanaPrincipal ventana;
	ArrayList<StatisticaAudio> listaAudio = new ArrayList<StatisticaAudio>();
	private JTable tabla;
	private DefaultTableModel modelo;

	public PaneloStatisticaAudio(VentanaPrincipal ventana, ArrayList<StatisticaAudio> listaAudio) {
		this.ventana = ventana;
		this.listaAudio = listaAudio;
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
		String[] columnas = { "Cancion", "Artista", "Reproducciones" };

		modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnas);

		tabla = new JTable(modelo);
		tabla.setRowHeight(30);
		tabla.setFont(new Font("Arial", Font.PLAIN, 16));

		// CARGAR DATOS
		for (StatisticaAudio stat : listaAudio) {

			Object[] fila = { stat.getNombreArtista(), stat.getNombreAudio(), stat.getReproducioines() };

			modelo.addRow(fila);
		}

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(50, 100, 700, 450);

		add(scroll);
	}
}