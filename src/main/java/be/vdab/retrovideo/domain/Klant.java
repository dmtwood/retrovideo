package be.vdab.retrovideo.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


public class Klant {
@Positive private long id;
@NotNull @NotBlank String familienaam;
@NotNull @NotBlank private String voornaam;
@NotNull @NotBlank private String straatNummer;
@NotNull @NotBlank private String postcode;
@NotNull @NotBlank private String gemeente;

	/**
	 * Constructor of Klant Objects
	 * @param id of Klant in DB (PK)
	 * @param familienaam
	 * @param voornaam
	 * @param straatNummer
	 * @param postcode
	 * @param gemeente
	 */
public Klant(long id, String familienaam, String voornaam, String straatNummer, String postcode, String gemeente) {
	this.id = id;
	this.familienaam = familienaam;
	this.voornaam = voornaam;
	this.straatNummer = straatNummer;
	this.postcode = postcode;
	this.gemeente = gemeente;
}

public long getId() {
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
