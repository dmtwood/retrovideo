package be.vdab.retrovideo.controllers;

import be.vdab.retrovideo.services.FilmsService;
import be.vdab.retrovideo.sessions.Mandje;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
@RequestMapping("film")
class FilmController {

	private final FilmsService filmsService;
	private final Mandje mandje;

	public FilmController(FilmsService filmsService, Mandje mandje) {
		this.filmsService = filmsService;
		this.mandje = mandje;
	}

	private final static String FILM_VIEW = "film";

	@GetMapping("{id}")
    ModelAndView toonFilm(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView(FILM_VIEW);
		filmsService.read(id).ifPresent(film -> modelAndView.addObject(film));
		return modelAndView;
	}

	private static final String REDIRECT_NA_TOEVOEGEN = "redirect:/mandje";

	@PostMapping("{id}")
    ModelAndView voegFilmToeAanMandje(@PathVariable int id, RedirectAttributes redirectAttributes) {
		Set<Integer> filmIds = mandje.getFilmIds();
		int eersteSize = filmIds.size();
		filmIds.add(id);
		int tweedeSize = filmIds.size();
		int reedsInMandje = id;
		if (eersteSize == tweedeSize) {
			redirectAttributes.addAttribute("reedsInMandje", reedsInMandje);
		}
		return new ModelAndView(REDIRECT_NA_TOEVOEGEN);
	}
}
