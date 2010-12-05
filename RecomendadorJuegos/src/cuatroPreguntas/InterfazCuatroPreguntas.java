package cuatroPreguntas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import jcolibri.exception.ExecutionException;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import sistema.Game;
import sistema.Subdomains;

//VS4E -- DO NOT REMOVE THIS LINE!
public class InterfazCuatroPreguntas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JTextField jTextField0;
	private JTextField jTextField1;
	private JTextField jTextField2;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JLabel jLabel4;
	private JButton jButton0;
	private JComboBox jComboBox0;
	
	private CuatroPreguntas preguntas;
	private Game game;
	
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	
	public InterfazCuatroPreguntas() {
		this.game = new Game();
		initComponents();
	}
	
	public InterfazCuatroPreguntas(CuatroPreguntas preguntas){
		this.preguntas = preguntas;
		this.game = new Game();
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(34, 10, 10), new Leading(48, 10, 10)));
		add(getJLabel1(), new Constraints(new Leading(34, 12, 12), new Leading(81, 12, 12)));
		add(getJTextField0(), new Constraints(new Leading(183, 12, 12), new Leading(44, 12, 12)));
		add(getJTextField1(), new Constraints(new Leading(183, 12, 12), new Leading(75, 12, 12)));
		add(getJTextField2(), new Constraints(new Leading(183, 12, 12), new Leading(106, 12, 12)));
		add(getJLabel3(), new Constraints(new Leading(34, 12, 12), new Leading(141, 12, 12)));
		add(getJLabel2(), new Constraints(new Leading(34, 12, 12), new Leading(112, 12, 12)));
		add(getJLabel4(), new Constraints(new Leading(254, 12, 12), new Leading(81, 12, 12)));
		add(getJButton0(), new Constraints(new Leading(113, 10, 10), new Leading(186, 10, 10)));
		add(getJComboBox0(), new Constraints(new Leading(183, 12, 12), new Leading(133, 12, 12)));
		setSize(367, 240);
	}

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
			jComboBox0.setModel(new DefaultComboBoxModel(new Object[] { Subdomains.AbstractGames.name(), 
																		Subdomains.ChildrenGames.name(),
																		Subdomains.CustomizableGames.name(),
																		Subdomains.FamilyGames.name(),
																		Subdomains.PartyGames.name(),
																		Subdomains.StrategyGames.name(),
																		Subdomains.ThematicGames.name(),
																		Subdomains.Wargames.name()}));
			jComboBox0.setDoubleBuffered(false);
			jComboBox0.setBorder(null);
		}
		return jComboBox0;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Buscar");
			jButton0.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jButton0ActionActionPerformed(event);
				}
			});
		}
		return jButton0;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("minutos");
		}
		return jLabel4;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Edad");
		}
		return jLabel2;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Subdominio");
		}
		return jLabel3;
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setColumns(5);
		}
		return jTextField2;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setColumns(5);
		}
		return jTextField1;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
			jTextField0.setColumns(5);
		}
		return jTextField0;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Tiempo de juego");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Numero jugadores");
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

	private void jButton0ActionActionPerformed(ActionEvent event) {
		this.game.setMinNumPlayers(Integer.parseInt(this.jTextField0.getText()));
		this.game.setPlayingTime(Integer.parseInt(this.jTextField1.getText()));
		this.game.setAge(Integer.parseInt(this.jTextField2.getText()));
		ArrayList<String> subdominio = new ArrayList<String>();
		subdominio.add((String)this.jComboBox0.getSelectedItem());
		this.game.setSubdomains(subdominio);
		
		preguntas.query.setDescription(this.game);
		try{
			preguntas.cycle(preguntas.query);
		}catch (ExecutionException e)
		{
			e.printStackTrace();
		}
		this.dispose();
	}
}
