package cliente;

import java.util.HashMap;
import java.util.Map;

public class ClienteControlador {
	
	private ClienteModelo modelo = null;
	private ClienteVistaAgenda agenda = null;
	private ClienteVistaLogin login = null;
	private ClienteVistaRegistro registro = null;
	
	public void setClienteModelo(ClienteModelo modelo){
		this.modelo = modelo;
	}
	
	public void setClienteVistaAgenda(ClienteVistaAgenda agenda){
		this.agenda = agenda;
	}
	
	public void setClienteVistaLogin(ClienteVistaLogin login){
		this.login = login;
	}

	public void setClienteVistaRegistro(ClienteVistaRegistro registro){
		this.registro = registro;
	}
	/**
	 * Cuando la vista recibe el evento de registrar un usuario llama a este método para coger los datos 
	 * del registro. Luego envía los datos al modelo llamando al método correspondiente
	 */
	public void datosRegistro(){
		String nombreUsuario = registro.getUsuario();
		String passwordUsuario = registro.getPassword();
		String telefonoUsuario = registro.getTelefono();
		Map<String, String> mapaAlta = new HashMap<String, String>();
		System.out.println(nombreUsuario + ", " + passwordUsuario + ", " + "telefonoUsuario");

		mapaAlta.put("opcion", "alta");
		mapaAlta.put("nombreUsuario", nombreUsuario);
		mapaAlta.put("passwordUsuario", passwordUsuario);
		mapaAlta.put("telefonoUsuario", telefonoUsuario);
		
		modelo.enviarRegistro(mapaAlta);
	}
	/**
	 * Solicita al modelo que cambie la visibilidad de las pantallas
	 */
	public void volverLogin(){
		modelo.ocultarRegistro();
		modelo.mostrarLogin();
	}
	/**
	 * Cuando la vista recibe el evento de logear un usuario llama a este método para coger los datos 
	 * del login. Luego envía los datos al modelo llamando al método correspondiente
	 */
	public void datosLogin(){
		String nombreUsuario = login.getUsuario();
		String passwordUsuario = login.getPassword();
		Map<String, String> mapaLogin = new HashMap<String, String>();
		
		mapaLogin.put("nombreUsuario", nombreUsuario);
		mapaLogin.put("passwordUsuario", passwordUsuario);
		
		modelo.enviarLogin(mapaLogin, nombreUsuario);
	}
	/**
	 * Solicita al modelo que cambie la visibilidad de las pantallas
	 */
	public void irRegistro(){
		modelo.ocultarLogin();
		modelo.mostrarRegistro();
	}
	/**
	 * Cuando la vista recibe el evento de guardar el contacto de un usuario llama a este método para coger 
	 * los datos del contacto y usuario. Luego envía los datos al modelo llamando al método correspondiente
	 */
	public void datosAltaContacto(){
		String nombreContacto = agenda.getNombre();
		String direccionContacto = agenda.getDireccion();
		String telefonoContacto = agenda.getTelefono();
		Map<String, String> mapaContacto = new HashMap<String, String>();
		
		mapaContacto.put("nombreUsuario", modelo.getUsuarioSesion());
		mapaContacto.put("nombreContacto", nombreContacto);
		mapaContacto.put("direccionContacto", direccionContacto);
		mapaContacto.put("telefonoContacto", telefonoContacto);
		
		modelo.altaContacto(mapaContacto);
	}
	/**
	 * Cuando la vista recibe el evento de modificar el contacto de un usuario llama a este método para coger 
	 * los datos del contacto y usuario. Luego envía los datos al modelo llamando al método correspondiente
	 */
	public void datosModifContacto(){
		String nombreContOriginal = agenda.getNombreOriginal();
		String nombreContacto = agenda.getNombre();
		String direccionContacto = agenda.getDireccion();
		String telefonoContacto = agenda.getTelefono();
		Map<String, String> mapaModif = new HashMap<String, String>();
		
		mapaModif.put("nombreUsuario", modelo.getUsuarioSesion());
		mapaModif.put("nombreContactoOriginal", nombreContOriginal);
		mapaModif.put("nombreContacto", nombreContacto);
		mapaModif.put("direccionContacto", direccionContacto);
		mapaModif.put("telefonoContacto", telefonoContacto);
		
		modelo.modificarContacto(mapaModif);
	}
	/**
	 * Cuando la vista recibe el evento de eliminar el contacto de un usuario llama a este método para coger 
	 * los datos del contacto y usuario. Luego envía los datos al modelo llamando al método correspondiente
	 * para que el servidor borre el contacto de la BBDD
	 */
	public void eliminarContacto(){
		String nombreContacto = agenda.getNombre();
		Map<String, String> mapaEliminar = new HashMap<String, String>();
		
		mapaEliminar.put("nombreUsuario", modelo.getUsuarioSesion());
		mapaEliminar.put("nombreContacto", nombreContacto);

		modelo.borrarContacto(mapaEliminar);
	}
	/**
	 * Solicita al modelo que cambie la visibilidad de las pantallas
	 */
	public void cerrarSesion(){
		modelo.mostrarLogin();
		modelo.ocultarAgenda();
	}
}
