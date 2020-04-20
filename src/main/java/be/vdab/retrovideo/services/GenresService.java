package be.vdab.retrovideo.services;

import be.vdab.retrovideo.domain.Genre;

import java.util.List;

public interface GenresService {
	List<Genre> findUniekeGenres();
}
