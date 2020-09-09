package xmlreader;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ReadXML {
	
	private String xmlPath = "https://www.bsi.si/_data/tecajnice/dtecbs-l.xml";
	private NodeList seznam_tecajnic;
	
	public NodeList readNodes() {
		try {
			URL url = new URL(xmlPath);
			InputStream stream = url.openStream();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stream);
			doc.getDocumentElement().normalize();

			seznam_tecajnic = doc.getElementsByTagName("tecajnica");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seznam_tecajnic;
	}
}
