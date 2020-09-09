package com.andrej.rates;

import java.util.List;
import java.util.TreeMap;

import model.Tecaj;
import xmlreader.ModelBuilder;

public class OCcalculator {
	
	public double[] calculateOC(String start_date, String end_date, List<String> valute) {
		
		List<Tecaj> start_date_values;
		List<Tecaj> end_date_values;
		
		ModelBuilder mb = new ModelBuilder();
		TreeMap<String, List<Tecaj>> podatki = mb.queryDB(start_date, end_date, valute).getSortedTecajnica();
		
		start_date_values = podatki.firstEntry().getValue();
		end_date_values = podatki.lastEntry().getValue();

		double first_currency_result = Double.parseDouble(start_date_values.get(0).getTecaj()) / Double.parseDouble(end_date_values.get(0).getTecaj());
		double second_currency_result = Double.parseDouble(start_date_values.get(1).getTecaj()) / Double.parseDouble(end_date_values.get(1).getTecaj());
		
		double first_percentage = round((first_currency_result - 1) * 100);
		double second_percentage = round((second_currency_result -1) * 100);
		
		double first_vs_second = round(first_percentage - second_percentage);
		double[] result = {first_percentage, second_percentage, first_vs_second};
		return result;
	}
	
	static double round(double value) {
		return (double) Math.round(value * 1000) / 1000.0;
	}
}
