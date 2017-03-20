package cuadrante;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Aquí se cargan los turnos posibles, que irán en la tabla cuadrante en comboTurnos
 */
public class Turnos implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static String rutaArchivoTurnos = System.getProperty("user.home")+"/sabir/data/turnos/";
	private String nombreTurno, equivaleTurno;
	private float numHoras;
	private boolean auto;
	
	public Turnos(String nombreTurno, boolean auto, float numHoras, String equivaleTurno){
		
		this.nombreTurno = nombreTurno;
		this.auto = auto;
		this.numHoras = numHoras;
		this.equivaleTurno = equivaleTurno;
		
	}
	
	
	//SETTER turnos; la existencia del archivo se comprueba en el getter
	public static void setTurnos(List<Turnos> listaTurnos){
		
		try{

			FileOutputStream fos = new FileOutputStream(new File(rutaArchivoTurnos+"turnos.data"));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			//Escribimos el objeto en el archivo
			oos.writeObject(listaTurnos);
			
			//Cerramos 
			oos.close();
			fos.close();
					
		}catch (IOException e) {
			System.out.println("No se ha encontrado el archivo SET TURNOS");
			e.printStackTrace();
		}	
		
	}
	
	
	
	
	
	//GETTER TURNOS
	public static List<Turnos> getTurnos(){
		
		List<Turnos> listaTurnos = new ArrayList<>();
			
		try{
				
			//Comprobamos si el fichero existe y creamos sus directorios si no existen
			File f = new File(rutaArchivoTurnos+"turnos.data");
			f.getParentFile().mkdirs();
			
				//Si el fichero no existe, lo creamos con una lista con turnos por defecto
				if (!f.exists()){	
					listaTurnos.add(new Turnos("M",true,8,"M"));
					listaTurnos.add(new Turnos("T",true,8,"T"));
					listaTurnos.add(new Turnos("N",true,8,"N"));
					listaTurnos.add(new Turnos("X",true,16,"X"));
				    listaTurnos.add(new Turnos("S",true,0,""));
					listaTurnos.add(new Turnos(" ",false,0,""));
				    listaTurnos.add(new Turnos("DS",true,0,""));
					
					setTurnos(listaTurnos);
				}
			
			//Importamos el objeto serializado
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			//Leemos y almacenamos la lista
			listaTurnos = (List<Turnos>) ois.readObject();
			ois.close();
			fis.close();
			
		
		}catch (ClassNotFoundException c) {
			System.out.println("clase no encontrada GET TURNOS");
			c.printStackTrace();
		}catch (IOException e) {
			System.out.println("No se ha encontrado el archivo GET TURNOS");
			e.printStackTrace();
		}
			
		return listaTurnos;
	
	
	}
	
	
	
	
	/**
	 * GETTERS
	 */
		
	public String getNombre(){
		
		return nombreTurno;
		
	}
	
	public float getHoras(){
		
		return numHoras;
		
	}
	
	public String getEquivale(){
		
		return equivaleTurno;
		
	}

	public boolean getAuto(){
	
	return auto;
	
	}
	
	
}
































