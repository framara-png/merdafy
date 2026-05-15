package Paneles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Ventanas.VentanaPrincipal;
import controlador.GestorCliente;
import modelo.*;

public class PanelArtista extends JPanel {
    
    private GestorCliente gestor = new GestorCliente();
    private JList<String> listDiscos;
    private DefaultListModel<String> listModel;
    private JTextArea txtInfoArtista;
    private JLabel lblFoto;
    private Musico artistaActual;  // Cambiado a Muisco

    public PanelArtista(VentanaPrincipal ventana, cliente clientelogeado, String nombreArtista) {  // Cambiado parámetro
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        this.artistaActual = gestor.obtenerMusicoPorNombre(nombreArtista);
        
        // PANEL SUPERIOR CON BOTONES
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnPerfil = new JButton("Perfil");
        JButton btnAtras = new JButton("Atrás");
        btnAtras.addActionListener(e -> ventana.cambiarPanel("cliente"));
        btnPerfil.addActionListener(e -> ventana.cambiarPanel("perfil"));
        panelSuperior.add(btnAtras);
        panelSuperior.add(btnPerfil);
        add(panelSuperior, BorderLayout.NORTH);
        
        // PANEL CENTRAL CON DOS COLUMNAS
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // ===== COLUMNA IZQUIERDA: LISTA DE DISCOS =====
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Discografía"));
        
        listModel = new DefaultListModel<>();
        listDiscos = new JList<>(listModel);
        listDiscos.setFont(new Font("Arial", Font.PLAIN, 14));
        listDiscos.setFixedCellHeight(50);
        listDiscos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Renderer personalizado para mostrar discos con formato
        listDiscos.setCellRenderer(new DiscoListRenderer());
        
        listDiscos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    String seleccionado = listDiscos.getSelectedValue();
                    if (seleccionado != null && !seleccionado.equals("No hay discos disponibles")) {
                        // Extraer el nombre del disco
                        String nombreDisco = seleccionado.split(" \\| ")[0];
                        // Crear objeto album o pasar nombre
                        Album albumTemp = new Album();
                        albumTemp.setTitulo(nombreDisco);
                        ventana.setAl(albumTemp);
                        ventana.cambiarPanel("canciones");  // Va directo a canciones
                    }
                }
            }
        });
        
        JScrollPane scrollDiscos = new JScrollPane(listDiscos);
        panelIzquierdo.add(scrollDiscos, BorderLayout.CENTER);
        
        // ===== COLUMNA DERECHA: INFORMACIÓN DEL ARTISTA =====
        JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Información del Artista"));
        
        // Panel para foto y datos
        JPanel panelInfoArtista = new JPanel(new BorderLayout(10, 10));
        
        // Foto del artista (usando la del objeto Muisco)
        lblFoto = new JLabel();
        lblFoto.setHorizontalAlignment(JLabel.CENTER);
        lblFoto.setPreferredSize(new Dimension(200, 200));
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Cargar la foto del artista si existe
        if (artistaActual != null && artistaActual.getFoto() != null) {
            String rutaFoto = artistaActual.getFoto();
            if (rutaFoto != null && !rutaFoto.isEmpty()) {
                ImageIcon fotoIcon = new ImageIcon(rutaFoto);
                Image img = fotoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                lblFoto.setIcon(new ImageIcon(img));
            } else {
                lblFoto.setText("Sin foto");
                lblFoto.setHorizontalAlignment(JLabel.CENTER);
            }
        } else {
            lblFoto.setText("Sin foto");
            lblFoto.setHorizontalAlignment(JLabel.CENTER);
        }
        panelInfoArtista.add(lblFoto, BorderLayout.NORTH);
        
        // Información del artista (género, descripción)
        txtInfoArtista = new JTextArea();
        txtInfoArtista.setEditable(false);
        txtInfoArtista.setFont(new Font("Arial", Font.PLAIN, 12));
        txtInfoArtista.setLineWrap(true);
        txtInfoArtista.setWrapStyleWord(true);
        
        // Cargar la información del artista usando los getters de Muisco
        if (artistaActual != null) {
            StringBuilder info = new StringBuilder();
            info.append("Nombre: ").append(artistaActual.getNombreArt()).append("\n");
            info.append("Género: ").append(artistaActual.getGenero() != null ? artistaActual.getGenero() : "No especificado").append("\n\n");
            info.append("Descripción:\n").append(artistaActual.getDescripcion() != null ? artistaActual.getDescripcion() : "Sin descripción").append("\n");
            txtInfoArtista.setText(info.toString());
        } else {
            txtInfoArtista.setText("No hay información disponible del artista");
        }
        
        JScrollPane scrollInfo = new JScrollPane(txtInfoArtista);
        panelInfoArtista.add(scrollInfo, BorderLayout.CENTER);
        
        panelDerecho.add(panelInfoArtista, BorderLayout.CENTER);
        
        panelCentral.add(panelIzquierdo);
        panelCentral.add(panelDerecho);
        add(panelCentral, BorderLayout.CENTER);
        
        // CARGAR DISCOS DEL ARTISTA
        if (artistaActual != null) {
            cargarDiscos(artistaActual.getNombreArt());
        }
    }
    
    private void cargarDiscos(String nombreArtista) {
        listModel.clear();
        
        // Capturar la salida del gestor
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        java.io.PrintStream old = System.out;
        System.setOut(ps);
        
        gestor.visualizarAlbum(nombreArtista);
        
        System.out.flush();
        System.setOut(old);
        
        String output = baos.toString();
        String[] lineas = output.split("\n");
        
        for (String linea : lineas) {
            linea = linea.trim();
            if (!linea.isEmpty() && !linea.contains("Discografia de") && !linea.contains("---")) {
                String formatoDisco = formatearDisco(linea);
                listModel.addElement(formatoDisco);
            }
        }
        
        if (listModel.isEmpty()) {
            listModel.addElement("No hay discos disponibles");
        }
    }
    
    private String formatearDisco(String linea) {
        String titulo = linea;
        if (linea.contains(" - Reproducciones: ")) {
            titulo = linea.split(" - Reproducciones: ")[0];
        }
        
        // Aquí deberías obtener la fecha real y número de canciones
        return titulo + " | 2024 | 10 canciones";
    }
    
    // Renderer personalizado para mostrar discos con formato
    class DiscoListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                                                       int index, boolean isSelected, 
                                                       boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value != null) {
                String texto = value.toString();
                if (!texto.equals("No hay discos disponibles")) {
                    String[] partes = texto.split(" \\| ");
                    if (partes.length >= 3) {
                        setText("<html><b>" + partes[0] + "</b><br>" +
                                "📅 " + partes[1] + " | 🎵 " + partes[2] + "</html>");
                    } else {
                        setText("<html><b>" + texto + "</b></html>");
                    }
                }
            }
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            return this;
        }
    }
}