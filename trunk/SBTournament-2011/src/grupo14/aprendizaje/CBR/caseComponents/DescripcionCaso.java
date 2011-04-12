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
	private Integer time;
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



	public int getTime() {
		return time;
	}



	public void setTime(int time) {
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
}
