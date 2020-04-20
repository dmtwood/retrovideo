package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Klant;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class JdbcKlantenRepository implements KlantenRepository {

	private final NamedParameterJdbcTemplate template;

	JdbcKlantenRepository(NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	private static final String SELECT_BY_NAAM_BEVAT = "select id, familienaam, voornaam, straatNummer, postcode, gemeente from klanten where familienaam like :zoals order by familienaam";

	private final RowMapper<Klant> klantRowMapper = (resultSet, rowNum) -> new Klant(resultSet.getInt("id"),
			resultSet.getString("familienaam"), resultSet.getString("voornaam"), resultSet.getString("straatNummer"),
			resultSet.getString("postcode"), resultSet.getString("gemeente"));

	@Override
	public List<Klant> findByFamilienaamBevat(String deelNaam) {
		return template.query(SELECT_BY_NAAM_BEVAT, Collections.singletonMap("zoals", '%' + deelNaam + '%'),
				klantRowMapper);
	}

	private static final String READ = "select id, familienaam, voornaam, straatNummer, postcode, gemeente from klanten where id= :id";

	@Override
	public Klant read(int id) {

			return template.queryForObject(READ, Collections.singletonMap("id", id), klantRowMapper);

	}

}
