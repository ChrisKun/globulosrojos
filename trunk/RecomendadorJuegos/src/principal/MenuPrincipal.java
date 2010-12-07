package principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
import recomendadorPorPerfil.RecomendadorPorPerfil;
import sistema.Game;
import sistema.GameConnector;
import sistema.Perfil;
import sistema.ProfileConnector2;
import sistema.Sistema;
import cuatroPreguntas.CuatroPreguntas;
import evaluar.EvaluatorFrame;
import grupo.CrearGrupo;

//VS4E -- DO NOT REMOVE THIS LINE!
public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton botonCuatroPreguntas;
	private JButton botonRefinarPerfil;
	private JButton botonRecomendarPerfil;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JButton botonSeleccionar;
	private JButton botonEvaluarJuegos;
	private JButton botonCrearGrupo;
	private static final String PREFERRED_LOOK_AND_FEEL = "jabotonEvaluarJuegosg.plaf.metal.MetalLookAndFeel";
	public MenuPrincipal(String nick) {
		//Se crean las bases de casos de usuarios y de juegos
		CBRCaseBase gameCaseBase = sistema.Sistema.getCBjuegosInstance();
		CBRCaseBase userCaseBase = sistema.Sistema.getCBusuariosInstance();
		//Se crean los conectores para cargar las dos bases de casos
		GameConnector gameConnector = new GameConnector();
		ProfileConnector2 profileConnector = new ProfileConnector2();
		//ProfileConnector profileConnector = new ProfileConnector();

		//Se cargan las dos bases en memoria
		try{
			gameCaseBase.init(gameConnector);
			userCaseBase.init(profileConnector);
			//userCaseBase.init(profileConnector);
		}catch (InitializingException e)
		{
			e.printStackTrace();
		}
		//Se establece el numero de usuarios registrados por si es necesario asignar un id nuevo
		Sistema.setNumOfUsers(Sistema.getCBusuariosInstance().getCases().size());
		System.out.println("Num of users: "+ Sistema.getNumOfusers());
		
		//Se carga la tabla de los juegos mejor valorados
		MejoresJuegos.init();
		
		initComponents();
		
		// Cargamos el perfil del usuario logueado desde el CBR de usuarios
		Collection<CBRCase> usuarios = Sistema.getCBusuariosInstance().getCases();
		for (CBRCase cbrCase : usuarios) {
			if (((Perfil)cbrCase.getDescription()).getNickName().equals(nick)) {
				Sistema.setPerfil((Perfil)cbrCase.getDescription());
				break;
			}
		}
		
		presentarMejoresJuegos();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getBotonCuatroPreguntas(), new Constraints(new Leading(12, 12, 12), new Leading(12, 12, 12)));
		add(getBotonRefinarPerfil(), new Constraints(new Trailing(12, 142, 162, 162), new Leading(12, 12, 12)));
		add(getBotonRecomendarPerfil(), new Constraints(new Trailing(160, 162, 162), new Leading(12, 12, 12)));
		add(getBotonCrearGrupo(), new Constraints(new Trailing(12, 142, 260, 600), new Leading(90, 12, 12)));
		add(getJScrollPane0(), new Constraints(new Leading(186, 200, 10, 10), new Leading(70, 150, 10, 10)));
		add(getBotonSeleccionar(), new Constraints(new Leading(407, 10, 10), new Leading(153, 10, 10)));
		add(getJButton0(), new Constraints(new Trailing(12, 142, 220, 398), new Leading(50, 12, 12)));
		setSize(590, 240);
	}

	private JButton getJButton0() {
		if (botonEvaluarJuegos == null) {
			botonEvaluarJuegos = new JButton();
			botonEvaluarJuegos.setText("Evaluar juegos");
			botonEvaluarJuegos.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					botonEvaluarJuegosActionPerformed(event);
				}
			});
		}
		return botonEvaluarJuegos;
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

	
	private JButton getBotonCrearGrupo() {
		if (botonCrearGrupo == null) {
			botonCrearGrupo = new JButton();
			botonCrearGrupo.setText("Crear grupo");
			botonCrearGrupo.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					botonCrearGrupoActionPerformed(event);
				}
			});
		}
		return botonCrearGrupo;
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
			ArrayList<CBRCase> listaMJ = MejoresJuegos.getMejoresJuegos(10);
			Object[][] tablaMJ = new Object[listaMJ.size()][2];
			for (int i=0; i < listaMJ.size(); i++) {
				Game juego = (Game)listaMJ.get(i).getDescription();
				tablaMJ[i][0] = juego.getgameId();
				tablaMJ[i][1] = juego.getName();
			}
			jTable0.setModel(new DefaultTableModel(tablaMJ, new String[] { "ID", "Titulo", }) {
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
		RecomendadorPorPerfil rpp = new RecomendadorPorPerfil();
		rpp.recomendar();
	}
	
	private void botonSeleccionarActionPerformed(ActionEvent event){
		Integer str = (Integer)jTable0.getModel().getValueAt(jTable0.getSelectedRow(), 0);
		//LLamar a la pantalla del producto
	}
	
	private void botonEvaluarJuegosActionPerformed(ActionEvent event) {
		EvaluatorFrame ef = new EvaluatorFrame();
		ef.setDefaultCloseOperation(EvaluatorFrame.DISPOSE_ON_CLOSE);
		ef.getContentPane().setPreferredSize(ef.getSize());
		ef.pack();
		ef.setLocationRelativeTo(null);
		ef.setVisible(true);
	} 
	
	private void botonCrearGrupoActionPerformed(ActionEvent event) {
		CrearGrupo cg = new CrearGrupo();
		cg.setDefaultCloseOperation(EvaluatorFrame.DISPOSE_ON_CLOSE);
		cg.getContentPane().setPreferredSize(cg.getSize());
		cg.pack();
		cg.setLocationRelativeTo(null);
		cg.setVisible(true);
	} 
	
	private void presentarMejoresJuegos()
	{
		ArrayList<CBRCase> mejoresJuegos = MejoresJuegos.getMejoresJuegos(2);
		for(int i = 0; i < mejoresJuegos.size(); i++)
		{
			CBRCase caso = mejoresJuegos.get(i);
			this.jTable0.getModel().setValueAt(((Game)caso.getDescription()).getGameId(), i, 0);
			this.jTable0.getModel().setValueAt(((Game)caso.getDescription()).getName(), i, 1);
		}
	}

}
