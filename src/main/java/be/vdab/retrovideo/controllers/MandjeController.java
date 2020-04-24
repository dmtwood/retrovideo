package be.vdab.retrovideo.controllers;

import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.services.FilmsService;
import be.vdab.retrovideo.sessions.Mandje;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** Controller Class handling mandje requests
 * depends on filmService and FilmService
 * creates MandjeController-Bean
 */
@Controller
@RequestMapping("/mandje")
class MandjeController {

	final Mandje mandje;
	final FilmsService filmsService;

	MandjeController(Mandje mandje, FilmsService filmsService) {
		this.mandje = mandje;
		this.filmsService = filmsService;
	}

	List<Film> maakFilmsVanFilmsIds(Set<Long> filmIds) {
		List<Film> films = new ArrayList<>(filmIds.size());
		for (long id : filmIds) {
			filmsService.read(id).
					ifPresent(
							films::add
					);
		}
		return films;
	}

	private List<BigDecimal> maakPrijzenVanFilmsIds(Set<Long> filmIds) {
		List<BigDecimal> prijzen = new ArrayList<>(filmIds.size());
		for (long id : filmIds) {
			filmsService.read(id)
					.ifPresent(
							film -> prijzen.add(film.getPrijs())
					);
		}
		return prijzen;
	}

	private final static String MANDJE_MAV = "mandje";
	private static final String  FILMSINMANDJE = "filmsInMandje";
	private static final String TOTALEPRIJS = "totalePrijs";
	@GetMapping()
    ModelAndView toonMandje() {
		List<Film> films = maakFilmsVanFilmsIds(mandje.getFilmIds());
		List<BigDecimal> prijzen = maakPrijzenVanFilmsIds(mandje.getFilmIds());
		ModelAndView modelAndView = new ModelAndView(MANDJE_MAV);
		modelAndView.addObject(
				FILMSINMANDJE,
				films
		);
		modelAndView.addObject(
				TOTALEPRIJS,
				mandje.berekenTotalePrijs(prijzen)
		);
		return modelAndView;
	}

	private static final String REEDSINMANDJE = "reedsInMandje";
	@GetMapping("?reedsInMandje") // nog nodig?
    ModelAndView toonMandjeDubbeleFilm(@PathVariable long reedsInMandje) {
		List<Film> films = maakFilmsVanFilmsIds(mandje.getFilmIds());
		List<BigDecimal> prijzen = maakPrijzenVanFilmsIds(mandje.getFilmIds());
		ModelAndView modelAndView = new ModelAndView(MANDJE_MAV);
		modelAndView.addObject(
				FILMSINMANDJE,
				films
		);
		modelAndView.addObject(
				TOTALEPRIJS,
				mandje.berekenTotalePrijs(prijzen)
		);
		modelAndView.addObject(
				REEDSINMANDJE,
				reedsInMandje
		);
		return modelAndView;
	} // nog nodig?

	private final static String REDIRECT_NA_DELETE = "redirect:/mandje";

	@PostMapping("verwijderid")
    ModelAndView verwijderId(long[] verwijderid) {
		if (verwijderid != null) {
			mandje.verwijderFilmId(verwijderid);
		}
		return new ModelAndView(REDIRECT_NA_DELETE);
	}

	@PostMapping
    ModelAndView VerwijderZonderId() {
		return new ModelAndView(REDIRECT_NA_DELETE);
	}
}
