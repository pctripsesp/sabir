package interfaz;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import cuadrante.Turnos;

/**
 *Con esta clase se sobreescribe la clase renderer por defecto de las celdas de JTable, y le damos color 
 *En el constructor podemos implementar otras posibilidades, como centrar los elementos de las celdas;
 *lo hacemos después de llamar a super();
 */
class ColorRenderer extends DefaultTableCellRenderer{
		
		//Almacenamos los días que son fin de semana en una lista para colorearlos distinto
		private static final Set<Integer> finesSemana = new HashSet<Integer>(Arrays.asList(7,8,14,15,21,22,28,29,35,36));
		
		public ColorRenderer(){
			
			super();
			setHorizontalAlignment(SwingConstants.CENTER);
			
		}
	
		private Component c;
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
			
			c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
			//Aquí ponemos las condiciones para que cambien las celdas de color.
				
				c.setBackground(new Color(250,213,161));
				
			//Fines de semana
			if (finesSemana.contains(column)){
				
				c.setBackground(new Color(222,180,119));
			}
	
			
			/**
			 * 
			 */
			/*
			//Cargamos lor turnos (nos da un List<String>)
			List<String> turnosCargados = Turnos.getTurnos();
			
			//Los pasamos a arrays
			String[] turnosArray = new String [turnosCargados.size()];
			turnosArray = turnosCargados.toArray(turnosArray);
			JComboBox<String>comboTurnos = new JComboBox<>(turnosArray);
			*/
			
			/**
			 * 
			 * 
			 */
			
			return c;
			
		}
				

	}




class ComboBoxTabla extends DefaultTableCellRenderer{
	
	
	public ComboBoxTabla(){
		
		super();
		
	}

	private Component c;
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
		
		c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		String t = table.getModel().getValueAt(row, column).toString();
		//String s = table.getModel().getValueAt(row, column).toString();
		
		if (t == "N"){
			
			c.setBackground(Color.black);
			
		}
		
		if (value == "M"){
			
			c.setBackground(Color.YELLOW);
			
		}
		
			
		//Fines de semana
			/*
		if (finesSemana.contains(column)){
			
			c.setBackground(new Color(222,180,119));
		}
	*/
		
		return c;
		
	}
			

}











































