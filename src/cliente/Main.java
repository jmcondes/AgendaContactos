package cliente;

public class Main {
	
	public Main(){
		init();
	}
	/**
	 * Es aquí donde se crean instancias de todas las clases, y se asigna valores mediante los setter de cada clase
	 * a las referencias de objetos para comunicar las clases según el modelo MVC.
	 */
	public void init(){
		
		ClienteModelo modelo = new ClienteModelo();
		ClienteControlador controlador = new ClienteControlador();
		controlador.setClienteModelo(modelo);
		
		ClienteVistaLogin loginItf = new ClienteVistaLogin();
		controlador.setClienteVistaLogin(loginItf);
		loginItf.setClienteControlador(controlador);
		
		ClienteVistaRegistro registroItf = new ClienteVistaRegistro();
		controlador.setClienteVistaRegistro(registroItf);
		registroItf.setClienteControlador(controlador);
		
		ClienteVistaAgenda agendaItf = new ClienteVistaAgenda();
		controlador.setClienteVistaAgenda(agendaItf);
		agendaItf.setClienteControlador(controlador);
		agendaItf.setCliente(modelo);
		
		modelo.setClienteLoginInterfaz(loginItf);
		modelo.setClienteRegistroInterfaz(registroItf);
		modelo.setClienteInterfaz(agendaItf);
	}

	public static void main(String[] args) {

		new Main();
	}
}
