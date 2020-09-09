package xmlreader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import model.DatesAndCurrency;
import model.Tecaj;
import model.Tecajnica;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

@Component
public class XmlReader {

	public Tecajnica queryXML(String start_date, String end_date, List<String> valute) {

		Tecajnica tecajnica = new Tecajnica();

		int start_date_index = 0;
		int end_date_index = 0;

		try {
			File inputFile = new File("C:\\\\Users\\\\andre\\\\Desktop\\\\dtecbs-l.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			NodeList seznam_tecajnic = doc.getElementsByTagName("tecajnica");

			//Find index of start_date and end_date
			for (int i = 0; i < seznam_tecajnic.getLength(); i++) {
				Node tecajnica_d = seznam_tecajnic.item(i);
				if (tecajnica_d.getNodeType() == Node.ELEMENT_NODE) {
					Element tecajnica_datum = (Element) tecajnica_d;
					if (start_date.equals(tecajnica_datum.getAttribute("datum"))) {
						start_date_index = i;
					} else if (end_date.equals(tecajnica_datum.getAttribute("datum"))) {
						end_date_index = i;
					} else {
						continue;
					}
				}
			}

			//Iterate only selected time frame, add only selected currency to list, put date(key) and list(value) to Tecajnica obj
			for (int index = start_date_index; index <= end_date_index; index++) {
				Node q_t = seznam_tecajnic.item(index);
				if (q_t.getNodeType() == Node.ELEMENT_NODE) {
					Element q_tecajnica = (Element) q_t;

					//String with date of current element
					String datum_valute = q_tecajnica.getAttribute("datum");
					List<Tecaj> seznam_tecajev = new ArrayList<Tecaj>();
					NodeList tecaj = q_tecajnica.getElementsByTagName("tecaj");

					for (int count = 0; count < tecaj.getLength(); count++) {
						Node node1 = tecaj.item(count);


						if (node1.getNodeType() == Node.ELEMENT_NODE) {
							Element single_tecaj = (Element) node1;
							for (String valuta : valute) {
								//Only add currency from the list
								if (single_tecaj.getAttribute("oznaka").equals(valuta)) {
									Tecaj tecaj_obj = new Tecaj(single_tecaj.getAttribute("sifra"), single_tecaj.getAttribute("oznaka"), single_tecaj.getTextContent());
									seznam_tecajev.add(tecaj_obj);
								}
							}
						}
					}
					tecajnica.addTecaj(datum_valute, seznam_tecajev);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tecajnica;
	}

	public DatesAndCurrency getAllDates() {
		DatesAndCurrency dc = new DatesAndCurrency();
		try {
			File inputFile = new File("C:\\\\Users\\\\andre\\\\Desktop\\\\dtecbs-l.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			NodeList seznam_tecajnic = doc.getElementsByTagName("tecajnica");

			for (int i = 0; i < seznam_tecajnic.getLength(); i++) {
				Node tecajnica_d = seznam_tecajnic.item(i);
				if (tecajnica_d.getNodeType() == Node.ELEMENT_NODE) {
					Element tecajnica_datum = (Element) tecajnica_d;
					dc.addDate(tecajnica_datum.getAttribute("datum"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dc;
	}
}