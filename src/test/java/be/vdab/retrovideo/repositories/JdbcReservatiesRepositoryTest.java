package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Reservatie;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@JdbcTest
@Import(JdbcReservatiesRepository.class)
@Sql("/insertReservatie.sql")

public class JdbcReservatiesRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

	private static final String RESERVATIES = "reservaties";

	private JdbcReservatiesRepository repository;

	void create() {
		long old = super.countRowsInTable(RESERVATIES);
		repository.create(
				new Reservatie(
						100,
						110,
						LocalDateTime.now()
				)
		);
		assertThat(old).isEqualTo(super.countRowsInTable(RESERVATIES));
	}

}
