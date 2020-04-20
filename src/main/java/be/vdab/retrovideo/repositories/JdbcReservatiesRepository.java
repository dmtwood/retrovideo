package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Reservatie;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcReservatiesRepository implements ReservatiesRepository {

	private final SimpleJdbcInsert insert;

	JdbcReservatiesRepository(DataSource dataSource) {
		this.insert = new SimpleJdbcInsert(dataSource);
		insert.withTableName("reservaties");
	}

	@Override
	public void create(Reservatie reservatie) {
		  Map<String, Object> kolomWaarden = new HashMap<>();
		  kolomWaarden.put("klantid", reservatie.getKlantId()); 
		  kolomWaarden.put("filmid", reservatie.getFilmId());  
		  kolomWaarden.put("reservatie", reservatie.getReservatie());  
		  insert.execute(kolomWaarden);

	}

}
