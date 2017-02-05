package servidor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 * Clase que gestiona la conexión a MySQL además de todos los métodos necesarios
 * para leer de la BBDD y para actualizar las tablas.
 * @author Jose Manuel Condes Moreno
 *
 */
public class ConexionBBDD {
	
	private final String rutaFile = "Config.ini";
	private String userMySQL;
	private String passwordMySQL;
	private String bd;
	private String url;
	private Connection conexion;
	private int contador = 3;
	
	public ConexionBBDD() {
		crearConexion();
	}

	/**
	 * Crea la conexión, al tercer intento fallido de conectar cierra el programa.
	 */
	public void crearConexion(){
		boolean conexionCreada = false;
		do{		
			//Se leen los datos de la conexión del archivo Config.ini adjuntado en el proyecto.
			leerConfig();
			try {			
				Class.forName("com.mysql.jdbc.Driver");
				conexion = DriverManager.getConnection((url + bd), userMySQL, passwordMySQL);
				System.out.println("     - Conexión con MySQL establecida -\n");
				conexionCreada = true;				
			} catch (Exception e) {
				if(contador == 0){
					System.out.println("El sistema ha detectado un intento de intrusión y se cerrará.");
					System.exit(-1);				
				}
				System.out.println(" – Error de Conexión con MySQL - \n");
				System.out.println(" – Tiene " + this.contador + " intentos para introducir los datos. - \n");
				escribirConfig();
				leerConfig();
			}
		}while(!conexionCreada);
	}
	/**
	 * Lee el fichero de configuración con getProperty()
	 */
	public void leerConfig(){
		Properties propiedades = new Properties();
		InputStream entrada = null;
		try{
			File miFichero = new File(rutaFile);
			if(miFichero.exists()){
				entrada = new FileInputStream(miFichero);
				propiedades.load(entrada);
				this.url = propiedades.getProperty("BBDD_URL");
				this.userMySQL = propiedades.getProperty("BBDD_USER");
				this.passwordMySQL = propiedades.getProperty("BBDD_PASSWORD");
				this.bd = propiedades.getProperty("BBDD_BD");
			}
			else{
				System.out.println(" - No se ha encontrado el fichero ini, "
						+ "introduzca los parámetros para crearlo.");
				escribirConfig();
				leerConfig();
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	/**
	 * Escribe el fichero de configuración si no exixtiera, o bien si los parámetros de la conexión
	 * son erróneos.
	 */
	public void escribirConfig(){
		Properties propiedades = new Properties();
		OutputStream salida = null;
		PrintWriter pw = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try{
			System.out.println(" - Introduzca el nombre de usuario: ");
			String usuario = br.readLine();
			System.out.println(" - Introduzca el password: ");
			String password = br.readLine();
			System.out.println(" - Introduzca el nombre de la BBDD: ");
			String bbdd = br.readLine();
			File miFichero = new File(rutaFile);
			pw = new PrintWriter(miFichero);
			pw.println("###########################");
			if(miFichero.exists()){
				salida = new FileOutputStream(miFichero);
				propiedades.setProperty("BBDD_URL","jdbc:mysql://localhost/");
				propiedades.setProperty("BBDD_USER",usuario);
				propiedades.setProperty("BBDD_PASSWORD",password);
				propiedades.setProperty("BBDD_BD",bbdd);
				propiedades.store(salida,"##### DATABASE CONFIG #####");
				this.contador--;
			}
			else{
				System.out.println("Fichero no encontrado");
			}						
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	/**
	 * Manda los datos del usuario para que sean guardados en la BBDD
	 * @param nombre
	 * @param password
	 * @param telefono
	 * @return
	 */
	public boolean registrarUsuario(String nombre, String password, String telefono){
		System.out.println(nombre + ", " + password + ", " + telefono);
		boolean registrado = false;
		String query = "";
		PreparedStatement pstmt = null;
		int regActualizados = 0;
		try{
			query = "INSERT INTO agenda.usuarios (nombre_usuario, password_usuario, telefono_usuario) VALUES(?, ?, ?)";
			pstmt = conexion.prepareStatement(query);
			pstmt.setString(1, nombre);
			pstmt.setString(2, password);
			pstmt.setString(3, telefono);
			regActualizados= pstmt.executeUpdate();
			if (regActualizados > 0) {
				registrado = true;
			}
			pstmt.close();
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return registrado;
	}

	/**
	 * Método que comprueba si existe un usuario con ese nombre. La columna nombre en la BBDD
	 * es de tipo Unique. Este método se reutiliza para obtener la id del usuario en la tabla.
	 * @param usuario
	 * @param password
	 * @return
	 */
	public String existeUsuario(String usuario){
		String idUsuario = null;
		String query = "";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			query = "SELECT id_usuario FROM agenda.usuarios WHERE nombre_usuario = ?";
			pstmt = conexion.prepareStatement(query);
			pstmt.setString(1, usuario);
			rset = pstmt.executeQuery();
			if(rset.next()){
				idUsuario = rset.getString("id_usuario");
			}
			rset.close();
			pstmt.close();
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return idUsuario;
	}
	/**
	 * Comprueba que existe el usuario en la BBDD
	 * @param usuario
	 * @param password
	 * @return
	 */
	public String comprobarLogin(String usuario, String password){
		String idUsuario = null;
		String query = "";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			query = "SELECT id_usuario FROM agenda.usuarios WHERE nombre_usuario = ? AND password_usuario = ?";
			pstmt = conexion.prepareStatement(query);
			pstmt.setString(1, usuario);
			pstmt.setString(2, password);
			rset = pstmt.executeQuery();
			if(rset.next()){
				idUsuario = rset.getString("id_usuario");
			}
			rset.close();
			pstmt.close();
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return idUsuario;
	}
	/**
	 * Método que coge los datos de todos los contactos de un usuario y los almacena en un JSONArray. Devuelve
	 * ese JSONArray.
	 * @param idUsuario
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getContactos(String idUsuario){
		System.out.println(idUsuario);
		JSONArray contactos = new JSONArray();
		String query = "";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			query = "SELECT * FROM agenda.contactos WHERE id_usuario = ?";
			pstmt = conexion.prepareStatement(query);
			pstmt.setString(1, idUsuario);
			rset = pstmt.executeQuery();
			while(rset.next()){
				JSONObject temp = new JSONObject();
				temp.put("nombreContacto", rset.getString("nombre_contacto"));
				temp.put("direccionContacto", rset.getString("direccion_contacto"));
				temp.put("telefonoContacto", rset.getString("telefono_contacto"));
				contactos.add(temp);
			}
			rset.close();
			pstmt.close();
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return contactos;
	}
	/**
	 * Guarda un contacto en la BBDD, llama al método existeUsuario() para obtener la id
	 * @param usuarioContacto
	 * @return
	 */
	public boolean guardarContacto(Map<String, String> usuarioContacto){
		boolean guardado = false;
		Map<String, String> map = usuarioContacto;
		String nombreUsuario = map.get("nombreUsuario");
		String nombreContacto = map.get("nombreContacto");
		String direccionContacto = map.get("direccionContacto");
		String telefonoContacto = map.get("telefonoContacto");
		String idUsuario = existeUsuario(nombreUsuario);
		
		if(idUsuario != null){
			if(!comprobarContacto(idUsuario, nombreContacto)){
				String query = "";
				PreparedStatement pstmt = null;
				int regActualizados = 0;
				try{
					query = "INSERT INTO agenda.contactos (id_usuario, nombre_contacto, direccion_contacto, telefono_contacto) VALUES(?, ?, ?, ?)";
					pstmt = conexion.prepareStatement(query);
					pstmt.setString(1, idUsuario);
					pstmt.setString(2, nombreContacto);
					pstmt.setString(3, direccionContacto);
					pstmt.setString(4, telefonoContacto);				
					regActualizados= pstmt.executeUpdate();
					if (regActualizados > 0) {
						guardado = true;
					}
					pstmt.close();
				} catch (SQLException s) {
					s.printStackTrace();
				}
			}
		}		
		return guardado;
	}
	/**
	 * Modifica un contacto de la agenda del usuario
	 * @param usuarioContacto
	 * @return
	 */
	public boolean modificarContacto(Map<String, String> usuarioContacto){
		boolean modificado = false;
		Map<String, String> map = usuarioContacto;
		String nombreUsuario = map.get("nombreUsuario");
		String nombreContactoOriginal = map.get("nombreContactoOriginal");
		String nombreContacto = map.get("nombreContacto");
		String direccionContacto = map.get("direccionContacto");
		String telefonoContacto = map.get("telefonoContacto");
		String idUsuario = existeUsuario(nombreUsuario);
		
		if(idUsuario != null){
			//En caso de no variar el nombre se actualizan los datos de telefono y direccion
			if(nombreContactoOriginal.equals(nombreContacto)){		 
				 String query = "";
					PreparedStatement pstmt = null;
					int regActualizados = 0;
					try{
						query = "UPDATE agenda.contactos SET direccion_contacto = ?, telefono_contacto = ? "
								+ "WHERE id_Usuario = ? AND nombre_contacto = ?";
						pstmt = conexion.prepareStatement(query);
						pstmt.setString(1, direccionContacto);
						pstmt.setString(2, telefonoContacto);
						pstmt.setString(3, idUsuario);
						pstmt.setString(4, nombreContacto);				
						regActualizados= pstmt.executeUpdate();
						if (regActualizados > 0) {
							modificado = true;
						}
						pstmt.close();
					} catch (SQLException s) {
						s.printStackTrace();
					}
			}else{
				//Si cambia el nombre, comprueba que éste no existe ya en la agenda.
				if(!comprobarContacto(idUsuario, nombreContacto)){
					String query = "";
					PreparedStatement pstmt = null;
					int regActualizados = 0;
					try{
						query = "UPDATE agenda.contactos SET nombre_contacto = ?, direccion_contacto = ?, telefono_contacto = ? "
								+ "WHERE id_Usuario = ? AND nombre_contacto = ?";
						pstmt = conexion.prepareStatement(query);
						pstmt.setString(1, nombreContacto);
						pstmt.setString(2, direccionContacto);
						pstmt.setString(3, telefonoContacto);
						pstmt.setString(4, idUsuario);
						pstmt.setString(5, nombreContactoOriginal);				
						regActualizados= pstmt.executeUpdate();
						if (regActualizados > 0) {
							modificado = true;
						}
						pstmt.close();
					} catch (SQLException s) {
						s.printStackTrace();
					}
				}
			}			
		}		
		return modificado;
	}
	/**
	 * Borra un contacto de la agenda de un usuario
	 * @param usuarioContacto
	 * @return
	 */
	public boolean borrarContacto(Map<String, String> usuarioContacto){
		boolean borrado = false;
		Map<String, String> map = usuarioContacto;
		String nombreUsuario = map.get("nombreUsuario");
		String nombreContacto = map.get("nombreContacto");
		String idUsuario = existeUsuario(nombreUsuario);
		
		if(comprobarContacto(idUsuario, nombreContacto)){
			String query = "";
			PreparedStatement pstmt = null;
			int regActualizados = 0;
			try{
				query = "DELETE FROM agenda.contactos WHERE id_Usuario = ? AND nombre_contacto = ?";
				pstmt = conexion.prepareStatement(query);
				pstmt.setString(1, idUsuario);
				pstmt.setString(2, nombreContacto);			
				regActualizados= pstmt.executeUpdate();
				if (regActualizados > 0) {
					borrado = true;
				}
				pstmt.close();
			} catch (SQLException s) {
				s.printStackTrace();
			}
		}
		return borrado;
	}
	/**
	 * Comprueba si ya existe el nombre de un contacto en la agenda de un usuario en concreto
	 * @param idUsuario
	 * @param nombreContacto
	 * @return
	 */
	public boolean comprobarContacto(String idUsuario, String nombreContacto){
		boolean existeContacto = false;
		String query = "";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			query = "SELECT nombre_contacto FROM agenda.contactos WHERE id_usuario = ? AND nombre_contacto = ?";
			pstmt = conexion.prepareStatement(query);
			pstmt.setString(1, idUsuario);
			pstmt.setString(2, nombreContacto);
			rset = pstmt.executeQuery();
			if(rset.next()){
				existeContacto = true;
			}
			rset.close();
			pstmt.close();
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return existeContacto;
	}
}