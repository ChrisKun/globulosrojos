package grupo14.players;

/**
 * Acciones de alto nivel que realizarán los jugadores
 * @author markel
 *
 */
//TODO Estaría bien que cada jugador implemente las acciones dependiendo de su rol. Por ejemplo: con IrAPosicionDeEspera un defensa va aun lugar y un delantero a otro
public enum PlayerActions {
	CorrerAlAtaque,		//Subir p'arriba y maricón el último
	CorrerADefensa,		//Bajar a defender
	CorrerHaciaBalon,	//Ir directo hacia el balón
	ChutarAPuerta,		//Autoexplicado
	Chutar,				//Diferente a chutar a puerta en que chuta a cualquier parte
	BloquearContrario,	//Siempre hay que joder un poco
	Desbloquearse,		//Por si el otro equipo esta lleno de anormales
	IrAPosicionDeEspera,//Una posición por ejemplo para cuando se pierde el balón tener el equipo bien ordenado atras (estilo Clemente?)
	Desmarcarse,		//Más o menos alejarse lo más posible de los contrarios (a ser porsible sin terminar perdido en un corner)
	TaparPorteria		//Ponerse entre el balón y la portería para imperdir un gol
}
