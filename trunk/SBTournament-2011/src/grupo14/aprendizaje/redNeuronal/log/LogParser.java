package grupo14.aprendizaje.redNeuronal.log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class LogParser {
	
	/** Nodo actual de la estructura XML */
	private Node actualNode;
	
	public static void main(String args[]) {
		LogParser lp = new LogParser("log.xml");
		//for (int i = 0; i < 5; i++)
			lp.getNextLogEntry();
		
	}
	
	/** Constructora dada la ruta al log. 
	 * @param logPath Ruta al fichero log. */
	public LogParser(String logPath) {
		Document document = parseXML(logPath);
		Node root = document.getFirstChild();
		actualNode = root.getChildNodes().item(3);
	}
	
	/** Recupera el siguiente nodo del log en un formato de clases. 
	 * @return El siguiente nodo del fichero XML con formato de clase. */
	public LogEntry getNextLogEntry() {
		LogEntry logEntry = new LogEntry();
		
		// Tiempo del log
		logEntry.setTime(Integer.parseInt(actualNode.getAttributes().item(0).getNodeValue()));
		
		NodeList nl = actualNode.getChildNodes();
		// Estado de la bola
		BallInfo ballInfo = getBallInfo(nl.item(1));
		logEntry.setBallInfo(ballInfo);
		
		// Estado de los jugadores del westTeam
		ArrayList<PlayerInfo> westTeamInfo = getTeamInfo(nl.item(3));
		logEntry.setWestTeamInfo(westTeamInfo);
		
		// Estado de los jugadores del eastTeam
		ArrayList<PlayerInfo> eastTeamInfo = getTeamInfo(nl.item(5));
		logEntry.setEastTeamInfo(eastTeamInfo);
		
		// Un salto de más debido a los saltos de linea del documento
		actualNode = actualNode.getNextSibling().getNextSibling();
		
		return logEntry;
	}
	
	/** Obtiene la información de la bola a partir de su nodo XML. 
	 * @param ballNode Nodo XML que tiene la información de la bola.
	 * @return Información de la bola en un objeto de la clase BallInfo. */
	private BallInfo getBallInfo(Node ballNode) {
		BallInfo ballInfo = new BallInfo();
		
		NodeList ballNL = ballNode.getChildNodes();
		ballInfo.setPositionX(Float.parseFloat(ballNL.item(1).getAttributes().item(0).getNodeValue()));
		ballInfo.setPositionY(Float.parseFloat(ballNL.item(1).getAttributes().item(1).getNodeValue()));
		ballInfo.setTimeout(Float.parseFloat(ballNL.item(3).getAttributes().item(0).getNodeValue()));
		ballInfo.setVelocityX(Float.parseFloat(ballNL.item(5).getAttributes().item(0).getNodeValue()));
		ballInfo.setVelocityY(Float.parseFloat(ballNL.item(5).getAttributes().item(1).getNodeValue()));
		ballInfo.setWestScore(Integer.parseInt(ballNL.item(7).getAttributes().item(0).getNodeValue()));
		ballInfo.setEastScore(Integer.parseInt(ballNL.item(7).getAttributes().item(1).getNodeValue()));
		
		return ballInfo;
	}
	
	/** Obtiene la información del equipo a partir de su nodo XML.
	 * @param teamNode Nodo del equipo.
	 * @return Una lista de objetos de la clase PlayerInfo. */
	private ArrayList<PlayerInfo> getTeamInfo(Node teamNode) {
		// Estado de los jugadores del eastTeam
		ArrayList<PlayerInfo> teamInfo = new ArrayList<PlayerInfo>();
		for (int iPlayer = 0; iPlayer < 5; iPlayer++) {
			PlayerInfo playerInfo = new PlayerInfo();
			Node playerNode = teamNode.getChildNodes().item(iPlayer * 2 + 1);
			NodeList playerNL = playerNode.getChildNodes();
			NodeList robotNL = playerNL.item(1).getChildNodes();
			
			playerInfo.setRobotId(Integer.parseInt(playerNode.getAttributes().item(1).getNodeValue()));
			playerInfo.setControlSystem(playerNode.getAttributes().item(0).getNodeValue());
			playerInfo.setPlayerId(Integer.parseInt(playerNL.item(1).getAttributes().item(1).getNodeValue()));
			playerInfo.setPositionX(Float.parseFloat(robotNL.item(1).getAttributes().item(0).getNodeValue()));
			playerInfo.setPositionY(Float.parseFloat(robotNL.item(1).getAttributes().item(1).getNodeValue()));
			playerInfo.setHeadingX(Float.parseFloat(robotNL.item(3).getAttributes().item(0).getNodeValue()));
			playerInfo.setHeadingY(Float.parseFloat(robotNL.item(3).getAttributes().item(1).getNodeValue()));
			playerInfo.setSpeed(Float.parseFloat(robotNL.item(5).getAttributes().item(0).getNodeValue()));
			
			teamInfo.add(playerInfo);
		}
		
		return teamInfo;
	}
	
	/** Parsea un fichero XML dada la ruta al fichero. 
	 * @param path Ruta al fichero XML. */
	private Document parseXML(String path) {
		Document document = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File(path));
		}
		catch(ParserConfigurationException e){
			System.err.println("No se ha podido crear una instancia de DocumentBuilder");
		}catch(SAXException e){
			System.err.println("Error SAX al parsear el archivo");
		}catch(IOException e){
			System.err.println("Se ha producido un error de entrada salida");
		}
		
		return document;
	}
}
