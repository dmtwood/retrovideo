package be.vdab.retrovideo.services;

import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.domain.Reservatie;
import be.vdab.retrovideo.repositories.FilmsRepository;
import be.vdab.retrovideo.repositories.ReservatiesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
class DefaultReservatiesService implements ReservatiesService {
	
	private final ReservatiesRepository reservatieRepository;
	private final FilmsRepository filmRepository;
	
	DefaultReservatiesService(ReservatiesRepository reservatieRepository, FilmsRepository filmRepository){
		this.reservatieRepository = reservatieRepository;
		this.filmRepository = filmRepository;	
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public void updateReservatiesEnFilms(Reservatie reservatie, Film film) {
		reservatieRepository.create(reservatie);
		filmRepository.update(film);
		
	}

}
