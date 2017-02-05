package cliente;

import java.awt.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClienteVistaLogin {

	private JFrame frameLogin;
	private JTextField txt_Usuario;
	private JPasswordField txt_Password;
	private JButton btnLogin;
	private JButton btnRegistrar;
	private JLabel lblPassword;
	private JLabel lblUsuario;
	private ClienteControlador controlador;
	private JLabel lbl_login;

	/**
	 * Create the application.
	 */
	public ClienteVistaLogin() {
		initialize();
	}
	
	public void setClienteControlador(ClienteControlador controlador){
		this.controlador = controlador;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		frameLogin = new JFrame("Login");
		frameLogin.setIconImage(Toolkit.getDefaultToolkit().getImage(ClienteVistaLogin.class.getResource("/imagenes/Icono_Password.png")));
		frameLogin.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		frameLogin.setBounds(100, 100, 414, 346);
		frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		lblUsuario = new JLabel("Usuario");
		lblUsuario.setForeground(Color.BLACK);
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
		
		lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.BLACK);
		lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
		
		txt_Usuario = new JTextField();
		txt_Usuario.setColumns(10);
		
		txt_Password = new JPasswordField();
		
		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Arial", Font.PLAIN, 16));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controlador.datosLogin();
				limpiarCampos();
			}
		});
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setFont(new Font("Arial", Font.PLAIN, 16));
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.irRegistro();
			}
		});
		
		lbl_login = new JLabel("Login");
		lbl_login.setForeground(new Color(32, 178, 170));
		lbl_login.setFont(new Font("Arial", Font.BOLD, 26));
		GroupLayout groupLayout = new GroupLayout(frameLogin.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(108)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblUsuario)
							.addContainerGap())
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(btnRegistrar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnLogin, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(txt_Password, Alignment.LEADING)
									.addComponent(txt_Usuario, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
								.addContainerGap()))))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(160, Short.MAX_VALUE)
					.addComponent(lbl_login, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					.addGap(137))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lbl_login)
					.addGap(25)
					.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txt_Usuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblPassword)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txt_Password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnLogin)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnRegistrar)
					.addGap(24))
		);
		frameLogin.getContentPane().setLayout(groupLayout);
		frameLogin.setLocationRelativeTo(null);
		frameLogin.setResizable(false);
		frameLogin.setVisible(true);
	}
	
	public String getUsuario(){
		return txt_Usuario.getText();
	}
	
	public String getPassword(){
		char[] pass = txt_Password.getPassword();
		String password = new String(pass);
		return password;
	}
	
	public void avisoLogin(){
		JOptionPane.showMessageDialog(null,"El usuario no existe, debes registrarte", "Usuario no válido", 2);
	}
	
	public void mensajeEmergente(String mensaje, int tipo){
		JOptionPane.showMessageDialog(null,mensaje, "Usuario no válido", tipo);
	}
	
	public void cerrarVentana(){
		frameLogin.setVisible(false);
	}
	
	public void mostrarVentana(){
		frameLogin.setVisible(true);
	}
	
	public void limpiarCampos(){
		txt_Usuario.setText("");
		txt_Password.setText("");
	}
	
}
