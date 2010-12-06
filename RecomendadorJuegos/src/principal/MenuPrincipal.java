package principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.exception.InitializingException;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import preguntasOpcionales.RefinarPerfil;
import sistema.Game;
import sistema.GameConnector;
import sistema.ProfileConnector;
import cuatroPreguntas.CuatroPreguntas;

//VS4E -- DO NOT REMOVE THIS LINE!
public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton botonCuatroPreguntas;
	private JButton botonRefinarPerfil;
	private JButton botonRecomendarPerfil;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JButton botonSeleccionar;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	public MenuPrincipal() {
		
		//Se crean las bases de casos de usuarios y de juegos
		CBRCaseBase gameCaseBase = sistema.Sistema.getCBjuegosInstance();
		CBRCaseBase userCaseBase = sistema.Sistema.getCBusuariosInstance();
		//Se crean los conectores para cargar las dos bases de casos
		GameConnector gameConnector = new GameConnector();
		//ProfileConnector profileConnector = new ProfileConnector();

		//Se cargan las dos bases en memoria
		try{
			gameCaseBase.init(gameConnector);
			//userCaseBase.init(profileConnector);
		}catch (InitializingException e)
		{
			e.printStackTrace();
		}
		//Se carga la tabla de los juegos mejor valorados
		//MejoresJuegos.init();
		
		initComponents();
		
		//presentarMejoresJuegos();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getBotonCuatroPreguntas(), new Constraints(new Leading(12, 12, 12), new Leading(12, 12, 12)));
		add(getBotonRefinarPerfil(), new Constraints(new Trailing(12, 142, 162, 162), new Leading(12, 12, 12)));
		add(getBotonRecomendarPerfil(), new Constraints(new Trailing(160, 162, 162), new Leading(12, 12, 12)));
		add(getJScrollPane0(), new Constraints(new Leading(186, 200, 10, 10), new Leading(70, 150, 10, 10)));
		add(getBotonSeleccionar(), new Constraints(new Leading(407, 10, 10), new Leading(153, 10, 10)));
		setSize(590, 240);
	}

	private JButton getBotonSeleccionar() {
		if (botonSeleccionar == null) {
			botonSeleccionar = new JButton();
			botonSeleccionar.setText("Seleccionar");
			botonSeleccionar.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					botonSeleccionarActionPerformed(event);
				}
			});
		}
		return botonSeleccionar;
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
			jTable0.setModel(new DefaultTableModel(new Object[][] { { "6", "Juego1", }, { "3521", "Juego2", }, }, new String[] { "ID", "Titulo", }) {
				private static final long serialVersionUID = 1L;
				Class<?>[] types = new Class<?>[] { Object.class, Object.class, };
	
				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}
			});
		}
		return jTable0;
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
	
	private void botonSeleccionarActionPerformed(ActionEvent event){
		Integer str = Integer.parseInt((String)jTable0.getModel().getValueAt(jTable0.getSelectedRow(), 0));
	}
	
	private void presentarMejoresJuegos()
	{
		ArrayList<CBRCase> mejoresJuegos = MejoresJuegos.getMejoresJuegos(6);
		for(int i = 0; i < mejoresJuegos.size(); i++)
		{
			CBRCase caso = mejoresJuegos.get(i);
			this.jTable0.getModel().setValueAt(((Game)caso.getDescription()).getIdAttribute(), i, 0);
			this.jTable0.getModel().setValueAt(((Game)caso.getDescription()).getName(), i, 1);
		}
	}

}
