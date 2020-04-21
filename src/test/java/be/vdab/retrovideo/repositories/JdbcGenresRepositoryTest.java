package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Genre;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@JdbcTest
@Import(JdbcGenresRepository.class)
@Sql("/insertGenre.sql")

public class JdbcGenresRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

	private JdbcGenresRepository repository;

	@Test
	public void findUniekeGenresVolgensId() {
		List<Genre> genres = repository.findUniekeGenres();
		long aantalGenres = super.jdbcTemplate.queryForObject("select count(distinct id) from genres", Long.class);
		assertThat(aantalGenres).isEqualTo(genres.size());
		long vorigeGenre = 0;
		for (Genre genre : genres) {
			assertThat(genre.getId()).isEqualTo(vorigeGenre = genre.getId());
		}

	}

}
