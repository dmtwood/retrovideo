package be.vdab.retrovideo.domain;

public class Klant {
private int id;
private String familienaam;
private String voornaam;
private String straatNummer;
private String postcode;
private String gemeente;

public Klant() {
}

public Klant(int id, String familienaam, String voornaam, String straatNummer, String postcode, String gemeente) {
	this.id = id;
	this.familienaam = familienaam;
	this.voornaam = voornaam;
	this.straatNummer = straatNummer;
	this.postcode = postcode;
	this.gemeente = gemeente;
}

public int getId() {
	return id;
}
public String getFamilienaam() {
	return familienaam;
}
public String getVoornaam() {
	return voornaam;
}
public String getStraatNummer() {
	return straatNummer;
}
public String getPostcode() {
	return postcode;
}
public String getGemeente() {
	return gemeente;
}


}
