package be.vdab.retrovideo.services;

import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.domain.Reservatie;

public interface ReservatiesService {
	void updateReservatiesEnFilms(Reservatie reservatie, Film film);
}
