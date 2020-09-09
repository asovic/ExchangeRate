package model;

public class Tecaj {
	private String sifra;
	private String oznaka;
	private String tecaj;
	
	public Tecaj(String sifra, String oznaka, String tecaj) {
		super();
		this.sifra = sifra;
		this.oznaka = oznaka;
		this.tecaj = tecaj;
	}
	public String getSifra() {
		return sifra;
	}
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
	public String getOznaka() {
		return oznaka;
	}
	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}
	public String getTecaj() {
		return tecaj;
	}
	public void setTecaj(String tecaj) {
		this.tecaj = tecaj;
	}
}
