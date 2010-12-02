package RecomendadorJuegos;

import java.util.ArrayList;
import java.util.Collection;

import jcolibri.cbrcore.CBRCase;

public class prueba 
{
	public static void main (String[] args)
	{
		Perfil perfil = new Perfil();
		perfil.Registrar("Ibai");
		if(perfil.Loguear("Ibai"))
			System.out.println("Logueado");
		ArrayList lista = new ArrayList(1);
		lista.add("LOL");
		lista.add("Lel");
		perfil.RegistrarDatosDelUsuario(lista);
		perfil.Registrar("Jon");
		if(perfil.Loguear("Jon"))
			System.out.println("Logueado");
		lista.add("liil");
		perfil.RegistrarDatosDelUsuario(lista);
		System.out.print("FINISH");
		ProfileConnector pc = new ProfileConnector();
		Collection<CBRCase> lol = pc.retrieveAllCases();
		System.out.println(lol);
	}
}
