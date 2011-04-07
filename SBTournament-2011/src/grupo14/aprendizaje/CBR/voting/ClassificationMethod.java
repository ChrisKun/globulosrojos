package grupo14.aprendizaje.CBR.voting;

import java.util.Collection;

import jcolibri.method.retrieve.RetrievalResult;

/**
 * Intefaz a implementar por cada m�todo de clasificaci�n.
 * Un m�todo de clasificaci�n recibe una lista de documentos ordenados seg�n su similitud a la consulta y realiza una predicci�n de su categoria de acuerdo a ellos.
 * 
 * @author GAIA - Juan A. Recio Garc�a
 *
 */
public interface ClassificationMethod {
	
	/**
	 * Realiza una predicci�n a partir de una lista de de documentos ordenados seg�n su similitud a la consulta.
	 * @param cases lista de casos ordenados seg�n su similitud a la consulta
	 * @return la predicci�n.
	 */
	public Prediction getPredictedClass(Collection<RetrievalResult> cases);
	
}
