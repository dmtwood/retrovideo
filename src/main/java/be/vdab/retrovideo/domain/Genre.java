package be.vdab.retrovideo.domain;

public class Genre {
	private long id;
	private String naam;
	
	public Genre(long id, String naam) {
		this.id = id;
		this.naam = naam;
	}
	
	public Genre() {
	}
	public long getId() {
		return id;
	}
	public String getNaam() {
		return naam;
	}
	
	

}
