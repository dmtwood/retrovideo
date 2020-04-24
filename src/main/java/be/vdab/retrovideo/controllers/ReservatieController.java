package be.vdab.retrovideo.controllers;

import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.domain.Klant;
import be.vdab.retrovideo.domain.Reservatie;
import be.vdab.retrovideo.exceptions.FilmNietGevondenException;
import be.vdab.retrovideo.exceptions.TeWeinigVoorraadException;
import be.vdab.retrovideo.services.FilmsService;
import be.vdab.retrovideo.services.GenresService;
import be.vdab.retrovideo.services.KlantenService;
import be.vdab.retrovideo.services.ReservatiesService;
import be.vdab.retrovideo.sessions.Mandje;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Controller Class handling index klant requests (bis)
 * depends on  mandje, klantenService, filmService and GenreService
 * creates ReservatieController-Bean
 */
@Controller
@RequestMapping("klant")
class ReservatieController {
    private final Mandje mandje;
    private final KlantenService klantenService;
    private final ReservatiesService reservatiesService;
    private final FilmsService filmsService;
    private final GenresService genresService;

    ReservatieController(Mandje mandje, KlantenService klantenService, ReservatiesService reservatiesService, FilmsService filmsService, GenresService genresService) {
        this.mandje = mandje;
        this.klantenService = klantenService;
        this.reservatiesService = reservatiesService;
        this.filmsService = filmsService;
        this.genresService = genresService;
    }

    private static final String MISLUKFILMS = "mislukFilms";
    private static final String GELUKTEFILMS = "gelukteFilms";
    private static final String BEVESTIGEN_MAV = "bevestigen" ;
    private static final String MANDJE = "mandje";
    private static final String KLANT = "klant";
    private static final String KLANTVOORNAAM = "klantVoornaam";

    /**
     * @param id id of Klant for Reservation
     * @return bevestigen.html with Klant details and misluk/gelukte Film-titels
     */
    @GetMapping("{id}")
    ModelAndView bevestigReservatie(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView(
                BEVESTIGEN_MAV,
                MANDJE,
                mandje.getFilmIds()
        );
        modelAndView.addObject(
                KLANT,
                klantenService.read(id).get().getFamilienaam()
        );
        modelAndView.addObject(
                KLANTVOORNAAM,
                klantenService.read(id).get().getVoornaam()
        );
        Set<Long> filmIdss = mandje.getFilmIds();
        if (!filmIdss.isEmpty()) {
            Set<String> filmsInMandje = new HashSet<>(filmIdss.size());
            for (long id1 : filmIdss) {
                filmsService.read(id1)
                        .ifPresent(
                                film -> filmsInMandje.add(film.getTitel()));
            }
            modelAndView.addObject(
                    GELUKTEFILMS, filmsInMandje
            );
        }
        Set<Long> filmWeinigIds = mandje.getFilmWeinigVoorraadIds();
        if (!filmWeinigIds.isEmpty()) {
            Set<String> films = new HashSet<>(filmWeinigIds.size());
            for (long idd : filmWeinigIds) {
                filmsService.read(idd)
                        .ifPresent(
                                film -> films.add(film.getTitel()));
            }
            modelAndView.addObject(
                    MISLUKFILMS, films
            );
        }
        return modelAndView;
    }


    /**
     * @param klantId id of Klant whose reservation is handled
     * @return bevestigd.html with Film-details of non-reservations (when voorraad < gereserveerd)
     */
    @GetMapping("{klantId}/bevestigd")
    ModelAndView bevestigdview(@PathVariable long klantId) {
        ModelAndView modelAndView = new ModelAndView("bevestigd");
        Set<Long> filmWeinigIds = mandje.getFilmWeinigVoorraadIds();
        if (!filmWeinigIds.isEmpty()) {
            Set<String> films = new HashSet<>(filmWeinigIds.size());
            for (long id : filmWeinigIds) {
                filmsService.read(id)
                        .ifPresent(
                                film -> films.add(film.getTitel()
                                )
                        );
            }
            modelAndView.addObject(
                    MISLUKFILMS, films
            );
        }

        Set<Long> filmIds = mandje.getFilmIds();
        if (!filmIds.isEmpty()) {
            Set<String> filmsInMandje = new HashSet<>(filmIds.size());
            for (long id : filmIds) {
                filmsService.read(id).ifPresent(film -> filmsInMandje.add(film.getTitel()));
            }
            modelAndView.addObject(
                    GELUKTEFILMS, filmsInMandje
            );
        }

        return modelAndView;
    }

    private static final String REDIRECT_BEVESTIGD = "redirect:/klant/{klantId}/bevestigd";
    @PostMapping("{klantId}/bevestigd")
    ModelAndView bevestigd(@PathVariable long klantId, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView(REDIRECT_BEVESTIGD);
        for (long filmId : mandje.getFilmIds()) {
            Reservatie reservatie = new Reservatie(klantId, filmId, LocalDateTime.now());
            Optional<Film> film = filmsService.read(filmId);
            try {
                reservatiesService.updateReservatiesEnFilms(reservatie, film.get());
                modelAndView.addObject(
                        MISLUKFILMS,
                        filmsService.read(filmId).get().getTitel()); //nodig?
            } catch (TeWeinigVoorraadException ex) {
                modelAndView.addObject(
                        MISLUKFILMS,
                        filmsService.read(filmId).get().getTitel());
            }
        }
        return modelAndView;
    }


    private static final String INDEX = "index";
    private static final String GENRES = "genres";

    /**
     * handles request to Genres when Reservatie is processed
     * clear mandje variables and objects holding Films titel from handled reservation
     * @return index page cleared
     */
    @GetMapping("new")
    ModelAndView index() {
        ModelAndView mAV = new ModelAndView(
                INDEX,
                GENRES,
                genresService.findUniekeGenres()
        );
        mandje.mandjeLeeg();
        mandje.verwijderFilmsWeinigVoorraad();
        ModelAndView renewKlantenObjects = new ModelAndView(
                KLANT);
        renewKlantenObjects.addObject(
                MISLUKFILMS,
                null
        );
        renewKlantenObjects.addObject(
                GELUKTEFILMS,
                null
        );
        return mAV;
    }


    private static final String TE_WEINIG_VOORRAAD = "teWeinigVoorraad";

    /**
     * catch Custom TeWeinigVoorraadException (when voorraad < gereserveerd)
     * @return bevestigen.html with Te Weinig Voorraad object
     */
    @ExceptionHandler({TeWeinigVoorraadException.class})
    public ModelAndView teWeinigVoorraad(TeWeinigVoorraadException ex) {
        return new ModelAndView(
                BEVESTIGEN_MAV,
                TE_WEINIG_VOORRAAD,
                ex.getMessage()
        );
    }

}
