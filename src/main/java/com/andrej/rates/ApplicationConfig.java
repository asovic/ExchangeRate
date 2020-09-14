package com.andrej.rates;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import model.DatesAndCurrency;
import model.Tecajnica;

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
}
