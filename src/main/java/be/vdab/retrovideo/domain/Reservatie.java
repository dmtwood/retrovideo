package be.vdab.retrovideo.domain;

import java.time.LocalDateTime;

public class Reservatie {
	private int klantId;
	private int filmId;
	private LocalDateTime reservatie = LocalDateTime.now();
	
	Reservatie() {
	}

	public Reservatie(int klantId, int filmId, LocalDateTime reservatie) {
		this.klantId = klantId;
		this.filmId = filmId;
		this.reservatie = reservatie;
	}
	
	public int getKlantId() {
		return klantId;
	}
	public void setKlantId(int klantId) {
		this.klantId = klantId;
	}
	public int getFilmId() {
		return filmId;
	}
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}
	public LocalDateTime getReservatie() {
		return reservatie;
	}

	
	

}
