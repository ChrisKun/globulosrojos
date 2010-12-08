package logIn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import jcolibri.cbrcore.CBRCase;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import principal.MenuPrincipal;
import sistema.Perfil;
import sistema.ProfileConnector;
import sistema.Sistema;

//VS4E -- DO NOT REMOVE THIS LINE!
public class InterfazLogIn extends JFrame {

	public static Perfil perfil;
	private static final long serialVersionUID = 1L;
	private JButton jButton0;
	private JButton jButton1;
	private JTextField jTextField0;
	private JButton jButton2;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	public InterfazLogIn() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJTextField0(), new Constraints(new Leading(35, 153, 10, 10), new Leading(52, 12, 12)));
		add(getJButton1(), new Constraints(new Leading(220, 158, 10, 10), new Leading(90, 12, 12)));
		add(getJButton0(), new Constraints(new Leading(220, 158, 12, 12), new Leading(49, 12, 12)));
		add(getJButton2(), new Constraints(new Leading(220, 158, 12, 12), new Leading(132, 10, 10)));
		setSize(426, 195);
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("Guardar los Perfiles");
			jButton2.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jButton2ActionActionPerformed(event);
				}
			});
		}
		return jButton2;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
			jTextField0.setText("Usuario");
		}
		return jTextField0;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Registrar");
			jButton1.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jButton1ActionActionPerformed(event);
				}
			});
		}
		return jButton1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Iniciar Sesion");
			jButton0.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jButton0ActionActionPerformed(event);
				}
			});
		}
		return jButton0;
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
				InterfazLogIn frame = new InterfazLogIn();
				frame.setDefaultCloseOperation(InterfazLogIn.EXIT_ON_CLOSE);
				frame.setTitle("InterfazLogIn");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				perfil = new Perfil();
			}
		});
	}

	private void jButton0ActionActionPerformed(ActionEvent event) {
		if(perfil.Loguear(jTextField0.getText()))
		{
			JOptionPane.showMessageDialog(InterfazLogIn.this, "Logueado con exito", "Informacion",JOptionPane.INFORMATION_MESSAGE);
			Sistema.setPerfil(perfil);
			this.setVisible(false);
			MenuPrincipal menu = new MenuPrincipal(jTextField0.getText());
			menu.getContentPane().setPreferredSize(menu.getSize());
			menu.pack();
			menu.setLocationRelativeTo(null);
			menu.setVisible(true);
		}
		else
		{
			JOptionPane.showMessageDialog(InterfazLogIn.this, "El usuario no existe", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void jButton1ActionActionPerformed(ActionEvent event) {
		if(perfil.Registrar(jTextField0.getText()))
		{
			JOptionPane.showMessageDialog(InterfazLogIn.this, "Registrado con exito", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		}
		else
			JOptionPane.showMessageDialog(InterfazLogIn.this, "Error al registrar el nick", "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void jButton2ActionActionPerformed(ActionEvent event) {
		Sistema.resetNumOfUsers();//Pone el contador de usuarios a cero
		ProfileConnector pc = new ProfileConnector();
		Collection<CBRCase> casos = pc.retrieveAllCases();
		if(JOptionPane.showConfirmDialog(InterfazLogIn.this,"" +
								"¿Estas seguro que quieres guardar los perfiles?") == 0)
		{
			if(Perfil.UserCBRcasesToFile(casos))
				JOptionPane.showMessageDialog(InterfazLogIn.this, "exito", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(InterfazLogIn.this, "Error", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
