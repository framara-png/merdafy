package Paneles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Ventanas.VentanaPrincipal;
import controlador.GestorCliente;
import modelo.Playlist;
import modelo.cliente;

public class PanelPlaylist extends JPanel {

    private GestorCliente gestor = new GestorCliente();

    private JList<String> listPlaylist;
    private DefaultListModel<String> listModel;

    private ArrayList<Playlist> playlistsActuales;

    public PanelPlaylist(VentanaPrincipal ventana, cliente clienteLogeado) {

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ================= TOP =================
        JPanel top = new JPanel(new BorderLayout());

        JButton btnAtras = new JButton("Atrás");
        JButton btnPerfil = new JButton("Perfil");

        btnAtras.addActionListener(e -> ventana.cambiarPanel("cliente"));
        btnPerfil.addActionListener(e -> ventana.cambiarPanel("perfil"));

        top.add(btnAtras, BorderLayout.WEST);
        top.add(btnPerfil, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        // ================= CENTER =================
        JPanel center = new JPanel(new BorderLayout(15, 15));

        // ================= LISTA PLAYLIST =================
        JPanel panelLista = new JPanel(new BorderLayout());
        panelLista.setBorder(
                BorderFactory.createTitledBorder("Tus Playlists")
        );

        listModel = new DefaultListModel<>();
        listPlaylist = new JList<>(listModel);

        listPlaylist.setFont(new Font("Arial", Font.BOLD, 16));
        listPlaylist.setFixedCellHeight(45);
        listPlaylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        panelLista.add(new JScrollPane(listPlaylist), BorderLayout.CENTER);

        // CLICK PLAYLIST
        listPlaylist.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 1) {

                    int index = listPlaylist.getSelectedIndex();

                    if (index < 0 || playlistsActuales == null) return;

                    Playlist seleccionada = playlistsActuales.get(index);

                    ventana.setPlaylistSelecionada(seleccionada);

                    ventana.cambiarPanel("cancionesPlaylist");
                }
            }
        });

        // ================= BOTONES DERECHA =================
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(4, 1, 10, 20));
        panelBotones.setPreferredSize(new Dimension(220, 300));

        JButton btnCrear = new JButton("Crear Nueva");
        JButton btnEliminar = new JButton("Eliminar Playlist");
        JButton btnImportar = new JButton("Importar");
        JButton btnExportar = new JButton("Exportar");

        Font fontBotones = new Font("Arial", Font.BOLD, 18);

        btnCrear.setFont(fontBotones);
        btnEliminar.setFont(fontBotones);
        btnImportar.setFont(fontBotones);
        btnExportar.setFont(fontBotones);

        btnCrear.setPreferredSize(new Dimension(200, 60));
        btnEliminar.setPreferredSize(new Dimension(200, 60));
        btnImportar.setPreferredSize(new Dimension(200, 60));
        btnExportar.setPreferredSize(new Dimension(200, 60));

        // ================= ACCIONES =================

        btnCrear.addActionListener(e -> {

            String nombre = JOptionPane.showInputDialog(
                    this,
                    "Nombre playlist:"
            );

            if (nombre != null && !nombre.trim().isEmpty()) {

                gestor.controladordb.iniciarConexion();

                gestor.controladordb.insertarPlaylist(
                        nombre,
                        clienteLogeado.getId()
                );

                gestor.controladordb.cerrarConexion();

                cargarPlaylists(clienteLogeado);
            }
        });

        btnEliminar.addActionListener(e -> {

            int index = listPlaylist.getSelectedIndex();

            if (index < 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Selecciona una playlist"
                );
                return;
            }

            Playlist seleccionada = playlistsActuales.get(index);

            gestor.controladordb.iniciarConexion();

            gestor.controladordb.eliminarPlaylist(
                    seleccionada.getTitulo(),clienteLogeado.getUsuario()
            );

            gestor.controladordb.cerrarConexion();

            cargarPlaylists(clienteLogeado);
        });

        btnImportar.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Importar playlist próximamente"
            );
        });

        btnExportar.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Exportar playlist próximamente"
            );
        });

        panelBotones.add(btnCrear);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnImportar);
        panelBotones.add(btnExportar);

        // ================= AGREGAR =================
        center.add(panelLista, BorderLayout.CENTER);
        center.add(panelBotones, BorderLayout.EAST);

        add(center, BorderLayout.CENTER);

        // ================= CARGAR PLAYLIST =================
        cargarPlaylists(clienteLogeado);
    }

    // ================= LOAD PLAYLIST =================
    private void cargarPlaylists(cliente clienteLogeado) {

        listModel.clear();

        playlistsActuales = gestor.obtenerPlaylist(clienteLogeado);

        if (playlistsActuales != null && !playlistsActuales.isEmpty()) {

            for (Playlist p : playlistsActuales) {

                listModel.addElement(
                        p.getTitulo()
                );
            }

        } else {

            listModel.addElement("No tienes playlists");
        }
    }
}