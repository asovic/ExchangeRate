package com.andrej.rates;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import model.DatesAndCurrency;
import model.Tecajnica;
import xmlreader.ModelBuilder;
import xmlreader.ReadXML;

@Configuration
public class ApplicationConfig {
	
	@Bean
	public Tecajnica tecajnica() {
		return new Tecajnica();
	}
	
	@Bean
	public DatesAndCurrency dc() {
		return new DatesAndCurrency();
	}
	
//	@Bean
//	public ReadXML reader() {
//		return new ReadXML();
//	}
	
	@Bean
	public ModelBuilder mb() {
		return new ModelBuilder();
	}
}
