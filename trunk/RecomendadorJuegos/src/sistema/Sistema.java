package sistema;

import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbrcore.CBRCaseBase;

public class Sistema {

	private static CBRCaseBase CBusuarios;
	private static CBRCaseBase CBjuegos;
	
	//private static Perfil usuarioActual;
	
	public static CBRCaseBase getCBusuariosInstance() {
		if (CBusuarios == null)
			CBusuarios = new LinealCaseBase();
		return CBusuarios;
	} 
	
	public static CBRCaseBase getCBjuegosInstance() {
		if (CBjuegos == null)
			CBjuegos = new LinealCaseBase();
		return CBjuegos;
	} 
}
