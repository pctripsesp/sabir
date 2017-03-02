package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 * Esta lámina se cargará al seleccionar "personal" en el menú
 */

public class LaminaPersonal extends JPanel{

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
		botonGuardar = new JButton("Guardar");
		
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
		
	
		
		
		
	}
	
}

























































