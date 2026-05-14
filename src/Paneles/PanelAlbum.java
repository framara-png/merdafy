package Paneles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import Ventanas.VentanaPrincipal;
import controlador.GestorCliente;
import modelo.*;
public class PanelAlbum extends JPanel {
    
    private GestorCliente gestor = new GestorCliente();
    private JTextArea txtArea;
    
    public PanelAlbum(VentanaPrincipal ventana, cliente clientelogeado, String NombreArt) {
        setLayout(null);
     // BOTON ATRAS
     		JButton btnProfilo = new JButton("perfil");
     		JButton btnAtras = new JButton("atras");
     		btnProfilo.setBounds(10, 10, 80, 30);
     		btnAtras.setBounds(700, 10, 80, 25);
     		btnAtras.addActionListener(e -> ventana.cambiarPanel("cliente"));
     		btnProfilo.addActionListener(e -> ventana.cambiarPanel("perfil"));
     		this.add(btnAtras);
     		this.add(btnProfilo);
        
        // BOTON PARA GUARDAR LO SELECCIONADO
        JButton btnSeleccionar = new JButton("Seleccionar");
        btnSeleccionar.setBounds(350, 520, 100, 30);
        btnSeleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccionado = txtArea.getSelectedText();
                if (seleccionado != null && !seleccionado.isEmpty()) {
                    System.out.println("Has seleccionado: " + seleccionado);
                    JOptionPane.showMessageDialog(null, "Seleccionaste: " + seleccionado);
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un texto primero, porcodio!");
                }
            }
        });
        this.add(btnSeleccionar);
        
        // CAPTURAR PRINT
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        
        gestor.visualizarAlbum(NombreArt);
        
        System.out.flush();
        System.setOut(old);
        
        // TEXTO
        txtArea = new JTextArea(baos.toString());
        txtArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(txtArea);
        scrollPane.setBounds(50, 50, 700, 450);
        this.add(scrollPane);
    }
}