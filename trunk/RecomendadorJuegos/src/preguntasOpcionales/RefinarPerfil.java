package preguntasOpcionales;

public class RefinarPerfil {

	
	public void execute()
	{	
		InterfazRefinarPerfil interfaz = new InterfazRefinarPerfil();
		//interfaz.setDefaultCloseOperation(interfaz.EXIT_ON_CLOSE);
		interfaz.setTitle("Refinar Perfil");
		interfaz.getContentPane().setPreferredSize(interfaz.getSize());
		interfaz.pack();
		interfaz.setLocationRelativeTo(null);
		interfaz.setVisible(true);
	}
}
