package grupo14.aprendizaje.redNeuronal.log;

import java.util.ArrayList;

public class LogEntry {
	private int time;
	private BallInfo ballInfo;
	private ArrayList<PlayerInfo> westTeamInfo;
	private ArrayList<PlayerInfo> eastTeamInfo;
	
	public LogEntry() {
		time = 0;
		ballInfo = null;
		westTeamInfo = new ArrayList<PlayerInfo>();
		eastTeamInfo = new ArrayList<PlayerInfo>();
	}
	
	public int getTime() {return time;}
	public void setTime(int time) {this.time = time;}
	public BallInfo getBallInfo() {return ballInfo;}
	public void setBallInfo(BallInfo ballInfo) {this.ballInfo = ballInfo;}
	public ArrayList<PlayerInfo> getWestTeamInfo() {return westTeamInfo;}
	public void setWestTeamInfo(ArrayList<PlayerInfo> westTeamInfo) {this.westTeamInfo = westTeamInfo;}
	public ArrayList<PlayerInfo> getEastTeamInfo() {return eastTeamInfo;}
	public void setEastTeamInfo(ArrayList<PlayerInfo> eastTeamInfo) {this.eastTeamInfo = eastTeamInfo;}
}
