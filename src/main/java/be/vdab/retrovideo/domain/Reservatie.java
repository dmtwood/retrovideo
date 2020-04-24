package be.vdab.retrovideo.domain;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class Reservatie {
	@Positive private long klantId;
	@Positive private long filmId;
	private LocalDateTime reservatie = LocalDateTime.now();

	/**
	 * Constructor of Reservatie objects
	 * @param klantId id of Klant in DB (FK)
	 * @param filmId id of Film in DB (FK)
	 * @param tijdVanReservatie Local Date and Time of processing
	 */
	public Reservatie(long klantId, long filmId, LocalDateTime tijdVanReservatie) {
		this.klantId = klantId;
		this.filmId = filmId;
		this.reservatie = tijdVanReservatie;
	}
	
	public long getKlantId() {
		return klantId;
	}
	public void setKlantId(int klantId) {
		this.klantId = klantId;
	}
	public long getFilmId() {
		return filmId;
	}
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}
	public LocalDateTime getReservatie() {
		return reservatie;
	}

	
	

}
