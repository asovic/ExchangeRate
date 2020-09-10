package com.andrej.rates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestFilter {
	
	public boolean checkDateValidity(String start_date, String end_date) throws ParseException {
		boolean result = true;
		Date s_date = new SimpleDateFormat("yyyy-MM-dd").parse(start_date);
		Date e_date = new SimpleDateFormat("yyyy-MM-dd").parse(end_date);
		if (s_date.after(e_date)) {
			result = false;
		}
		return result;
	}
}
