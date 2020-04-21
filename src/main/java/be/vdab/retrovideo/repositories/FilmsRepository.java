package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Film;

import java.util.List;
import java.util.Optional;

public interface FilmsRepository {
	List<Film> findByGenre(long genreId);
	Optional<Film> read(long id);
	void update(Film film); 
}
