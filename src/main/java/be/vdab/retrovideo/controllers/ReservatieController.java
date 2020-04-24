package be.vdab.retrovideo.controllers;

import be.vdab.retrovideo.domain.Film;
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

    @GetMapping("{id}")
    ModelAndView bevestigReservatie(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView(
                "bevestigen",
                "mandje",
                mandje.getFilmIds()
        );
        modelAndView.addObject(
                "klant",
                klantenService.read(id).get().getFamilienaam()
        );
        modelAndView.addObject(
                "klantVoornaam",
                klantenService.read(id).get().getVoornaam()
        );
        Set<Long> filmIdss = mandje.getFilmIds();
        if (!filmIdss.isEmpty()) {

            Set<String> filmsInMandje = new HashSet<>(filmIdss.size());
            for (long id1 : filmIdss) {
                filmsService.read(id1).ifPresent(film -> filmsInMandje.add(film.getTitel()));
            }
            modelAndView.addObject(
                    GELUKTEFILMS, filmsInMandje
            );
        }
        Set<Long> filmWeinigIds = mandje.getFilmWeinigVoorraadIds();
        if (!filmWeinigIds.isEmpty()) {
            Set<String> films = new HashSet<>(filmWeinigIds.size());
            System.out.println(filmWeinigIds.size() + " : aantal films in te weinig voorraad");
            for (long idd : filmWeinigIds) {
                filmsService.read(idd).ifPresent(film -> films.add(film.getTitel()));
            }
            modelAndView.addObject(
                    MISLUKFILMS, films // FEEDS BEVESTGD
            );
        }
//		modelAndView.addObject(
//				"mislukteFilms",
//				mandje.getFilmWeinigVoorraadIds().stream().forEach( id -> filmsService.);
//		);
        return modelAndView;
    }


    @GetMapping("{klantId}/bevestigd")
    ModelAndView bevestigdview(@PathVariable long klantId) {
        ModelAndView modelAndView = new ModelAndView("bevestigd");

        Set<Long> filmWeinigIds = mandje.getFilmWeinigVoorraadIds();
        if (!filmWeinigIds.isEmpty()) {
            Set<String> films = new HashSet<>(filmWeinigIds.size());
            System.out.println(filmWeinigIds.size() + " : aantal films in te weinig voorraad");
            for (long id : filmWeinigIds) {
                filmsService.read(id).ifPresent(film -> films.add(film.getTitel()));
            }
            modelAndView.addObject(
                    MISLUKFILMS, films // FEEDS BEVESTGD
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

    @PostMapping("{klantId}/bevestigd")
    ModelAndView bevestigd(@PathVariable long klantId, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("redirect:/klant/{klantId}/bevestigd");
        String nietGelukt = "";
        for (long filmId : mandje.getFilmIds()) {
            Reservatie reservatie = new Reservatie(klantId, filmId, LocalDateTime.now());
            Optional<Film> film = filmsService.read(filmId);
            try {
                reservatiesService.updateReservatiesEnFilms(reservatie, film.get());
                modelAndView.addObject(MISLUKFILMS, filmsService.read(filmId).get().getTitel());
//				modelAndView.addObject("mislukFilms", null);
//				mandje.mandjeLeeg();
////				mandje.verwijderFilmsWeinigVoorraad();
            } catch (TeWeinigVoorraadException ex) {
                modelAndView.addObject(MISLUKFILMS, filmsService.read(filmId).get().getTitel());
            }
        }
//		redirectAttributes.addAttribute("mislukteFilms", nietGelukt);
//		mandje.mandjeLeeg();
//		mandje.verwijderFilmsWeinigVoorraad();
        return modelAndView;
    }

    @GetMapping("new")
    ModelAndView index() {
        ModelAndView mAV = new ModelAndView(
                "index",
                "genres",
                genresService.findUniekeGenres()
        );
        mandje.mandjeLeeg();
        mandje.verwijderFilmsWeinigVoorraad();
        ModelAndView renewKlantenObjects = new ModelAndView(
                "klant");

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


//	@GetMapping("{klantId}/bevestigd?mislukteFilms")
//    ModelAndView misluktview(@PathVariable String mislukt) {
//		ModelAndView modelAndView = new ModelAndView(
//				"bevestigd"
//		);
//		String[] gesplitstefilms = mislukt.split(",");
//		modelAndView.addObject(
//				"mislukteFilms", gesplitstefilms
//		);
//		return modelAndView;
//	}


    @ExceptionHandler({TeWeinigVoorraadException.class})
    public ModelAndView teWeinigVoorraad(TeWeinigVoorraadException ex) {
        return new ModelAndView("bevestigen", "teWeinigVoorraad", ex.getMessage());
    }

}
