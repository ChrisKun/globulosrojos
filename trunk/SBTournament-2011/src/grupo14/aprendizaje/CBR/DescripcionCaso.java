package grupo14.aprendizaje.CBR;

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
	private int ballPosition;
	/**
	 * Specifies the time of the match in seconds
	 */
	private int time;
	/**
	 * Specifies the number of goals scored by our team
	 */
	private int ourGoals;
	/**
	 * Specifies the number of goals scored by the other team
	 */
	private int theirGoals;



	public ArrayList<Integer> getNumPlayersEachOctant() {
		return numPlayersEachOctant.octants;
	}



	public void setNumPlayersEachOctant(ArrayList<Integer> numPlayersEachOctant) {
		this.numPlayersEachOctant.octants = numPlayersEachOctant;
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
	
	public String toString()
	{
		return "Not yet implemented";
	}
}
