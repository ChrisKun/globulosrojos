package jcolibri.method.retrieve.NNRetrieval.NNConfig;

import sistema.Game;
import jcolibri.cbrcore.Attribute;
import jcolibri.method.retrieve.NNRetrieval.similarity.local.AverageList;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;

public class SimConfigJuegos extends NNConfig{

	public SimConfigJuegos() {
		// Funcion de similitud global
		this.setDescriptionSimFunction(new Average());
		// Funciones de similitud locales
		this.addMapping(new Attribute("minNumPlayers", Game.class), new Interval(2.0));
		this.addMapping(new Attribute("maxNumPlayers", Game.class), new Interval(2.0));
		this.addMapping(new Attribute("minBestNumPlayers", Game.class), new Interval(2.0));
		this.addMapping(new Attribute("maxBestNumPlayers", Game.class), new Interval(2.0));
		this.addMapping(new Attribute("minRecNumPlayers", Game.class), new Interval(2.0));
		this.addMapping(new Attribute("maxRecNumPlayers", Game.class), new Interval(2.0));
		this.addMapping(new Attribute("playingTime", Game.class), new Interval(30.0));
		this.addMapping(new Attribute("age", Game.class), new Interval(10.0));
		this.addMapping(new Attribute("subdomains", Game.class), new AverageList());
		this.addMapping(new Attribute("categories", Game.class), new AverageList());
		this.addMapping(new Attribute("mechanics", Game.class), new AverageList());}
}
