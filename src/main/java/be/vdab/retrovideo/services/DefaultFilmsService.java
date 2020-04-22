package be.vdab.retrovideo.services;

import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.repositories.FilmsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


/**
 * Service Class to read, search and update the table Films in DB
 * depends on the FilmRepository bean
 * generates FilmService-Bean for FilmController, MandjeController and ReservatieController
 * @Transactional
 */
@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultFilmsService implements FilmsService {

	private final FilmsRepository filmsRepository;

	DefaultFilmsService(FilmsRepository filmsRepository) {
		this.filmsRepository = filmsRepository;
	}

	@Override
	public List<Film> findByGenre(long genreId) {
		return filmsRepository.findByGenre(genreId);
	}

	@Override
	public Optional<Film> read(long id) {
		return filmsRepository.read(id);
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void update(Film film) {
		filmsRepository.update(film);
		
	}

}
