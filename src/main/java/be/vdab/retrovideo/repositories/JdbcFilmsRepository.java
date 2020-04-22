package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.exceptions.FilmNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * repository to CRUD the table films on retrovideo-JDBC
 * uses RowMapper to create Film-objects from DB-records
 * creates FilmRepository-bean to inject in FilmService
 */
@Repository
public class JdbcFilmsRepository implements FilmsRepository {

    private final JdbcTemplate template;

    // inject the JDBC-template in the films repository to
    JdbcFilmsRepository(JdbcTemplate template) {
        this.template = template;
    }

    private final RowMapper<Film> filmsRowMapper = (resultSet, rowNum) -> new Film(resultSet.getInt("id"),
            resultSet.getInt("genreid"), resultSet.getString("titel"), resultSet.getInt("voorraad"),
            resultSet.getInt("gereserveerd"), resultSet.getBigDecimal("prijs"));


    @Override
    public List<Film> findByGenre(long genreId) {
        final String SELECT_ON_ID = "select id, genreid, titel, voorraad, gereserveerd, prijs from films where genreid=? order by titel";
        return template.query(SELECT_ON_ID, filmsRowMapper, genreId);
    }


    @Override
    public Optional<Film> read(long id) {
        final String READ = "select id, genreid, titel, voorraad, gereserveerd, prijs from films where id=?";
        try {
            return Optional.of(template.queryForObject(READ, filmsRowMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Film film) {
        final String UPDATE_FILM = "update films set voorraad=?, gereserveerd=? where id=? and 0 < voorraad";
        long algereserveerd = film.getGereserveerd();
        System.out.println("al gereserveerd ????????????????????????" + algereserveerd);
        long nieuwGereserveerd = film.getGereserveerd() + 1;
        System.out.println("nieuw gereserveerd !!!!!!!!!!!!!!!!!!!!!!!!!!!!" + nieuwGereserveerd);
        long nieuwVoorraad = film.getVoorraad() - 1;
        long filmId = film.getId();
        System.out.println(filmId);

//        template.update(UPDATE_FILM, nieuwVoorraad, nieuwGereserveerd, filmId);
//
//		Map<String, Object> parameters = new HashMap<>();
//		parameters.put("gereserveerd", film.getGereserveerd() + 1);
//		parameters.put("id", film.getId());

        if (template.update(UPDATE_FILM, nieuwVoorraad, nieuwGereserveerd, filmId) == 0) {
            throw new FilmNietGevondenException();
        }
    }
}
