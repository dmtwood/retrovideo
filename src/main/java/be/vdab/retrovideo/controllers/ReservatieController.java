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
import java.util.Set;
import java.util.stream.IntStream;

/** Controller Class handling index klant requests (bis)
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

	ReservatieController(Mandje mandje, KlantenService klantenService, ReservatiesService reservatiesService, FilmsService filmsService) {
		this.mandje = mandje;
		this.klantenService = klantenService;
		this.reservatiesService = reservatiesService;
		this.filmsService = filmsService;
	}

	@GetMapping("{id}")
    ModelAndView bevestigReservatie(@PathVariable long id) {
		ModelAndView modelAndView = new ModelAndView(
				"bevestigen",
				"mandje",
				mandje.getFilmIds()
		);
		modelAndView.addObject(
				"klant",
				klantenService.read(id)
		);
		return modelAndView;
	}

	@GetMapping("{klantId}/bevestigd")
    ModelAndView bevestigdview(@PathVariable long klantId) {
		ModelAndView modelAndView = new ModelAndView("bevestigd");
		mandje.mandjeLeeg();
		return modelAndView;
	}

	@PostMapping("{klantId}/bevestigd")
    ModelAndView bevestigd(@PathVariable long klantId, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:/klant/{klantId}/bevestigd");
		String mislukt = "";
		for (long filmId : mandje.getFilmIds()) {
			Reservatie reservatie = new Reservatie(klantId, filmId, LocalDateTime.now());
			Optional<Film> film = filmsService.read(filmId);
			try {
				System.out.println("hier moet t gebeuren");
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
		ModelAndView modelAndView = new ModelAndView(
				"bevestigd"
		);
		String[] gesplitstefilms = mislukt.split(",");
		modelAndView.addObject(
				"mislukteFilms", gesplitstefilms
		);
		return modelAndView;
	}
}
