package estadistica;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cuadrante.Cuadrante;
import cuadrante.Turnos;
import interfaz.Principal;

/**
 * Clase para crear funciones que permitan análisis de turnos y generación de cuadrante en función de la estadística de cada componente
 */


public class Auto {
	
	public static void main(String[] args) {
		
		comprobarCondiciones();
		
	}

	/**
	 * AUTO
	 */
	public static void auto(){
		
		comprobarCondiciones();
		turnosCandidatos();
	}
	
	/**
	 * Comprobamos las condiciones del contador y devolvemos lista con vectores de los puntos donde no se cumplen
	 * COMPROBACIÓN VERTICAL
	 */
	public static void comprobarCondiciones(){
		String[][] condicionesCargadas = Cuadrante.getCondiciones(Cuadrante.mes, Cuadrante.anyo);
		String[][] contadorCargado = Cuadrante.getContador(Cuadrante.mes, Cuadrante.anyo);
		ArrayList<int[]> condicionesFalsas = new ArrayList<>();
		boolean bool = true;
		
		try{
		for (int j=1; j<condicionesCargadas[0].length;j++){
			for (int i=0; i<condicionesCargadas.length; i++){
				bool = true;
				String s = condicionesCargadas[i][j];
				if (s!=null){
					
					if (s.startsWith("-")){
						int[] condFalsa = new int[2]; 
						bool = Integer.parseInt(contadorCargado[i][j]) < Integer.parseInt(s.replace("-", ""));
						//Almacenamos fila y columna de la condición falsa
						if (!bool){
							condFalsa[0] = i;
							condFalsa[1] = j;
							condicionesFalsas.add(condFalsa);
						}
						
						
					}if (s.startsWith("+")){
						int[] condFalsa = new int[2]; 
						bool = Integer.parseInt(contadorCargado[i][j]) < Integer.parseInt(s.replace("+", ""));
						if (!bool){
							condFalsa[0] = i;
							condFalsa[1] = j;
							condicionesFalsas.add(condFalsa);
						}
						
					}if (s.contains("-")){
						String[] intervalo = s.split("-");
						int[] condFalsa = new int[2];
						int min= Integer.parseInt(intervalo[0]);
						int max= Integer.parseInt(intervalo[1]);
						int num = Integer.parseInt(contadorCargado[i][j]);
						//Comprobamos si no está entre mínimo y máximo de las condiciones
						if (min > num || num > max){
							condFalsa[0] = i;
							condFalsa[1] = j;
							condicionesFalsas.add(condFalsa);
						}
						
						
					}else{
						
						try{
							int[] condFalsa = new int[2];
							
							bool = (Integer.parseInt(s) == Integer.parseInt(contadorCargado[i][j]));
							if (!bool){
								condFalsa[0] = i;
								condFalsa[1] = j;
								condicionesFalsas.add(condFalsa);
							}					
							
						}catch(NumberFormatException e){
							
						}
					}
				}
			}
		}
		
		}catch(NumberFormatException e){
			
		}
	}
	
	
	
	/**
	 *  Almacenamos los candidatos cargando los turnos "AUTO" que equivalgan a M,T,N,X
	 */
	public static void turnosCandidatos(){
		
		ArrayList<String> turnosAuto = new ArrayList<>();
		ArrayList<String> turnosCandidatos = new ArrayList<>();
		List<Turnos> turnosCargados = new ArrayList<>();
		
		turnosCargados = Turnos.getTurnos();
		
		for (Turnos turno: turnosCargados){
			if (turno.getAuto() && (turno.getEquivale().length()>0)){
				turnosAuto.add(turno.getNombre());
			}		
		}
		
		// 
		for (int[] cond:condicionesFalsas){
			
			System.out.println(Arrays.toString(cond));
		}
		
	}

		
		
}
	
	















































