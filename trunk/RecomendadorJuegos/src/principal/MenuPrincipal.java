package principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import preguntasOpcionales.RefinarPerfil;

import sistema.Sistema;
import sistema.GameConnector;

import cuatroPreguntas.CuatroPreguntas;

import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.Connector;
import jcolibri.exception.InitializingException;

//VS4E -- DO NOT REMOVE THIS LINE!
public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton botonCuatroPreguntas;
	private JButton botonRefinarPerfil;
	private JButton botonRecomendarPerfil;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	public MenuPrincipal() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getBotonCuatroPreguntas(), new Constraints(new Leading(12, 12, 12), new Leading(12, 12, 12)));
		add(getBotonRefinarPerfil(), new Constraints(new Trailing(12, 142, 162, 162), new Leading(12, 12, 12)));
		add(getBotonRecomendarPerfil(), new Constraints(new Trailing(160, 162, 162), new Leading(12, 12, 12)));
		setSize(590, 240);
	}

	private JButton getBotonRecomendarPerfil() {
		if (botonRecomendarPerfil == null) {
			botonRecomendarPerfil = new JButton();
			botonRecomendarPerfil.setText("Recomiendame");
			botonRecomendarPerfil.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					botonRecomendarPerfilActionActionPerformed(event);
				}
			});
		}
		return botonRecomendarPerfil;
	}

	private JButton getBotonRefinarPerfil() {
		if (botonRefinarPerfil == null) {
			botonRefinarPerfil = new JButton();
			botonRefinarPerfil.setText("Refinar Perfil");
			botonRefinarPerfil.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					botonRefinarPerfilActionActionPerformed(event);
				}
			});
		}
		return botonRefinarPerfil;
	}

	private JButton getBotonCuatroPreguntas() {
		if (botonCuatroPreguntas == null) {
			botonCuatroPreguntas = new JButton();
			botonCuatroPreguntas.setText("Comprar Juego");
			botonCuatroPreguntas.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					botonCuatroPreguntasActionActionPerformed(event);
				}
			});
		}
		return botonCuatroPreguntas;
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

	public static void main(String[] args) {
		
		//Se crea al principio la base de casos y esta en memoria durante la ejecucion
		CBRCaseBase _gameCaseBase = Sistema.getCBjuegosInstance();
		Connector gameConnector = new GameConnector();
		try{
			_gameCaseBase.init(gameConnector);
		}catch (InitializingException e)
		{
			e.printStackTrace();
		}
		
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

	private void botonCuatroPreguntasActionActionPerformed(ActionEvent event) {
		CuatroPreguntas preguntas = new CuatroPreguntas();
		preguntas.execute();
	}

	private void botonRefinarPerfilActionActionPerformed(ActionEvent event) {
		RefinarPerfil refinar = new RefinarPerfil();
		refinar.execute();
	}

	private void botonRecomendarPerfilActionActionPerformed(ActionEvent event) {
	}

}
