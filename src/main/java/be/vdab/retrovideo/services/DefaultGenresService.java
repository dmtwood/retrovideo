package be.vdab.retrovideo.services;

import be.vdab.retrovideo.domain.Genre;
import be.vdab.retrovideo.repositories.GenresRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
