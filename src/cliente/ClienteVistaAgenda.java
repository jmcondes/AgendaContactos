package cliente;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClienteVistaAgenda {

	private JFrame frame;
	private JTable table_Contactos;
	private JScrollPane scrollPane;
	private JTextField txt_Nombre;
	private JLabel lbl_Direccion;
	private JTextField txt_Direccion;
	private JLabel lbl_Telefono;
	private JTextField txt_Telefono;
	private DefaultTableModel model = new DefaultTableModel();
	private JButton btn_Nuevo;
	private JButton btn_Modificar;
	private JButton btn_Borrar;
	private ClienteModelo cliente;
	private ClienteControlador controlador;
	private JLabel lbl_Nombre;
	private JButton btn_salir;

	/**
	 * Create the application.
	 */
	public ClienteVistaAgenda() {
		initialize();
	}
	
	public void setCliente(ClienteModelo cliente){
		this.cliente = cliente;
	}
	
	public void setClienteControlador(ClienteControlador controlador){
		this.controlador = controlador;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		frame = new JFrame();
		
		scrollPane = new JScrollPane();
		
		lbl_Nombre = new JLabel("Nombre:");
		lbl_Nombre.setForeground(new Color(0, 51, 255));
		lbl_Nombre.setFont(new Font("Arial", Font.PLAIN, 14));
		
		txt_Nombre = new JTextField();
		txt_Nombre.setForeground(Color.BLACK);
		txt_Nombre.setBackground(Color.WHITE);
		txt_Nombre.setFont(new Font("Arial", Font.PLAIN, 14));
		txt_Nombre.setColumns(10);
		
		lbl_Direccion = new JLabel("Direccion:");
		lbl_Direccion.setForeground(new Color(0, 51, 255));
		lbl_Direccion.setFont(new Font("Arial", Font.PLAIN, 14));
		
		txt_Direccion = new JTextField();
		txt_Direccion.setBackground(Color.WHITE);
		txt_Direccion.setForeground(Color.BLACK);
		txt_Direccion.setFont(new Font("Arial", Font.PLAIN, 14));
		txt_Direccion.setColumns(10);
		
		lbl_Telefono = new JLabel("Tel\u00E9fono:");
		lbl_Telefono.setForeground(new Color(0, 51, 255));
		lbl_Telefono.setFont(new Font("Arial", Font.PLAIN, 14));
		
		txt_Telefono = new JTextField();
		txt_Telefono.setBackground(Color.WHITE);
		txt_Telefono.setForeground(Color.BLACK);
		txt_Telefono.setFont(new Font("Arial", Font.PLAIN, 14));
		txt_Telefono.setColumns(10);
		
		btn_Nuevo = new JButton("Nuevo Contacto");
		btn_Nuevo.setForeground(new Color(0, 51, 255));
		btn_Nuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!(txt_Nombre.getText().equals("") || txt_Direccion.getText().equals("") || txt_Telefono.getText().equals(""))){					
					controlador.datosAltaContacto();
				}
				else{
					mensajeEmergente("Asegúrese de rellenar el formulario del contacto.", 2);
				}
			}
		});
		btn_Nuevo.setFont(new Font("Arial", Font.PLAIN, 16));
		
		btn_Modificar = new JButton("Modificar Contacto");
		btn_Modificar.setForeground(new Color(0, 51, 255));
		btn_Modificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table_Contactos.getSelectedRow() != -1){																							
					controlador.datosModifContacto();
				}
				else{
					mensajeEmergente("No hay seleccionado ningún contacto.", 2);
				}
			}
		});
		btn_Modificar.setFont(new Font("Arial", Font.PLAIN, 16));
		
		btn_Borrar = new JButton("Borrar Contacto");
		btn_Borrar.setForeground(new Color(0, 51, 255));
		btn_Borrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table_Contactos.getSelectedRow() != -1){
					controlador.eliminarContacto();				
				}
				else{
					mensajeEmergente("No hay seleccionado ningún contacto.", 2);
				}
			}
		});
		btn_Borrar.setFont(new Font("Arial", Font.PLAIN, 16));
		
		btn_salir = new JButton("Volver a Inicio");
		btn_salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controlador.cerrarSesion();				
			}
		});
		btn_salir.setForeground(new Color(0, 51, 255));
		btn_salir.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btn_Nuevo, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btn_Modificar)
							.addGap(18)
							.addComponent(btn_Borrar, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btn_salir, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
									.addComponent(lbl_Telefono)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(txt_Telefono))
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
									.addComponent(lbl_Nombre)
									.addGap(18)
									.addComponent(txt_Nombre, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE)))
							.addGap(24)
							.addComponent(lbl_Direccion, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txt_Direccion, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE)
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_Nombre)
						.addComponent(txt_Nombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txt_Direccion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl_Direccion))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_Telefono)
						.addComponent(txt_Telefono, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_Nuevo)
						.addComponent(btn_Modificar)
						.addComponent(btn_Borrar)
						.addComponent(btn_salir))
					.addGap(25))
		);
		
		table_Contactos = new JTable(model);
		table_Contactos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre de Contacto","Dirección","Teléfono"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false,false,false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		table_Contactos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				txt_Nombre.setText((String)((DefaultTableModel) table_Contactos.getModel()).getValueAt(table_Contactos.getSelectedRow(), 0));
				txt_Direccion.setText((String)((DefaultTableModel) table_Contactos.getModel()).getValueAt(table_Contactos.getSelectedRow(), 1));
				txt_Telefono.setText((String)((DefaultTableModel) table_Contactos.getModel()).getValueAt(table_Contactos.getSelectedRow(), 2));
			}
		});
		
		table_Contactos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txt_Nombre.setText((String)((DefaultTableModel) table_Contactos.getModel()).getValueAt(table_Contactos.getSelectedRow(), 0));
				txt_Direccion.setText((String)((DefaultTableModel) table_Contactos.getModel()).getValueAt(table_Contactos.getSelectedRow(), 1));
				txt_Telefono.setText((String)((DefaultTableModel) table_Contactos.getModel()).getValueAt(table_Contactos.getSelectedRow(), 2));
			}
		});
		
		table_Contactos.setForeground(Color.BLACK);
		table_Contactos.setFont(new Font("Arial", Font.PLAIN, 14));
		scrollPane.setViewportView(table_Contactos);
		frame.getContentPane().setLayout(groupLayout);
		frame.setBounds(100, 100, 753, 533);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}
	
	public String getNombre(){
		return txt_Nombre.getText();
	}
	
	public String getNombreOriginal(){
		String nombreOriginal = (String) ((DefaultTableModel) table_Contactos.getModel()).getValueAt(table_Contactos.getSelectedRow(), 0);
		return nombreOriginal;
	}
	
	public String getDireccion(){
		return txt_Direccion.getText();
	}
	
	public String getTelefono(){
		return txt_Telefono.getText();
	}
	/**
	 * Añade la fila a la tabla cogiendo los datos de los textfield del formulario
	 */
	public void addFilaTabla(){
		Object[] fila = new Object[getNumColumnasTabla()];
		fila[0] = getNombre();
		fila[1] = getDireccion();
		fila[2] = getTelefono();
		((DefaultTableModel) table_Contactos.getModel()).addRow(fila);
		table_Contactos.getSelectionModel().clearSelection();
	}
	/**
	 * Modifica la fila de la tabla con los datos que coge de los textfield del formulario
	 */
	public void modifFilaTabla(){
		((DefaultTableModel) table_Contactos.getModel()).setValueAt(getNombre(), table_Contactos.getSelectedRow(), 0);
		((DefaultTableModel) table_Contactos.getModel()).setValueAt(getDireccion(), table_Contactos.getSelectedRow(), 1);
		((DefaultTableModel) table_Contactos.getModel()).setValueAt(getTelefono(), table_Contactos.getSelectedRow(), 2);
	}
	/**
	 * Borra de la tabla la fila seleccionada
	 */
	public void borraFilaTabla(){
		DefaultTableModel modelo = (DefaultTableModel) table_Contactos.getModel();
		modelo.removeRow(table_Contactos.getSelectedRow());
	}
	/**
	 * Deselecciona la fila actual
	 */
	public void deselectFilaTabla(){
		table_Contactos.getSelectionModel().clearSelection();
	}
	
	public int getNumColumnasTabla() {
		return ((DefaultTableModel) table_Contactos.getModel()).getColumnCount();
	}
	/**
	 * El modelo llama iterativamente a este método para ir actualizando fila a fila la tabla.
	 * @param fila
	 */
	public void actualizaTabla(Object[] fila) {		
		((DefaultTableModel) table_Contactos.getModel()).addRow(fila);
	}
	/**
	 * Limpia la tabla
	 */
	public void borrarTabla() {
		int filas = table_Contactos.getRowCount();
		try {
			DefaultTableModel modelo = (DefaultTableModel) table_Contactos.getModel();
			for (int i = 0; filas > i; i++) {
				modelo.removeRow(0);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
		}
	}
	/**
	 * Borra los campos del formulario
	 */
	public void borrarCampos(){
		txt_Nombre.setText("");
		txt_Direccion.setText("");
		txt_Telefono.setText("");
	}
	
	public void mostrarAgenda(){
		//Asigna a la ventana el título cogiendo el nombre del usuario que ha logeado.
		frame.setTitle("Usuario: " + cliente.getUsuarioSesion());
		frame.setVisible(true);		
	}
	
	public void cerrarVentana(){
		frame.setVisible(false);
	}
	/**
	 * Método reutilizable para mostrar cualquier mensaje emergente
	 * @param mensaje
	 * @param tipo
	 */
	public void mensajeEmergente(String mensaje, int tipo){
		JOptionPane.showMessageDialog(null, mensaje, "Aviso", tipo);
	}
}
