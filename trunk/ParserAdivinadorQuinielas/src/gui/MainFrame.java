package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import jcolibri.cbrcore.CBRQuery;
import jcolibri.method.retrieve.RetrievalResult;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import parser.Parser;
import cbr.Casos;
import cbr.Prediction;

//VS4E -- DO NOT REMOVE THIS LINE!
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuItem jMenuItem0;
	private JMenu jMenu0;
	private JMenuItem jMenuItem1;
	private JMenu jMenu1;
	private JMenuItem jMenuItem2;
	private JMenu jMenu2;
	private JMenuBar jMenuBar0;
	private JMenuItem jMenuItem3;
	private JMenuItem jMenuItem4;
	private JMenuItem jMenuItem5;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JPanel jPanel1;
	private JPanel jPanel0;
	private JLabel jLabel1;
	private JComboBox jComboBox0;
	private JLabel jLabel0;
	private JLabel jLabel2;
	private JProgressBar jProgressBar0;
	
	ArrayList<CBRQuery> queryList;
	ArrayList<Prediction> predictionList;
	ArrayList<Collection<RetrievalResult>> knnList;
	
	private JTable jTable1;
	private JScrollPane jScrollPane1;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JLabel jLabel10;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	public MainFrame() {
		initComponents();
	}

	private void initComponents() {
		setTitle("Adivinador de quinielas");
		setLayout(new GroupLayout());
		add(getJPanel1(), new Constraints(new Leading(12, 398, 10, 10), new Leading(12, 130, 50, 174)));
		add(getJPanel0(), new Constraints(new Trailing(12, 398, 10, 10), new Leading(12, 130, 10, 10)));
		add(getJLabel2(), new Constraints(new Leading(18, 10, 10), new Leading(162, 10, 10)));
		add(getJScrollPane0(), new Constraints(new Leading(12, 820, 12, 12), new Leading(184, 150, 10, 10)));
		add(getJProgressBar0(), new Constraints(new Leading(12, 820, 12, 12), new Leading(346, 12, 12)));
		setJMenuBar(getJMenuBar0());
		setSize(844, 394);
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("-");
		}
		return jLabel10;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("-");
		}
		return jLabel9;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("-");
		}
		return jLabel8;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("knn:");
		}
		return jLabel7;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Votación:");
		}
		return jLabel6;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Confianza:");
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("-");
		}
		return jLabel4;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Resultado previsto:");
		}
		return jLabel3;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			jTable1.setModel(new TablaQueryModel(null));
		}
		return jTable1;
	}

	public JProgressBar getJProgressBar0() {
		if (jProgressBar0 == null) {
			jProgressBar0 = new JProgressBar();
			jProgressBar0.setStringPainted(true);
		}
		return jProgressBar0;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("k nearest neighbor");
		}
		return jLabel2;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Predicción:");
		}
		return jLabel0;
	}

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
			jComboBox0.setModel(new DefaultComboBoxModel(new Object[] {}));
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
			jLabel1.setText("Query:");
		}
		return jLabel1;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(new LineBorder(Color.black, 1, false));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJLabel0(), new Constraints(new Leading(12, 12, 12), new Leading(12, 12, 12)));
			jPanel0.add(getJLabel3(), new Constraints(new Leading(15, 10, 10), new Leading(40, 12, 12)));
			jPanel0.add(getJLabel4(), new Constraints(new Leading(136, 12, 12), new Leading(40, 12, 12)));
			jPanel0.add(getJLabel5(), new Constraints(new Leading(15, 12, 12), new Leading(62, 12, 12)));
			jPanel0.add(getJLabel6(), new Constraints(new Leading(15, 12, 12), new Leading(84, 12, 12)));
			jPanel0.add(getJLabel7(), new Constraints(new Leading(15, 12, 12), new Leading(106, 12, 12)));
			jPanel0.add(getJLabel8(), new Constraints(new Leading(136, 12, 12), new Leading(62, 12, 12)));
			jPanel0.add(getJLabel9(), new Constraints(new Leading(136, 12, 12), new Leading(84, 12, 12)));
			jPanel0.add(getJLabel10(), new Constraints(new Leading(136, 12, 12), new Leading(106, 12, 12)));
		}
		return jPanel0;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(new LineBorder(Color.black, 1, false));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJLabel1(), new Constraints(new Leading(12, 12, 12), new Leading(12, 12, 12)));
			jPanel1.add(getJComboBox0(), new Constraints(new Trailing(12, 134, 67, 67), new Leading(8, 12, 12)));
			jPanel1.add(getJScrollPane1(), new Constraints(new Bilateral(12, 12, 22), new Leading(46, 73, 12, 12)));
		}
		return jPanel1;
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
			jTable0.setModel(new TablaKNNModel(null));
		}
		return jTable0;
	}

	private JMenuItem getJMenuItem5() {
		if (jMenuItem5 == null) {
			jMenuItem5 = new JMenuItem();
			jMenuItem5.setText("Leave-one-out");
		}
		return jMenuItem5;
	}

	private JMenuItem getJMenuItem4() {
		if (jMenuItem4 == null) {
			jMenuItem4 = new JMenuItem();
			jMenuItem4.setText("Hold-out");
		}
		return jMenuItem4;
	}

	private JMenuItem getJMenuItem3() {
		if (jMenuItem3 == null) {
			jMenuItem3 = new JMenuItem();
			jMenuItem3.setText("Seleccionar Partido(s)");
			jMenuItem3.addMouseListener(new MouseAdapter() {
	
				public void mousePressed(MouseEvent event) {
					jMenuItem3MouseMousePressed(event);
				}
			});
		}
		return jMenuItem3;
	}

	private JMenuBar getJMenuBar0() {
		if (jMenuBar0 == null) {
			jMenuBar0 = new JMenuBar();
			jMenuBar0.add(getJMenu0());
			jMenuBar0.add(getJMenu1());
			jMenuBar0.add(getJMenu2());
		}
		return jMenuBar0;
	}

	private JMenu getJMenu2() {
		if (jMenu2 == null) {
			jMenu2 = new JMenu();
			jMenu2.setText("Evaluación");
			jMenu2.setOpaque(false);
			jMenu2.add(getJMenuItem2());
			jMenu2.add(getJMenuItem4());
			jMenu2.add(getJMenuItem5());
		}
		return jMenu2;
	}

	private JMenuItem getJMenuItem2() {
		if (jMenuItem2 == null) {
			jMenuItem2 = new JMenuItem();
			jMenuItem2.setText("N-Fold");
		}
		return jMenuItem2;
	}

	private JMenu getJMenu1() {
		if (jMenu1 == null) {
			jMenu1 = new JMenu();
			jMenu1.setText("Query");
			jMenu1.setOpaque(false);
			jMenu1.add(getJMenuItem1());
			jMenu1.add(getJMenuItem3());
		}
		return jMenu1;
	}

	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setText("Crear Partido");
		}
		return jMenuItem1;
	}

	private JMenu getJMenu0() {
		if (jMenu0 == null) {
			jMenu0 = new JMenu();
			jMenu0.setText("Archivo");
			jMenu0.add(getJMenuItem0());
		}
		return jMenu0;
	}

	private JMenuItem getJMenuItem0() {
		if (jMenuItem0 == null) {
			jMenuItem0 = new JMenuItem();
			jMenuItem0.setText("Cargar BC");
			jMenuItem0.addMouseListener(new MouseAdapter() {
	
				public void mousePressed(MouseEvent event) {
					jMenuItem0MouseMousePressed(event);
				}
			});
		}
		return jMenuItem0;
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
				MainFrame frame = new MainFrame();
				frame.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
				frame.setTitle("MainFrame");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	private void jMenuItem0MouseMousePressed(MouseEvent event) {
		Thread t = new Thread(new Runnable() {
			public void run()
			{
				Parser.parse(jProgressBar0);
			}
		});
		t.start();
		//while (t.isAlive()) {this.repaint();}
	}

	private void jMenuItem3MouseMousePressed(MouseEvent event) {
		QueryPartidosFrame qpf = new QueryPartidosFrame(this);
		qpf.setVisible(true);
	}
	
	public void showPredictions(ArrayList<CBRQuery> queryList,
								ArrayList<Prediction> predictionList,
								ArrayList<Collection<RetrievalResult>> knnList) {
		this.queryList = queryList;
		this.predictionList = predictionList;
		this.knnList = knnList;
		
		jComboBox0.removeAll();
		for (CBRQuery q : queryList) {
			jComboBox0.addItem(((Casos)q.getDescription()).getEquipoLocal() + " - " + ((Casos)q.getDescription()).getEquipoVisitante());
		}
	}

	private void jComboBox0ActionActionPerformed(ActionEvent event) {
		int index = ((JComboBox)event.getSource()).getSelectedIndex();
		
		// Tabla de query
		Casos c = (Casos)queryList.get(index).getDescription();
		
		jTable1.setModel(new TablaQueryModel(c));
		
		// Datos de la predicción
		Prediction p = predictionList.get(index);
		jLabel4.setText(p.getClassification().toString());
		jLabel8.setText(Double.toString(p.getConfidence()));
		jLabel9.setText(Opciones.opcionVotacion.toString());
		jLabel10.setText(Integer.toString(Opciones.opcionKNN));
		
		// Datos de los knn elegidos
		Collection<RetrievalResult> k = knnList.get(index);
		jTable0.setModel(new TablaKNNModel(k));
	}

}
