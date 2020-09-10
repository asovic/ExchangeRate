package xmlreader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.stereotype.Component;
import model.DatesAndCurrency;
import model.Tecaj;
import model.Tecajnica;

@Component
public class ModelBuilder {

	private TreeMap<String, List<Tecaj>> whole_tecajnica = ReadXML.getInstance().getWholeTecajnica().getSortedTecajnica();

	public DatesAndCurrency getAllDates() {
		DatesAndCurrency dates_currency = new DatesAndCurrency();
		for (String key : whole_tecajnica.keySet()) {
			dates_currency.addDate(key);
		}
		return dates_currency;
	}

	public Tecajnica queryDB(String start_date, String end_date, List<String> valute) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(end_date));
		c.add(Calendar.DATE, 1);
		end_date = sdf.format(c.getTime());

		SortedMap<String,List<Tecaj>> cut_out;
		Tecajnica queryTecajnica = new Tecajnica();
		cut_out = whole_tecajnica.subMap(start_date, end_date);
		Set<String> keySet = cut_out.keySet();
		for (String key : keySet) {
			List<Tecaj> fullList = cut_out.get(key);
			List<Tecaj> tempList = new ArrayList<Tecaj>();
			for (Tecaj tecaj : fullList) {
				if (valute.contains(tecaj.getOznaka())) {
					tempList.add(tecaj);
				}
			}
			cut_out.replace(key, tempList);
		}
		queryTecajnica.setTecajnica(cut_out);
		return queryTecajnica;
	}
}
