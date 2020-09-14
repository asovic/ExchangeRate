package com.andrej.rates;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RatesApplication {

	public static void main(String[] args) throws IOException {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(RatesApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);
		openHomePage();
	}
	
    private static void openHomePage() {
        Runtime rt = Runtime.getRuntime();
        String os = System.getProperty("os.name").toLowerCase();
        
            String page = "http://localhost:8080";
            System.out.println("Opening: "+ page);

            String myOS = System.getProperty("os.name").toLowerCase();
            System.out.println("(Your operating system is: "+ myOS +")\n");

            try {
                if(os.indexOf( "win" ) >= 0) {
                    System.out.println(" -- Launching browser on windows ...");
                    rt.exec( "rundll32 url.dll,FileProtocolHandler " + page);
                } else {
                    if(os.indexOf( "mac" ) >= 0) {
                        System.out.println(" -- Going on Apple with 'open'...");
                        rt.exec("open " + page);
                    } 
                    else if(os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) {
                        System.out.println(" -- Opening browser on Linux with 'xdg-open'...");
                        rt.exec("xdg-open " + page);
                    }
                    else 
                        System.out.println("Unable to launch a browser in your OS");
                }
                System.out.println("\nFinished.");
            }
            catch(IOException eek) {
                System.out.println("**Error: "+ eek.getMessage());
            }
        }
}
