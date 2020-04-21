package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Klant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class JdbcKlantenRepository implements KlantenRepository {

	private final JdbcTemplate template;

	JdbcKlantenRepository(JdbcTemplate template) {
		this.template = template;
	}

	private static final String SELECT_BY_NAAM_BEVAT =
			"select id, familienaam, voornaam, straatNummer, postcode, gemeente from klanten " +
					"where familienaam like :zoals order by familienaam";

	private final RowMapper<Klant> klantRowMapper = (resultSet, rowNum) ->
			new Klant(
					resultSet.getLong("id"),
					resultSet.getString("familienaam"),
					resultSet.getString("voornaam"),
					resultSet.getString("straatNummer"),
					resultSet.getString("postcode"),
					resultSet.getString("gemeente"));

	@Override
	public List<Klant> findByFamilienaamBevat(String deelNaam) {
		return template.query(SELECT_BY_NAAM_BEVAT,
				klantRowMapper,
				Collections.singletonMap("zoals", '%' + deelNaam + '%'));
	}

	private static final String READ = "select id, familienaam, voornaam, straatNummer, postcode, gemeente from klanten " +
			"where id=?";

	@Override
	public Klant read(long id) {

			return template.queryForObject(READ, klantRowMapper, id);

	}

}
