package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Klant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JdbcKlantenRepository.class)
@Sql("/insertKlant.sql")

public class JdbcKlantenRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private JdbcKlantenRepository repository;

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

	private int idVanTestKlant() {
		return super.jdbcTemplate.queryForObject("select id from klanten where familienaam='testfamilienaam'",
				Integer.class);
	}

	@Test
	public void read() {
		assertEquals("testvoornaam", repository.read(idVanTestKlant()).getVoornaam());
	}

}
