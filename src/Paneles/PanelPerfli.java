package Paneles;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Ventanas.VentanaPrincipal;
import modelo.cliente;

public class PanelPerfli extends JPanel {
    
    public PanelPerfli(VentanaPrincipal ventana, cliente clientelogeado) {
        setLayout(null);
        setBackground(new java.awt.Color(240, 240, 240));
        
        // TITULO GRANDE CENTRADO
        JLabel lblTitulo = new JLabel("MI PERFIL");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setBounds(300, 20, 200, 40);
        this.add(lblTitulo);
        
        // DATOS - TODO CENTRADO Y GRANDE
        int y = 100;
        int separacion = 45;
        
        JLabel lblNombre = new JLabel("Nombre: " + clientelogeado.getNombre());
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 18));
        lblNombre.setBounds(200, y, 400, 30);
        this.add(lblNombre);
        
        JLabel lblApellido = new JLabel("Apellido: " + clientelogeado.getApellido());
        lblApellido.setFont(new Font("Arial", Font.PLAIN, 18));
        lblApellido.setBounds(200, y + separacion, 400, 30);
        this.add(lblApellido);
        
        JLabel lblIdioma = new JLabel("Idioma: " + clientelogeado.getIdioma());
        lblIdioma.setFont(new Font("Arial", Font.PLAIN, 18));
        lblIdioma.setBounds(200, y + separacion * 2, 400, 30);
        this.add(lblIdioma);
        
        JLabel lblUsuario = new JLabel("Usuario: " + clientelogeado.getUsuario());
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
        lblUsuario.setBounds(200, y + separacion * 3, 400, 30);
        this.add(lblUsuario);
        
        JLabel lblFechaNac = new JLabel("Fecha Nacimiento: " + clientelogeado.getFecNac());
        lblFechaNac.setFont(new Font("Arial", Font.PLAIN, 18));
        lblFechaNac.setBounds(200, y + separacion * 4, 400, 30);
        this.add(lblFechaNac);
        
        JLabel lblFechaReg = new JLabel("Fecha Registro: " + clientelogeado.getFecReg());
        lblFechaReg.setFont(new Font("Arial", Font.PLAIN, 18));
        lblFechaReg.setBounds(200, y + separacion * 5, 400, 30);
        this.add(lblFechaReg);
        
 
        // BOTON ATRAS
        JButton btnAtras = new JButton("ATRAS");
        btnAtras.setFont(new Font("Arial", Font.BOLD, 14));
        btnAtras.setBounds(700, 10, 80, 25);
        btnAtras.addActionListener(e -> ventana.cambiarPanel("cliente"));
        this.add(btnAtras);
    }
}