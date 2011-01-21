package recuperaciondeinformacion.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import jcolibri.cbrcore.CBRCase;

import recuperaciondeinformacion.utils.representation.NewsDescription;
import recuperaciondeinformacion.utils.representation.NewsSolution;

public class MenuNoticia extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public MenuNoticia(CBRCase cbrCase) {
		// Configuraci�n propia del frame
		this.setTitle("Noticia");
		
		// Creamos el panel principal y le asignamos un layout
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		/* El frame se compondr� de jlabels para mostrar:
		 * 	- T�tulo
		 * 	- Imagen
		 * 	- Texto
		 * 	- Nombres
		 * 	- Verbos
		 * 	- Propiedades
		 */
		JLabel titulo;
		JLabel imagen;
		JTextArea texto;
		JLabel lNombres;
		JLabel nombres;
		JLabel lVerbos;
		JLabel verbos;
		JLabel lPropiedades;
		JLabel propiedades;
		
		// Comprobaci�n para evitar excepciones
		if (cbrCase == null) return;

		// Extraemos la descripci�n y la soluci�n del caso
		NewsDescription newsD = (NewsDescription) cbrCase.getDescription(); 
		NewsSolution newsS = (NewsSolution) cbrCase.getSolution();
		
		// T�tulo
		titulo = new JLabel(newsD.getTitle().toString());
		titulo.setPreferredSize(new Dimension(500, 50));
		titulo.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, 2),
				BorderFactory.createBevelBorder(BevelBorder.RAISED)));
		titulo.setHorizontalAlignment(JLabel.CENTER);
		titulo.setToolTipText(newsD.getTitle().toString());
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 0;
		c.insets = new Insets(5, 10, 5, 10);
		panel.add(titulo, c);
		
		// Imagen
		ImageIcon img = new ImageIcon("data/noticias/img/" + newsS.getImgURL());
		imagen = new JLabel("", img, JLabel.CENTER);
		imagen.setPreferredSize(new Dimension(Math.min(500, img.getIconWidth()), Math.min(150, img.getIconHeight())));
		JPanel p = new JPanel();
		p.add(imagen);
		p.setPreferredSize(new Dimension(Math.min(500, img.getIconWidth()), Math.min(150, img.getIconHeight())));
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 1;
		c.insets = new Insets(5, 10, 5, 10);
		c.anchor = GridBagConstraints.CENTER;
		panel.add(p, c);
		
		// Texto
		texto = new JTextArea(newsD.getText().getRAWContent(), 10, 5);
		texto.setEditable(false);
		texto.setLineWrap(true);
		JScrollPane scrollPanel = new JScrollPane(texto);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, 2),
				BorderFactory.createBevelBorder(BevelBorder.RAISED)));
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 2;
		c.insets = new Insets(5, 10, 5, 10);
		panel.add(scrollPanel, c);
		
		// lNombres
		lNombres = new JLabel("Nombres: ");
		lNombres.setPreferredSize(new Dimension(60, 20));
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 3;
		c.insets = new Insets(5, 10, 5, 10);
		panel.add(lNombres, c);
		
		// Nombres
		nombres = new JLabel(newsD.getNombres().toString());
		nombres.setToolTipText(newsD.getNombres().toString());
		nombres.setPreferredSize(new Dimension(440, 20));
		nombres.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridy = 3;
		c.insets = new Insets(5, 10, 5, 10);
		panel.add(nombres, c);
		
		// lVerbos
		lVerbos = new JLabel("Verbos: ");
		lVerbos.setPreferredSize(new Dimension(60, 20));
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 4;
		c.insets = new Insets(5, 10, 5, 10);
		panel.add(lVerbos, c);
		
		// Verbos
		verbos = new JLabel(newsD.getVerbos().toString());
		verbos.setPreferredSize(new Dimension(440, 20));
		verbos.setToolTipText(newsD.getVerbos().toString());
		verbos.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridy = 4;
		c.insets = new Insets(5, 10, 5, 10);
		panel.add(verbos, c);
		
		HashMap<String, ArrayList<String>> listaProps = newsD.getPropiedades();
		int fila = 5;
		for (Entry<String, ArrayList<String>> prop : listaProps.entrySet()) {
			// lPropiedades
			lPropiedades = new JLabel(prop.getKey() + ": ");
			lPropiedades.setPreferredSize(new Dimension(60, 20));
			c.gridx = 0;
			c.gridwidth = 1;
			c.gridy = fila;
			c.insets = new Insets(5, 10, 5, 10);
			panel.add(lPropiedades, c);
			
			// Propiedades
			propiedades = new JLabel(prop.getValue().toString());
			propiedades.setToolTipText(prop.getValue().toString());
			propiedades.setPreferredSize(new Dimension(440, 20));
			propiedades.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			c.gridx = 1;
			c.gridwidth = 1;
			c.gridy = fila;
			c.insets = new Insets(5, 10, 5, 10);
			panel.add(propiedades, c);	
			
			fila++;
		}
		
		add(panel);		
	}
}
