package principal;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

//VS4E -- DO NOT REMOVE THIS LINE!
public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton botonPreguntasOpcionales;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	public MenuPrincipal() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJButton0(), new Constraints(new Trailing(12, 12, 12), new Leading(12, 12, 12)));
		add(getJButton0(), new Constraints(new Leading(12, 170, 170), new Leading(12, 12, 12)));
		add(getJButton0(), new Constraints(new Trailing(12, 146, 12, 12), new Leading(47, 10, 10)));
		setSize(697, 240);
	}

	private JButton getJButton0() {
		if (botonPreguntasOpcionales == null) {
			botonPreguntasOpcionales = new JButton();
			botonPreguntasOpcionales.setText("Recomiendame");
		}
		return botonPreguntasOpcionales;
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
				MenuPrincipal frame = new MenuPrincipal();
				frame.setDefaultCloseOperation(MenuPrincipal.EXIT_ON_CLOSE);
				frame.setTitle("MenuPrincipal");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

}
