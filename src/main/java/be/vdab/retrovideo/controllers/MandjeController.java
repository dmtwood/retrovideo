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

@Controller
@RequestMapping("/mandje")
class MandjeController {

	final Mandje mandje;
	private final FilmsService filmsService;

	MandjeController(Mandje mandje, FilmsService filmsService) {
		this.mandje = mandje;
		this.filmsService = filmsService;
	}

	private List<Film> maakFilmsVanFilmsIds(Set<Integer> filmIds) {
		List<Film> films = new ArrayList<>(filmIds.size());
		for (int id : filmIds) {
			filmsService.read(id).ifPresent(film -> films.add(film));
		}
		return films;
	}

	private List<BigDecimal> maakPrijzenVanFilmsIds(Set<Integer> filmIds) {
		List<BigDecimal> prijzen = new ArrayList<>(filmIds.size());
		for (int id : filmIds) {
			filmsService.read(id).ifPresent(film -> prijzen.add(film.getPrijs()));
		}
		return prijzen;
	}

	private final static String MANDJE_VIEW = "mandje";

	@GetMapping()
    ModelAndView toonMandje() {
		List<Film> films = maakFilmsVanFilmsIds(mandje.getFilmIds());
		List<BigDecimal> prijzen = maakPrijzenVanFilmsIds(mandje.getFilmIds());
		ModelAndView modelAndView = new ModelAndView(MANDJE_VIEW);
		modelAndView.addObject("filmsInMandje", films);
		modelAndView.addObject("totalePrijs", mandje.berekenTotalePrijs(prijzen));
		return modelAndView;
	}

	@GetMapping("?reedsInMandje")
    ModelAndView toonMandjeIgvDubbeleFilm(@PathVariable int reedsInMandje) {
		List<Film> films = maakFilmsVanFilmsIds(mandje.getFilmIds());
		List<BigDecimal> prijzen = maakPrijzenVanFilmsIds(mandje.getFilmIds());
		ModelAndView modelAndView = new ModelAndView(MANDJE_VIEW);
		modelAndView.addObject("filmsInMandje", films);
		modelAndView.addObject("totalePrijs", mandje.berekenTotalePrijs(prijzen));
		modelAndView.addObject("reedsInMandje", reedsInMandje);
		return modelAndView;
	}

	private final static String REDIRECT_NA_DELETE = "redirect:/mandje";

	@PostMapping(params = "verwijderid")
    ModelAndView verwijderId(int[] verwijderid) {
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
