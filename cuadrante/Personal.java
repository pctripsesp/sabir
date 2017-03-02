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

public class Personal implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static String rutaArchivoPersonal = System.getProperty("user.home")+"/sabir/data/personal/personal.data";
	private String nombre,telefono,direccion,observaciones;

	

	//Mínimo debería introducir nombre*
	public Personal(String nombre, String telefono, String direccion, String observaciones){
		
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		this.observaciones = observaciones;
		
		
	}
	
	//SETTER para serializar el array de listas, y escribirlo en personal.data (Para evitar problemas se debe hacer la llamada al setter antes que al getter, 
	//para realizar la comprobación de la existencia del archivo
	public static void setPersonal(List<Personal> listaPersonal){
	
		try{

			FileOutputStream fos = new FileOutputStream(new File(rutaArchivoPersonal));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			//Escribimos el objeto en el archivo
			oos.writeObject(listaPersonal);
			
			//Cerramos 
			oos.close();
			fos.close();
			
			
		}catch (IOException e) {
			System.out.println("No se ha encontrado el archivo");
		}
		
	}
	
	
	//Cargamos la lista con objetos tipo "PERSONAL"
	public static List<Personal> getPersonal(){
		
		List<Personal> listaPersonal = new ArrayList<>();
			
		try{
				
			//Comprobamos si el fichero existe y creamos sus directorios si no existen
			File f = new File(rutaArchivoPersonal);
			f.getParentFile().mkdirs();
			
				//Si el fichero no existe, lo creamos con una lista null
				if (!f.exists()){	
					setPersonal(listaPersonal);
				}
			
			//Importamos el objeto serializado
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			//Leemos y almacenamos la lista
			listaPersonal = (List<Personal>) ois.readObject();
			ois.close();
			fis.close();
			
			
		}catch (ClassNotFoundException c) {
			System.out.println("clase no encontrada");
			
		}catch (IOException e) {
			System.out.println("No se ha encontrado el archivo");
			
		}
			
		return listaPersonal;
										
}
			
			
			
	
	
	/**
	 * GETTERS
	 */
		
	public String getNombre(){
		
		return nombre;
		
	}
	
	public String getTelefono(){
		
		return telefono;
		
	}
	
	public String getDireccion(){
		
		return direccion;
		
	}

	public String getObservaciones(){
	
	return observaciones;
	
}
	
	
	
	/**
	 * SETTERS
	 */
		/*
	public void setNombre(String nombre){
		
		this.nombre = nombre;
		
	}
	
	public void setTelefono(String telefono){
		
		this.telefono = telefono;
		
	}
	
	public void setDireccion(String direccion){
		
		this.direccion = direccion;
		
	}

	public void setObservaciones(String observaciones){
	
	this.observaciones = observaciones;
	
}
	*/
	
	
}
























































