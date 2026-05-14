package Paneles;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import Ventanas.VentanaPrincipal;
import controlador.GestorCliente;
import modelo.*;
import controlador.*;
public class PanelMusicos extends JPanel {
    
    private GestorCliente gestor = new GestorCliente();
    
    public PanelMusicos(VentanaPrincipal ventana, cliente clientelogeado) {
        setLayout(null);
        
        // BOTON ATRAS
        JButton btnAtras = new JButton("atras");
        btnAtras.setBounds(700, 10, 80, 25);
        btnAtras.addActionListener(e -> ventana.cambiarPanel("cliente"));
        this.add(btnAtras);
        
        // CAPTURAR PRINT
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        
        gestor.visualizarMusicos();
        
        System.out.flush();
        System.setOut(old);
        
        // TEXTO PURO
        JTextArea txtArea = new JTextArea(baos.toString());
        txtArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(txtArea);
        scrollPane.setBounds(50, 50, 700, 500);
        this.add(scrollPane);
    }
}