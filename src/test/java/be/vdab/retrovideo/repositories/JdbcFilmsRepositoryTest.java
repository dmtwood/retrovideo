package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.exceptions.FilmNietGevondenException;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@JdbcTest
@Import(JdbcFilmsRepository.class)
@Sql("/insertFilm.sql")

public class JdbcFilmsRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

	private static final String FILMS = "films";
	JdbcFilmsRepository repository;

	@Test
	public void findByGenre() {
		List<Film> films = repository.findByGenre(1);
		String vorigeFilm = "";
		for (Film film : films) {
			assertThat(vorigeFilm).isLessThanOrEqualTo(film.getTitel());
			vorigeFilm = film.getTitel();
		}
		assertEquals(super.countRowsInTableWhere(FILMS, "genreid = 1"), films.size());
	}

	private long idVanTestFilm(){
		return super.jdbcTemplate.queryForObject("select id from films where titel='test'", long.class);
	}

	@Test
	public void read() {
		assertThat( repository.read(idVanTestFilm()).get().getTitel()).isEqualTo("test");
	}

	@Test
	public void readOnbestaandeFilm() {
		assertThat(repository.read(-1)).isNull();
	}

	@Test
	public void update() {
		long id = idVanTestFilm();
		Film film = new Film(id, 1, "test", 10, 6, BigDecimal.TEN);
		repository.update(film);
		long test = repository.read(idVanTestFilm()).get().getGereserveerd();
		assertThat(test).isEqualTo(7);
	}



	@Test(expected = FilmNietGevondenException.class)
	public void updateOnbestaandeFilm() {
		long id = idVanTestFilm();
		Film film = new Film(id - 1, 2, "test", 10, 6, BigDecimal.TEN);
		repository.update(film);
	}
}