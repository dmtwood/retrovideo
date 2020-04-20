package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Film;

import java.util.List;
import java.util.Optional;

public interface FilmsRepository {
	List<Film> findByGenre(int genreId);
	Optional<Film> read(int id);
	void update(Film film); 
}
