package xmlreader;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Tecaj;
import model.Tecajnica;

public class ReadXML {

	private static String xmlPath = "https://www.bsi.si/_data/tecajnice/dtecbs-l.xml";
	private static Tecajnica whole_tecajnica;

	private static ReadXML theOnlyOne = new ReadXML();
	private ReadXML() {
		readNodes();
	}
	public static ReadXML getInstance() {
		return theOnlyOne;
	}

	public Tecajnica getWholeTecajnica() {
		return whole_tecajnica;
	}

	public static void readNodes() {
		NodeList seznam_tecajnic = null;
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
		whole_tecajnica = (Tecajnica) makeItAnObject(seznam_tecajnic);
	}

	public static Tecajnica makeItAnObject(NodeList whole_doc) {
		Tecajnica tecajnica = new Tecajnica();
		for (int i = 0; i<whole_doc.getLength(); i++) {
			Node q_t = whole_doc.item(i);
			if (q_t.getNodeType() == Node.ELEMENT_NODE) {
				Element q_tecajnica = (Element) q_t;
				String datum_valute = q_tecajnica.getAttribute("datum");
				List<Tecaj> seznam_tecajev = new ArrayList<Tecaj>();
				NodeList tecaj = q_tecajnica.getElementsByTagName("tecaj");
				for (int count = 0; count < tecaj.getLength(); count++) {
					Node node1 = tecaj.item(count);
					if (node1.getNodeType() == Node.ELEMENT_NODE) {
						Element single_tecaj = (Element) node1;
						Tecaj tecaj_obj = new Tecaj(single_tecaj.getAttribute("sifra"), single_tecaj.getAttribute("oznaka"), single_tecaj.getTextContent());
						seznam_tecajev.add(tecaj_obj);
					}
				}
				tecajnica.addTecaj(datum_valute, seznam_tecajev);
			}
		}
		return tecajnica;
	}
}
