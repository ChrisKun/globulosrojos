package grupo14.aprendizaje.redNeuronal.log;

public class BallInfo {
	private float positionX;
	private float positionY;
	private float timeout;
	private float velocityX;
	private float velocityY;
	private int westScore;
	private int eastScore;
	
	public BallInfo() {
		positionX = 0.0f;
		positionY = 0.0f;
		timeout = 0.0f;
		velocityX = 0.0f;
		velocityY = 0.0f;
		westScore = 0;
		eastScore = 0;
	}
	
	public float getPositionX() {return positionX;}
	public void setPositionX(float positionX) {this.positionX = positionX;}
	public float getPositionY() {return positionY;}
	public void setPositionY(float positionY) {this.positionY = positionY;}
	public float getTimeout() {return timeout;}
	public void setTimeout(float timeout) {this.timeout = timeout;}
	public float getVelocityX() {return velocityX;}
	public void setVelocityX(float velocityX) {this.velocityX = velocityX;}
	public float getVelocityY() {return velocityY;}
	public void setVelocityY(float velocityY) {this.velocityY = velocityY;}
	public int getWestScore() {return westScore;}
	public void setWestScore(int westScore) {this.westScore = westScore;}
	public int getEastScore() {return eastScore;}
	public void setEastScore(int eastScore) {this.eastScore = eastScore;}
}
