package be.vdab.retrovideo.controllers;

import be.vdab.retrovideo.services.FilmsService;
import be.vdab.retrovideo.services.GenresService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
/** toon gevraagde film op filmpagina
 * @param id Path variabele die id van gezochte film injecteert in de filmsService
 * @return voegt de film met gezochte id toe aan de filmpagina
 * @Date 21.04.2020
 */
class IndexController {

	private final GenresService genresService;
	private final FilmsService filmsService;

	IndexController(GenresService genresService, FilmsService filmsService) {
		this.genresService = genresService;
		this.filmsService = filmsService;
	}

	private final static String WELKOM_VIEW = "index";
	private final static String GENRES = "genres";


	/** create a ModelAndView for index.html and add all unique and genre-objects
	 * @return index with genres
	 */
	@GetMapping
    ModelAndView index() {
		return new ModelAndView(
				WELKOM_VIEW,
				GENRES,
				genresService.findUniekeGenres());
	}

	@GetMapping("genres/{genreId}")
    ModelAndView findFilmsByGenre(@PathVariable int genreId) {
		return new ModelAndView(
				WELKOM_VIEW,
				GENRES,
				genresService.findUniekeGenres()
		).addObject(
				"genreFilms",
				filmsService.findByGenre(genreId)
		);
	}


}
