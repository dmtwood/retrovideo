package be.vdab.retrovideo.controllers;

import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.domain.Reservatie;
import be.vdab.retrovideo.exceptions.FilmNietGevondenException;
import be.vdab.retrovideo.services.FilmsService;
import be.vdab.retrovideo.services.KlantenService;
import be.vdab.retrovideo.services.ReservatiesService;
import be.vdab.retrovideo.sessions.Mandje;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("klant")
class RapportController {

	private final Mandje mandje;
	private final KlantenService klantenService;
	private final ReservatiesService reservatiesService;
	private final FilmsService filmsService;

	RapportController(Mandje mandje, KlantenService klantenService, ReservatiesService reservatiesService,
                      FilmsService filmsService) {
		this.mandje = mandje;
		this.klantenService = klantenService;
		this.reservatiesService = reservatiesService;
		this.filmsService = filmsService;
	}

	private final static String BEVESTIGEN_VIEW = "bevestigen";

	@GetMapping("{id}")
    ModelAndView bevestigReservatie(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView(BEVESTIGEN_VIEW, "mandje", mandje.getFilmIds());
		modelAndView.addObject("klant", klantenService.read(id));
		return modelAndView;
	}

	private final static String BEVESTIGD_VIEW = "bevestigd";

	@GetMapping("{klantId}/bevestigd")
    ModelAndView bevestigdview(@PathVariable int klantId) {
		ModelAndView modelAndView = new ModelAndView(BEVESTIGD_VIEW);
		return modelAndView;
	}

	private final static String REDIRECT_URL_NA_BEVESTIGING = "redirect:/klant/{klantId}/bevestigd";

	@PostMapping("{klantId}/bevestigd")
    ModelAndView bevestigd(@PathVariable int klantId, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_NA_BEVESTIGING);
		String mislukt = "";
		for (int filmId : mandje.getFilmIds()) {
			Reservatie reservatie = new Reservatie(klantId, filmId, LocalDateTime.now());
			Optional<Film> film = filmsService.read(filmId);
			try {
				reservatiesService.updateReservatiesEnFilms(reservatie, film.get());
			} catch (FilmNietGevondenException ex) {
				String filmNaam = film.get().getTitel();
				mislukt = mislukt + "," + filmNaam;
			}
		}
		redirectAttributes.addAttribute("mislukteFilms", mislukt);
		return modelAndView;
	}

	@GetMapping("{klantId}/bevestigd?mislukteFilms")
    ModelAndView misluktview(@PathVariable String mislukt) {
		ModelAndView modelAndView = new ModelAndView(BEVESTIGD_VIEW);
		String[] gesplitstefilms = mislukt.split(",");
		modelAndView.addObject("mislukteFilms", gesplitstefilms);
		return modelAndView;
	}
}
