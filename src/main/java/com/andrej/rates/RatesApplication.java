package com.andrej.rates;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RatesApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(RatesApplication.class, args);
		openHomePage();
	}
	
    private static void openHomePage() throws IOException {
        Runtime rt = Runtime.getRuntime();
        rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:8080");
     }
}
