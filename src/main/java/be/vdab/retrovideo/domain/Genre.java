package be.vdab.retrovideo.domain;

public class Genre {
	private int id;
	private String naam;
	
	public Genre(int id, String naam) {
		this.id = id;
		this.naam = naam;
	}
	
	public Genre() {
	}
	public int getId() {
		return id;
	}
	public String getNaam() {
		return naam;
	}
	
	

}
