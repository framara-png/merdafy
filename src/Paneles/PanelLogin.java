package Paneles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Ventanas.VentanaPrincipal;
import controlador.controladorDB;
import modelo.cliente;
import controlador.GestorCliente;

public class PanelLogin extends JPanel {

	private GestorCliente gestor;

	private cliente cli;

	public PanelLogin(VentanaPrincipal ventana) {

		this.gestor = new GestorCliente(ventana.getControladordb());

		setSize(ventana.getSize());
		setLayout(null);

		JLabel lblUsuario = new JLabel("usuario");
		JLabel lblPass = new JLabel("Contraseña");

		JTextField txtUsuario = new JTextField();
		JPasswordField txtPass = new JPasswordField();

		JButton btnLogin = new JButton("Login");
		JButton btnRegistrarse = new JButton("registrarse");

		lblUsuario.setBounds(100, 100, 150, 30);
		lblPass.setBounds(100, 200, 150, 30);
		txtUsuario.setBounds(300, 100, 150, 30);
		txtPass.setBounds(300, 200, 150, 30);
		btnLogin.setBounds(300, 300, 150, 30);
		btnRegistrarse.setBounds(100, 300, 150, 30);

		add(lblUsuario);
		add(lblPass);
		add(txtUsuario);
		add(txtPass);
		add(btnLogin);
		add(btnRegistrarse);

		btnLogin.addActionListener(e -> {

			String usuario = txtUsuario.getText();
			String pass = String.valueOf(txtPass.getPassword());

			cli = gestor.login(usuario, pass);

			if (cli == null) {
				System.out.println("Login fallido");
				return;
			}

			ventana.setClientelogeado(cli);

			if (gestor.esAdmin(cli)) {
				ventana.cambiarPanel("panelAdmin");
			} else {
				ventana.cambiarPanel("cliente");
			}
		});

		btnRegistrarse.addActionListener(e -> ventana.cambiarPanel("registro"));
	}
}