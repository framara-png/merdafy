package Paneles;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import Ventanas.VentanaPrincipal;
import controlador.GestorCliente;
import modelo.Podcaster;
import modelo.cliente;


public class PanelPodcasters extends JPanel{
	  private GestorCliente gestor = new GestorCliente();
	    private JList<String> listPodcasters;
	    private DefaultListModel<String> listModel;

	    public PanelPodcasters(VentanaPrincipal ventana, cliente clientelogeado) {
	        setLayout(null);

	        // BOTONES
	        JButton btnPerfil = new JButton("Perfil");
	        JButton btnAtras = new JButton("Atrás");
	        btnPerfil.setBounds(10, 10, 80, 30);
	        btnAtras.setBounds(700, 10, 80, 25);
	        btnAtras.addActionListener(e -> ventana.cambiarPanel("cliente"));
	        btnPerfil.addActionListener(e -> ventana.cambiarPanel("perfil"));
	        this.add(btnAtras);
	        this.add(btnPerfil);

	        // CAPTURAR LOS MÚSICOS DEL GESTOR
	        List<String> musicos = obtenerListaPodcasters();
	        
	        // CREAR MODELO Y LISTA
	        listModel = new DefaultListModel<>();
	        for (String musico : musicos) {
	            listModel.addElement(musico);
	        }
	        
	        listPodcasters = new JList<>(listModel);
	        listPodcasters.setFont(new Font("Arial", Font.BOLD, 18)); // Fuente grande
	        listPodcasters.setFixedCellHeight(40); // Altura fija para separarlos bien
	        listPodcasters.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        listPodcasters.setBackground(new Color(240, 240, 240));
	        
	        // HACER CLICABLE CADA ELEMENTO
	        listPodcasters.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                if (e.getClickCount() == 1) {

	                    String seleccionado = listPodcasters.getSelectedValue();
	                    if (seleccionado == null) return;

	                    String nombreArtista = seleccionado.split(" - Reproducciones: ")[0].trim();

	                    Podcaster p = gestor.obtenerPodcasterPorNombre(nombreArtista);

	                    if (p == null) return;

	                    ventana.setPodcasterSeleccionado(p);

	                    ventana.cambiarPanel("podcaster");
	                }
	            }
	        });
	        
	        JScrollPane scrollPane = new JScrollPane(listPodcasters);
	        scrollPane.setBounds(50, 50, 700, 450);
	        this.add(scrollPane);
	    }
	    
	    private List<String> obtenerListaPodcasters() {
	        List<String> musicos = new ArrayList<>();
	        
	        // Capturar la salida del método visualizarMusicos()
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PrintStream ps = new PrintStream(baos);
	        PrintStream old = System.out;
	        System.setOut(ps);
	        
	        gestor.obtenerPodcasters();
	        
	        System.out.flush();
	        System.setOut(old);
	        
	        // Procesar cada línea de la salida
	        String output = baos.toString();
	        String[] lineas = output.split("\n");
	        
	        for (String linea : lineas) {
	            linea = linea.trim();
	            if (!linea.isEmpty() && (linea.contains("NumReproduciones") || linea.contains("Reproducciones"))) {
	                // Limpiar el formato y extraer la información relevante
	                String limpia = linea.replace("NumReproduciones", "- Reproducciones:");
	                musicos.add(limpia);
	            } else if (!linea.isEmpty() && !linea.startsWith("---") && !linea.startsWith("Músicos")) {
	                // Si ya viene con formato limpio
	                musicos.add(linea);
	            }
	        }
	        
	        // Si no se capturó nada, agregar datos de ejemplo
	        if (musicos.isEmpty()) {
	            musicos.add("Artista 1 - Reproducciones: 1000");
	            musicos.add("Artista 2 - Reproducciones: 2000");
	            musicos.add("Artista 3 - Reproducciones: 1500");
	        }
	        
	        return musicos;
	    }
}
