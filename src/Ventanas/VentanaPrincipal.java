package Ventanas;

import javax.swing.JFrame;

import Paneles.PanelLogin;
import Paneles.PanelRegistro;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	public VentanaPrincipal() {

		setSize(800, 600); // Tamaño
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Operacion de cierre
		setResizable(false); // No redimensionar
		setTitle("Ventana Principal"); // Titulo

	}

	public void cambiarPanel(String nombrePanel) {

		switch (nombrePanel) {
		case "login":
			PanelLogin panelLogin = new PanelLogin(this);
			setContentPane(panelLogin);
			 revalidate();
			break;

		case "registro":
			PanelRegistro panelRegistro = new PanelRegistro(this);
		setContentPane(panelRegistro);
		 revalidate();
		break;
		}
	}

	public void ejecutarVentana() {
		cambiarPanel("login");
		setVisible(true);
	}
}
