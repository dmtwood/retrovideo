package be.vdab.retrovideo.services;

import be.vdab.retrovideo.domain.Film;

import java.util.List;
import java.util.Optional;

public interface FilmsService {
	List<Film> findByGenre(int genreId);
	Optional<Film> read(int id);
	void update(Film film);
}
