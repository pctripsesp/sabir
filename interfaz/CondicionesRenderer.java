package interfaz;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;



public class CondicionesRenderer extends DefaultTableCellRenderer{

	public CondicionesRenderer(){
		// TODO Auto-generated constructor stub
			
		super();
		setHorizontalAlignment(SwingConstants.CENTER);
	}


	private Component c;
		
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
			
		c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		//Aquí ponemos las condiciones para que cambien las celdas de color.
				
		c.setBackground(new Color(84,255,50));
			
		return c;
			
	}
				

}



