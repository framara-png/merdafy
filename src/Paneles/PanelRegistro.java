package Paneles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Ventanas.VentanaPrincipal;
import controlador.*;
import modelo.*;

public class PanelRegistro extends JPanel {
    controladorDB controladordb = new controladorDB("merdafy");
    private cliente clienteTemp = new cliente();
    
    
    public PanelRegistro(VentanaPrincipal ventana) {
        setSize(ventana.getSize());
        setLayout(null);
        controladordb.iniciarConexion();
       clienteTemp.setId(0); 
        // Creazione componenti
        JLabel lblNombre = new JLabel("nombre");
        JLabel lblApellido = new JLabel("apellido");
        JLabel lblUsuario = new JLabel("usuario");
        JLabel lblContrasena = new JLabel("contrasena");
        JLabel lblFecNac = new JLabel("Fecha Nacimiento");
     
        JComboBox<String> cmbAbonamento = new JComboBox<>();
        cmbAbonamento.addItem("Free");
        cmbAbonamento.addItem("Premium"); 
        
        ArrayList<String> idiomi = controladordb.obteneridiomas();
        JComboBox<String> cmbIdiomi = new JComboBox<>();
        for (String i : idiomi) {
            cmbIdiomi.addItem(i);
        }
        
        // Campi di input
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtUsuario = new JTextField();
        JPasswordField txtContrasena = new JPasswordField();
        JTextField txtFecNac = new JTextField();
        
        // Pulsanti
        JButton btnRegistrar = new JButton("Registrarse");
        JButton btnVolver = new JButton("Volver");
        
        // SETBOUNDS
        lblNombre.setBounds(150, 50, 100, 25);
        lblApellido.setBounds(150, 90, 100, 25);
        lblUsuario.setBounds(150, 130, 100, 25);
        lblContrasena.setBounds(150, 170, 100, 25);
        lblFecNac.setBounds(150, 210, 150, 25);
        cmbIdiomi.setBounds(300, 250, 200, 25);
        cmbAbonamento.setBounds(300, 290, 200, 25);
        
        txtNombre.setBounds(300, 50, 200, 25);
        txtApellido.setBounds(300, 90, 200, 25);
        txtUsuario.setBounds(300, 130, 200, 25);
        txtContrasena.setBounds(300, 170, 200, 25);
        txtFecNac.setBounds(300, 210, 200, 25);
        
        btnRegistrar.setBounds(200, 400, 150, 30);
        btnVolver.setBounds(400, 400, 150, 30);
        
        // Aggiunta componenti
        add(lblNombre);
        add(txtNombre);
        add(lblApellido);
        add(txtApellido);
        add(lblUsuario);
        add(txtUsuario);
        add(lblContrasena);
        add(txtContrasena);
        add(lblFecNac);
        add(txtFecNac);
        add(cmbIdiomi);
        add(cmbAbonamento);
        add(btnRegistrar);
        add(btnVolver);
        
        // BIND dei campi all'oggetto cliente (si aggiorna in automatico) il bind collega l interfaccia con l oggetto cliente
      // esto hace de manera que los datos se sobresrriben en automatico hasta que el cliente confirma el registro y guardad en la db
        bindCampoACliente(txtNombre, clienteTemp::setNombre);
        bindCampoACliente(txtApellido, clienteTemp::setApellido);
        bindCampoACliente(txtUsuario, clienteTemp::setUsuario);
        bindCampoACliente(txtFecNac, clienteTemp::setFecNac);
        bindPasswordACliente(txtContrasena, clienteTemp::setContrasena);
        bindComboACliente(cmbIdiomi, clienteTemp::setIdioma);
        
        // Bind per l'abbonamento (converte String a boolean)
        cmbAbonamento.addActionListener(e -> {
            String seleccion = (String) cmbAbonamento.getSelectedItem();
            clienteTemp.setEsPremium("Premium".equals(seleccion));
        });
        
        // ActionListeners
        btnRegistrar.addActionListener(new ActionListener() {
          
            	 @Override
            	    public void actionPerformed(ActionEvent e) {
            	        System.out.println("Idioma selezionato: " + clienteTemp.getIdioma());
            	        System.out.println("Lunghezza: " + clienteTemp.getIdioma().length());
            	        controladordb.insertarCliente(clienteTemp);
            }
        });
        
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.cambiarPanel("login");
            }
        });
    }
    
    // Metodi di bind
    private void bindCampoACliente(JTextField campo, java.util.function.Consumer<String> setter) {
        campo.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { setter.accept(campo.getText()); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { setter.accept(campo.getText()); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { setter.accept(campo.getText()); }
        });
    }
    
    private void bindPasswordACliente(JPasswordField campo, java.util.function.Consumer<String> setter) {
        campo.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { setter.accept(new String(campo.getPassword())); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { setter.accept(new String(campo.getPassword())); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { setter.accept(new String(campo.getPassword())); }
        });
    }
    // metodo che sirve en caso de tener varias opciones que selecionar en un combobox
    private void bindComboACliente(JComboBox<String> combo, java.util.function.Consumer<String> setter) {
        combo.addActionListener(e -> setter.accept((String) combo.getSelectedItem()));
    }
}