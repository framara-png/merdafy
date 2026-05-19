package Paneles;

import modelo.*;
import Ventanas.VentanaPrincipal;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class PanelGestionPodcast extends JPanel {

    public VentanaPrincipal ventana;
    private cliente clienteLogeado;

    private ArrayList<Podcast> podcasts;

    private JList<String> listPodcasts;
    private DefaultListModel<String> listModel;

    public PanelGestionPodcast(VentanaPrincipal ventana, cliente clienteLogeado) {

        this.ventana = ventana;
        this.clienteLogeado = clienteLogeado;

        this.podcasts = ventana.getControladordb().obtenerTodosPodcasts();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ================= TOP =================
        JPanel top = new JPanel(new BorderLayout());

        JButton btnAtras = new JButton("Atrás");
        JButton btnPerfil = new JButton("Perfil");

        btnAtras.addActionListener(e -> ventana.cambiarPanel("panelAdmin"));
        btnPerfil.addActionListener(e -> ventana.cambiarPanel("perfil"));

        top.add(btnAtras, BorderLayout.WEST);
        top.add(btnPerfil, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        // ================= CENTER =================
        JPanel center = new JPanel(new BorderLayout(15, 15));

        // ================= LISTA =================
        JPanel panelLista = new JPanel(new BorderLayout());
        panelLista.setBorder(BorderFactory.createTitledBorder("Lista Podcast"));

        listModel = new DefaultListModel<>();
        listPodcasts = new JList<>(listModel);

        listPodcasts.setFont(new Font("Arial", Font.BOLD, 16));
        listPodcasts.setFixedCellHeight(45);
        listPodcasts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(listPodcasts);
        panelLista.add(scroll, BorderLayout.CENTER);

        // ================= CLICK =================
        listPodcasts.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {

                    int index = listPodcasts.getSelectedIndex();
                    if (index < 0 || podcasts == null || index >= podcasts.size()) return;

                    Podcast seleccionado = podcasts.get(index);

                    JOptionPane.showMessageDialog(
                            null,
                            "Podcast: " + seleccionado.getNombreAudio()
                    );
                }
            }
        });

        // ================= BOTONES =================
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 10, 20));
        panelBotones.setPreferredSize(new Dimension(250, 300));

        JButton btnAgregar = new JButton("Agregar Podcast");
        JButton btnModificar = new JButton("Modificar Podcast");
        JButton btnEliminar = new JButton("Eliminar Podcast");

        Font font = new Font("Arial", Font.BOLD, 18);

        btnAgregar.setFont(font);
        btnModificar.setFont(font);
        btnEliminar.setFont(font);

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);

        // ================= AGREGAR (CON DIALOG) =================
        btnAgregar.addActionListener(e -> {

            DialogCrearPodcast dialog = new DialogCrearPodcast(ventana);
            dialog.setVisible(true);

            // refresh dopo creazione
            podcasts = ventana.getControladordb().obtenerTodosPodcasts();
            cargarPodcasts();
        });

        // ================= MODIFICAR =================
        btnModificar.addActionListener(e -> {

            int index = listPodcasts.getSelectedIndex();
            if (index < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un podcast");
                return;
            }

            Podcast p = podcasts.get(index);

            String nuevoNombre = JOptionPane.showInputDialog(
                    this,
                    "Nuevo nombre:",
                    p.getNombreAudio()
            );

            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {

                p.setNombreAudio(nuevoNombre);

                ventana.getControladordb().actualizarPodcast(p);

                podcasts = ventana.getControladordb().obtenerTodosPodcasts();
                cargarPodcasts();
            }
        });

        // ================= ELIMINAR =================
        btnEliminar.addActionListener(e -> {

            int index = listPodcasts.getSelectedIndex();

            if (index < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un podcast");
                return;
            }

            Podcast p = podcasts.get(index);

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Eliminar " + p.getNombreAudio() + "?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {

                ventana.getControladordb().eliminarAudio(p.getNombreAudio());

                podcasts = ventana.getControladordb().obtenerTodosPodcasts();
                cargarPodcasts();
            }
        });

        // ================= ADD =================
        center.add(panelLista, BorderLayout.CENTER);
        center.add(panelBotones, BorderLayout.EAST);

        add(center, BorderLayout.CENTER);

        cargarPodcasts();
    }

    // ================= LOAD =================
    private void cargarPodcasts() {

        listModel.clear();

        if (podcasts != null && !podcasts.isEmpty()) {

            for (Podcast p : podcasts) {

                listModel.addElement(
                        p.getNombreAudio()
                        + " | Participantes: " + p.getNumeroParticipantes()
                        + " | Reproducciones: " + p.getNumRep()
                );
            }

        } else {
            listModel.addElement("No hay podcasts");
        }
    }
   
}