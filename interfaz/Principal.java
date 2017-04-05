package interfaz;

//Importamos las clases para usar sus getters/setters
import cuadrante.Cuadrante;
import cuadrante.Personal;
import cuadrante.Turnos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.OutputDeviceAssigned;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
	private Map<String,Float> mapaHoraTurno;
	private Map<String,String> mapaEquivaleTurno;
	
	
	public static int width=1280,height=950;
	
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
			Cuadrante.getUltimoMesModificado();
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
	private List<Turnos> turnosCargados;
	private List<String> listaNombresTurnos;
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
	private JMenuItem añadirPersonal,modificarTurnos;

	
	private String[] sMeses = {"ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
	
	
	
	public LaminaPrincipal() throws IOException{
		
		setLayout(new BorderLayout());
		
		//Carga el Menú
		cargarMenu();
		
		//Carga lámina cuadrante y lámina contador con sus scrolls
		cargarLaminaPrincipal();
			
		//Hacemos un setter del número de semanas del mes actual
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
				menuPersonal.add(añadirPersonal);
	
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
				//Botón Guardar
				botonGuardar = new JButton("EXPORTAR");
				botonGuardar.setActionCommand("Exportar Excel");
				botonGuardar.addActionListener(this);
				
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
							
					//Eventos click en tabla POPUP MENU
					JPopupMenu MenuPopupEliminar = new JPopupMenu();
					JMenuItem eliminarPopup = new JMenuItem("Eliminar persona");
					eliminarPopup.setActionCommand("Eliminar Persona");
					eliminarPopup.addActionListener(this);
					MenuPopupEliminar.add(eliminarPopup);
					tablaCuadrante.setComponentPopupMenu(MenuPopupEliminar);
					
					
					cajaCuadrante.add(tablaCuadrante);
					
					/**
					 * 
					 * ***********************************************
					 * pendiente MODIFICAR COMBO EN FUNCIÓN DEL TURNO ANTERIOR**
					 * ***********************************************
					 * 
					 * 
					 * 
					 */
				
						//Hacemos ComboBox en los elementos de la tabla para poner los turnos
						//comboTurnos.setEditable(true); //Si queremos que se pueda escribir en la casilla
						
					
						comboTurnos = cargarTurnos();
						comboTurnos.setActionCommand("Seleccion Turno");
						comboTurnos.addActionListener(this);
						editorCombo = new DefaultCellEditor(comboTurnos);
						
						
						//editorCombo = new DefaultCellEditor(cargarTurnos());
		
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
					actualizarContador();	

				
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
	 * Cargamos los turnos. Cargamos el mapa de horas para el contador de la tabla cuadrante.
	 * Cargamos el mapa de equivalencias de turnos para el contador  
	 */
	private JComboBox<String> cargarTurnos() throws IOException{
		
		String nombre,equivale;
		float horas;
		
		turnosCargados = new ArrayList<>();
		listaNombresTurnos = new ArrayList<>();
		mapaHoraTurno = new HashMap<String, Float>();
		mapaEquivaleTurno = new HashMap<String,String>();
		
		//Cargamos lor turnos (nos da un List<Turnos>)
		turnosCargados = Turnos.getTurnos();
		
		for (int i=0; i<turnosCargados.size();i++){
			nombre = turnosCargados.get(i).getNombre();
			horas = turnosCargados.get(i).getHoras();
			equivale = turnosCargados.get(i).getEquivale();
			
			listaNombresTurnos.add(nombre);
			mapaHoraTurno.put(nombre, horas);
			mapaEquivaleTurno.put(nombre, equivale);
		}
		
		//Los pasamos a arrays
		turnosArray = new String [listaNombresTurnos.size()];
		turnosArray = listaNombresTurnos.toArray(turnosArray);
		comboTurnos = new JComboBox<>(turnosArray);
		
		return comboTurnos;
		
	}
	
	
	
	/**
	 * Acualizar horas. Sumamos las horas por efectivo
	 */
	private void actualizarHoras(){
		float contar = 0;

		for (int row=0; row<tablaCuadrante.getModel().getRowCount();row++){
			contar=0;
			for (int column=2; column<tablaCuadrante.getColumnCount();column++){		
				String valorCelda = (String) tablaCuadrante.getModel().getValueAt(row, column);
				
				if (valorCelda!=null && mapaHoraTurno.containsKey(valorCelda)){
					contar += mapaHoraTurno.get(valorCelda);				
				}
			}
			tablaCuadrante.getModel().setValueAt(String.valueOf(contar), row, 0);	
		}	
	}
	
	
	
	/**
	 * Actualizar contador. Sumamos todos los turnos
	 */
	
	private void actualizarContador(){
		
		int contarM = 0, contarT=0, contarN=0;
		
		for (int column=2; column<tablaCuadrante.getModel().getColumnCount();column++){
			contarM=0;
			contarT=0;
			contarN=0;
			for(int row=0;row<tablaCuadrante.getModel().getRowCount();row++){
		
				String valorCelda = (String) tablaCuadrante.getModel().getValueAt(row, column);
				
			
				if (valorCelda!=null && mapaEquivaleTurno.containsKey(valorCelda)){
					String valorEquivale = mapaEquivaleTurno.get(valorCelda);
					
					switch (valorEquivale) {	
					case "M":
						contarM++;
						break;
						
					case "T":
						contarT++;
						break;
						
					case "N":
						contarN++;
						break;
						
					case "X":
						contarM++;
						contarN++;
	
					default:
						break;
					}
				
				}	
			}
						
			//M
			tablaContador.getModel().setValueAt(String.valueOf(contarM), 0, column-1);
			
			//T
			tablaContador.getModel().setValueAt(String.valueOf(contarT), 1, column-1);
			
			//N
			tablaContador.getModel().setValueAt(String.valueOf(contarN), 2, column-1);
				
			}
		
		
		/*
		
		for (int column=2; column<tablaCuadrante.getModel().getColumnCount();column++){
			contarM=0;
			contarT=0;
			contarN=0;
			for(int row=0;row<tablaCuadrante.getModel().getRowCount();row++){
		
				String valorCelda = (String) tablaCuadrante.getModel().getValueAt(row, column);
			
			
				if (valorCelda!=null){			
					switch (valorCelda) {	
					case "M":
						contarM++;
						break;
						
					case "T":
						contarT++;
						break;
						
					case "N":
						contarN++;
						break;
						
					case "X":
						contarM++;
						contarN++;
	
					default:
						break;
					}
				
				}	
			}
						
			//M
			tablaContador.getModel().setValueAt(String.valueOf(contarM), 0, column-1);
			
			//T
			tablaContador.getModel().setValueAt(String.valueOf(contarT), 1, column-1);
			
			//N
			tablaContador.getModel().setValueAt(String.valueOf(contarN), 2, column-1);
				
			}
		
		*/
		
		}
		
	
	
	
	/**
	 * Guardar Datos del Cuadrante
	 */
	private void guardarCuadrante(){
		
		ArrayList<String[]> cuadranteGuardado = new ArrayList<>();
		String[] filaGuardada = new String[cabeceraCargada.length];
		String valorCelda;
		
		
		for(int row=0;row<tablaCuadrante.getModel().getRowCount();row++){
			for (int column=0; column<tablaCuadrante.getModel().getColumnCount();column++){	
				valorCelda = (String) tablaCuadrante.getModel().getValueAt(row, column);
				filaGuardada[column] = valorCelda;
						 
			}		
			cuadranteGuardado.add(filaGuardada);
			filaGuardada = new String[cabeceraCargada.length];		
			
		}		
		Cuadrante.setCuadrante(cuadranteGuardado, mes,anyo);
	}
	
	
	/**
	 * Eliminar persona. Devuelve lista con el Personal actualizado
	 */
	public void eliminarPersona(){
		 
		//Cargamos la lista actual de personas	
		String personaSeleccionada = (String) tablaCuadrante.getValueAt(tablaCuadrante.getSelectedRow(), 1);
		List<Personal> listaPersonalE = null;
		
		//Mensaje confirmación
		if(JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere eliminar a "+personaSeleccionada+"?","Eliminar persona del cuadrante",2,JOptionPane.WARNING_MESSAGE)==JOptionPane.YES_OPTION){
				
			listaPersonalE = new ArrayList<>();
			
			//Cargamos el archivo
			listaPersonalE = Personal.getPersonal();
		
			//Utilizamos un iterador para poder modificar la lista durante el loop (con un bucle for each salta excepción java.util.ConcurrentModificationException)
			Iterator<Personal> it = listaPersonalE.iterator();
			
			while(it.hasNext()){	
				if(it.next().getNombre().equals(personaSeleccionada)){
					Cuadrante.setUltimoMesModificado(mes,anyo);
					it.remove();
					//Actualizamos la lista de personal
					Personal.setPersonal(listaPersonalE);
				}
			}	
		}		
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
			if (mes!=sMes.getSelectedIndex()){
				mes = sMes.getSelectedIndex();
				cambioLamina(0);
			}
			break;
			
		case "Cambio Anyo":
			//Cogemos el texto (año) del Combobox y lo ponemos como int en el anyo
			anyoSeleccionado = sAnyo.getSelectedIndex();
			anyo = Integer.parseInt(sAnyos[sAnyo.getSelectedIndex()]);
			
			cambioLamina(0);
			break;
		
		case "Seleccion Turno":
			//Reiniciamos si selecciona algún turno
			if (comboTurnos.getSelectedItem()!=null){
				actualizarHoras();
				guardarCuadrante();
				actualizarContador();				
			}
			break;
			
		case "Exportar Excel":
			Cuadrante.toExcel(cuadranteCargado, cabeceraCargada, mes, anyo);
			break;
			
		case "Eliminar Persona":
			//Por alguna razón al abrir el JComboBox de cualquier turno se ejecuta el evento también, para evitarlo le pedimos que sea de la clase JMenuItem
			if (arg0.getSource().getClass().getSimpleName().equalsIgnoreCase("JMenuItem")){	
				eliminarPersona();
				cambioLamina(0);
			}		
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
		
		private JTextField textoTurnos, textoHoras;
		private JCheckBox checkAuto;
		private JComboBox<String> comboTurnos;
		private Box cajaFondo,cajaBotones, cajaOpciones,cajaTextoTurno;
		private JButton botonEliminar,botonAnyadir,botonCancelar;
		private JLabel etiquetaTurnos, etiquetaTitulo, etiquetaEquivale, etiquetaNumHoras;
		private List<String> listaNombreTurnos;
		private List<Turnos> listaTurnos;
 		private String[] turnosArray;
		
		public LaminaTurnos(){
			
			//Cargamos los turnos M,T,N para el contador
			turnosArray = new String[5];
			turnosArray[0]=" ";
			turnosArray[1]="M";
			turnosArray[2]="T";
			turnosArray[3]="N";
			turnosArray[4]="X";
			
			//Cargamos los turnos actuales
			listaTurnos = new ArrayList<>();
			listaTurnos = Turnos.getTurnos();
			
			listaNombreTurnos = new ArrayList<>();
			
			for (int i=0; i<listaTurnos.size();i++){
				
				listaNombreTurnos.add(listaTurnos.get(i).getNombre());

			}
			
			//Cajas contenedoras
			cajaFondo = Box.createVerticalBox();
			cajaOpciones = Box.createHorizontalBox();
			cajaTextoTurno = Box.createHorizontalBox();
			cajaBotones = Box.createHorizontalBox();
			
			add(cajaFondo,BorderLayout.CENTER);
			
			//Caja Texto Turno
			textoTurnos = new JTextField(10);
			
			cajaTextoTurno.add(new JLabel("Introduce un turno a editar"));
			cajaTextoTurno.add(Box.createHorizontalStrut(20));
			cajaTextoTurno.add(textoTurnos);
			cajaTextoTurno.add(Box.createHorizontalStrut(180));
			
			//Caja Botones
			botonAnyadir = new JButton("Añadir");
			botonEliminar = new JButton("Eliminar");
			botonCancelar = new JButton("Cancelar");
			cajaBotones.add(botonCancelar);
			cajaBotones.add(Box.createRigidArea(new Dimension(100,0)));
			cajaBotones.add(botonEliminar);
			cajaBotones.add(Box.createRigidArea(new Dimension(100,0)));
			cajaBotones.add(botonAnyadir);
			
			//Caja Opciones
			comboTurnos = new JComboBox<>(turnosArray);	
			textoHoras = new JTextField();
			textoHoras.setPreferredSize(new Dimension(20, 24));
			etiquetaEquivale = new JLabel("Equivale a: ");
			etiquetaNumHoras = new JLabel("Número de horas: ");
			checkAuto = new JCheckBox("AUTO");
			
			cajaOpciones.add(etiquetaEquivale);
			cajaOpciones.add(comboTurnos);
			cajaOpciones.add(Box.createHorizontalStrut(80));
			cajaOpciones.add(etiquetaNumHoras);
			cajaOpciones.add(textoHoras);
			cajaOpciones.add(Box.createHorizontalStrut(80));
			cajaOpciones.add(checkAuto);
			
			
			//Etiqueas
			etiquetaTitulo = new JLabel("TURNOS EXISTENTES:");
			etiquetaTitulo.setAlignmentX(MarcoPrincipal.CENTER_ALIGNMENT);
			etiquetaTurnos = new JLabel(pintarTurnos());
			etiquetaTurnos.setAlignmentX(MarcoPrincipal.CENTER_ALIGNMENT);
			
			//Caja Fondo
			cajaFondo.add(Box.createVerticalStrut(150));
			cajaFondo.add(etiquetaTitulo);
			cajaFondo.add(Box.createVerticalStrut(50));
			cajaFondo.add(etiquetaTurnos);
			cajaFondo.add(Box.createVerticalStrut(50));
			cajaFondo.add(cajaTextoTurno);
			cajaFondo.add(Box.createVerticalStrut(50));
			cajaFondo.add(cajaOpciones);
			cajaFondo.add(Box.createVerticalStrut(200));
			cajaFondo.add(cajaBotones);		
				
			//Eventos
			botonCancelar.setActionCommand("Cancelar Turnos");
			botonCancelar.addActionListener(this);
			
			botonEliminar.setActionCommand("Eliminar Turno");
			botonEliminar.addActionListener(this);
			
			botonAnyadir.setActionCommand("Añadir Turno");
			botonAnyadir.addActionListener(this);
			
			
		}
		
		//Construye un string a partir de los elementos de la lista
		public String pintarTurnos(){
			
			StringBuilder sb = new StringBuilder();
			
			for (String turno:listaNombreTurnos){
				
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
					float horas = 0;
					boolean inputNum = true;
					
					if (textoTurnos.getText().length()>0){
						
						//Si no se introduce un número, saltará mensaje
						try{
							horas = Float.parseFloat(textoHoras.getText());
							
						}catch (NumberFormatException n){
							inputNum = false;
						}
						
						if (inputNum){
							listaTurnos.add(new Turnos(textoTurnos.getText(),checkAuto.isSelected(), horas,(String) comboTurnos.getSelectedItem()));
							
							Turnos.setTurnos(listaTurnos);
							cambioLamina(0);
							break;
							
						}else{
							JOptionPane.showMessageDialog(this,"Debe introducir un número válido de horas");
						}
											
					}else{	
						JOptionPane.showMessageDialog(this,"Debe introducir un turno a editar");		
					}
					

					
				case "Eliminar Turno":
					
					String nuevoTurno = textoTurnos.getText();
					
					//Para eliminar el elemento en el bucle necesitamos un iterador:
					Iterator<Turnos> it = listaTurnos.iterator();
					
					while (it.hasNext()){
						if (it.next().getNombre().equals(nuevoTurno)){
							it.remove();
							Turnos.setTurnos(listaTurnos);
							cambioLamina(0);
						}	
					}

					break;
							
					
				default:
					break;
					
				}
			}
	
	
   }
}




















































