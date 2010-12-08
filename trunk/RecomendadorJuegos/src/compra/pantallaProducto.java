package compra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import jcolibri.cbrcore.CBRCase;

import sistema.*;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import principal.MejoresJuegos;

//VS4E -- DO NOT REMOVE THIS LINE!
public class pantallaProducto extends JFrame {

	private static final long serialVersionUID = 1L;
	Integer id;
	private JLabel jLabel0; 
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JLabel jLabel10;
	private JLabel jLabel11;
	private JLabel jLabel12;
	private JLabel jLabel13;
	private JLabel jLabel14;
	private JButton botonComprar;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JButton botonSeleccionar;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	private Game juego;
	private boolean puedeComprar;
	
	public pantallaProducto(Game juego) {
		this.puedeComprar = true;
		this.juego = juego;
		initComponents();
//		paint2();
	}
//	public void paint2()
//	{
//		//Dimension tamanio = jPanel0.getSize();
//		Image image = new ImageIcon("play.gif").getImage();
//		((Object) jPanel0).setImagen(image);
//		//g.drawImage(image, 0, 0, 10, 10, null);
//		//super.paintComponents(g);
//	}

	private void initComponents() {
		jLabel0 = new JLabel();
		String[] urlImagen;
		urlImagen = juego.getImage().split("/"); 
		jLabel0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/"+urlImagen[4])));
		getJLabel8().setText(juego.getName());
		getJLabel9().setText(juego.getUrl());
		getJLabel10().setText(juego.getAge().toString());
		getJLabel11().setText(juego.getPlayingTime().toString());
		getJLabel12().setText(juego.getMaxRecNumPlayers().toString());
		getJLabel13().setText(juego.getMaxBestNumPlayers().toString());
		getJLabel9().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
			try{
				
				Runtime.getRuntime().exec(juego.getUrl());
			}catch (Exception e1) {
				// TODO: handle exception
			}
			}
		});
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(25, 12, 12), new Leading(180, 12, 12)));
		add(getJLabel1(), new Constraints(new Leading(25, 12, 12), new Leading(28, 12, 12)));
		add(getJLabel2(), new Constraints(new Leading(25, 12, 12), new Leading(50, 12, 12)));
		add(getJLabel3(), new Constraints(new Leading(25, 12, 12), new Leading(72, 12, 12)));
		add(getJLabel4(), new Constraints(new Leading(25, 12, 12), new Leading(94, 12, 12)));
		add(getJLabel5(), new Constraints(new Leading(25, 12, 12), new Leading(116, 12, 12)));
		add(getJLabel6(), new Constraints(new Leading(25, 12, 12), new Leading(138, 12, 12)));
		add(getJLabel7(), new Constraints(new Leading(25, 12, 12), new Leading(160, 12, 12)));
		add(getJLabel8(), new Constraints(new Leading(230, 12, 12), new Leading(28, 12, 12)));
		add(getJLabel9(), new Constraints(new Leading(230, 12, 12), new Leading(50, 12, 12)));
		add(getJLabel10(), new Constraints(new Leading(230, 12, 12), new Leading(72, 12, 12)));
		add(getJLabel11(), new Constraints(new Leading(230, 12, 12), new Leading(94, 12, 12)));
		add(getJLabel12(), new Constraints(new Leading(230, 12, 12), new Leading(116, 12, 12)));
		add(getJLabel13(), new Constraints(new Leading(230, 12, 12), new Leading(138, 12, 12)));
		add(getBotonComprar(), new Constraints(new Leading(230, 12, 12), new Leading(175, 12, 12)));
		add(getJLabel14(), new Constraints(new Leading(230, 12, 12), new Leading(210, 12, 12)));
		add(getJScrollPane0(), new Constraints(new Leading(230, 200, 10, 10), new Leading(230, 100, 10, 10)));
		add(getBotonSeleccionar(), new Constraints(new Leading(230, 12, 12), new Leading(340, 12, 12)));
		
		if (!puedeComprar)
			this.botonComprar.setEnabled(false);
		/*
		 * 8=  nombre, url, edadmin, tiempo jueg, jug rec, jug best
		 * 0 = imagen
		 */
		setSize(600, 400);
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
			jTable0.setModel(new JuegosParecidosTableModel(juego));
		}
		return jTable0;
	}

	private JButton getBotonComprar() {
		if (botonComprar == null) {
			botonComprar = new JButton();
			botonComprar.setText("Comprar Juego");
			botonComprar.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					botonComprarActionPerformed(event);
				}
			});
		}
		return botonComprar;
	}
	
	private JButton getBotonSeleccionar() {
		if (botonSeleccionar == null) {
			botonSeleccionar = new JButton();
			botonSeleccionar.setText("Seleccionar juego");
			botonSeleccionar.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					botonSeleccionarJuegoActionPerformed(event);
				}
			});
		}
		return botonSeleccionar;
	}
	
	private JLabel getJLabel14() {
		if (jLabel14 == null) {
			jLabel14 = new JLabel();
			jLabel14.setText("A los usuarios que les gustó este juego también les gustó:");
		}
		return jLabel14;
	}
	
	private JLabel getJLabel13() {
		if (jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setText("jLabel13");
		}
		return jLabel13;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText("jLabel12");
		}
		return jLabel12;
	}

	private JLabel getJLabel11() {
		if (jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("jLabel11");
		}
		return jLabel11;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("jLabel10");
		}
		return jLabel10;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("jLabel9");
		}
		return jLabel9;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("jLabel8");
		}
		return jLabel8;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
		}
		return jLabel7;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Mejor numero de jugadores:");
		}
		return jLabel6;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Jugadores recomendados:");
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Tiempo de juego:");
		}
		return jLabel4;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Edad minima:");
		}
		return jLabel3;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("URL:");
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Nombre:");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("jLabel0");
		}
		return jLabel0;
	}

	private void botonComprarActionPerformed(ActionEvent event) {
		Perfil perfil = Sistema.getPerfil();
		if (!perfil.getListaValoraciones().containsKey(juego.getgameId())) {
			perfil.getListaValoraciones().put(juego.getgameId(), (float)-1.0);
			JOptionPane.showMessageDialog(pantallaProducto.this, "Juego comprado con éxito", "Informacion",JOptionPane.INFORMATION_MESSAGE);
		}
		else
			JOptionPane.showMessageDialog(pantallaProducto.this, "El juego ya estaba comprado", "Informacion",JOptionPane.INFORMATION_MESSAGE);
		this.dispose();
	} 
	
	private void botonSeleccionarJuegoActionPerformed(ActionEvent event) {
		Integer number = (Integer)jTable0.getModel().getValueAt(jTable0.getSelectedRow(), 0);
		for(CBRCase caso : Sistema.getCBjuegosInstance().getCases())
		{
			if(((Game)caso.getDescription()).getgameId() == number)
			{
				pantallaProducto pantProducto = new pantallaProducto((Game)caso.getDescription());
				if (!puedeComprar)
					pantProducto.disableComprar();
				pantProducto.setVisible(true);
				this.dispose();
				return;
			}
		}
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
	
	public void disableComprar() {
		this.puedeComprar = false;
		this.botonComprar.setEnabled(false);
	}

	/**
	 * Main entry of the class.
	 * Note: This class is only created so that you can easily preview the result at runtime.
	 * It is not expected to be managed by the designer.
	 * You can modify it as you like.
	 */
//	public static void main(String[] args) {
//		installLnF();
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				pantallaProducto frame = new pantallaProducto();
//				frame.setDefaultCloseOperation(pantallaProducto.EXIT_ON_CLOSE);
//				frame.setTitle("pantallaProducto");
//				frame.getContentPane().setPreferredSize(frame.getSize());
//				frame.pack();
//				frame.setLocationRelativeTo(null);
//				frame.setVisible(true);
//			}
//		});
//	}

}
