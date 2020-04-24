package be.vdab.retrovideo.repositories;
//import org.testng.annotations.Test;


import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.exceptions.FilmNietGevondenException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;



@JdbcTest
@Import(JdbcFilmsRepository.class)
@Sql("/insertFilm.sql")
class JdbcFilmsRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

	private static final String FILMS = "films";
	JdbcFilmsRepository repository;

	public JdbcFilmsRepositoryTest(JdbcFilmsRepository repository) {
		this.repository = repository;
	}

	private long idVanTestFilm() {
		return super.jdbcTemplate.queryForObject(
				"select id from films where titel ='test'", Long.class);
	};
		@Test
	public void findByGenre() {
		assertThat(
//				jdbcTemplate.queryForObject(
				repository.findByGenre(
						1).size()
		).isEqualTo(super.countRowsInTableWhere(FILMS, "genreid = 1")
		);
	}

	@Test
	public void read() {

		assertThat(
				repository.read(idVanTestFilm())
						.get().getTitel()
		).isEqualTo("test");
	}

	@Test
	public void readOnbestaandeFilm() {
		assertThat(repository.read(-1)).isNotPresent();
	}

	@Test
	public void update() {
		long id = idVanTestFilm();
		Film film = new Film( id, 1, "test", 10, 6, BigDecimal.TEN);
		repository.update(film);
		long test = repository.read(idVanTestFilm()).get().getGereserveerd();
		assertThat(test).isEqualTo(7);
	}


	@Test
	public void updateOnbestaandeFilm() {
			assertThatExceptionOfType(
					FilmNietGevondenException.class
			).isThrownBy(
					() -> repository.update(
							new Film( - 1, 2, "test", 10, 6, BigDecimal.TEN)
					)
			);
	}
}