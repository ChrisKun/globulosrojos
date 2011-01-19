package recuperaciondeinformacion.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import jcolibri.cbrcore.CBRCase;

import recuperaciondeinformacion.utils.representation.NewsDescription;

public class ListaNoticias extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Ruta a la carpeta de im�genes para los n�meros.
	 * */
	private static final String IMG_PATH = "img" + File.separator;
	
	/**
	 * Constructora.
	 * @param news Lista de noticias a mostrar.
	 * */
	public ListaNoticias(ArrayList<CBRCase> cbrCases) {
		// Configuracion propia del frame
		this.setTitle("Noticias");
		
		// Creamos el panel principal y le asignamos un layout
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		/* El frame se compondr� �nicamente de botones para seleccionar
		 * una noticia y labels que contendr�n los iconos para los n�meros
		 */
		JLabel label;
		JButton boton;
		
		// Comprobaci�n para evitar excepciones
		if (cbrCases == null) return;
		
		/* Por cada noticia de la lista de noticias creamos su label (icono)
		 * y un bot�n con el t�tulo de la noticia para poder seleccionarla
		 * Se impone un l�mite m�ximo de 9 noticias para que funcione correctamente
		 * la numeraci�n en los iconos
		 */
		for (int i=0; i < cbrCases.size() && i < 9; i++) {
			NewsDescription news = (NewsDescription) cbrCases.get(i).getDescription();
			// Icono (posici�n en la lista)
			label = new JLabel("");
			label.setPreferredSize(new Dimension(50, 50));
			label.setIcon(new ImageIcon(IMG_PATH + (i + 1) + ".jpg"));
			c.gridx = 0;
			c.gridy = i;
			c.insets = new Insets(5, 10, 5, 10);
			panel.add(label, c);
			
			// Bot�n (T�tulo de la noticia)
			boton = new JButton(news.getTitle().toString());
			boton.setPreferredSize(new Dimension(300, 50));
			boton.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createLineBorder(Color.BLACK, 2),
							BorderFactory.createBevelBorder(BevelBorder.RAISED)));
			boton.setToolTipText(news.getTitle().toString());
			boton.addActionListener(new newsButtonActionListener(cbrCases.get(i)));
			c.gridx = 2;
			c.gridy = i;
			c.insets = new Insets(5, 0, 5, 10);
			panel.add(boton, c);
		}
		
		add(panel);
	}

}

/**
 * Implementacion del listener asociado a los botones del menu de seleccion.
 *  */
class newsButtonActionListener implements ActionListener {

	/**
	 * Noticia asociada al listener.
	 * */
	private CBRCase cbrCase;
	
	/**
	 * Constructora.
	 * @param news La noticia asociada a este listener.
	 * */
	public newsButtonActionListener(CBRCase cbrCase) {
		this.cbrCase = cbrCase;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		MenuNoticia mn = new MenuNoticia(cbrCase);
		mn.pack();
		mn.setVisible(true);
		//System.out.println("Noticia " + news.getId() + " seleccionada.");
	}
	
}