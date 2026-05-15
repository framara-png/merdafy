package Paneles;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

import Ventanas.VentanaPrincipal;
import controlador.GestorCliente;
import modelo.Podcaster;
import modelo.cliente;

public class PanelPodcasters extends JPanel {

    private GestorCliente gestor = new GestorCliente();
    private JList<String> listPodcasters;
    private DefaultListModel<String> listModel;

    public PanelPodcasters(VentanaPrincipal ventana, cliente clientelogeado) {

        setLayout(null);

        // ================= BOTONES =================
        JButton btnPerfil = new JButton("Perfil");
        JButton btnAtras = new JButton("Atrás");

        btnPerfil.setBounds(10, 10, 80, 30);
        btnAtras.setBounds(700, 10, 80, 25);

        btnAtras.addActionListener(e -> ventana.cambiarPanel("cliente"));
        btnPerfil.addActionListener(e -> ventana.cambiarPanel("perfil"));

        add(btnAtras);
        add(btnPerfil);

        // ================= DATI =================
        ArrayList<Podcaster> podcasters = gestor.obtenerPodcasters();

        listModel = new DefaultListModel<>();

        if (podcasters != null && !podcasters.isEmpty()) {
            for (Podcaster p : podcasters) {

                int rep = gestor.obtenerRepTotPodcaster(p.getNombreArt());

                listModel.addElement(
                        p.getNombreArt()
                        + " - Reproducciones: "
                        + rep
                );
            }
        } else {
            listModel.addElement("No hay podcasters disponibles");
        }

        // ================= LISTA =================
        listPodcasters = new JList<>(listModel);
        listPodcasters.setFont(new Font("Arial", Font.BOLD, 18));
        listPodcasters.setFixedCellHeight(40);
        listPodcasters.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listPodcasters.setBackground(new Color(240, 240, 240));

        // ================= CLICK =================
        listPodcasters.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 1) {

                    String sel = listPodcasters.getSelectedValue();
                    if (sel == null || sel.startsWith("No hay")) return;

                    String nombre = sel.split(" - Reproducciones: ")[0].trim();

                    Podcaster p = gestor.obtenerPodcasterPorNombre(nombre);
                    if (p == null) return;

                    // 🔥 QUI METTI IL PODCASTER NELLA VENTANA
                    ventana.setPodcasterSeleccionado(p);

                    // vai alla pagina podcaster (o quella che usi)
                    ventana.cambiarPanel("podcast");
                }
            }
        });

        JScrollPane scroll = new JScrollPane(listPodcasters);
        scroll.setBounds(50, 50, 700, 450);

        add(scroll);
    }
}