package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatesAndCurrency {
	
	private List<String> dates = new ArrayList<String>();
	private List<String> currency = Arrays.asList("USD", "JPY","BGN","CZK","DKK","GBP", "HUF", "PLN", "RON", "SEK",
												"ISK", "CHF", "NOK", "HRK", "RUB", "TRY", "AUD", "BRL", "CAD", "CNY",
												"HKD", "IDR", "ILS", "INR", "KRW", "MXN", "MYR", "NZD", "PHP", "SGD",
												"THB", "ZAR");
	
	public void addDate(String date) {
		dates.add(date);
	}
	
	public List<String> getCurrency() {
		return currency;
	}
	
	public List<String> getDates() {
		return dates;
	}
}
