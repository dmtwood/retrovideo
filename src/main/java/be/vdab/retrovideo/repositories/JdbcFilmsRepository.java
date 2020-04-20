package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.exceptions.FilmNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JdbcFilmsRepository implements FilmsRepository {

//	private final NamedParameterJdbcTemplate template;

	private final JdbcTemplate template;

	JdbcFilmsRepository(JdbcTemplate template) {
		this.template = template;
	}

	private final RowMapper<Film> filmsRowMapper = (resultSet, rowNum) -> new Film(resultSet.getInt("id"),
			resultSet.getInt("genreid"), resultSet.getString("titel"), resultSet.getInt("voorraad"),
			resultSet.getInt("gereserveerd"), resultSet.getBigDecimal("prijs"));

	private static final String SELECT_BY_ID =
			"select id, genreid, titel, voorraad, gereserveerd, prijs from films " +
					"where genreid=? order by titel";

	@Override
	public List<Film> findByGenre(int genreId) {

		return template.query(SELECT_BY_ID,  filmsRowMapper, genreId);

}




	private static final String READ =
			"select id, genreid, titel, voorraad, gereserveerd, prijs from films where id= ?";

	@Override
	public Optional<Film> read(int id) {
		try {
			return Optional.of(template.queryForObject(READ,  filmsRowMapper, id));
		} catch (IncorrectResultSizeDataAccessException ex) {
			return Optional.empty();
		}
	}

//	@Override
//	public Optional<HerenKapsels> findById(long id) {
//		try {
//			String sql = "select idmannenkapsels, naammannenkapsel, prijsmannenkapsel from mannenkapsels where idmannenkapsels = ?";
//
//			return Optional.of(template.queryForObject(sql, mannenKapselMapper, id));
//		} catch (IncorrectResultSizeDataAccessException ex) {
//			return Optional.empty();
//		}
//	}
//
//
//






	private static final String UPDATE_FILM =
			"update films set gereserveerd = ? where id =? and gereserveerd < voorraad";

	@Override
	public void update(Film film) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("gereserveerd", film.getGereserveerd() + 1);
		parameters.put("id", film.getId());
		if (template.update(UPDATE_FILM, parameters) == 0) {
			throw new FilmNietGevondenException();
		}
	}
}
