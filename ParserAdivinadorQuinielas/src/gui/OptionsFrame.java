package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

//VS4E -- DO NOT REMOVE THIS LINE!
public class OptionsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JTextField jTextField0;
	private JRadioButton jRadioButton0;
	private JRadioButton jRadioButton1;
	private JButton jButton0;
	private JButton jButton2;
	private JLabel jLabel2;
	private JComboBox jComboBox0;
	
	private MainFrame mf;
	
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	
	public OptionsFrame(MainFrame mf) {
		initComponents();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("Opciones");
		getContentPane().setPreferredSize(getSize());
		pack();
		setLocationRelativeTo(null);
		this.mf = mf;
	}

	private void initComponents() {
		setTitle("Opciones");
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setForeground(Color.black);
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(15, 10, 10), new Leading(19, 10, 10)));
		add(getJLabel1(), new Constraints(new Leading(15, 10, 10), new Leading(55, 10, 10)));
		add(getJRadioButton0(), new Constraints(new Leading(155, 8, 8), new Leading(51, 8, 8)));
		add(getJRadioButton1(), new Constraints(new Leading(155, 8, 8), new Leading(79, 8, 8)));
		add(getJTextField0(), new Constraints(new Leading(160, 33, 10, 10), new Leading(17, 12, 12)));
		add(getJButton0(), new Constraints(new Leading(133, 12, 12), new Trailing(12, 111, 111)));
		add(getJButton2(), new Constraints(new Leading(222, 10, 10), new Trailing(12, 111, 111)));
		add(getJLabel2(), new Constraints(new Leading(15, 12, 12), new Leading(111, 12, 12)));
		add(getJComboBox0(), new Constraints(new Leading(155, 12, 12), new Leading(107, 50, 50)));
		setSize(320, 190);
	}

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
			jComboBox0.setModel(new DefaultComboBoxModel(new Object[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38" }));
			jComboBox0.setDoubleBuffered(false);
			jComboBox0.setBorder(null);
			jComboBox0.setSelectedItem(Integer.toString(Opciones.ultimaJornada));
			jComboBox0.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jComboBox0ActionActionPerformed(event);
				}
			});
		}
		return jComboBox0;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Última jornada:");
		}
		return jLabel2;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("Cancelar");
			jButton2.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					jButton2MouseMouseClicked(event);
				}
			});
		}
		return jButton2;
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

	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setSelected(Opciones.opcionVotacion == Opciones.votacion.PONDERADA);
			jRadioButton1.setText("Votación ponderada");
			jRadioButton1.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jRadioButton1ActionActionPerformed(event);
				}
			});
		}
		return jRadioButton1;
	}

	private JRadioButton getJRadioButton0() {
		if (jRadioButton0 == null) {
			jRadioButton0 = new JRadioButton();
			jRadioButton0.setText("Votación básica");
			jRadioButton0.setSelected(Opciones.opcionVotacion == Opciones.votacion.BASICA);
			jRadioButton0.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jRadioButton0ActionActionPerformed(event);
				}
			});
		}
		return jRadioButton0;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
			jTextField0.setText(Integer.toString(Opciones.opcionKNN));
		}
		return jTextField0;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Sistema de votación:");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Valor de k (k-nn):");
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
				OptionsFrame frame = new OptionsFrame(null);
				frame.setDefaultCloseOperation(OptionsFrame.EXIT_ON_CLOSE);
				frame.setTitle("OptionsFrame");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	private void jButton0MouseMouseClicked(MouseEvent event) {
		try {
			Opciones.opcionKNN = Integer.parseInt(jTextField0.getText());
			if (jRadioButton0.isSelected()) Opciones.opcionVotacion = Opciones.votacion.BASICA;
			else Opciones.opcionVotacion = Opciones.votacion.PONDERADA;
		} catch (NumberFormatException e){}
		
		mf.enableFrame();
		dispose();
	}

	private void jRadioButton0ActionActionPerformed(ActionEvent event) {
		jRadioButton1.setSelected(!jRadioButton0.isSelected());
	}

	private void jRadioButton1ActionActionPerformed(ActionEvent event) {
		jRadioButton0.setSelected(!jRadioButton1.isSelected());
	}

	private void jButton2MouseMouseClicked(MouseEvent event) {
		mf.enableFrame();
		dispose();
	}

	private void jComboBox0ActionActionPerformed(ActionEvent event) {
		Opciones.ultimaJornada = Integer.parseInt((String) jComboBox0.getSelectedItem());
	}

}
