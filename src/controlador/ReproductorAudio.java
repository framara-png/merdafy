package controlador;

import java.util.*;
import modelo.*;

public class ReproductorAudio {
	private int contador = 0;
	private controladorDB controladordb = new controladorDB("merdafy");

	public audio SiguenteAudio(ArrayList<audio> audios) {
		contador++;

		if (audios.size() <= contador) {
			contador = 0;
		}
		return audios.get(contador);
	}

	public audio AntecedenteAudio(ArrayList<audio> audios) {
		contador--;
		if (contador < 0) {
			contador = audios.size() - 1;

		}
		return audios.get(contador);
	}

	public void DarleAlLike(int idCancion, int idCliente) {
		controladordb.iniciarConexion();
		controladordb.anadirAGustos(idCancion, idCliente);
	}


}
