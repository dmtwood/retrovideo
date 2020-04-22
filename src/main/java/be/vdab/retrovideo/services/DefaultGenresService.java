package be.vdab.retrovideo.services;

import be.vdab.retrovideo.domain.Genre;
import be.vdab.retrovideo.repositories.GenresRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Class to search the table genres in DB
 * depends on the GenreRepository bean
 * generates GenreServiceBean for IndexController (handling genres as root of process)
 */
@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultGenresService implements GenresService {

	private final GenresRepository genresRepository;

	DefaultGenresService(GenresRepository genresRepository) {
		this.genresRepository = genresRepository;
	}

	@Override
	public List<Genre> findUniekeGenres() {
		return genresRepository.findUniekeGenres();
	}

}
