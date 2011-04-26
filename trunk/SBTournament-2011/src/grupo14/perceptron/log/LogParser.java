package grupo14.perceptron.log;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class LogParser {
	
	/** Lista de nodos del XML correspondientes a los instantes de tiempo
	 * del log. */
	private NodeList logNodes;
	/** Índice que apunta al siguiente nodo XML. */
	private int nextNode;
	
	public static void main(String args[]) {
		LogParser lp = new LogParser("log.xml");
	}
	
	/** Constructora dada la ruta al log. 
	 * @param logPath Ruta al fichero log. */
	public LogParser(String logPath) {
		Document document = parseXML(logPath);
		Node root = document.getFirstChild();
		logNodes = root.getChildNodes();
		nextNode = 1;
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
