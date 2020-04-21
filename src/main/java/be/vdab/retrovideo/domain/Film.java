package be.vdab.retrovideo.domain;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Film {
	@Positive private int id;
	@Positive private int genreId;
	@NotBlank @NotNull private String titel;
	private int voorraad;
	private int gereserveerd;
	@NumberFormat(pattern = "€ 0.00") private BigDecimal prijs;

public Film(int id, int genreId, String titel, int voorraad, int gereserveerd, BigDecimal prijs) {
		this.id = id;
		this.genreId = genreId;
		this.titel = titel;
		this.voorraad = voorraad;
		this.gereserveerd = gereserveerd;
		this.prijs = prijs;
	}

	public int getId() {
		return id;
	}
	public int getGenreId() {
		return genreId;
	}
	public String getTitel() {
		return titel;
	}
	public int getVoorraad() {
		return voorraad;
	}
	public int getGereserveerd() {
		return gereserveerd;
	}
	public BigDecimal getPrijs() {
		return prijs;
	}
	
	

}
