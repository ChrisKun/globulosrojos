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

import recuperaciondeinformacion.utils.representation.NewsDescription;

public class ListaNoticias extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Ruta a la carpeta de imágenes para los números.
	 * */
	private static final String IMG_PATH = "img" + File.separator;

	/**
	 * Constructora.
	 * @param news Lista de noticias a mostrar.
	 * */
	public ListaNoticias(ArrayList<NewsDescription> news) {
		// Configuración propia del frame
		this.setTitle("Noticias");
		
		// Creamos el panel principal y le asignamos un layout
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		/* El frame se compondrá únicamente de botones para seleccionar
		 * una noticia y labels que contendrán los iconos para los números
		 */
		JLabel label;
		JButton boton;
		
		// Comprobación para evitar excepciones
		if (news == null) return;
		
		/* Por cada noticia de la lista de noticias creamos su label (icono)
		 * y un botón con el título de la noticia para poder seleccionarla
		 * Se impone un límite máximo de 9 noticias para que funcione correctamente
		 * la numeración en los iconos
		 */
		for (int i=0; i < news.size() && i < 9; i++) {
			// Icono (posición en la lista)
			label = new JLabel("");
			label.setPreferredSize(new Dimension(50, 50));
			label.setIcon(new ImageIcon(IMG_PATH + (i + 1) + ".jpg"));
			c.gridx = 0;
			c.gridy = i;
			c.insets = new Insets(5, 10, 5, 10);
			panel.add(label, c);
			
			// Botón (Título de la noticia)
			boton = new JButton(news.get(i).getTitle().toString());
			boton.setPreferredSize(new Dimension(300, 50));
			boton.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createLineBorder(Color.BLACK, 2),
							BorderFactory.createBevelBorder(BevelBorder.RAISED)));
			boton.setToolTipText(news.get(i).getTitle().toString());
			boton.addActionListener(new newsButtonActionListener(news.get(i)));
			c.gridx = 2;
			c.gridy = i;
			c.insets = new Insets(5, 0, 5, 10);
			panel.add(boton, c);
		}
		
		add(panel);
	}
	
	public static void main(String[] args) {
		ArrayList<NewsDescription> news = new ArrayList<NewsDescription>();
		// TODO: Meter noticias para ver algo en el menú
		ListaNoticias menu = new ListaNoticias(news);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.pack();
		menu.setVisible(true);
	}

}

/**
 * Implementación del listener asociado a los botones del menú de selección.
 *  */
class newsButtonActionListener implements ActionListener {

	/**
	 * Noticia asociada al listener.
	 * */
	private NewsDescription news;
	
	/**
	 * Constructora.
	 * @param news La noticia asociada a este listener.
	 * */
	public newsButtonActionListener(NewsDescription news) {
		this.news = news;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Noticia " + news.getId() + " seleccionada.");
	}
	
}