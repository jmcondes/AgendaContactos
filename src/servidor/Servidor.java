package servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
/**
 * 
 * @author Jose Manuel Condes Moreno
 *
 */
public interface Servidor extends Remote{

	
	/**
	 * M�todo al que pasar la informaci�n recibida desde el cliente y desde el que ejecutar un m�todo u otro.
	 * Recibe el String recibido desde Internet, lo parsea con el m�todo descodificarJSON(String mensajeDesdeCliente)
	 * y, en funci�n de los datos recibidos, pasa la informaci�n al m�todo pertinente.
	 * Si llegan mensajes iguales para varias cosas (caso por ejemplo de alta nuevo contacto y modificaci�n de contacto)
	 * hay que verificar si el contacto ya existe o no para escoger el m�todo adecuado a ejecutar. 
	 * @param mensajeRecibido
	 */
	public void recibirMensaje(String mensajeRecibido) throws RemoteException;
	
	
	/**
	 * M�todo para parsear datos clave/valor a JSON, pero devuelve directamente el String a enviar por la red.
	 * @param datos
	 * @return
	 */
	public String parsearAJSON(Map<String,String> datos) throws RemoteException;
	
	/**
	 * M�todo para obtener los datos recibidos por la red desde el cliente en formato Map
	 * @param mensajeDesdeCliente
	 * @return
	 */
	public Map<String,String> descodificarJSON(String mensajeDesdeCliente) throws RemoteException;
	
	
	/**
	 * M�todo para enviar por la red al cliente destinatario los mensajes respuesta.
	 * @param mensaje
	 * @param direccionCliente
	 * @return
	 */
	public boolean enviarInfo(Map<String,String> mensajeDireccionCliente) throws RemoteException;
	
	
	/**
	 * M�todo para dar de alta un usuario. 
	 * Invocado desde m�todo recibirMensaje(String mensajeRecibido).
	 * Devuelve el String ya parseado de JSON (llamar al m�todo parsearAJSON(Map datos)) con el mensaje resultado de la operaci�n.
	 * @param nombrePasswordTlf
	 * @return
	 */
	public String altaUsuario(Map<String,String> nombrePasswordTlf) throws RemoteException;
	
	
	/**
	 * M�todo para verificar que los datos recibidos de un usuario son los correctos y, por tanto, puede iniciar sesi�n en la aplicaci�n.
	 * Invocado desde el m�todo recibirMensaje(String mensajeRecibido).
	 * Devuelve el String ya parseado de JSON (llamar al m�todo parsearAJSON(Map datos)) con el mensaje resultado de la operaci�n.
	 * En caso de que el login sea correcto, se env�an los datos de todos los contactos del usuario.
	 * @param usuarioContacto
	 * @return
	 */
	public String loginUsuario(Map<String,String> usuario) throws RemoteException;
	
	
	/**
	 * M�todo para dar de alta un nuevo contacto en la agenda de un usuario existente.
	 * Invocado desde el m�todo recibirMensaje(String mensajeRecibido).
	 * Devuelve el String ya parseado de JSON (llamar al m�todo parsearAJSON(Map datos)) con el mensaje resultado de la operaci�n.
	 * @param usuarioContacto
	 * @return
	 */
	public String altaContacto(Map<String,String> usuarioContacto) throws RemoteException;
	
	
	
	/**
	 * M�todo para modificar los datos de un contacto (nombre, direcci�n y tel�fono) ya existente en la agenda de un usuario.
	 * Invocado desde el m�todo recibirMensaje(String mensajeRecibido).
	 * Devuelve el String ya parseado de JSON (llamar al m�todo parsearAJSON(Map datos)) con el mensaje resultado de la operaci�n.
	 * @param usuarioContacto
	 * @return
	 */
	public String modificarContacto(Map<String,String> usuarioContacto) throws RemoteException;
	
	
	// eliminar contacto
	/**
	 * M�todo que elimina un contacto de la agenda de un usuario.
	 * Recibe �nicamente los nombres del usuario y del contacto a eliminar.
	 * Invocado desde el m�todo recibirMensaje(String mensajeRecibido).
	 * Devuelve el String ya parseado de JSON (llamar al m�todo parsearAJSON(Map datos)) con el mensaje resultado de la operaci�n.
	 * @param usuarioContacto
	 * @return
	 */
	public String eliminarContacto(Map<String,String> usuarioContacto) throws RemoteException;
}
