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

	public Album obtenerAlbumPorNombre(String nombreAlbum, int idMusico) {

		Album al = null;

		String query = "SELECT * FROM album WHERE titulo ='" + nombreAlbum + "' and idMusico = '" + idMusico + "'";

		try {

			Statement consulta = conexion.createStatement();

			ResultSet resultado = consulta.executeQuery(query);

			if (resultado.next()) {

				al = new Album(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getString(4), resultado.getString(5), resultado.getInt(6));
			}

			consulta.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return al;
	}

	public Podcaster obtenerPodcasterPorNombre(String NombrePodcaster) {
		Podcaster p = null;

		String query = "SELECT a.idArtista,a.nombreArtistico,a.genero,a.descripcion,a.imagen"
				+ " FROM artista a join podcaster p on a.idArtista = p.idPodcaster Where nombreArtistico ='"
				+ NombrePodcaster + "'";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				p = new Podcaster(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getString(4), resultado.getString(5));

			}
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return p;
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

	public Musico obtenerMusicoPorNombre(String NombreMusico) {
		Musico m = new Musico();

		String query = "SELECT a.idArtista,a.nombreArtistico,a.genero,a.descripcion,a.imagen,m.caracteristica"
				+ " FROM artista a join musico m on a.idArtista = m.idMusico Where nombreArtistico ='" + NombreMusico
				+ "'";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				m = new Musico(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getString(4), resultado.getString(5), resultado.getString(6));

			}
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return m;
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

	public ArrayList<Cancion> obtenerTodasCanciones() {
		ArrayList<Cancion> canciones = new ArrayList<Cancion>();

		String query = "SELECT a.idAudio,a.nombreAudio,a.archivo,a.duracion,a.Nreproduciones,"
				+ "c.idAlbum ,c.artistasInvitados FROM audio a join cancion c on c.idCancion= a.IdAudio";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				String tipo = "cancion";

				Cancion nuevaCancion = new Cancion(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getTime(4), resultado.getInt(5), resultado.getInt(6), resultado.getString(7), tipo);
				canciones.add(nuevaCancion);
			}
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return canciones;
	}

	public ArrayList<Podcast> obtenerTodosPodcasts() {
		ArrayList<Podcast> podcasts = new ArrayList<Podcast>();

		String query = "SELECT a.idAudio, a.nombreAudio, a.archivo, a.duracion, a.Nreproduciones, p.Ncolaboradores, p.idPodcaster"
				+ " FROM audio a JOIN podcast p ON p.idPodcast = a.idAudio";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				String tipo = "podcast";

				Podcast nuevoPodcast = new Podcast(resultado.getInt("idAudio"), resultado.getString("nombreAudio"),
						resultado.getString("archivo"), resultado.getTime(4), resultado.getInt("Nreproduciones"),
						resultado.getInt("idPodcaster"), resultado.getInt("Ncolaboradores"), tipo);

				podcasts.add(nuevoPodcast);
			}
			consulta.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return podcasts;
	}

	public ArrayList<Album> obtenerTodosAlbum() {
		ArrayList<Album> albums = new ArrayList<Album>();

		String query = "SELECT * FROM album";
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
				+ " where titulo = '" + nombreAlbum + "')";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				String tipo = "cancion";

				Cancion nuevaCancion = new Cancion(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getTime(4), resultado.getInt(5), resultado.getInt(6), resultado.getString(7), tipo);
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

				Podcast nuevoPodcast = new Podcast(resultado.getInt("idAudio"), resultado.getString("nombreAudio"),
						resultado.getString("archivo"), resultado.getTime(4), resultado.getInt("Nreproduciones"),
						resultado.getInt("idPodcaster"), resultado.getInt("Ncolaboradores"), tipo);

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

	public ArrayList<Cancion> obtenerCancionesPlaylist(String nombrePlaylist, int idCliente) {
		ArrayList<Cancion> cancionesPlaylist = new ArrayList<Cancion>();

		String query = "SELECT a.idAudio,a.nombreAudio,a.archivo,a.duracion,a.Nreproduciones,c.idAlbum ,"
				+ "c.artistasInvitados FROM `audio` a join cancion c on a.idAudio = c.idCancion "
				+ "join playlist_canciones pc on pc.idCancion = c.idCancion join playlist p on pc.IdPlaylist = p.IDlist"
				+ " where titulo ='" + nombrePlaylist + "' and IdCliente ='" + idCliente + "'";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				String tipo = "cancion";

				Cancion nuevaCancion = new Cancion(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getTime(4), resultado.getInt(5), resultado.getInt(6), resultado.getString(7), tipo);

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

			String query = "CALL AnadirMusico('" + m.getNombreArt() + "','" + m.getGenero() + "','" + m.getFoto()
					+ "','" + m.getDescripcion() + "','" + m.getComposicion() + "')";

			ResultSet rs = stmt.executeQuery(query);

			// messaggio della procedure
			if (rs.next()) {

				System.out.println(rs.getString("mensaje"));
			}

			stmt.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void insertarPodcaster(Podcaster p) {

		try {

			Statement stmt = conexion.createStatement();

			String query = "CALL AnadirPodcaster('" + p.getNombreArt() + "','" + p.getGenero() + "','" + p.getFoto()
					+ "','" + p.getDescripcion() + "')";

			stmt.execute(query);

			stmt.close();


		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void insertarCancion(Cancion c) {

		try {

			Statement st = conexion.createStatement();

			String sql = "CALL AnadirCancion('" + c.getNombreAudio() + "','" + c.getDurata() + "','" + c.getArchivo()
					+ "'," + c.getIdAlbum() + ",'" + c.getNombresColaboradores() + "')";

			st.execute(sql);

			st.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
	}

	public void insertarAlbum(Album a) {

		try {

			Statement stmt = conexion.createStatement();

			String query = "CALL anadirAlbum('" + a.getTitulo() + "','" + a.getFechaPub() + "','" + a.getGenero()
					+ "','" + a.getFoto() + "'," + a.getIdMusico() + ")";

			ResultSet rs = stmt.executeQuery(query);

			// messaggio della procedure
			if (rs.next()) {

				System.out.println(rs.getString("mensaje"));
			}

			stmt.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void insertarPodcast(Podcast p) {

		try {

			Statement st = conexion.createStatement();

			String sql = "CALL AnadirPodcast('" + p.getNombreAudio() + "','" + p.getDurata() + "','" + p.getArchivo()
					+ "'," + p.getIdPodcaster() + "," + p.getNumeroParticipantes() + ")";

			st.execute(sql);

			st.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

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

	public boolean insertarCliente(cliente c) {
		String abonamento = c.isEsPremium() ? "premium" : "free";
		try {
			Statement stmt = conexion.createStatement();
			String query = "CALL AnadirCliente('" + c.getNombre() + "','" + c.getApellido() + "','" + c.getIdioma()
					+ "','" + c.getUsuario() + "','" + c.getContrasena() + "','" + c.getFecNac() + "',"
					+ "CURRENT_DATE," + "'" + abonamento + "')";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				String mensaje = rs.getString("mensaje");
				System.out.println(mensaje);
				if (mensaje.contains("sucesso")) {
					if (c.isEsPremium()) {
						ResultSet rid = stmt
								.executeQuery("SELECT idCliente FROM cliente WHERE usuario='" + c.getUsuario() + "'");
						int idCliente = 0;
						if (rid.next()) {
							idCliente = rid.getInt("idCliente");
						}
						String queryPremium = "INSERT INTO premium(idCliente) VALUES(" + idCliente + ")";
						stmt.executeUpdate(queryPremium);
					}
					stmt.close();
					return true;
				}
			}
			stmt.close();

			return false;

		} catch (

		SQLException e) {

			e.printStackTrace();

			return false;
		}
	}

	public ArrayList<StastisticaCancion> obtenerCancionesFavoritas() {
		ArrayList<StastisticaCancion> lista = new ArrayList<>();
		try {
			Statement stmt = conexion.createStatement();
			String query = "SELECT * FROM canciones_favoritas";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				StastisticaCancion cf = new StastisticaCancion(rs.getString(1), rs.getString(2), rs.getInt(3));
				lista.add(cf);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public ArrayList<StatisticaAudio> obtenerstataudio() {
		ArrayList<StatisticaAudio> statisticasAudios = new ArrayList<StatisticaAudio>();

		String query = "Select * from audiosmasescuchados ";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);
			while (resultado.next()) {
				StatisticaAudio sta = new StatisticaAudio(resultado.getString(1), resultado.getString(2),
						resultado.getInt(3));
				statisticasAudios.add(sta);
			}
			consulta.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return statisticasAudios;
	}

	public ArrayList<StatisticaPodcast> obtenerPodcastsFavoritos() {
		ArrayList<StatisticaPodcast> lista = new ArrayList<>();
		try {
			Statement stmt = conexion.createStatement();
			String query = "SELECT * FROM podcasts_favoritos";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				StatisticaPodcast pf = new StatisticaPodcast(rs.getString(1), rs.getString(2), rs.getInt(3));
				lista.add(pf);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public ArrayList<StatisticaPlaylist> obtenerstatPlaylist() {
		ArrayList<StatisticaPlaylist> statisticasPlaylist = new ArrayList<StatisticaPlaylist>();

		String query = "Select * from playlist_favoritas ";

		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);
			while (resultado.next()) {
				StatisticaPlaylist sta = new StatisticaPlaylist(resultado.getString(1), resultado.getString(2),
						resultado.getInt(3));
				statisticasPlaylist.add(sta);
			}
			consulta.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return statisticasPlaylist;
	}

	// Aggiornare un cliente
	public void actualizarCliente(cliente c) {
		try {
			Statement stmt = conexion.createStatement();
			String query = "UPDATE cliente SET nombre = '" + c.getNombre() + "', apellidos = '" + c.getApellido()
					+ "', idioma = '" + c.getIdioma() + "', usuario = '" + c.getUsuario() + "', contrasena = '"
					+ c.getContrasena() + "', fechaNacimiento = '" + c.getFecNac() + "', tipo = '"
					+ (c.isEsPremium() ? "premium" : "free") + "' WHERE idCliente = " + c.getId();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				System.out.println("Error, ye existe un podcaster con este nombre");

			} else {
				e.printStackTrace();
			}
		}
	}

	// Aggiornare un musico
	public void actualizarMusico(Musico m) {
		try {
			Statement stmt = conexion.createStatement();
			String queryArtista = "UPDATE artista SET nombreArtistico = '" + m.getNombreArt() + "', genero = '"
					+ m.getGenero() + "', descripcion = '" + m.getDescripcion() + "', imagen = '" + m.getFoto()
					+ "' WHERE idArtista = " + m.getId();
			stmt.executeUpdate(queryArtista);

			// update musico
			String queryMusico = "Update musico set  caracteristica = '" + m.getComposicion() + "' where idMusico = "
					+ m.getId();
			stmt.executeUpdate(queryMusico);

			stmt.close();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				System.out.println("Error, ye existe un artista con este nombre");

			} else {
				e.printStackTrace();
			}
		}
	}

	// Aggiornare un podcaster
	public void actualizarPodcaster(Podcaster p) {
		try {
			Statement stmt = conexion.createStatement();
			String queryArtista = "UPDATE artista SET nombreArtistico = '" + p.getNombreArt() + "', genero = '"
					+ p.getGenero() + "', descripcion = '" + p.getDescripcion() + "', imagen = '" + p.getFoto()
					+ "' WHERE idArtista = " + p.getId();
			stmt.executeUpdate(queryArtista);

			stmt.close();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				System.out.println("Error, ye existe un artista con este nombre");

			} else {
				e.printStackTrace();
			}
		}
	}

	// Aggiornare una canzone
	public void actualizarCancion(Cancion c) {
		try {
			Statement stmt = conexion.createStatement();

			// Update en audio
			String queryAudio = "UPDATE audio SET nombreAudio = '" + c.getNombreAudio() + "', archivo = '"
					+ c.getArchivo() + "', duracion = '" + c.getDurata() + "', Nreproduciones = " + c.getNumRep()
					+ " WHERE idAudio = " + c.getId();
			stmt.executeUpdate(queryAudio);

			// Update en cancion
			String queryCancion = "UPDATE cancion SET idAlbum = " + c.getIdAlbum() + ", artistasInvitados = '"
					+ c.getNombresColaboradores() + "' WHERE idCancion = " + c.getId();
			stmt.executeUpdate(queryCancion);

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Aggiornare un album
	public void actualizarAlbum(Album a) {
		try {
			Statement stmt = conexion.createStatement();
			String query = "UPDATE album SET titulo = '" + a.getTitulo() + "', anno = '" + a.getFechaPub()
					+ "', genero = '" + a.getGenero() + "', imagen = '" + a.getFoto() + "', idMusico = "
					+ a.getIdMusico() + " WHERE idAlbum = " + a.getId();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Aggiornare un podcast
	public void actualizarPodcast(Podcast p) {
		try {
			Statement stmt = conexion.createStatement();

			// Update en audio
			String queryAudio = "UPDATE audio SET nombreAudio = '" + p.getNombreAudio() + "', archivo = '"
					+ p.getArchivo() + "', duracion = '" + p.getDurata() + "', Nreproduciones = " + p.getNumRep()
					+ " WHERE idAudio = " + p.getId();
			stmt.executeUpdate(queryAudio);

			// Update en podcast
			String queryPodcast = "UPDATE podcast SET Ncolaboradores = " + p.getNumeroParticipantes()
					+ ", idPodcaster = " + p.getIdPodcaster() + " WHERE idPodcast = " + p.getId();
			stmt.executeUpdate(queryPodcast);

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Aggiornare una playlist (solo título)
	public void actualizarPlaylist(int idPlaylist, String nuevoTitulo) {
		try {
			Statement stmt = conexion.createStatement();
			String query = "UPDATE playlist SET titulo = '" + nuevoTitulo + "' WHERE idPlaylist = " + idPlaylist;
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Incrementar reproducciones de un audio
	public void incrementarReproducciones(String nombreAudio) {
		try {
			Statement stmt = conexion.createStatement();
			String query = "UPDATE audio SET Nreproduciones = Nreproduciones + 1 WHERE nombreAudio = " + nombreAudio;
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Añadir una canción a una playlist
	public void añadirCancionAPlaylist(String nombreCancion, String tituloPlaylist, String usuarioCliente) {

		try {
			Statement stmt = conexion.createStatement();
			String query = "INSERT INTO playlist_canciones (idPlaylist, idCancion) " + "SELECT p.IDlist, c.idCancion "
					+ "FROM playlist p " + "JOIN cliente cl ON p.IdCliente = cl.idCliente "
					+ "JOIN cancion c ON c.idCancion = (SELECT a.idAudio FROM audio a WHERE a.nombreAudio = '"
					+ nombreCancion + "') " + "WHERE p.titulo = '" + tituloPlaylist + "' " + "AND cl.usuario = '"
					+ usuarioCliente + "'";
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Eliminar un cliente - per username
	public void eliminarCliente(String usuario) {
		try {
			Statement stmt = conexion.createStatement();
			String query = "DELETE FROM cliente WHERE usuario = '" + usuario + "'";
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1392) {
				System.out.println("Error, no hay clienes con este user");

			} else {
				e.printStackTrace();
			}
		}
	}

	// Eliminar un artista
	public void eliminarArtista(int idArtista) {

		try {

			Statement stmt = conexion.createStatement();

			String query = "CALL eliminarArtista('" + idArtista + "')";

			ResultSet rs = stmt.executeQuery(query);

			// messaggio della procedure
			if (rs.next()) {

				System.out.println(rs.getString("mensaje"));
			}

			stmt.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// Eliminar un audio
	public void eliminarAudio(int idAudio) {

		try {

			Statement stmt = conexion.createStatement();

			String query = "CALL eliminarAudio('" + idAudio + "')";

			ResultSet rs = stmt.executeQuery(query);

			// messaggio della procedure
			if (rs.next()) {

				System.out.println(rs.getString("mensaje"));
			}

			stmt.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// Eliminar un album
	public void eliminarAlbum(int idAlbum) {

		try {

			Statement stmt = conexion.createStatement();

			String query = "CALL eliminarAlbum('" + idAlbum + "')";

			ResultSet rs = stmt.executeQuery(query);

			// messaggio della procedure
			if (rs.next()) {

				System.out.println(rs.getString("mensaje"));
			}

			stmt.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// Eliminar una playlist - per titolo e username cliente
	public void eliminarPlaylist(String tituloPlaylist, String usuarioCliente) {
		try {
			Statement stmt = conexion.createStatement();
			String query = "DELETE p FROM playlist p JOIN cliente c ON p.IdCliente = c.idCliente "
					+ "WHERE p.titulo = '" + tituloPlaylist + "' AND c.usuario = '" + usuarioCliente + "'";
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Eliminar una canción de una playlist
	public void eliminarCancionDePlaylist(int idPlaylist, int idCancion) {
		try {
			Statement stmt = conexion.createStatement();
			String query = "DELETE  FROM playlist_canciones where IdPlaylist ='" + idPlaylist + "' and idCancion = '"
					+ idCancion + "'";
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int obtenerReproduccionesTotalesMusico(String nombreMusico) {
		int total = 0;
		try {
			Statement stmt = conexion.createStatement();
			String query = "SELECT RepTotMusico('" + nombreMusico + "')";
			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				total = rs.getInt(1);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

	public int obtenerReproduccionesTotalesAlbum(String nombreAlbum, String nombreMusico) {
		int total = 0;
		try {
			Statement stmt = conexion.createStatement();
			String query = "SELECT RepTotAlbum('" + nombreMusico + "', '" + nombreAlbum + "')";
			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				total = rs.getInt(1);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

	public int obtenerReproduccionesTotalPodcaster(String nombrePodcaster) {
		int total = 0;
		try {
			Statement stmt = conexion.createStatement();
			String query = "SELECT RepTotPodcaster('" + nombrePodcaster + "')";
			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				total = rs.getInt(1);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

	public ArrayList<String> obteneridiomas() {
		ArrayList<String> idiomas = new ArrayList<String>();
		String query = "Select idIdioma from idioma";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);
			while (resultado.next()) {
				String idioma = resultado.getString(1);
				idiomas.add(idioma);

			}
			consulta.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return idiomas;
	}

	public void anadirAGustos(int idCliente, int idAudio) {
		String consulta = "INSERT INTO gustos (idCliente, idAudio) VALUES (" + idCliente + ", " + idAudio + ")";
		try {
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(consulta);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<StastisticaCancion> StatCancion() {

		ArrayList<StastisticaCancion> StatisticaCanciones = new ArrayList<StastisticaCancion>();
		String query = "SELECT * from canciones_favoritas";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				StastisticaCancion NueaStat = new StastisticaCancion(resultado.getString(1), resultado.getString(2),
						resultado.getInt(3));
				StatisticaCanciones.add(NueaStat);
			}
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return StatisticaCanciones;
	}

	public ArrayList<StatisticaAudio> StatAudio() {

		ArrayList<StatisticaAudio> StatisticaAudios = new ArrayList<StatisticaAudio>();
		String query = "SELECT * from audiosmasescuchados";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				StatisticaAudio NueaStat = new StatisticaAudio(resultado.getString(1), resultado.getString(2),
						resultado.getInt(3));
				StatisticaAudios.add(NueaStat);
			}
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return StatisticaAudios;
	}

	public ArrayList<StatisticaPlaylist> StatPlaylist() {

		ArrayList<StatisticaPlaylist> StatisticaPlaylist = new ArrayList<StatisticaPlaylist>();
		String query = "SELECT * from playlist_favoritas";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				StatisticaPlaylist NueaStat = new StatisticaPlaylist(resultado.getString(1), resultado.getString(2),
						resultado.getInt(3));
				StatisticaPlaylist.add(NueaStat);
			}
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return StatisticaPlaylist;
	}

	public ArrayList<StatisticaPodcast> StatPodcast() {

		ArrayList<StatisticaPodcast> StatisticaPodcast = new ArrayList<StatisticaPodcast>();
		String query = "SELECT * from podcasts_favoritos";
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);

			while (resultado.next()) {
				StatisticaPodcast NueaStat = new StatisticaPodcast(resultado.getString(1), resultado.getString(2),
						resultado.getInt(3));
				StatisticaPodcast.add(NueaStat);
			}
			consulta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return StatisticaPodcast;
	}

	public boolean obtenerAlbumPorId(int idAlbum) {
		String query = "SELECT idAlbum FROM album WHERE idAlbum=" + idAlbum;
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);
			boolean existe = resultado.next();
			consulta.close();
			return existe;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean obtenerPodcasterPorId(int idPodcaster) {
		String query = "SELECT idPodcaster FROM porcaster WHERE idPodcaster=" + idPodcaster;
		try {
			Statement consulta = conexion.createStatement();
			ResultSet resultado = consulta.executeQuery(query);
			boolean existe = resultado.next();
			consulta.close();
			return existe;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Musico obtenerMusicoPorId(int id) {

		String query = "SELECT * FROM artista a " + "JOIN musico m ON a.idArtista = m.idMusico " + "WHERE m.idMusico = "
				+ id;

		try {

			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery(query);

			if (rs.next()) {

				Musico m = new Musico(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6));

				st.close();

				return m;
			}

			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}