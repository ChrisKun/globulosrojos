package grupo14.aprendizaje.redNeuronal.log;

public class PlayerInfo {
	private int robotId;
	private String controlSystem;
	private int playerId;
	private float positionX;
	private float positionY;
	private float headingX;
	private float headingY;
	private float speed;
	
	public PlayerInfo() {
		robotId = 0;
		controlSystem = null;
		playerId = 0;
		positionX = 0.0f;
		positionY = 0.0f;
		headingX = 0.0f;
		headingY = 0.0f;
		speed = 0.0f;
	}
	
	public int getRobotId() {return robotId;}
	public void setRobotId(int robotId) {this.robotId = robotId;}
	public String getControlSystem() {return controlSystem;}
	public void setControlSystem(String controlSystem) {this.controlSystem = controlSystem;}
	public int getPlayerId() {return playerId;}
	public void setPlayerId(int playerId) {this.playerId = playerId;}
	public float getPositionX() {return positionX;}
	public void setPositionX(float positionX) {this.positionX = positionX;}
	public float getPositionY() {return positionY;}
	public void setPositionY(float positionY) {this.positionY = positionY;}
	public float getHeadingX() {return headingX;}
	public void setHeadingX(float headingX) {this.headingX = headingX;}
	public float getHeadingY() {return headingY;}
	public void setHeadingY(float headingY) {this.headingY = headingY;}
	public float getSpeed() {return speed;}
	public void setSpeed(float speed) {this.speed = speed;}
	
	
}
