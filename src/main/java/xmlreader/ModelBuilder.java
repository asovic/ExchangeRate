package xmlreader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.DatesAndCurrency;
import model.Tecaj;
import model.Tecajnica;

@Component
public class ModelBuilder extends ReadXML {

	NodeList seznam_tecajnic;
	private int[] date_index = new int[2];

	public int[] getStartEndIndex(String start_date, String end_date) {
		seznam_tecajnic = readNodes();
		for (int i = 0; i < seznam_tecajnic.getLength(); i++) {
			Node tecajnica_d = seznam_tecajnic.item(i);
			if (tecajnica_d.getNodeType() == Node.ELEMENT_NODE) {
				Element tecajnica_datum = (Element) tecajnica_d;
				if (start_date.equals(tecajnica_datum.getAttribute("datum"))) {
					date_index[0] = i;
				} else if (end_date.equals(tecajnica_datum.getAttribute("datum"))) {
					date_index[1] = i;
				} else {
					continue;
				}
			}
		}
		return date_index;
	}

	public DatesAndCurrency getAllDates() {
		DatesAndCurrency dates_currency = new DatesAndCurrency();
		seznam_tecajnic = readNodes();
		for (int i = 0; i < seznam_tecajnic.getLength(); i++) {
			Node tecajnica_d = seznam_tecajnic.item(i);
			if (tecajnica_d.getNodeType() == Node.ELEMENT_NODE) {
				Element tecajnica_datum = (Element) tecajnica_d;
				dates_currency.addDate(tecajnica_datum.getAttribute("datum"));
			}
		}
		return dates_currency;
	}

	public Tecajnica queryDB(String start_date, String end_date, List<String> valute) {
		Tecajnica tecajnica = new Tecajnica();
		getStartEndIndex(start_date, end_date);
		for (int index = date_index[0]; index <= date_index[1]; index++) {
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
		return tecajnica;
	}
}
