package interfaz;

//Importamos las clases para usar sus getters/setters
import cuadrante.Cuadrante;
import cuadrante.Personal;
import cuadrante.Turnos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

public class Principal {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		new MarcoPrincipal();
			
	}
}


//Marco
class MarcoPrincipal extends JFrame{

	private static final long serialVersionUID = 1L;
	
	LaminaPrincipal laminaPrincipal;
	LaminaPersonal laminaPersonal;
	LaminaTurnos laminaTurnos;
	private int mes,anyo,dia;
	private int anyoSeleccionado = 1;
	private String[] sAnyos = new String[3];
	
	
	public static int width=1280,height=950;
	//public static int width=1280,height=800;
	
	public MarcoPrincipal() throws IOException{

		//Adaptamos el ancho al número de semanas
		adaptarMarco();
		
		setTitle("SABIR");
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Get current date
		getCurrentDate();
		
		//Get último mes modificad
		Cuadrante.getUltimoMesModificado();
		
		
		/**
		 * Añadimos la lámina principal al marco principal. Al hacerlo con getContentPane, podremos quitar la lámina y añadir otra cuando nos movamos por el menú. 
		 */
		laminaPrincipal = new LaminaPrincipal();
		getContentPane().add(laminaPrincipal);
		
		setVisible(true);
		
	}
	
	
	/**
	 * ADAPTAR MARCO A NÚMERO DE SEMANAS
	 */
	public void adaptarMarco(){
		
		int numSemanas = Cuadrante.getNumSemanas();
		
		switch (numSemanas) {
			case 4:
				width=1280;
				height=950;
				break;
			
			case 5:
				width=1560;
				height=950;
				break;
				
			default:
				break;
			}
			
	}
	
	
	/**
	 * GET CURRENT DATE
	 */
	private void getCurrentDate(){
		Calendar calendario = Calendar.getInstance();
		dia = calendario.get(Calendar.DAY_OF_MONTH);
		mes = calendario.get(Calendar.MONTH);
		anyo = calendario.get(Calendar.YEAR);
		
		//Creamos el array con los 3 años para el combobox
		sAnyos[0] = Integer.toString(anyo-1);
		sAnyos[1] = Integer.toString(anyo);
		sAnyos[2] = Integer.toString(anyo+1);
	}
	
	/**
	 * Esta función cambia la ventana, en función del ActionListener
	 */
	public void cambioLamina(int laminaId){

		switch (laminaId) {
		
		//Principal
		case 0:
			
			try {
				laminaPrincipal = new LaminaPrincipal();
				getContentPane().removeAll();
				getContentPane().add(laminaPrincipal);
				setVisible(true);
				break;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
				
			
		//Añadir personal
		case 1:
			laminaPersonal = new LaminaPersonal();
			getContentPane().removeAll();
			getContentPane().add(laminaPersonal);
			setVisible(true);
			break;
			
		//Modifica turnos
		case 2:
			laminaTurnos = new LaminaTurnos();
			getContentPane().removeAll();
			getContentPane().add(laminaTurnos);
			setVisible(true);
			break;	
			

		default:
			break;
		}
		
		
	}
	


	/**
	 * Hacemos la clase interna para poder modificar la lámina con los eventos
	 */
	private class LaminaPrincipal extends JPanel implements ActionListener{
		
		private static final long serialVersionUID = 1L;
	/**
	 * Variables
	 */
	private List<String> turnosCargados;
	private ArrayList<String[]> cuadranteCargado;
	private String [] cabeceraCargada,turnosArray,cabeceraContador;
	private String [][] cuadranteArray, datosContador;
	private Box cajaCentro,cajaTablas,cajaCuadrante,cajaContador,cajaMenu,cajaFecha,cajaTop;
	private JTable tablaCuadrante,tablaContador;
	private JScrollPane scrollCuadrante,scrollContador,scrollCajaTablas;
	private JPanel laminaMenu,laminaFecha;
	private JButton botonAuto,botonGuardar;
	private JComboBox<String> comboTurnos, sMes,sAnyo;
	private JMenu menuEstadistica,menuPersonal,menuTurnos;
	private JMenuBar barraMenu;
	private JMenuItem añadirPersonal,borrarPersonal,modificarTurnos;
	
	private String[] sMeses = {"ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
	
	
	
	public LaminaPrincipal() throws IOException{
		
		
		setLayout(new BorderLayout());
		
		//Carga el Menú
		cargarMenu();
		
		//Carga lámina cuadrante y lámina contador con sus scrolls
		cargarLaminaPrincipal();
			
		//Hacemos un setter del número de semanas del mes actual
		//Cuadrante.setNumSemanasEsteMes(Cuadrante.getNumSemanas());
		Cuadrante.setNumSemanas();
	}
	
		
	/**
	 * MENÚ
	 */
	private void cargarMenu(){
		
		//Esta caja va a contener las otras dos (menú y fecha)
		cajaTop = Box.createHorizontalBox();
			
			//MENU
			cajaMenu = Box.createHorizontalBox();
			cajaTop.add(cajaMenu);
				
				laminaMenu = new JPanel(new FlowLayout(FlowLayout.LEFT));
				//Creamos la barra de menu y los menus
				barraMenu = new JMenuBar();
				menuPersonal = new JMenu("Personal");
				menuTurnos = new JMenu("Turnos");
				menuEstadistica = new JMenu("Estadística");	
				barraMenu.add(menuPersonal);
				barraMenu.add(menuTurnos);
				barraMenu.add(menuEstadistica);
				
				/**
				 * añadimos los Items personal
				 */
				añadirPersonal = new JMenuItem("Añadir");
				
				//Le damos un nombre al ActionCommand para capturar de donde vendrá el evento al hacer click
				añadirPersonal.setActionCommand("Añadir Personal");
				borrarPersonal = new JMenuItem("Eliminar");
				menuPersonal.add(añadirPersonal);
				menuPersonal.add(borrarPersonal);
				
				//Evento añadir personal
				añadirPersonal.addActionListener(this);
				
				/**
				 * Añadimos los Items turnos
				 */
				modificarTurnos = new JMenuItem("Modificar");
				modificarTurnos.setActionCommand("Modificar Turnos");
				menuTurnos.add(modificarTurnos);
				
				//Evento modificar turnos
				modificarTurnos.addActionListener(this);
				
				
				/**
				 * Añadimos la barra a la lámina y a la caja
				 */
				laminaMenu.add(barraMenu);
				cajaMenu.add(laminaMenu);
				
			//FECHA
			cajaFecha = Box.createHorizontalBox();
			cajaTop.add(cajaFecha);
			
				laminaFecha = new JPanel(new FlowLayout(FlowLayout.LEFT));
				sMes = new JComboBox<>(sMeses);
				sAnyo= new JComboBox<>(sAnyos);
				
				// Ponemos el combobox por defecto
				sMes.setSelectedIndex(mes);
				sAnyo.setSelectedIndex(anyoSeleccionado);
				
				//Ponemos a la escucha para los eventos
				sMes.setActionCommand("Cambio Mes");
				sAnyo.setActionCommand("Cambio Anyo");
				sMes.addActionListener(this);
				sAnyo.addActionListener(this);
				
				
				laminaFecha.add(sMes);
				laminaFecha.add(sAnyo);
				
				//Botón auto
				botonAuto = new JButton("Auto");
				//Botón GUARDAR
				botonGuardar = new JButton("GUARDAR");
				laminaFecha.add(botonAuto);
				laminaFecha.add(botonGuardar);
				
				cajaFecha.add(laminaFecha);
					
				
		add(cajaTop, BorderLayout.NORTH);
	
	}
	
	
	/**
	 * LÁMINA CUADRANTE Y LÁMINA CONTADOR CON SCROLLS
	 */
	private void cargarLaminaPrincipal() throws IOException{
		
		TableColumn columnasTurnos;
		DefaultCellEditor editorCombo;
		
		/**
		 * Caja Vertical Centro
		 */
		
		cajaCentro=Box.createVerticalBox();
		add(cajaCentro);
		
		
			//Caja Vertical tablas 
			cajaTablas = Box.createVerticalBox();
			cajaCentro.add(cajaTablas);
			
				/**
				 * Caja Horizontal Cuadrante
				 */
				cajaCuadrante=Box.createHorizontalBox();

				cajaTablas.add(cajaCuadrante);
			
					//Tabla cuadrante --> metemos el getter de los datos y de la cabecera
					tablaCuadrante = cargarCuadrante(mes, anyo);
				
					tablaCuadrante.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					
					//Alineamos las celdas y les damos color con el renderer
					tablaCuadrante.setDefaultRenderer(Object.class, new ColorRenderer());					
	
					
					//Modificamos el tamaño de las celdas
					for (int i=2; i<cabeceraCargada.length;i++){
						tablaCuadrante.getColumnModel().getColumn(i).setMaxWidth(40);
					}
							
					cajaCuadrante.add(tablaCuadrante);
					
						//Hacemos ComboBox en los elementos de la tabla para poner los turnos
						//comboTurnos.setEditable(true); //Si queremos que se pueda escribir en la casilla
						editorCombo = new DefaultCellEditor(cargarTurnos());
						for (int i=2; i<cabeceraCargada.length;i++){
							columnasTurnos = tablaCuadrante.getColumnModel().getColumn(i);
							columnasTurnos.setCellEditor(editorCombo);
						}	
						
						
						//Scroll Caja Cuadrante   
						scrollCuadrante=new JScrollPane(tablaCuadrante, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
						scrollCuadrante.setPreferredSize(new Dimension(1000,100));
						scrollCuadrante.setMinimumSize(new Dimension(1000,100));
						scrollCuadrante.setMaximumSize(new Dimension(2000,20000));
						cajaCuadrante.add(scrollCuadrante);
								
					
						
				/**
				 * Caja Horizontal Contador
				 */
					cabeceraContador = Cuadrante.getCabeceraContador(mes, anyo);
					datosContador = Cuadrante.getContador();
						
					cajaContador=Box.createHorizontalBox();
					cajaTablas.add(cajaContador);
					
					//Tabla contador
					tablaContador = new JTable(datosContador,cabeceraContador);
					
					//Utilizamos un renderer para la alineación y el color
					tablaContador.setDefaultRenderer(Object.class, new ContadorRenderer());
		
					tablaContador.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);					
					
					//Modificamos el tamaño de las celdas
					tablaContador.getColumnModel().getColumn(0).setMinWidth(150);
					for (int i=1; i<cabeceraContador.length;i++){
						tablaContador.getColumnModel().getColumn(i).setMaxWidth(40);
					}
					
					cajaContador.add(tablaContador);

						//Scroll contador
						scrollContador=new JScrollPane(tablaContador, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
						
						scrollContador.setPreferredSize(new Dimension(1000,80));
						scrollContador.setMinimumSize(new Dimension(1000,80));
						scrollContador.setMaximumSize(new Dimension(2000,80));
					
					cajaContador.add(scrollContador);
						

				
				//Creamos el scroll de todas las tablas
				scrollCajaTablas = new JScrollPane(cajaTablas,JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				
				cajaCentro.add(scrollCajaTablas);
				
			
				

	}
	
	private JTable cargarCuadrante(int mes, int anyo){
		
		//Cargamos la cabecera
		cabeceraCargada = Cuadrante.getCabecera(mes, anyo);
		
		//Cargamos el cuadrante (es una lista de arrays)
		cuadranteCargado = Cuadrante.getCuadrante(mes, anyo);
		
		//Pasamos el cuadrante a un array
		cuadranteArray = new String[cuadranteCargado.size()][];
		cuadranteCargado.toArray(cuadranteArray);		
		
		return new JTable(cuadranteArray,cabeceraCargada);
		
	}


	
	/**
	 * Cargamos los turnos  
	 */
	private JComboBox<String> cargarTurnos() throws IOException{
		
		//Cargamos lor turnos (nos da un List<String>)
		turnosCargados = Turnos.getTurnos();
		
		//Los pasamos a arrays
		turnosArray = new String [turnosCargados.size()];
		turnosArray = turnosCargados.toArray(turnosArray);
		comboTurnos = new JComboBox<>(turnosArray);
		
		return comboTurnos;
		
	}
	
	



	/**
	 * EVENTOS LÁMINA PRINCIPAL
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//Recogemos la fuente del evento
		String comando = arg0.getActionCommand();
		
		switch (comando) {
				
		case "Añadir Personal":
			cambioLamina(1);
			break;
			
		case "Modificar Turnos":
			cambioLamina(2);
			break;
			
		case "Cambio Mes":
			mes = sMes.getSelectedIndex();
			cambioLamina(0);
			break;
			
		case "Cambio Anyo":
			//Cogemos el texto (año) del Combobox y lo ponemos como int en el anyo
			anyoSeleccionado = sAnyo.getSelectedIndex();
			anyo = Integer.parseInt(sAnyos[sAnyo.getSelectedIndex()]);
			
			cambioLamina(0);
			break;
			
			
		default:
			break;
			
		}
		
		
	}
	
		
	
	
}

	
	/**
	 * Esta lámina se cargará al seleccionar "personal" en el menú
	 */
	private class LaminaPersonal extends JPanel implements ActionListener{ 

		private static final long serialVersionUID = 1L;
		
		private JLabel etiquetaNombre, etiquetaDireccion, etiquetaTelefono,etiquetaObservaciones;
		private JTextField nombre,direccion,telefono;
		private JTextArea observaciones;
		private Box cajaFondo, cajaNombre,cajaDir,cajaTelf,cajaBotones,cajaObservaciones;
		private JButton botonCancelar,botonGuardar;
		
		public LaminaPersonal(){
			
			//Cajas contenedoras
			cajaFondo = Box.createVerticalBox();
			cajaNombre = Box.createHorizontalBox();
			cajaDir = Box.createHorizontalBox();
			cajaTelf = Box.createHorizontalBox();
			cajaObservaciones = Box.createHorizontalBox();
			cajaBotones = Box.createHorizontalBox();
			
			
			add(cajaFondo,BorderLayout.CENTER);
			
			//Añadimos espacios verticales entre las cajas
			cajaFondo.add(Box.createVerticalStrut(150));
			cajaFondo.add(cajaNombre);
			cajaFondo.add(Box.createVerticalStrut(50));
			cajaFondo.add(cajaTelf);
			cajaFondo.add(Box.createVerticalStrut(50));
			cajaFondo.add(cajaDir);
			cajaFondo.add(Box.createVerticalStrut(50));
			cajaFondo.add(cajaObservaciones);		
			cajaFondo.add(Box.createVerticalStrut(100));
			cajaFondo.add(cajaBotones);
			
			//Botones
			botonCancelar = new JButton("Cancelar");
			botonGuardar = new JButton("Añadir");
			
			//Etiquetas
			etiquetaNombre = new JLabel("Nombre: ");
			etiquetaDireccion = new JLabel("Dirección: ");
			etiquetaTelefono = new JLabel("Teléfono: ");
			etiquetaObservaciones = new JLabel("Observaciones: ");
			
			//TextField
			nombre = new JTextField(20);
			direccion = new JTextField();
			telefono = new JTextField();
			observaciones = new JTextArea(5,10);
			
			etiquetaNombre.setLocation(100, 100);
			etiquetaTelefono.setLocation(100, 300);
			etiquetaDireccion.setLocation(100, 400);
			
			cajaNombre.add(etiquetaNombre);
			cajaNombre.add(nombre);
			cajaTelf.add(etiquetaTelefono);
			cajaTelf.add(telefono);
			cajaDir.add(etiquetaDireccion);
			cajaDir.add(direccion);
			cajaObservaciones.add(etiquetaObservaciones);
			cajaObservaciones.add(observaciones);
			cajaBotones.add(botonCancelar);
			cajaBotones.add(Box.createRigidArea(new Dimension(100,0)));
			cajaBotones.add(botonGuardar);
				
			//Eventos
			botonCancelar.setActionCommand("Cancelar Personal");
			botonCancelar.addActionListener(this);
			
			botonGuardar.setActionCommand("Guardar Personal");
			botonGuardar.addActionListener(this);
			
		}
		
		
		//Con esta función recogemos los datos del formulario, los almacenamos y llamamos al setPersonal
		public void recogerDatosPersonal(){
			
			
			//Comprobamos que se introduzca el nombre
			if (nombre.getText().length()<1){
				JOptionPane.showMessageDialog(this,"Debe introducir un nombre");
				
			}else{
				
				//Primero cargo la lista actual de personas, para añadir esta	
				List<Personal> listaPersonal = new ArrayList<>();
			
				//Cargamos el archivo
				listaPersonal = Personal.getPersonal();			
				
				//Creamos la persona con los datos introducidos
				Personal nuevaPersona = new Personal(nombre.getText(), telefono.getText(), direccion.getText(), observaciones.getText());
				
				//La añadimos a la lista
				listaPersonal.add(nuevaPersona);
				
				Personal.setPersonal(listaPersonal);
				
				//Reseteamos el último cuadrante modificado
				Cuadrante.setUltimoMesModificado(mes,anyo);
						
				cambioLamina(0);
			
			}
			
			
		}
		
		

		/**
		 * EVENTOS LÁMINA PERSONAL
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub	
			String comando = e.getActionCommand();
			
			switch (comando) {
								
			case "Cancelar Personal":
				cambioLamina(0);
				break;
				
			case "Guardar Personal":
				recogerDatosPersonal();
				break;
						
			default:
				break;
				
			}
		}
		
		
	}


	/**
	 * Esta lámina se cargará al seleccionar "modificar turnos" en el menú
	 */
	private class LaminaTurnos extends JPanel implements ActionListener{ 

		private static final long serialVersionUID = 1L;
		
		private JTextField textoTurnos;
		private Box cajaFondo,cajaBotones;
		private JButton botonEliminar,botonAnyadir,botonCancelar;
		private JLabel etiquetaTurnos, etiquetaTitulo;
		private List<String> listaTurnos;
		
		public LaminaTurnos(){
			
			//Cargamos los turnos actuales
			listaTurnos = new ArrayList<>();
			listaTurnos = Turnos.getTurnos();
			
			//Cajas contenedoras
			cajaFondo = Box.createVerticalBox();
			cajaBotones = Box.createHorizontalBox();
			
			add(cajaFondo,BorderLayout.CENTER);
			
			//Botones
			botonAnyadir = new JButton("Añadir");
			botonEliminar = new JButton("Eliminar");
			botonCancelar = new JButton("Cancelar");
			cajaBotones.add(botonCancelar);
			cajaBotones.add(Box.createRigidArea(new Dimension(100,0)));
			cajaBotones.add(botonAnyadir);
			cajaBotones.add(Box.createRigidArea(new Dimension(100,0)));
			cajaBotones.add(botonEliminar);
			
			//Etiquetas
			etiquetaTitulo = new JLabel("Nombre: ");
			etiquetaTurnos = new JLabel("Dirección: ");
			
			//TextField
			textoTurnos = new JTextField(20);
			
			//Etiqueas
			etiquetaTitulo = new JLabel("TURNOS EXISTENTES:");
			etiquetaTitulo.setAlignmentX(MarcoPrincipal.CENTER_ALIGNMENT);
			etiquetaTurnos = new JLabel(pintarTurnos());
			etiquetaTurnos.setAlignmentX(MarcoPrincipal.CENTER_ALIGNMENT);
			
			//Añadimos espacios verticales entre las cajas
			cajaFondo.add(Box.createVerticalStrut(150));
			cajaFondo.add(etiquetaTitulo);
			cajaFondo.add(Box.createVerticalStrut(50));
			cajaFondo.add(etiquetaTurnos);
			cajaFondo.add(Box.createVerticalStrut(50));
			cajaFondo.add(textoTurnos);
			cajaFondo.add(Box.createVerticalStrut(50));
			cajaFondo.add(cajaBotones);		
				
			//Eventos
			botonCancelar.setActionCommand("Cancelar Turnos");
			botonCancelar.addActionListener(this);
			
			botonEliminar.setActionCommand("Eliminar Turno");
			botonEliminar.addActionListener(this);
			
			botonAnyadir.setActionCommand("Añadir Turno");
			botonAnyadir.addActionListener(this);
			
			
		}
		
		//Construlle un string a partir de los elementos de la lista
		public String pintarTurnos(){
			
			StringBuilder sb = new StringBuilder();
			
			for (String turno:listaTurnos){
				
				sb.append(turno+"  ");
				
			}
			
			return sb.toString();
			
		}
				
			
			
			/**
			 * EVENTOS DE LA LÁMINA TURNOS
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String comando = e.getActionCommand();
				
				switch (comando) {
									
				case "Cancelar Turnos":
					cambioLamina(0);
					break;
					
				case "Añadir Turno":		
					listaTurnos.add(textoTurnos.getText());
					Turnos.setTurnos(listaTurnos);
					cambioLamina(0);
					break;
					
				case "Eliminar Turno":
					
					String nuevoTurno = textoTurnos.getText();
					
					if (listaTurnos.contains(nuevoTurno)){
						
						listaTurnos.remove(nuevoTurno);
						Turnos.setTurnos(listaTurnos);
						cambioLamina(0);
					}
					break;
							
				default:
					break;
					
				}
			}
	
	
   }
}



















































