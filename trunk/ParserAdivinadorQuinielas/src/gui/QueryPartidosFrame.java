package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import jcolibri.cbrcore.CBRQuery;
import jcolibri.method.retrieve.RetrievalResult;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import caseCreator.CaseCreator;
import cbr.Casos;
import cbr.Prediction;
import cbr.Quiniela;

import parserCalendario.ParserCalendario;
import parserClasificacion.Clasificacion;
import parserClasificacion.ParserClasificacion;
import parserClasificacion.Posicion;
import parserResultados.Jornada;
import parserResultados.ParserResultados;
import parserResultados.Partido;
import utils.StringUtils;

//VS4E -- DO NOT REMOVE THIS LINE!
public class QueryPartidosFrame extends JFrame {
	
	private final String pageRes = "http://www.lfp.es/?tabid=154&Controltype=cal&g=1&t=";
	private final String pageClas = "http://www.lfp.es/?tabid=154&Controltype=detcla&g=1&t=";
	
	private final static int ultimaJornada = 11;

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JComboBox jComboBox0;
	private JComboBox jComboBox1;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JButton jButton1;
	private JButton jButton0;
	private JProgressBar jProgressBar0;

	private JCheckBox jCheckBox0;
	
	enum estado {ESPERANDO, ACEPTADO, CANCELADO};
	private estado estadoActual;
	
	private ArrayList<CBRQuery> queryList;
	private ArrayList<Prediction> predictionList;
	private ArrayList<Collection<RetrievalResult>> knnList;

	private Jornada jornada;
	private Clasificacion clasificacion;
	
	private MainFrame mf;

	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";

	public estado getEstadoActual() {
		return estadoActual;
	}
	
	public ArrayList<CBRQuery> getQueryList() {
		return queryList;
	}
	
	public ArrayList<Prediction> getPredictionList() {
		return predictionList;
	}

	public QueryPartidosFrame(MainFrame mf) {
		initComponents();
		setTitle("Query Partido(s)");
		setLocationRelativeTo(null);
		estadoActual = estado.ESPERANDO;
		queryList = null;
		predictionList = null;
		knnList = null;
		jornada = null;
		clasificacion = null;
		this.mf = mf;
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(34, 10, 10), new Leading(31, 10, 10)));
		add(getJLabel1(), new Constraints(new Leading(240, 10, 10), new Leading(31, 12, 12)));
		add(getJComboBox0(), new Constraints(new Leading(114, 12, 12), new Leading(27, 12, 12)));
		add(getJComboBox1(), new Constraints(new Leading(302, 12, 12), new Leading(27, 12, 12)));
		add(getJScrollPane0(), new Constraints(new Leading(12, 384, 12, 12), new Leading(109, 216, 10, 10)));
		add(getJProgressBar0(), new Constraints(new Leading(12, 180, 194, 194), new Leading(350, 10, 10)));
		add(getJButton0(), new Constraints(new Leading(210, 109, 109), new Leading(343, 12, 12)));
		add(getJButton1(), new Constraints(new Leading(307, 12, 12), new Leading(343, 12, 12)));
		add(getJCheckBox0(), new Constraints(new Leading(12, 8, 8), new Leading(77, 8, 8)));
		setSize(408, 400);
	}

	private JCheckBox getJCheckBox0() {
		if (jCheckBox0 == null) {
			jCheckBox0 = new JCheckBox();
			jCheckBox0.setText("Seleccionar todos");
			jCheckBox0.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jCheckBox0ActionActionPerformed(event);
				}
			});
		}
		return jCheckBox0;
	}

	private JProgressBar getJProgressBar0() {
		if (jProgressBar0 == null) {
			jProgressBar0 = new JProgressBar();
		}
		return jProgressBar0;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Aceptar");
			jButton0.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					jButton0MouseMouseClicked(event);
				}
			});
		}
		return jButton0;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Cancelar");
		}
		return jButton1;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			jTable0.setModel(new TablaPartidosPredModel());
		}
		return jTable0;
	}

	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setModel(new DefaultComboBoxModel(new Object[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38" }));
			jComboBox1.setDoubleBuffered(false);
			jComboBox1.setBorder(null);
			jComboBox1.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jComboBox0ActionActionPerformed(event);
				}
			});
		}
		return jComboBox1;
	}

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
			jComboBox0.setModel(new DefaultComboBoxModel(new Object[] { "2007/08", "2008/09", "2009/10", "2010/11" }));
			jComboBox0.setDoubleBuffered(false);
			jComboBox0.setBorder(null);
			jComboBox0.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jComboBox0ActionActionPerformed(event);
				}
			});
		}
		return jComboBox0;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Jornada:");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Temporada:");
		}
		return jLabel0;
	}

	private static void installLnF() {
		try {
			String lnfClassname = PREFERRED_LOOK_AND_FEEL;
			if (lnfClassname == null)
				lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(lnfClassname);
		} catch (Exception e) {
			System.err.println("Cannot install " + PREFERRED_LOOK_AND_FEEL
					+ " on this platform:" + e.getMessage());
		}
	}

	/**
	 * Main entry of the class.
	 * Note: This class is only created so that you can easily preview the result at runtime.
	 * It is not expected to be managed by the designer.
	 * You can modify it as you like.
	 */
	public static void main(String[] args) {
		installLnF();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				QueryPartidosFrame frame = new QueryPartidosFrame(null);
				frame.setDefaultCloseOperation(QueryPartidosFrame.EXIT_ON_CLOSE);
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	private void jComboBox0ActionActionPerformed(ActionEvent event) {		
		String separator = System.getProperty("file.separator");
		
		int nTemporada = convertirTemporada(jComboBox0.getSelectedItem().toString());
		int nJornada = Integer.parseInt(jComboBox1.getSelectedItem().toString());
		
		// Si la temporada seleccionada es la actual solo mostramos hasta la jornada actual.
		if (nTemporada == 110 && event.getSource().equals(jComboBox0)) {
			Object[] model = new Object[ultimaJornada];
			for (int i=0; i < ultimaJornada; i++)
				model[i] = i + 1;
			jComboBox1.setModel(new DefaultComboBoxModel(model));
		}
		
		try {
			clasificacion = CaseCreator.leerClasificacion("Ficheros" + separator
					+ "ClasificacionesTemp" + nTemporada).get(nJornada);
			jornada = CaseCreator.leerResultados("Ficheros" + separator
						+ "ResultadosTemp" + nTemporada).get(nJornada);
		} catch (Exception e) {
			// Si salta una excepción es porque estamos intentando acceder a la última jornada.
			// La clasificación por lo tanto debería haberse leido correctamente.
			jornada = ParserCalendario.parseProximaJornada();
		}
		
		TablaPartidosPredModel tableModel = new TablaPartidosPredModel(jornada);
		jTable0.setModel(tableModel);
	}
	
	private int convertirTemporada(String t) {
		if (t.equals("2007/08")) return 107;
		if (t.equals("2008/09")) return 108;
		if (t.equals("2009/10")) return 109;
		if (t.equals("2010/11")) return 110;
		else return 110;
	}

	private void jCheckBox0ActionActionPerformed(ActionEvent event) {
		if (!((TablaPartidosPredModel)jTable0.getModel()).isEmpty())
			for (int i = 0; i < 10; i++)
				jTable0.getModel().setValueAt(jCheckBox0.isSelected(), i, 0);

		jTable0.updateUI();
	}

	private void jButton0MouseMouseClicked(MouseEvent event) {
		queryList = new ArrayList<CBRQuery>();
		predictionList = new ArrayList<Prediction>();
		knnList = new ArrayList<Collection<RetrievalResult>>();
		
		TablaPartidosPredModel tableModel = (TablaPartidosPredModel) jTable0.getModel();
		
		if (!tableModel.isEmpty())
			for (int i = 0; i < 10; i++)
				if ((Boolean)tableModel.getValueAt(i, 0)) {
					queryList.add(createQuery((String)tableModel.getValueAt(i, 1),
												(String)tableModel.getValueAt(i, 2)));
				}
		
		try {
			Quiniela quiniela = new Quiniela();
			
			quiniela.configure();
			quiniela.preCycle();
			for (int i = 0; i < queryList.size(); i++) {
				quiniela.cycle(queryList.get(i));
				predictionList.add(quiniela.getPrediction());
				knnList.add(quiniela.getEval());
			}
			
		} catch (Exception e) {}
		
		mf.showPredictions(queryList, predictionList, knnList);
		this.setVisible(false);
	}
	
	private CBRQuery createQuery(String local, String visitante) {
		CBRQuery query = new CBRQuery();
		Casos c = new Casos();
		
		Posicion posicionLocal = clasificacion.getPosicionByName(StringUtils.convertirNombresEquipos(local));
		Posicion posicionVisitante = clasificacion.getPosicionByName(StringUtils.convertirNombresEquipos(visitante));
		
		c.setEquipoLocal(local);
		c.setEquipoVisitante(visitante);
		c.setGolesAFavorEquipoLocal(posicionLocal.getGolesFavor());
		c.setGolesAFavorEquipoVisitante(posicionVisitante.getGolesFavor());
		c.setGolesEnContraEquipoLocal(posicionLocal.getGolesContra());
		c.setGolesEnContraEquipoVisitante(posicionVisitante.getGolesContra());
		
		if(posicionLocal.getPartidosJugadosCasa() != 0)
			c.setPorcentajeGanagadosLocal((posicionLocal.getPartidosGanadosCasa() / new Double(posicionLocal.getPartidosJugadosCasa())));
		else
			c.setPorcentajeGanagadosLocal(0.0);
		if (posicionLocal.getPartidosJugadosFuera() != 0)
			c.setPorcentajeGanagadosVisitante(posicionLocal.getPartidosGanadosFuera() / new Double(posicionLocal.getPartidosJugadosFuera()));
		else
			c.setPorcentajeGanagadosVisitante(0.0);
		
		c.setDiferenciaPuntos(posicionLocal.getPuntos() - posicionVisitante.getPuntos());
		c.setPosicionEquipoLocal(posicionLocal.getPosicion());
		c.setPosicionEquipoVisitante(posicionVisitante.getPosicion());
		c.setPuntosEquipoLocal(posicionLocal.getPuntos());
		c.setPuntosEquipoVisitante(posicionVisitante.getPuntos());

		query.setDescription(c);
		
		return query;
	}

}
