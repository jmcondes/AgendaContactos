package cliente;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import servidor.Servidor;

public class ClienteModelo {

	private Servidor servidor;
	private ClienteVistaLogin loginItf;
	private ClienteVistaRegistro registroItf;
	private ClienteVistaAgenda agendaItf;
	private String usuarioSesion;

	public ClienteModelo() {
		servidor = null;
		try {
			System.out.println("Localizando el registro de objetos remotos");
			//Localiza los objetos remotos en la ip y el puerto especificados
			Registry registry = LocateRegistry.getRegistry("localhost", 5555);
			System.out.println("Obteniendo el stub del objeto remoto");
			//El objeto stub 'servidor' se utiliza para invocar los métodos del servidor de manera remota 
			servidor = (Servidor) registry.lookup("Agenda");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setClienteLoginInterfaz(ClienteVistaLogin loginItf) {
		this.loginItf = loginItf;
	}

	public void setClienteRegistroInterfaz(ClienteVistaRegistro registroItf) {
		this.registroItf = registroItf;
	}
	
	public void setClienteInterfaz(ClienteVistaAgenda agendaItf) {
		this.agendaItf = agendaItf;
	}
	
	public String getUsuarioSesion(){
		return this.usuarioSesion;
	}
	/**
	 * Invoca de manera remota al método enviaRegistro del servidor, envía un Map con los datos de registro
	 * Procesa el JSON de respuesta y actualiza la vista
	 * @param mapaAlta
	 */
	public void enviarRegistro(Map<String, String> mapaAlta) {		
		try {
			String respuesta = servidor.altaUsuario(mapaAlta);
			Object jsonObject = JSONValue.parse(respuesta.toString());
            JSONObject jso = (JSONObject)jsonObject;
            if(jso.get("respuesta").equals("ok")){
            	registroItf.mensajeEmergente("El registro se ha completado con éxito.", "Registro Completado", 1);
            	ocultarRegistro();
            	mostrarLogin();
            }else{
            	registroItf.mensajeEmergente("El usuario ya existe", "Error registro", 2);
            }
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Invoca de manera remota al método enviaLogin del servidor, envía un Map con los datos de login
	 * Procesa el JSON de respuesta y actualiza la vista
	 * @param mapaLogin
	 * @param nombreUsuario
	 */
	public void enviarLogin(Map<String, String> mapaLogin, String nombreUsuario) {
		try {
			String respuesta = servidor.loginUsuario(mapaLogin);

			Object jsonObject = JSONValue.parse(respuesta.toString());
            JSONObject jso = (JSONObject)jsonObject;
            //Este código sirve únicamente para dar formato a la impresión por pantalla del JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
			String jsonString = gson.toJson(jso); 
			System.out.println(jsonString);
			
            if(jso.get("respuesta").equals("ok")){
            	usuarioSesion = nombreUsuario;
            	agendaItf.borrarTabla();
            	agendaItf.borrarCampos();
            	rellenarTabla((JSONArray)jso.get("contactos"));
            	ocultarLogin();
            	mostrarAgenda();
            }else{
            	loginItf.avisoLogin();
            }
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
	}
	/**
	 * Invoca al método del servidor para guardar un contacto, envía el Map con los datos del contacto
	 * de un usuario. Procesa la respuesta y actualiza la vista.
	 * @param mapaContacto
	 */
	public void altaContacto(Map<String, String> mapaContacto){				
		try {
			String respuesta = servidor.altaContacto(mapaContacto);
			System.out.println(respuesta);
			Object jsonObject = JSONValue.parse(respuesta.toString());
            JSONObject jso = (JSONObject)jsonObject;
            if(jso.get("respuesta").equals("ok")){
            	agendaItf.addFilaTabla();
            	agendaItf.deselectFilaTabla();
            	agendaItf.borrarCampos();
				agendaItf.mensajeEmergente("El contacto se ha guardado en la Base de Datos", 1);
            } else{
            	agendaItf.mensajeEmergente("El nombre de contacto ya existe", 2);
            }
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
	}
	/**
	 * LLama al método de modificar un contacto de manera remota, y actualiza la vista con la respuesta del
	 * servidor
	 * @param mapaModif
	 */
	public void modificarContacto(Map<String, String> mapaModif){		
		try {
			String respuesta = servidor.modificarContacto(mapaModif);
			System.out.println(respuesta);
			Object jsonObject = JSONValue.parse(respuesta.toString());
            JSONObject jso = (JSONObject)jsonObject;
            if(jso.get("respuesta").equals("ok")){
            	agendaItf.modifFilaTabla();
            	agendaItf.deselectFilaTabla();
            	agendaItf.borrarCampos();
            	agendaItf.mensajeEmergente("El contacto se ha actualizado", 1);
            }else{
            	agendaItf.mensajeEmergente("El nombre de contacto ya existe", 2);
            }
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
	}
	/**
	 * Invoca al método del servidor que elimina un contacto de la agenda del usuario, luego con la respuesta
	 * del JSON actualiza la vista.
	 * @param mapaEliminar
	 */
	public void borrarContacto(Map<String, String> mapaEliminar){		
		try {
			String respuesta = servidor.eliminarContacto(mapaEliminar);
			System.out.println(respuesta);
			Object jsonObject = JSONValue.parse(respuesta.toString());
            JSONObject jso = (JSONObject)jsonObject;
            if(jso.get("respuesta").equals("ok")){
            	agendaItf.borraFilaTabla();
            	agendaItf.borrarCampos();
            	agendaItf.mensajeEmergente("El contacto se ha eliminado.", 1);
            }else{
            	
            }
		} catch (RemoteException e) {
			e.printStackTrace();
		}			
	}
	/**
	 * Manda los datos del JSON recibido a las filas y columnas de la tabla.
	 * @param contactos
	 */
	@SuppressWarnings("unchecked")
	public void rellenarTabla(JSONArray contactos){
		int numColumnasTabla = agendaItf.getNumColumnasTabla();
    	if (contactos != null) {	    	
	    	Iterator<JSONObject> iterator= contactos.iterator();
	    	Object[] filaTabla = new Object[numColumnasTabla];
			while (iterator.hasNext()) {
				JSONObject jsonObject = (JSONObject)iterator.next();
				filaTabla[0] = jsonObject.get("nombreContacto");
				filaTabla[1] = jsonObject.get("direccionContacto");
				filaTabla[2] = jsonObject.get("telefonoContacto");
				agendaItf.actualizaTabla(filaTabla);
			}
    	}else{
    		agendaItf.borrarTabla();
    	}
	}
	
	public void mostrarLogin() {
		loginItf.mostrarVentana();
	}

	public void ocultarLogin() {
		loginItf.cerrarVentana();
	}

	public void mostrarRegistro() {
		registroItf.mostrarVentana();
	}

	public void ocultarRegistro() {
		registroItf.cerrarVentana();
	}
	
	public void mostrarAgenda(){
		agendaItf.mostrarAgenda();
	}
	
	public void ocultarAgenda(){
		agendaItf.cerrarVentana();
		this.usuarioSesion = "";
	}
}
