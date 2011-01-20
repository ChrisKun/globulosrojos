package recuperaciondeinformacion.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import jcolibri.cbrcore.CBRQuery;
import jcolibri.datatypes.Text;
import jcolibri.exception.ExecutionException;
import jcolibri.extensions.textual.IE.opennlp.IETextOpenNLP;

import recuperaciondeinformacion.RecuperadorDeInformacion;
import recuperaciondeinformacion.utils.representation.NewsDescription;

public class Consulta extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public Consulta(RecuperadorDeInformacion rdi) {
		// Configuracion propia del frame
		this.setTitle("Consulta");
		
		// Creamos el panel principal y le asignamos un layout
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		/* El frame se compondra de los siguientes componentes:
		 * 	- lConsulta
		 * 	- consulta
		 * 	- cbCompararAccionesObjetos
		 * 	- cbCompararPropiedades
		 * 	- bRecuperar
		 */
		JLabel lConsulta;
		JTextArea consulta;
		JCheckBox cbCompararAccionesObjetos;
		JCheckBox cbCompararPropiedades;
		JButton bRecuperar;

		// lConsulta
		lConsulta = new JLabel("Consulta:");
		lConsulta.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(lConsulta);
		
		// Consulta
		consulta = new JTextArea(5, 10);
		consulta.setAlignmentX(Component.LEFT_ALIGNMENT);
		consulta.setLineWrap(true);
		JScrollPane scrollPanel = new JScrollPane(consulta);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, 2),
				BorderFactory.createBevelBorder(BevelBorder.RAISED)));
		panel.add(scrollPanel);
		
		// Comparar Acciones y Objetos
		cbCompararAccionesObjetos = new JCheckBox("Comparar acciones y objetos");
		cbCompararAccionesObjetos.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(cbCompararAccionesObjetos);
		
		// Comparar Propiedades
		cbCompararPropiedades = new JCheckBox("Comparar propiedades");
		cbCompararPropiedades.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(cbCompararPropiedades);
		
		// Recuperar
		bRecuperar = new JButton("Recuperar");
		bRecuperar.setAlignmentX(Component.LEFT_ALIGNMENT);
		bRecuperar.addActionListener(new RecuperarActionListener(consulta, cbCompararAccionesObjetos, cbCompararPropiedades, rdi));
		panel.add(bRecuperar);
		
		add(panel);
	}
	
	public static void main(String[] args) {
		// Inicializamos la CBR Application
		RecuperadorDeInformacion rdi = new RecuperadorDeInformacion();
		try {
			rdi.configure();
			rdi.preCycle();
		}
		catch(Exception e) {e.printStackTrace();}
		 
		// Creamos el menu principal
		Consulta c = new Consulta(rdi);
		c.pack();
		c.setVisible(true);
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class RecuperarActionListener implements ActionListener {

	private JTextArea consulta;
	private JCheckBox accionesYObjetos;
	private JCheckBox propiedades;
	private RecuperadorDeInformacion rdi;
	
	public RecuperarActionListener(JTextArea consulta, JCheckBox ayo, JCheckBox props, RecuperadorDeInformacion rdi) {
		this.consulta = consulta;
		this.accionesYObjetos = ayo;
		this.propiedades = props;
		this.rdi = rdi;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Extraemos la query en forma de String
		String queryString = consulta.getText();
		
		/* Si es distinta de una cadena vacia lanzamos la query al recuperador
		 * de informacion con las opciones elegidas. 
		 */
		if (!queryString.equals("")) {
			// Extraemos la query de la consulta del usuario
			CBRQuery query = new CBRQuery();
            NewsDescription queryDescription = new NewsDescription();
            queryDescription.setText(new IETextOpenNLP(queryString));
            queryDescription.setTitle(new Text(queryString));
            query.setDescription(queryDescription);

            try {
            	// Configuramos el recuperador de informacion
            	rdi.setAcciones(accionesYObjetos.isSelected());
            	rdi.setPropiedades(propiedades.isSelected());
            	// Lanzamos la consulta
				rdi.cycle(query);
				rdi.postCycle();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}	
		}
		
	}
	
}