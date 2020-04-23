package be.vdab.retrovideo.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
@Import(JdbcGenresRepository.class)
@Sql("/insertGenre.sql")
public class JdbcGenresRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private JdbcGenresRepository repository;

    @Test
    public void findUniekeGenresVolgensId() {
        assertThat(
                jdbcTemplate.queryForObject(
                        "select count(distinct id) from genres", Long.class
                )
        ).isEqualTo(
                (super.countRowsInTable("genres")
                )
//                        repository.findUniekeGenres()).size()
        );
    }
}
