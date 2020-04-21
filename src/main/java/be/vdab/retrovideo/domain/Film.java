package be.vdab.retrovideo.domain;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Film {
	@Positive private long id;
	@Positive private long genreId;
	@NotBlank @NotNull private String titel;
	private long voorraad;
	private long gereserveerd;
	@NumberFormat(pattern = "€ 0.00") private BigDecimal prijs;

public Film(long id, long genreId, String titel, long voorraad, long gereserveerd, BigDecimal prijs) {
		this.id = id;
		this.genreId = genreId;
		this.titel = titel;
		this.voorraad = voorraad;
		this.gereserveerd = gereserveerd;
		this.prijs = prijs;
	}

	public long getId() {
		return id;
	}
	public long getGenreId() {
		return genreId;
	}
	public String getTitel() {
		return titel;
	}
	public long getVoorraad() {
		return voorraad;
	}
	public long getGereserveerd() {
		return gereserveerd;
	}
	public BigDecimal getPrijs() {
		return prijs;
	}
	
	

}
