package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Klant;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import(JdbcKlantenRepository.class)
@Sql("/insertKlant.sql")
public class JdbcKlantenRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

	private JdbcKlantenRepository repository;

	public JdbcKlantenRepositoryTest(JdbcKlantenRepository repository) {
		this.repository = repository;
	}

	@Test
	public void findByFamilienaamBevat() {
		List<Klant> klanten = repository.findByFamilienaamBevat("te");
		String vorigeNaam = "";
		for (Klant klant : klanten) {
			assertTrue(klant.getFamilienaam().toLowerCase().contains("te"));
			assertTrue(vorigeNaam.compareTo(klant.getFamilienaam()) <= 0);
			vorigeNaam = klant.getFamilienaam();
		}
		long aantalKlanten = super.jdbcTemplate
				.queryForObject("select count(*) from klanten where familienaam like '%te%'", Long.class);
		assertEquals(aantalKlanten, klanten.size());
	}

	 long idVanTestKlant() {
		return super.jdbcTemplate.queryForObject("select id from klanten where familienaam='testfamilienaam'", Long.class);
	}

	@Test
	public void read() {
		assertThat(
				repository.read(
						idVanTestKlant()
				).get().getFamilienaam()).isEqualTo("testfamilienaam");
	}

	@Test
	public void findByOnbestaandeIdVindtGeenKlant() {
		assertThat(repository.read(-1)).isEmpty();
	}

}
