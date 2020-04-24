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
 * Controller Class handling Film Request
 * depends on FilmService and Mandje
 * creates FilmController-Bean
 * @author Dimitri Gevers
 * @since 20-04-2020
 * @version 23-04-2020
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
	/**
	 * show Film with an id
	 * @param id Path Variable holding value from index.html
	 * @return Film object to film.html
	 */
	@GetMapping("{id}")
    ModelAndView toonFilm(@PathVariable long id) {
		ModelAndView modelAndView = new ModelAndView(FILM_MAV);
		filmsService.read(id)
				.ifPresent(
						film -> modelAndView.addObject(film)
				);
		return modelAndView;
	}


	private static final String REDIRECT_MANDJE = "redirect:/mandje";
	/**
	 * add Film to Mandje
	 * @param id Path Variable holding value from index.html
	 * @param redirectAttributes handle multiple Film in Mandje,
	 * @return
	 */
	@PostMapping("{id}")
    ModelAndView voegFilmToeAanMandje(@PathVariable long id, RedirectAttributes redirectAttributes) {
		Set<Long> filmIds = mandje.getFilmIds();
		long eersteSize = filmIds.size(); 	// niet meer nodig?
		// add the film to mandje.ids
		filmIds.add(id);
		long tweedeSize = filmIds.size(); 	// niet meer nodig?
		long reedsInMandje = id; 			// niet meer nodig?
		if (eersteSize == tweedeSize) {
			redirectAttributes.addAttribute("reedsInMandje", reedsInMandje);
		} // niet meer nodig?
		return new ModelAndView(REDIRECT_MANDJE);
	}
}
