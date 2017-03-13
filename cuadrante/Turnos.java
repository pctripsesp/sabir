package cuadrante;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Aquí se cargan los turnos posibles, que irán en la tabla cuadrante en comboTurnos
 */
public class Turnos {
	
	private static String rutaArchivoTurnos = System.getProperty("user.home")+"/sabir/data/turnos/turnos.data";
	
	
	//SETTER turnos; la existencia del archivo se comprueba en el getter
	public static void setTurnos(List<String> listaTurnos){
		
		try{

			FileOutputStream fos = new FileOutputStream(new File(rutaArchivoTurnos));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			//Escribimos el objeto en el archivo
			oos.writeObject(listaTurnos);
			
			//Cerramos 
			oos.close();
			fos.close();
					
		}catch (IOException e) {
			System.out.println("No se ha encontrado el archivo");
		}
		
	}
	
	
	//GETTER TURNOS; Cargamos la lista con objetos tipo "TURNOS"
	public static List<String> getTurnos(){
		
		List<String> listaTurnos = new ArrayList<>();
			
		try{
				
			//Comprobamos si el fichero existe y creamos sus directorios si no existen
			File f = new File(rutaArchivoTurnos);
			f.getParentFile().mkdirs();
			
				//Si el fichero no existe, lo creamos con una lista con turnos por defecto
				if (!f.exists()){	
					listaTurnos.add("M");
					listaTurnos.add("T");
					listaTurnos.add("N");
					listaTurnos.add("S");
					listaTurnos.add(" ");
					
					setTurnos(listaTurnos);
				}
			
			//Importamos el objeto serializado
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			//Leemos y almacenamos la lista
			listaTurnos = (List<String>) ois.readObject();
			ois.close();
			fis.close();
			
			
		}catch (ClassNotFoundException c) {
			System.out.println("clase no encontrada");
			
		}catch (IOException e) {
			System.out.println("No se ha encontrado el archivo");
			
		}
			
		return listaTurnos;
	
	
	}
	
	

}
































