package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * repository to search the table genres on retrovideo-JDBC
 * uses RowMapper to create Genre-objects from DB-records
 * creates GenreRepository-bean to inject in GenreService
 */
@Repository
public class JdbcGenresRepository implements GenresRepository {

	private final JdbcTemplate template;

	JdbcGenresRepository(JdbcTemplate template) {
		this.template = template;   }
	
	private final RowMapper<Genre> GenreRowMapper = (resultSet, rowNum) ->
			new Genre(resultSet.getLong("id"), resultSet.getString("naam"));
	
	private static final String SELECT_UNIEKE_GENRES =   "select distinct id, naam from genres order by id"; 

	@Override
	public List<Genre> findUniekeGenres() {
		  return template.query(SELECT_UNIEKE_GENRES, GenreRowMapper);
	}

}
