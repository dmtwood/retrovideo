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

/**
 * verwerkt film-gerelateerde verzoeken
 * @author Dimitri Gevers
 * @since 20-04-2020
 * @version 21-04-2020
 * @exception
 */
@Controller
@RequestMapping("film")
class FilmController {

	private final FilmsService filmsService;
	private final Mandje mandje;

	/** maakt een FilmController
	 * @param filmsService leest films in uit de film repository
	 * @param mandje sessie die film id's bewaart
	 */
	public FilmController(FilmsService filmsService, Mandje mandje) {
		this.filmsService = filmsService;
		this.mandje = mandje;
	}

	private final static String FILM_MAV = "film";


	@GetMapping("{id}")
    ModelAndView toonFilm(@PathVariable long id) {
		ModelAndView modelAndView = new ModelAndView(FILM_MAV);
		filmsService.read(id).ifPresent(film -> modelAndView.addObject(film));
		return modelAndView;
	}

	private static final String REDIRECT_MANDJE = "redirect:/mandje";

	@PostMapping("{id}")
    ModelAndView voegFilmToeAanMandje(@PathVariable long id, RedirectAttributes redirectAttributes) {
		Set<Long> filmIds = mandje.getFilmIds();
		long eersteSize = filmIds.size();
		filmIds.add(id);
		long tweedeSize = filmIds.size();
		long reedsInMandje = id;
		if (eersteSize == tweedeSize) {
			redirectAttributes.addAttribute("reedsInMandje", reedsInMandje);
		}
		return new ModelAndView(REDIRECT_MANDJE);
	}
}
