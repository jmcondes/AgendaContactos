package cliente;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.awt.*;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClienteVistaRegistro {

	private JFrame frameRegistro;
	private JTextField txt_nombre;
	private JPasswordField txt_password;
	private JButton btnRegistrar;
	private JButton btnVolver;
	private JLabel lbl_telefono;
	private JLabel lbl_password;
	private JLabel lbl_Nombre;
	private JTextField txt_telefono;
	private ClienteControlador controlador;
	private JLabel lbl_titulo;

	/**
	 * Create the application.
	 */
	public ClienteVistaRegistro() {
		initialize();
	}
	
	public void setClienteControlador(ClienteControlador controlador){
		this.controlador = controlador;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameRegistro = new JFrame();
		frameRegistro.getContentPane().setFont(new Font("Arial", Font.PLAIN, 14));
		frameRegistro.setBounds(100, 100, 453, 451);
		frameRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		lbl_titulo = new JLabel("Registro");
		lbl_titulo.setForeground(new Color(32, 178, 170));
		lbl_titulo.setFont(new Font("Arial", Font.BOLD, 26));
		
		lbl_Nombre = new JLabel("Nombre");
		lbl_Nombre.setFont(new Font("Arial", Font.BOLD, 14));
		
		txt_nombre = new JTextField();
		txt_nombre.setColumns(10);
		
		lbl_password = new JLabel("Password");
		lbl_password.setFont(new Font("Arial", Font.BOLD, 14));
		
		lbl_telefono = new JLabel("Tel\u00E9fono");
		lbl_telefono.setFont(new Font("Arial", Font.BOLD, 14));
		
		txt_password = new JPasswordField();
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txt_nombre.getText().equals("") || getPassword().equals("") || txt_telefono.getText().equals("")){
					mensajeEmergente("Los campos no pueden estar vacíos", "Campos Vacíos", 2);
				}else{
					controlador.datosRegistro();
				}
				limpiarCampos();
			}
		});
		btnRegistrar.setFont(new Font("Arial", Font.PLAIN, 16));
		
		btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Arial", Font.PLAIN, 16));
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controlador.volverLogin();
			}
		});
		
		txt_telefono = new JTextField();
		txt_telefono.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(frameRegistro.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(110)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lbl_Nombre, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnRegistrar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnVolver, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txt_nombre, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
								.addComponent(lbl_password)
								.addComponent(txt_password)
								.addComponent(lbl_telefono)
								.addComponent(txt_telefono)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(168)
							.addComponent(lbl_titulo)))
					.addContainerGap(101, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lbl_titulo)
					.addGap(40)
					.addComponent(lbl_Nombre)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txt_nombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lbl_password)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txt_password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lbl_telefono)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txt_telefono, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
					.addComponent(btnRegistrar)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnVolver)
					.addGap(42))
		);
		frameRegistro.getContentPane().setLayout(groupLayout);
		frameRegistro.setLocationRelativeTo(null);
		frameRegistro.setResizable(false);
	}
	
	public void cerrarVentana(){
		frameRegistro.setVisible(false);
	}
	
	public void mostrarVentana(){
		frameRegistro.setVisible(true);
	}
	
	public String getUsuario(){
		return txt_nombre.getText();
	}
	
	public String getPassword(){
		char[] pass = txt_password.getPassword();
		String password = new String(pass);
		return password;
	}
	
	public String getTelefono(){
		return txt_telefono.getText();
	}
	
	public void mensajeEmergente(String mensaje, String titulo, int tipo){
		JOptionPane.showMessageDialog(null,mensaje,titulo,tipo);
	}
	
	public void limpiarCampos(){
		txt_nombre.setText("");
		txt_password.setText("");
		txt_telefono.setText("");
	}
}