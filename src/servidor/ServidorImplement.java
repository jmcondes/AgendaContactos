package servidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ServidorImplement implements Servidor {

	ConexionBBDD conexion = null;

	public ServidorImplement() {
		conexion = new ConexionBBDD();
	}

	/**
	 * Al cambiar la tecnolog�a a RMI el m�todo de recibir el mensaje del cliente y el de enviar pueden ser el mismo
	 * ya que el mensaje de respuesta es el return. Es por ello que el cliente env�a directamente los mensajes a los
	 * m�todos que ejecutan el registro, login, alta, borrado y modificaci�n de contactos.
	 */
	@Override
	public void recibirMensaje(String mensajeRecibido) throws RemoteException{
		/*Se env�an los mensajes directamente a los otros m�todos para obtener el return de respuesta.
		Con Sockets si se deber�an utilizar estos dos m�todos para que fueran reutilizables por los m�todos
		encargados de tratar la informaci�n de los mensajes del cliente*/
	}

	/**
	 * Al enviar la respuesta en el return, se puede aprovechar para enviar al cliente directamente el Map, no es
	 * necesario transformarlo a JSON. No obstante este m�todo convertir�a el Map de entrada a JSONObject y devol-
	 * ver�a el String de ese JSON.
	 */
	@Override
	public String parsearAJSON(Map<String, String> datos) throws RemoteException{
		JSONObject jsonObject = new JSONObject(datos);
		return jsonObject.toString();
	}

	/**
	 * Por la misma raz�n que en el m�todo de parsearJSON se puede aprovechar que los m�todos del servidor reciben 
	 * directamente el Map del cliente y no hace falta que env�e un JSON para despu�s convertirlo a HashMap. No obstante, 
	 * el c�digo del m�todo convierte el String de entrada en caso de ser un JSON v�lido a un Map y lo devuelve.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> descodificarJSON(String mensajeDesdeCliente) throws RemoteException{
		Map<String, String> map = new HashMap<String, String>();
		try {
			String json = mensajeDesdeCliente;
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(json);
			map = (Map<String, String>) obj;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}
	
	/**
	 * Al cambiar la tecnolog�a a RMI el m�todo de recibir el mensaje del cliente y el de recibir deben ser el mismo
	 * ya que el mensaje de respuesta es el return. Es por ello que el cliente env�a directamente los mensajes a los
	 * m�todos que ejecutan el registro, login, alta y modificaci�n de contactos.
	 */
	@Override
	public boolean enviarInfo(Map<String, String> mensajeDireccionCliente) throws RemoteException{
		//Por la misma raz�n, la respuesta al cliente se env�a directamente en los otros m�todos
		return false;
	}
	/**
	 * Recibe el Map del cliente con los datos de registro, los extrae y los manda a la clase de la conexi�n con MySQL
	 * recibe un boolean con el resultado y en funci�n de �ste env�a el String del JSON de respuesta.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String altaUsuario(Map<String, String> nombrePasswordTlf) throws RemoteException{
		JSONObject jsonRespuesta = new JSONObject();		
		Map<String, String> map = nombrePasswordTlf;
		
		String nombre = map.get("nombreUsuario");
		String password = map.get("passwordUsuario");
		String telefono = map.get("telefonoUsuario");
		if(conexion.existeUsuario(nombre) == null){
			if(conexion.registrarUsuario(nombre, password, telefono)){
				jsonRespuesta.put("respuesta", "ok");
			}else{
				jsonRespuesta.put("respuesta", "no ok");
			}
		}else{
			jsonRespuesta.put("respuesta", "no ok");
		}
		return jsonRespuesta.toString();
	}
	/**
	 * Recibe un Map del cliente, extrae los datos del login y los env�a a la clase encargada de la gesti�n de la
	 * BBDD. Recibe la respuesta y manda el JSON al cliente en el return transform�ndolo antes a String. En un JSONArray
	 * a�adido al objeto JSON principal se mandad los contactos del usuario.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String loginUsuario(Map<String, String> usuario) throws RemoteException{
		JSONObject jsonRespuesta = new JSONObject();
		JSONArray contactos;
		Map<String, String> map = usuario;
		
		String nombre = map.get("nombreUsuario");
		String password = map.get("passwordUsuario");
		String idUsuario = conexion.comprobarLogin(nombre, password);
		if(idUsuario !=null){
			//Se llama al m�todo de la conexi�n que que mete los datos de todos los contactos en un JSONArray
			contactos = conexion.getContactos(idUsuario);
			jsonRespuesta.put("respuesta", "ok");
			jsonRespuesta.put("contactos", contactos);
		}else{
			jsonRespuesta.put("respuesta", "no ok");
		}
		return jsonRespuesta.toString();
	}
	/**
	 * Recibe el Map del cliente con el nombre del usuario y los datos de contacto. Los manda a la clase de la conexi�n y
	 * recibe la respuesta de esta confirmando que se hayan a�adido los datos. Se manda el JSON de respuesta transformado
	 * a String
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String altaContacto(Map<String, String> usuarioContacto) throws RemoteException{
		JSONObject jsonRespuesta = new JSONObject();
		
		if(conexion.guardarContacto(usuarioContacto)){
			jsonRespuesta.put("respuesta", "ok");
		}else{
			jsonRespuesta.put("respuesta", "no ok");
		}
		
		return jsonRespuesta.toString();
	}
	/**
	 * Recibe el Map del cliente, env�a los datos del contacto para ser modificados a ConexionBBDD y devuelve la respuesta
	 * en JSON.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String modificarContacto(Map<String, String> usuarioContacto) throws RemoteException{
		JSONObject jsonRespuesta = new JSONObject();
		
		if(conexion.modificarContacto(usuarioContacto)){
			jsonRespuesta.put("respuesta", "ok");
		}else{
			jsonRespuesta.put("respuesta", "no ok");
		}
		return jsonRespuesta.toString();
	}
	/**
	 * Igual que los m�todos anteriores, esta vez manda el nombre del usuario, y el nombre del contacto para borrar
	 * el contacto de la BBDD.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String eliminarContacto(Map<String, String> usuarioContacto) throws RemoteException{
		JSONObject jsonRespuesta = new JSONObject();
		
		if(conexion.borrarContacto(usuarioContacto)){
			jsonRespuesta.put("respuesta", "ok");
		}else{
			jsonRespuesta.put("respuesta", "no ok");
		}
		return jsonRespuesta.toString();
	}

	public static void main(String[] args) {

		Registry reg = null;
		try {
			System.out.println("Crea el registro de objetos, escuchando en el puerto 5555");
			reg = LocateRegistry.createRegistry(5555);
		} catch (Exception e) {
			System.out.println("ERROR: No se ha podido crear el registro");
			e.printStackTrace();
		}
		System.out.println("Creando el objeto servidor");
		ServidorImplement serverObject = new ServidorImplement();
		try {
			System.out.println("Inscribiendo el objeto servidor en el registro");
			System.out.println("Se le da un nombre �nico: Agenda");
			reg.rebind("Agenda", (Servidor) UnicastRemoteObject.exportObject(serverObject, 0));
		} catch (Exception e) {
			System.out.println("ERROR: No se ha podido inscribir el objeto servidor.");
			e.printStackTrace();
		}

	}

}
