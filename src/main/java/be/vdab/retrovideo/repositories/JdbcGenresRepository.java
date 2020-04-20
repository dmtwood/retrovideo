package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenresRepository implements GenresRepository {

	private final NamedParameterJdbcTemplate template;

	JdbcGenresRepository(NamedParameterJdbcTemplate template) {
		this.template = template;   }
	
	private final RowMapper<Genre> GenreRowMapper = (resultSet, rowNum) ->
			new Genre(resultSet.getInt("id"), resultSet.getString("naam"));
	
	private static final String SELECT_UNIEKE_GENRES =   "select distinct id, naam from genres order by id"; 

	@Override
	public List<Genre> findUniekeGenres() {
		  return template.query(SELECT_UNIEKE_GENRES, GenreRowMapper);
	}

}
