package grupo14.aprendizaje.CBR.caseComponents;

import grupo14.aprendizaje.CBR.OctantsState;

import java.util.ArrayList;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;
import jcolibri.extensions.classification.ClassificationSolution;

public class DescripcionCaso implements CaseComponent{

	private String caseId;
	/**
	 * Array that stores the number of players in each octant of the playing field
	 */
	private OctantsState numPlayersEachOctant;
	/**
	 * Specifies the octant where the ball is located private in
	 */
	private Integer ballPosition;
	/**
	 * Specifies the time of the match in seconds
	 */
	private Long time;
	/**
	 * Specifies the number of goals scored by our team
	 */
	private Integer ourGoals;
	/**
	 * Specifies the number of goals scored by the other team
	 */
	private Integer theirGoals;

	
	public DescripcionCaso()
	{
		this.numPlayersEachOctant = new OctantsState();
	}

	public OctantsState getNumPlayersEachOctant() {
		return this.numPlayersEachOctant;
	}

	public void setNumPlayersEachOctant(OctantsState numPlayersEachOctant) {
		this.numPlayersEachOctant = numPlayersEachOctant;
	}



	public int getBallPosition() {
		return ballPosition;
	}



	public void setBallPosition(int ballPosition) {
		this.ballPosition = ballPosition;
	}



	public Long getTime() {
		return time;
	}



	public void setTime(Long time) {
		this.time = time;
	}



	public int getOurGoals() {
		return ourGoals;
	}



	public void setOurGoals(int ourGoals) {
		this.ourGoals = ourGoals;
	}



	public int getTheirGoals() {
		return theirGoals;
	}



	public void setTheirGoals(int theirGoals) {
		this.theirGoals = theirGoals;
	}



	public Attribute getIdAttribute()
	{
		return new Attribute ("caseId", DescripcionCaso.class);
	}
	
	
	
	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String toString()
	{
		return this.caseId + this.ballPosition + this.ourGoals + this.theirGoals + this.time;
	}

	/**
	 * Devuelve si hay mas jugadores en el lado este del campo
	 * @return true si hay mas jugadores en el este, false en caso contrario
	 */
	public boolean masJugadoresEnElEste() {
		ArrayList<Integer> matchState = this.numPlayersEachOctant.octants;
		int jugadoresEnElEste = matchState.get(0) + matchState.get(1) + matchState.get(4) + matchState.get(5);
		if(jugadoresEnElEste >= 5)
			return true;
		else
			return false;
	}
}
