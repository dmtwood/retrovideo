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
/** Controller Class handling index and genres/id requests
 * depends on filmService and GenreService
 * creates IndexController-Bean
 * @Date 21.04.2020
 */
class IndexController {
	private final GenresService genresService;
	private final FilmsService filmsService;

	IndexController(GenresService genresService, FilmsService filmsService) {
		this.genresService = genresService;
		this.filmsService = filmsService;
	}

	private final static String INDEX_MAV = "index";
	private final static String GENRES_OBJ = "genres";

	/** create a ModelAndView for index.html and add all unique and genre-objects
	 * @return index with genres
	 */
	@GetMapping
    ModelAndView index() {
		return new ModelAndView(
				INDEX_MAV,
				GENRES_OBJ,
				genresService.findUniekeGenres());
	}


	/**
	 * find all films from a genre
	 * @param genreId injected from index.html
	 * @return genreFilms for index.html
	 */
	@GetMapping("genres/{genreId}")
    ModelAndView findFilmsByGenre(@PathVariable long genreId) {
		return new ModelAndView(
				INDEX_MAV,
				GENRES_OBJ,
				genresService.findUniekeGenres()
		).addObject(
				"genreFilms",
				filmsService.findByGenre(genreId)
		);
	}


}
