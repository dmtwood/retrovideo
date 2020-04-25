package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Klant;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * repository to read and search klanten table on retrovideo DB
 * uses RowMapper to convert a db-record to a Klant object
 * creates KlantenRepository-bean to inject in KlantenService
 */
@Repository
public class JdbcKlantenRepository implements KlantenRepository {

	private final JdbcTemplate template;

	JdbcKlantenRepository(JdbcTemplate template) {
		this.template = template;
	}

	private final RowMapper<Klant> klantRowMapper = (resultSet, rowNum) ->
			new Klant(
					resultSet.getLong("id"),
					resultSet.getString("familienaam"),
					resultSet.getString("voornaam"),
					resultSet.getString("straatNummer"),
					resultSet.getString("postcode"),
					resultSet.getString("gemeente"));


	/**
	 * @param deelNaam String injected from deelNaamForm
	 * @return a List of Klant-objects with familienames containing the deelnaam
	 */
	@Override
	public List<Klant> findByFamilienaamBevat(String deelNaam) {
		final String SELECT_FAMILIENAAM_BEVAT =
				"select id, familienaam, voornaam, straatNummer, postcode, gemeente from klanten where familienaam like ? order by familienaam";
		return template.query(
				SELECT_FAMILIENAAM_BEVAT,
				klantRowMapper,
				'%' + deelNaam + '%'
		);
	}

	/**
	 * @param id injects an Klant-id, coming from KlantenService of JdbcKlantenRepositoryTest
	 * @return Klant-Object with the given id when Valid, optional empty when id is invalid
	 */
	@Override
	public Optional<Klant> read(long id) {
		try {
			final String READ = "select id, familienaam, voornaam, straatNummer, postcode, gemeente from klanten where id=?";
			return Optional.of(
					template.queryForObject(
							READ,
							klantRowMapper,
							id
					)
			);
		} catch (IncorrectResultSizeDataAccessException ex) {
			return Optional.empty();
		}

	}

}
