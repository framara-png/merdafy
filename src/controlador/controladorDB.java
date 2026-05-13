package controlador;

import java.sql.*;
import java.sql.Time;
import java.util.ArrayList;
import modelo.*;

public class controladorDB {
	private Connection conexion;
	private String nombreBD;

	// Constructores
	public controladorDB(String nombreBD) {
		this.nombreBD = nombreBD;
	}

	// Iniciar conexion
	public boolean iniciarConexion() {
		boolean conexionRealizada = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Parametros para la conexion --> URL, user, pass puede hacer falta el puerto
			// localhost:puerto/
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/" + this.nombreBD, "root", "");
			conexionRealizada = true;
		} catch (ClassNotFoundException e) {
			System.out.println("No se encontró la librería de sqlconnection.jar");
		} catch (SQLException e) {
			System.out.println("no se encontró la BD " + this.nombreBD);
		}

		return conexionRealizada;
	}

	// Cerrar conexion
	public boolean cerrarConexion() {
		boolean Conexioncerrada = false;

		try {
			if (conexion != null && !conexion.isClosed()) {
				conexion.close();
				Conexioncerrada = true;
			}
		} catch (SQLException e) {
			System.out.println("No hay conexion con la BD");
		}

		return Conexioncerrada;
	}

	public ArrayList<cliente> obtenerClientes() {
		ArrayList<cliente> clientes = new ArrayList<cliente>();
		/*
		 * 1.- Definir la query 2.- Procesar los datos 3.- Devolver los datos
		 */

		String query = "SELECT * FROM cliente";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				cliente nuevoCliente = new cliente(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getString(4), resultado.getString(5), resultado.getString(6), resultado.getString(7),
						resultado.getString(8), resultado.getBoolean(9));
				clientes.add(nuevoCliente);
			}
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return clientes;
	}

	public ArrayList<Musico> obtenerMusicos() {
		ArrayList<Musico> musicos = new ArrayList<Musico>();

		String query = "SELECT a.idArtista,a.nombreArtistico,a.genero,a.descripcion,a.imagen,m.caracteristica"
				+ " FROM artista a join musico m on a.idArtista = m.idMusico";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				Musico nuevoMusico = new Musico(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getString(4), resultado.getString(5), resultado.getString(6));
				musicos.add(nuevoMusico);
			}
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return musicos;
	}

	public ArrayList<Podcaster> obtenerPodcasters() {
		ArrayList<Podcaster> podcasters = new ArrayList<Podcaster>();

		String query = " SELECT a.idArtista,a.nombreArtistico,a.genero,a.descripcion,"
				+ "a.imagen FROM artista a join podcaster p on a.idArtista = p.idPodcaster";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				Podcaster nuevoPodcaster = new Podcaster(resultado.getInt(1), resultado.getString(2),
						resultado.getString(3), resultado.getString(4), resultado.getString(5));
				podcasters.add(nuevoPodcaster);
			}
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return podcasters;
	}

	public ArrayList<Album> obtenerAlbum(String nombreMusico) {
		ArrayList<Album> albums = new ArrayList<Album>();

		String query = "SELECT * FROM album where idMusico in (Select idMusico from musico m join artista a on m.IdMusico = a.IdArtista "
				+ "where nombreArtistico = '" + nombreMusico + "')";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				Album nuevoAlbum = new Album(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getString(4), resultado.getString(5), resultado.getInt(6));
				albums.add(nuevoAlbum);
			}
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return albums;
	}

	public ArrayList<Cancion> obtenerCanciones(String nombreAlbum) {
		ArrayList<Cancion> canciones = new ArrayList<Cancion>();

		String query = "SELECT a.idAudio,a.nombreAudio,a.archivo,a.duracion,a.Nreproduciones,"
				+ "c.idAlbum ,c.artistasInvitados FROM audio a join cancion c on c.idCancion= a.IdAudio where idAlbum = (select idAlbum from album "
				+ " where titulo = '"+ nombreAlbum+"')";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				String tipo = "cancion";
				Time tiempo = resultado.getTime(4); // colonna 4 = duracion
				int duracionSegundos = tiempo.toLocalTime().toSecondOfDay();
				Cancion nuevaCancion = new Cancion(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						duracionSegundos, resultado.getInt(5), resultado.getInt(6), resultado.getString(7), tipo);
				canciones.add(nuevaCancion);
			}
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return canciones;
	}

	public ArrayList<Podcast> obtenerPodcasts(String nomPodcaster) {
		ArrayList<Podcast> podcasts = new ArrayList<Podcast>();

		String query = "SELECT a.idAudio, a.nombreAudio, a.archivo, a.duracion, a.Nreproduciones, p.Ncolaboradores, p.idPodcaster"
				+ " FROM audio a JOIN podcast p ON p.idPodcast = a.idAudio WHERE p.idPodcaster = ( Select idArtista from artista a join podcaster p on "
				+ "p.IdPodcaster = a.IdArtista where a.nombreArtistico = '" + nomPodcaster + "')";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				String tipo = "podcast";
				Time tiempo = resultado.getTime("duracion");
				int duracionSegundos = tiempo.toLocalTime().toSecondOfDay();

				Podcast nuevoPodcast = new Podcast(resultado.getInt("idAudio"), // id
						resultado.getString("nombreAudio"), // nombreAudio
						resultado.getString("archivo"), // archivo
						duracionSegundos, // duratasecondi
						resultado.getInt("Nreproduciones"), // NumRep
						resultado.getInt("idPodcaster"), // idPodcaster
						resultado.getInt("Ncolaboradores"), // numeroParticipantes
						tipo // tipo
				);

				podcasts.add(nuevoPodcast);
			}
			consulta.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return podcasts;
	}

	public ArrayList<Playlist> obtenerPlaylists(int idCliente) {
		ArrayList<Playlist> playlists = new ArrayList<Playlist>();

		String query = "SELECT * FROM playlist where idCliente = " + idCliente;
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				Playlist nuevaPlaylist = new Playlist(resultado.getInt(1), resultado.getString(2),
						resultado.getString(3), resultado.getInt(4));
				playlists.add(nuevaPlaylist);
			}
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return playlists;
	}

	public ArrayList<Cancion> obtenerCancionesPlaylist(int idPlaylist) {
		ArrayList<Cancion> cancionesPlaylist = new ArrayList<Cancion>();

		String query = "SELECT a.idAudio,a.nombreAudio,a.archivo,a.duracion,a.Nreproduciones,c.idAlbum ,"
				+ "c.artistasInvitados FROM `audio` a join cancion c on a.idAudio = c.idCancion "
				+ "join playlist_canciones p on p.idCancion = c.idCancion where p.idPlaylist = " + idPlaylist;
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				String tipo = "cancion";
				Time tiempo = resultado.getTime(4); // colonna 4 = duracion
				int duracionSegundos = tiempo.toLocalTime().toSecondOfDay();
				Cancion nuevaCancion = new Cancion(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						duracionSegundos, resultado.getInt(5), resultado.getInt(6), resultado.getString(7), tipo);

				cancionesPlaylist.add(nuevaCancion);
			}
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cancionesPlaylist;
	}

	public void insertarMusico(Musico m) {
		try {
			Statement stmt = conexion.createStatement();

			String queryArtista = "INSERT INTO artista (nombreArtistico, genero, imagen, descripcion) VALUES ('"
					+ m.getNombreArt() + "', '" + m.getGenero() + "', '" + m.getDescripcion() + "', '" + m.getFoto()
					+ "')";
			stmt.executeUpdate(queryArtista, Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = stmt.getGeneratedKeys();
			int idArtista = 0;
			if (rs.next()) {
				idArtista = rs.getInt(1);
			}

			String queryMusico = "INSERT INTO musico (idMusico, caracteristica) VALUES (" + idArtista + ", '"
					+ m.getComposicion() + "')";
			stmt.executeUpdate(queryMusico);

			stmt.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void insertarPodcaster(Podcaster p) {
		try {
			Statement stmt = conexion.createStatement();
			String queryArtista = "INSERT INTO artista (nombreArtistico, genero, descripcion, imagen) VALUES ('"
					+ p.getNombreArt() + "', '" + p.getGenero() + "', '" + p.getDescripcion() + "', '" + p.getFoto()
					+ "')";
			stmt.executeUpdate(queryArtista, Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = stmt.getGeneratedKeys();
			int idArtista = 0;
			if (rs.next()) {
				idArtista = rs.getInt(1);
			}
			String queryPodcaster = "INSERT INTO podcaster (idPodcaster) VALUES ('" + idArtista + "')";
			stmt.executeUpdate(queryPodcaster);

			stmt.close();
		} catch (SQLException e) {
			if (e instanceof SQLIntegrityConstraintViolationException) {
				System.out.println("Artista già esistente: " + p.getNombreArt());
			} else {
				e.printStackTrace();
			}
		}
	}

	public void insertarCancion(Cancion c) {
		try {
			Statement stmt = conexion.createStatement();

			int ore = c.getDuratasecondi() / 3600;
			int minuti = (c.getDuratasecondi() % 3600) / 60;
			int secondi = c.getDuratasecondi() % 60;
			String durataTime = String.format("%02d:%02d:%02d", ore, minuti, secondi);

			String queryAudio = "INSERT INTO audio (nombreAudio, archivo, duracion, Nreproduciones, tipo) VALUES ('"
					+ c.getNombreAudio() + "', '" + c.getArchivo() + "', '" + durataTime + "', 0, 'cancion')";
			stmt.executeUpdate(queryAudio, Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = stmt.getGeneratedKeys();
			int idAudio = 0;
			if (rs.next()) {
				idAudio = rs.getInt(1);
			}

			String queryCancion = "INSERT INTO cancion (idCancion, idAlbum, artistasInvitados) VALUES (" + idAudio
					+ ", " + c.getIdAlbum() + ", '" + c.getNombresColaboradores() + "')";
			stmt.executeUpdate(queryCancion);

			stmt.close();

		} catch (SQLException e) {
			if (e instanceof SQLIntegrityConstraintViolationException) {
				System.out.println("Audio già esistente: " + c.getNombreAudio());
			} else {
				e.printStackTrace();
			}
		}
	}

	public void insertarAlbum(Album a) {
		try {
			Statement stmt = conexion.createStatement();
			String query = "INSERT INTO album (titulo, anno, genero, imagen, idMusico) VALUES ('" + a.getTitulo()
					+ "', '" + a.getFechaPub() + "', '" + a.getGenero() + "', '" + a.getFoto() + "', " + a.getIdMusico()
					+ ")";
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			if (e instanceof SQLIntegrityConstraintViolationException) {
				System.out.println("Album già esistente: " + a.getTitulo());
			} else {
				e.printStackTrace();
			}
		}
	}

	public void insertarPodcast(Podcast p) {
		try {
			Statement stmt = conexion.createStatement();

			int ore = p.getDuratasecondi() / 3600;
			int minuti = (p.getDuratasecondi() % 3600) / 60;
			int secondi = p.getDuratasecondi() % 60;
			String durataTime = String.format("%02d:%02d:%02d", ore, minuti, secondi);

			String queryAudio = "INSERT INTO audio (nombreAudio, archivo, duracion, Nreproduciones, tipo) VALUES ('"
					+ p.getNombreAudio() + "', '" + p.getArchivo() + "', '" + durataTime + "', 0, 'podcast')";
			stmt.executeUpdate(queryAudio, Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = stmt.getGeneratedKeys();
			int idAudio = 0;
			if (rs.next()) {
				idAudio = rs.getInt(1);
			}

			String queryPodcast = "INSERT INTO podcast (idPodcast, Ncolaboradores, idPodcaster) VALUES (" + idAudio
					+ ", " + p.getNumeroParticipantes() + ", " + p.getIdPodcaster() + ")";
			stmt.executeUpdate(queryPodcast);

			stmt.close();

		} catch (SQLException e) {
			if (e instanceof SQLIntegrityConstraintViolationException) {
				System.out.println("Audio già esistente: " + p.getNombreAudio());
			} else {
				e.printStackTrace();
			}
		}
	}

	public void insertarPlaylist(String titulo, int idCliente) {
		try {
			Statement stmt = conexion.createStatement();
			String query = "INSERT INTO playlist ( titulo, fechaCreacion, idCliente) VALUES ('" + titulo
					+ "', CURRENT_DATE,'" + idCliente + "')";
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertarCancoinPlaylist(int idCancion, int idPlaylist) {
		try {
			Statement stmt = conexion.createStatement();
			String query = "INSERT INTO playlist_canciones (idCancion, idPlaylist, fechaPlaylist_cancion) VALUES ("
					+ idCancion + ", " + idPlaylist + ", CURRENT_DATE)";
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertarCliente(cliente c) {
		String abonamento = c.isEsPremium() ? "premium" : "free";

		try {
			Statement stmt = conexion.createStatement();

			String queryCliente = "INSERT INTO cliente (nombre,apellidos,idioma,usuario,contrasena,fechaNacimiento,fechaRegistro,tipo) VALUES ('"
					+ c.getNombre() + "', '" + c.getApellido() + "', '" + c.getIdioma() + "','" + c.getUsuario() + "','"
					+ c.getContrasena() + "','" + c.getFecNac() + "', CURRENT_DATE, '" + abonamento + "')";

			stmt.executeUpdate(queryCliente, Statement.RETURN_GENERATED_KEYS);

			if (c.isEsPremium()) {
				ResultSet rs = stmt.getGeneratedKeys();
				int idCliente = 0;
				if (rs.next()) {
					idCliente = rs.getInt(1);
				}
				String queryPremium = "INSERT INTO premium (idCliente) VALUES (" + idCliente + ")";
				stmt.executeUpdate(queryPremium);
			}

			stmt.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}
	}

	public ArrayList<StastisticaCancion> obtenerstatcanciones() {
		ArrayList<StastisticaCancion> statisticascanciones = new ArrayList<StastisticaCancion>();

		String query = "Select * from cancionesmasescuchadas ";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);
			while (resultado.next()) {
				StastisticaCancion sta = new StastisticaCancion(resultado.getInt(1), resultado.getString(2),
						resultado.getString(3), resultado.getInt(4));
				statisticascanciones.add(sta);
			}
			consulta.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return statisticascanciones;
	}

	public ArrayList<StatisticaAudio> obtenerstataudio() {
		ArrayList<StatisticaAudio> statisticasAudios = new ArrayList<StatisticaAudio>();

		String query = "Select * from audiosmasescuchados ";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);
			while (resultado.next()) {
				StatisticaAudio sta = new StatisticaAudio(resultado.getInt(1), resultado.getString(2),
						resultado.getInt(3));
				statisticasAudios.add(sta);
			}
			consulta.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return statisticasAudios;
	}

	public ArrayList<StatisticaPodcast> obtenerstatPodcast() {
		ArrayList<StatisticaPodcast> statisticasPodcast = new ArrayList<StatisticaPodcast>();

		String query = "Select * from podcastmasescuchado ";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);
			while (resultado.next()) {
				StatisticaPodcast sta = new StatisticaPodcast(resultado.getInt(1), resultado.getString(2),
						resultado.getString(3), resultado.getInt(4));
				statisticasPodcast.add(sta);
			}
			consulta.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return statisticasPodcast;
	}

	public ArrayList<StatisticaPlaylist> obtenerstatPlaylist() {
		ArrayList<StatisticaPlaylist> statisticasPlaylist = new ArrayList<StatisticaPlaylist>();

		String query = "Select * from playlistmasescuchada ";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);
			while (resultado.next()) {
				StatisticaPlaylist sta = new StatisticaPlaylist(resultado.getInt(1), resultado.getString(2),
						resultado.getInt(3));
				statisticasPlaylist.add(sta);
			}
			consulta.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return statisticasPlaylist;
	}
}