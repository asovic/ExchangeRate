package com.andrej.rates;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import graphing.Graph;
import model.DatesAndCurrency;
import model.Tecajnica;
import xmlreader.ModelBuilder;

@Controller
public class RatesController {
	
	@Autowired
	private ApplicationContext context;

	@Autowired
	Tecajnica tecajnica;

	@Autowired
	ModelBuilder mb;
	
	private DatesAndCurrency dc;

	@GetMapping(value = "/")
	public String getIndex(Model model) {
		ModelBuilder mb = new ModelBuilder();
		dc = mb.getAllDates();
		List<String> dates = dc.getDates();
		List<String> valute = dc.getCurrency();
		model.addAttribute("dates", dates);
		model.addAttribute("valute", valute);
		return "index";
	}
	
	@GetMapping(value = "/index")
	public String nonRefreshIndex(Model model) {
		List<String> dates = dc.getDates();
		List<String> valute = dc.getCurrency();
		model.addAttribute("dates", dates);
		model.addAttribute("valute", valute);
		return "index";
	}

	@PostMapping(value = "/selected")
	public String collect(
			@RequestParam("start_date") String start_date, 
			@RequestParam("end_date") String end_date, 
			@RequestParam("valute") List<String> valute, Model model) throws IOException, ParseException {
		RequestFilter rf = new RequestFilter();
		if (rf.checkDateValidity(start_date, end_date)) {
			ModelBuilder mb = new ModelBuilder();
			tecajnica = mb.queryDB(start_date, end_date, valute);
			model.addAttribute("graph_image", Base64.getEncoder().encodeToString(Graph.makeMeAChart(tecajnica)));
			model.addAttribute("rezultat", tecajnica.getSortedTecajnica());
			return "Result";
		} else {
		     model.addAttribute("error", "Please select end date past start date.");
			return "redirect:/";
		}
	}

	@GetMapping(value = "/oc")
	public String getOC(Model model) {
		List<String> dates = dc.getDates();
		List<String> valute = dc.getCurrency();
		model.addAttribute("dates", dates);
		model.addAttribute("valute", valute);
		return "OC";
	}

	@PostMapping(value = "/OCresult")
	public String calcOC(
			@RequestParam("start_date") String start_date, 
			@RequestParam("end_date") String end_date, 
			@RequestParam("valuta1") String valuta1, 
			@RequestParam("valuta2") String valuta2, 
			Model model) {
		List<String> valuti = new ArrayList<String>(Arrays.asList(valuta1, valuta2));
		OCcalculator calc = new OCcalculator();
		double[] result = calc.calculateOC(start_date, end_date, valuti);
		model.addAttribute("valuta1", valuta1);
		model.addAttribute("valuta2", valuta2);
		model.addAttribute("first_percentage", result[0]);
		model.addAttribute("second_percentage", result[1]);
		model.addAttribute("first_vs_second", result[2]);
		return "OCresult";
	}
	
	@GetMapping(value="/quit")
	public void shutdown() {
		File graph_file = new File("chart.png");
		graph_file.deleteOnExit();
		int exitCode = SpringApplication.exit(context, (ExitCodeGenerator) () -> 0);
		System.exit(exitCode);
	}
}
