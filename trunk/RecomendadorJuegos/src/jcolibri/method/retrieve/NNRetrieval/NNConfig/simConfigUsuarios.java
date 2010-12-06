package jcolibri.method.retrieve.NNRetrieval.NNConfig;

import sistema.Perfil;
import jcolibri.cbrcore.Attribute;
import jcolibri.method.retrieve.NNRetrieval.similarity.local.AverageMap;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;

public class simConfigUsuarios extends NNConfig{

	public simConfigUsuarios() {
		// Función de similitud global
		this.setDescriptionSimFunction(new Average());
		// Funciones de similitud locales
		this.addMapping(new Attribute("nickName", Perfil.class), new Equal());
		this.addMapping(new Attribute("age", Perfil.class), new Interval(10.0));
		this.addMapping(new Attribute("gender", Perfil.class), new Equal());
		this.addMapping(new Attribute("listaValoraciones", Perfil.class), new AverageMap());
		this.addMapping(new Attribute("tieneBuenaMemoria", Perfil.class), new Equal());
		this.addMapping(new Attribute("formaDeSer", Perfil.class), new Equal());
		this.addMapping(new Attribute("tienePaciencia", Perfil.class), new Equal());
	}
}
