package evaluar;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.TableColumn;

import jcolibri.cbrcore.CBRCase;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import sistema.Sistema;
import sistema.Game;

//VS4E -- DO NOT REMOVE THIS LINE!
public class EvaluatorFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JButton jButton0;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	public EvaluatorFrame() {
		initComponents();
	}

	private void initComponents() {
		setTitle("Juegos adquiridos");
		setLayout(new GroupLayout());
		add(getJScrollPane0(), new Constraints(new Bilateral(12, 12, 22), new Leading(12, 275, 10, 10)));
		add(getjButton0(), new Constraints(new Bilateral(141, 141, 60), new Leading(299, 12, 12)));
		setSize(342, 343);
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Salir");
			jButton0.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					jButton0MouseMouseClicked(event);
				}
			});
		}
		return jButton0;
	}

	private JButton getjButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Salir");
		}
		return jButton0;
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
			// TODO : Cambiar el objeto pasado por lo que se vaya a usar para la lista de juegos adquiridos
			/* Tenemos una lista de <GameID, Valoracion> que necesitamos cambiar a <Nombre, Valoracion>
			 *  para que sea más cómodo para el usuario.
			 */
			HashMap<String, Float> juegosAdquiridosStr = new HashMap<String, Float>();
			HashMap<Integer, Float> juegosAdquiridosID = Sistema.getPerfil().getListaValoraciones();
			
			Set<Entry<Integer, Float>> juegosID = Sistema.getPerfil().getListaValoraciones().entrySet();
			for (Entry<Integer, Float> entry : juegosID) {
				Collection<CBRCase> juegos = Sistema.getCBjuegosInstance().getCases();
				for (CBRCase cbrCase : juegos) {
					Game g = (Game)cbrCase.getDescription();
					if (juegosAdquiridosID.containsKey(g.getGameId()))
						juegosAdquiridosStr.put(g.getName(), juegosAdquiridosID.get(g.getGameId()));
				}
			}

			jTable0.setModel(new JuegosAdquiridosTableModel(juegosAdquiridosStr));
			TableColumn tc = jTable0.getColumnModel().getColumn(1);
			tc.setCellEditor(new DefaultCellEditor(new JTextField()));
	        
		}
		return jTable0;
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
				EvaluatorFrame frame = new EvaluatorFrame();
				frame.setDefaultCloseOperation(EvaluatorFrame.EXIT_ON_CLOSE);
				//frame.setTitle("EvaluatorFrame");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	private void jButton0MouseMouseClicked(MouseEvent event) {
		// TODO Actualizar la información con las nuevas valoraciones
		this.dispose();
	}
}