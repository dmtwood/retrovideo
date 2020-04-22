package be.vdab.retrovideo.domain;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class Reservatie {
	@Positive private long klantId;
	@Positive private long filmId;
	private LocalDateTime reservatie = LocalDateTime.now();

	public Reservatie(long klantId, long filmId, LocalDateTime reservatie) {
		this.klantId = klantId;
		this.filmId = filmId;
		this.reservatie = reservatie;
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
