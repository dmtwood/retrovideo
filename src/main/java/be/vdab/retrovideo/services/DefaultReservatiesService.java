package be.vdab.retrovideo.services;

import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.domain.Reservatie;
import be.vdab.retrovideo.repositories.FilmsRepository;
import be.vdab.retrovideo.repositories.ReservatiesRepository;
import be.vdab.retrovideo.sessions.Mandje;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class DefaultReservatiesService implements ReservatiesService {

    private final ReservatiesRepository reservatieRepository;
    private final FilmsRepository filmRepository;
    private final Mandje mandje;

    DefaultReservatiesService(ReservatiesRepository reservatieRepository, FilmsRepository filmRepository, Mandje mandje) {
        this.reservatieRepository = reservatieRepository;
        this.filmRepository = filmRepository;
        this.mandje=mandje;
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void updateReservatiesEnFilms(Reservatie reservatie, Film film) {
        if (film.getGereserveerd() < film.getVoorraad()) {
            reservatieRepository.create(reservatie);
            filmRepository.update(film);
        } else {
			mandje.addToFilmWeinigVoorraad(film.getId());
        }

    }

}
