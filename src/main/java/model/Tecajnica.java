package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

@Component
public class Tecajnica {
	
	private Map<String, List<Tecaj>> tecajnica = new HashMap<String, List<Tecaj>>();
	private TreeMap<String, List<Tecaj>> sortedTecajnica;

	public Map<String, List<Tecaj>> getTecajnica() {
		return tecajnica;
	}

	public void setTecajnica(Map<String, List<Tecaj>> tecajnica) {
		this.tecajnica = tecajnica;
	}
	
	public void addTecaj(String datum, List<Tecaj> tecaji) {
		tecajnica.put(datum, tecaji);
	}
	
	public TreeMap<String, List<Tecaj>> getSortedTecajnica() {
		sortedTecajnica = new TreeMap<>(this.tecajnica);
		return sortedTecajnica;
	}
}
