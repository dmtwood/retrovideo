package be.vdab.retrovideo.domain;

public class Genre {
	private long id;
	private String naam;

	/**
	 * Constructor of Genre Objects
	 * @param  id of Genre in DB (PK)
	 * @param naam String holding naam
	 */
	public Genre(long id, String naam) {
		this.id = id;
		this.naam = naam;
	}

	public long getId() {
		return id;
	}
	public String getNaam() {
		return naam;
	}
	
	

}
