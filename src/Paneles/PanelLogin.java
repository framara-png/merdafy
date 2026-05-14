package Paneles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Ventanas.VentanaPrincipal;

public class PanelLogin extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelLogin(VentanaPrincipal ventana) {
		setSize(ventana.getSize());
System.out.println("MERDAFY");
		setLayout(null);
		// DECLARAR COMPONENTES
		JLabel lblUsuario = new JLabel("usuario");
		JLabel lblPass = new JLabel("Contraseña");
		JPasswordField txtPass = new JPasswordField();
		JTextField txtUsuario = new JTextField();
		JButton btnLogin = new JButton("Login");
		JButton btnRegistrarse = new JButton("registrarse");
		// COLOCAR COMPONENTES
		lblUsuario.setBounds(100, 100, 150, 30);
		lblPass.setBounds(100, 200, 150, 30);
		txtUsuario.setBounds(300, 100, 150, 30);
		txtPass.setBounds(300, 200, 150, 30);
		btnLogin.setBounds(300, 300, 150, 30);
		btnRegistrarse.setBounds(100, 300, 150, 30);
		// AÑADIR COMPONENTES AL PANEL
		this.add(lblUsuario);
		this.add(lblPass);
		this.add(txtUsuario);
		this.add(txtPass);
		this.add(btnLogin);
		this.add(btnRegistrarse);
		// AÑADIR LISTENERS

		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.cambiarPanel("cliente");
			}
		});

		btnRegistrarse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.cambiarPanel("registro");

			}
		});
	}
}
