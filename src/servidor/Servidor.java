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
	 * Método al que pasar la información recibida desde el cliente y desde el que ejecutar un método u otro.
	 * Recibe el String recibido desde Internet, lo parsea con el método descodificarJSON(String mensajeDesdeCliente)
	 * y, en función de los datos recibidos, pasa la información al método pertinente.
	 * Si llegan mensajes iguales para varias cosas (caso por ejemplo de alta nuevo contacto y modificación de contacto)
	 * hay que verificar si el contacto ya existe o no para escoger el método adecuado a ejecutar. 
	 * @param mensajeRecibido
	 */
	public void recibirMensaje(String mensajeRecibido) throws RemoteException;
	
	
	/**
	 * Método para parsear datos clave/valor a JSON, pero devuelve directamente el String a enviar por la red.
	 * @param datos
	 * @return
	 */
	public String parsearAJSON(Map<String,String> datos) throws RemoteException;
	
	/**
	 * Método para obtener los datos recibidos por la red desde el cliente en formato Map
	 * @param mensajeDesdeCliente
	 * @return
	 */
	public Map<String,String> descodificarJSON(String mensajeDesdeCliente) throws RemoteException;
	
	
	/**
	 * Método para enviar por la red al cliente destinatario los mensajes respuesta.
	 * @param mensaje
	 * @param direccionCliente
	 * @return
	 */
	public boolean enviarInfo(Map<String,String> mensajeDireccionCliente) throws RemoteException;
	
	
	/**
	 * Método para dar de alta un usuario. 
	 * Invocado desde método recibirMensaje(String mensajeRecibido).
	 * Devuelve el String ya parseado de JSON (llamar al método parsearAJSON(Map datos)) con el mensaje resultado de la operación.
	 * @param nombrePasswordTlf
	 * @return
	 */
	public String altaUsuario(Map<String,String> nombrePasswordTlf) throws RemoteException;
	
	
	/**
	 * Método para verificar que los datos recibidos de un usuario son los correctos y, por tanto, puede iniciar sesión en la aplicación.
	 * Invocado desde el método recibirMensaje(String mensajeRecibido).
	 * Devuelve el String ya parseado de JSON (llamar al método parsearAJSON(Map datos)) con el mensaje resultado de la operación.
	 * En caso de que el login sea correcto, se envían los datos de todos los contactos del usuario.
	 * @param usuarioContacto
	 * @return
	 */
	public String loginUsuario(Map<String,String> usuario) throws RemoteException;
	
	
	/**
	 * Método para dar de alta un nuevo contacto en la agenda de un usuario existente.
	 * Invocado desde el método recibirMensaje(String mensajeRecibido).
	 * Devuelve el String ya parseado de JSON (llamar al método parsearAJSON(Map datos)) con el mensaje resultado de la operación.
	 * @param usuarioContacto
	 * @return
	 */
	public String altaContacto(Map<String,String> usuarioContacto) throws RemoteException;
	
	
	
	/**
	 * Método para modificar los datos de un contacto (nombre, dirección y teléfono) ya existente en la agenda de un usuario.
	 * Invocado desde el método recibirMensaje(String mensajeRecibido).
	 * Devuelve el String ya parseado de JSON (llamar al método parsearAJSON(Map datos)) con el mensaje resultado de la operación.
	 * @param usuarioContacto
	 * @return
	 */
	public String modificarContacto(Map<String,String> usuarioContacto) throws RemoteException;
	
	
	// eliminar contacto
	/**
	 * Método que elimina un contacto de la agenda de un usuario.
	 * Recibe únicamente los nombres del usuario y del contacto a eliminar.
	 * Invocado desde el método recibirMensaje(String mensajeRecibido).
	 * Devuelve el String ya parseado de JSON (llamar al método parsearAJSON(Map datos)) con el mensaje resultado de la operación.
	 * @param usuarioContacto
	 * @return
	 */
	public String eliminarContacto(Map<String,String> usuarioContacto) throws RemoteException;
}
