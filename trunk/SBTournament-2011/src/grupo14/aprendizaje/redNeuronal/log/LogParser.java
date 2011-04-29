package grupo14.aprendizaje.redNeuronal.log;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class LogParser {
	
	/** Nodo actual de la estructura XML */
	private Node actualNode;
	
	public static void main(String args[]) {
		LogParser lp = new LogParser("log.xml");
		for (int i = 0; i < 5; i++)
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
		BallInfo ballInfo = new BallInfo();
		Node ballNode = nl.item(1);
		NodeList ballNL = ballNode.getChildNodes();
		ballInfo.setPositionX(Float.parseFloat(ballNL.item(1).getAttributes().item(0).getNodeValue()));
		ballInfo.setPositionY(Float.parseFloat(ballNL.item(1).getAttributes().item(1).getNodeValue()));
		ballInfo.setTimeout(Float.parseFloat(ballNL.item(3).getAttributes().item(0).getNodeValue()));
		ballInfo.setVelocityX(Float.parseFloat(ballNL.item(5).getAttributes().item(0).getNodeValue()));
		ballInfo.setVelocityY(Float.parseFloat(ballNL.item(5).getAttributes().item(1).getNodeValue()));
		ballInfo.setWestScore(Integer.parseInt(ballNL.item(7).getAttributes().item(0).getNodeValue()));
		ballInfo.setEastScore(Integer.parseInt(ballNL.item(7).getAttributes().item(1).getNodeValue()));
		// Estado de los jugadores del westTeam
		
		// Un salto de más debido a los saltos de linea del documento
		actualNode = actualNode.getNextSibling().getNextSibling();
		
		return logEntry;
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
