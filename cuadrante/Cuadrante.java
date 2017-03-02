package cuadrante;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Cuadrante {

		
		public static int numSemanas,mes,anyo,ultimoAnyoModificado,ultimoMesModificado;
		private static String rutaArchivoCuadrante = System.getProperty("user.home")+"/sabir/data/cuadrantes/";
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			
		
		}

		/**
		 * SETTER ULTIMO MES MODIFICADO
		 */
		//Este setter actualizará el último mes y año que ha modificado su personal, para tener siempre acutalizado desde el mes actual
		
		public static void setUltimoMesModificado(int ultimoMesModif){
			
			ultimoMesModificado = ultimoMesModif; 
			
		}
		
		public static void setUltimoAnyoMoficado(int ultimoAnyoModif){
			
			ultimoAnyoModificado = ultimoAnyoModif;
			
		}
		
		
		/**
		 * GETTER CABECERA
		 */
		
		public static String[] getCabecera(int esteMes, int esteAnyo){
			
			mes = esteMes;
			anyo = esteAnyo;
			
			int ultimoDiaMesAnterior,diaSemana,ultimoDiaMes,primerDiaMes,posUltimoDiaMesAnterior;
			
			boolean pertenece = false;
			
			Calendar calendario = new GregorianCalendar(anyo, mes, 1);
			Calendar calendarioAnterior = new GregorianCalendar(anyo,mes-1,1);
			
			//Número de días del mes
			ultimoDiaMes = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			//Número de días del mes anterior
			ultimoDiaMesAnterior = calendarioAnterior.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			//Día de la semana (restamos uno porque el 1 cuenta como domingo, 2 como lunes...
			diaSemana = calendario.get(Calendar.DAY_OF_WEEK)-1;

				
			/**
			 * Miramos número de semanas
			 */
			
			//Comprobamos si el día 1 debe pertenecer a este mes
			if (diaSemana >0 && diaSemana <5){
				
				pertenece=true;
				
			}else{
				
				pertenece=false;
			}
			
			//Comprobamos el día 1 de este mes y del siguiente
			if (pertenece && !perteneceMes2()){
			
				numSemanas = 5;
				
			}else{
				
				numSemanas = 4;

			}
			
			
			/**
			 * Construímos la cabecera
			 */
			int longitudArray = 2+7*numSemanas;
			
			String[] cabecera = new String[longitudArray];
			
			cabecera[0] = "Horas";
			cabecera[1] = "Nombre";
			
			
			/**
			 * Si el día 1 pertenece al mes
			 */
			if (pertenece){
				
				//Si día 1 cae en lunes
				if (diaSemana==1){
					
					primerDiaMes = diaSemana;
					
					for (int i=2;i<longitudArray;i++){
						
						cabecera[i] =  Integer.toString(primerDiaMes);
						primerDiaMes++;
						
					}
					
					
				//Si día 1 no cae en lunes	
				}else{
					int j =1;
					primerDiaMes = ultimoDiaMesAnterior - diaSemana + 2;
					posUltimoDiaMesAnterior = 3 + ultimoDiaMesAnterior - primerDiaMes;
					
					//Colocamos los días del mes anterior
					for (int i = 2; i<posUltimoDiaMesAnterior; i++){
						
						cabecera[i] = Integer.toString(primerDiaMes);
						primerDiaMes++;
						
					}
					
					//Colocamos los días del mes actual
					for (int i = posUltimoDiaMesAnterior; i<longitudArray; i++){
						
						//Comprobamos que no se salga del mes, y que empiece con el día 1 del mes siguiente
						if(j>ultimoDiaMes){
							
							j=1;
							
						}
						
						cabecera[i] = Integer.toString(j);
						j++;
								
					}
					
					return cabecera;
				}
				

			/**
			 * Si el día 1 no pertenece al mes	
			 */
			}if (!pertenece){
				
				
				//1 cae en domingo
				if (diaSemana == 0){
					
					primerDiaMes = 2;
					
				}else{
				
					primerDiaMes = 9 - diaSemana;
					
				}	
				
				
				//Colocamos los días del mes actual
				for (int i = 2; i<longitudArray; i++){
					
					//Comprobamos que no se salga del mes, y que empiece con el día 1 del mes siguiente
					if(primerDiaMes>ultimoDiaMes){
						
						primerDiaMes=1;
						
					}
					
					cabecera[i] =  Integer.toString(primerDiaMes);
					primerDiaMes++;
							
				}		
			}
			return cabecera;
		
		}
		
		
		/**
		 * Comprobamos si el día 1 del mes siguiente, pertenece a este mes o no
		 */
		
		public static boolean perteneceMes2(){
			
			boolean perteneceMes2 = false;
			
			Calendar calendario = new GregorianCalendar(anyo, mes+1, 1);
			
			//Día de la semana
				int diaSemana = calendario.get(Calendar.DAY_OF_WEEK)-1;
					
				if (diaSemana >0 && diaSemana <5){
						
					perteneceMes2=true;
						
				}else{
						
					perteneceMes2=false;
				}
				
			return perteneceMes2;
		}
		
		
		

	/**
	 * GETTER CUADRANTE
	 */

		public static ArrayList<String[]> getCuadrante(int mes, int anyo){
			
			String [] arrayTemp;
			ArrayList<String[]> cuadrante = new ArrayList<>();
			List<Personal> listaPersonal = new ArrayList<>();
			int longitudArray = 2+7*numSemanas;
			
			try{	
				//Comprobamos si el fichero existe, y creamos los directorios si no existen
				File f = new File (rutaArchivoCuadrante+mes+anyo+".data");
				f.getParentFile().mkdirs();
				
				/**
				 * Lo creamos con los nombres en la primera columna
				 * Se crean tantos arrays como nombres hay en listaPersonal
				 * Se añaden los arrays al ArrayList del cuadrante
				 */
				
				//Cargamos el personal actual
				listaPersonal = Personal.getPersonal();
				/*
					if (!f.exists()){
					
						//creamos los arrays y los añadimos al cuadrante
						for (int i=0; i<listaPersonal.size();i++){
							
							arrayTemp = new String[longitudArray];
							arrayTemp[1] = listaPersonal.get(i).getNombre();
							cuadrante.add(arrayTemp);		
						}
						
						setCuadrante(cuadrante,mes,anyo);
					}
					
				*/
					//Comprobamos si ha podido haber alguna actualización del personal para generar el cuadrante nuevo, cogiendo el 
					//último mes modificado
					if ((!f.exists()) || (mes >= ultimoMesModificado) && (anyo>= ultimoAnyoModificado)){				
					
						//creamos los arrays y los añadimos al cuadrante
						for (int i=0; i<listaPersonal.size();i++){
							
							arrayTemp = new String[longitudArray];
							arrayTemp[1] = listaPersonal.get(i).getNombre();
							cuadrante.add(arrayTemp);		
						}
						
						setCuadrante(cuadrante,mes,anyo);
					}
					
					
					
				//Importamos el objeto serializado
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
					
				//Leemos y almacenamos la lista
				cuadrante = (ArrayList<String[]>) ois.readObject();
				ois.close();
				fis.close();	
				
			}catch (ClassNotFoundException c) {
				System.out.println("clase no encontrada");
				
			}catch (IOException e) {
				System.out.println("No se ha encontrado el archivo");
				
			}
							
			
		return cuadrante;
			
			
		}
		
		
		
		
		
	/**
	 * SETTER CUADRANTE
	 */
		
		public static void setCuadrante(ArrayList<String[]> cuadrante, int mes, int anyo){
			
			try{
				
				FileOutputStream fos = new FileOutputStream(new File(rutaArchivoCuadrante+mes+anyo+".data"));
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				
				//Escribimos el objeto en el archivo
				oos.writeObject(cuadrante);
				
				//Cerramos
				oos.close();
				fos.close();
				
			}catch (IOException e) {
				System.out.println("No se ha encontrado el archivo");
			}
			
		}

		
		
		
		/**
		 * GETTER CABECERA CONTADOR
		 */
		
		public static String[] getCabeceraContador(int mes, int anyo){
			
			String[] cabeceraCuadrante = getCabecera(mes, anyo);
			String[] cabeceraContador = new String[cabeceraCuadrante.length-1];
			
			cabeceraContador[0] = "Turno";
			
			//Pasamos los datos de la cabecera cuadrante a la cabecera contador
			for (int i=1; i<cabeceraContador.length; i++){
				
				cabeceraContador[i] = cabeceraCuadrante[i+1];
				
			}
			return cabeceraContador;
		}
		
		
		/**
		 * GETTER CONTADOR
		 */
		
		public static String[][] getContador(){
			
			String [][] contador = new String[3][1+7*numSemanas];
			
			contador[0][0] = "MAÑANA";
			contador[1][0] = "TARDE";
			contador[2][0] = "NOCHE";
			
			
			return contador;
			
		}
		
		
		
}

























































