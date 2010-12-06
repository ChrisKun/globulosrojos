package preguntasOpcionales;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
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

import sistema.Perfil;
import sistema.Perfil.FormaDeSer;
import sistema.Perfil.Gender;

//VS4E -- DO NOT REMOVE THIS LINE!
public class InterfazRefinarPerfil extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JLabel jLabel4;
	private JTextField jTextField0;
	private JRadioButton jRadioButton0;
	private JRadioButton jRadioButton1;
	private JComboBox jComboBox0;
	private JRadioButton jRadioButton2;
	private JRadioButton jRadioButton3;
	private JButton jButton0;
	private ButtonGroup buttonGroup1;
	private ButtonGroup buttonGroup2;
	private JButton jButton1;
	private JLabel jLabel5;
	private JRadioButton jRadioButton4;
	private JRadioButton jRadioButton5;
	private ButtonGroup buttonGroup3;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	public InterfazRefinarPerfil() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(17, 10, 10), new Leading(21, 10, 10)));
		add(getJLabel1(), new Constraints(new Leading(48, 10, 10), new Leading(54, 12, 12)));
		add(getJLabel3(), new Constraints(new Leading(48, 12, 12), new Leading(114, 12, 12)));
		add(getJLabel2(), new Constraints(new Leading(49, 12, 12), new Leading(87, 12, 12)));
		add(getJLabel4(), new Constraints(new Leading(49, 12, 12), new Leading(147, 12, 12)));
		add(getJTextField0(), new Constraints(new Leading(204, 10, 10), new Leading(54, 12, 12)));
		add(getJRadioButton0(), new Constraints(new Leading(204, 10, 10), new Leading(77, 8, 8)));
		add(getJRadioButton1(), new Constraints(new Leading(294, 10, 10), new Leading(77, 8, 8)));
		add(getJComboBox0(), new Constraints(new Leading(204, 10, 10), new Leading(105, 12, 12)));
		add(getJRadioButton2(), new Constraints(new Leading(204, 8, 8), new Leading(141, 10, 10)));
		add(getJRadioButton3(), new Constraints(new Leading(286, 10, 10), new Leading(141, 8, 8)));
		add(getJButton1(), new Constraints(new Leading(114, 10, 10), new Trailing(12, 174, 174)));
		add(getJButton0(), new Constraints(new Leading(251, 10, 10), new Leading(201, 10, 10)));
		add(getJLabel5(), new Constraints(new Leading(49, 12, 12), new Leading(174, 12, 12)));
		add(getJRadioButton4(), new Constraints(new Leading(203, 8, 8), new Leading(166, 45, 45)));
		add(getJRadioButton5(), new Constraints(new Leading(249, 10, 10), new Leading(166, 8, 8)));
		initButtonGroup1();
		initButtonGroup2();
		initButtonGroup3();
		setSize(460, 240);
	}

	private void initButtonGroup3() {
		buttonGroup3 = new ButtonGroup();
		buttonGroup3.add(getJRadioButton4());
		buttonGroup3.add(getJRadioButton5());
	}

	private JRadioButton getJRadioButton5() {
		if (jRadioButton5 == null) {
			jRadioButton5 = new JRadioButton();
			jRadioButton5.setText("No");
		}
		return jRadioButton5;
	}

	private JRadioButton getJRadioButton4() {
		if (jRadioButton4 == null) {
			jRadioButton4 = new JRadioButton();
			jRadioButton4.setSelected(true);
			jRadioButton4.setText("Si");
		}
		return jRadioButton4;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("¿Tienes paciencia?");
		}
		return jLabel5;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Cancelar");
			jButton1.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jButton1ActionActionPerformed(event);
				}
			});
		}
		return jButton1;
	}

	private void initButtonGroup2() {
		buttonGroup2 = new ButtonGroup();
		buttonGroup2.add(getJRadioButton2());
		buttonGroup2.add(getJRadioButton3());
	}

	private void initButtonGroup1() {
		buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(getJRadioButton0());
		buttonGroup1.add(getJRadioButton1());
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Guardar");
			jButton0.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jButton0ActionActionPerformed(event);
				}
			});
		}
		return jButton0;
	}

	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setText("Mala");
		}
		return jRadioButton3;
	}

	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setSelected(true);
			jRadioButton2.setText("Buena");
		}
		return jRadioButton2;
	}

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
			jComboBox0.setModel(new DefaultComboBoxModel(new Object[] { "escoge uno",
																		Perfil.FormaDeSer.agresivo,
																		Perfil.FormaDeSer.aventurero.name(),
																		Perfil.FormaDeSer.calculador.name(),
																		Perfil.FormaDeSer.clasico.name(),
																		Perfil.FormaDeSer.cotilla.name(),
																		Perfil.FormaDeSer.extrovetido.name(),
																		Perfil.FormaDeSer.friki.name(),
																		Perfil.FormaDeSer.solitario.name(),
																		Perfil.FormaDeSer.trabajador.name()}));
			jComboBox0.setDoubleBuffered(false);
			jComboBox0.setBorder(null);
		}
		return jComboBox0;
	}

	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setSelected(true);
			jRadioButton1.setText("Mujer");
		}
		return jRadioButton1;
	}

	private JRadioButton getJRadioButton0() {
		if (jRadioButton0 == null) {
			jRadioButton0 = new JRadioButton();
			jRadioButton0.setText("Hombre");
		}
		return jRadioButton0;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
			jTextField0.setColumns(5);
		}
		return jTextField0;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Define tu memoria");
		}
		return jLabel4;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Sexo");
		}
		return jLabel2;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Forma de ser");
		}
		return jLabel3;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Edad");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Contesta a las siguientes preguntas para refinar tu perfil:");
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
				InterfazRefinarPerfil frame = new InterfazRefinarPerfil();
				frame
						.setDefaultCloseOperation(InterfazRefinarPerfil.DISPOSE_ON_CLOSE);
				frame.setTitle("InterfazRefinarPerfil");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	private void jButton0ActionActionPerformed(ActionEvent event) {
		Perfil perfilRefinado = new Perfil();
		perfilRefinado.setAge(Integer.parseInt(this.jTextField0.getText()));
		
		if(jRadioButton0.isSelected())
			perfilRefinado.setGender(Gender.Male);
		else if(jRadioButton1.isSelected())
			perfilRefinado.setGender(Gender.Female);
		String formaDeSer = (String)jComboBox0.getSelectedItem();
		if(!formaDeSer.equals("escoge uno"))
		{
			perfilRefinado.setFormaDeSer((FormaDeSer)jComboBox0.getSelectedItem());
		}
		this.dispose();
	}

	private void jButton1ActionActionPerformed(ActionEvent event) {
		this.dispose();
	}

}
