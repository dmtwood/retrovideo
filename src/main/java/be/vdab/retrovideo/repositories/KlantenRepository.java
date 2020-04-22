package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Klant;

import java.util.List;
import java.util.Optional;

public interface KlantenRepository {
	List<Klant> findByFamilienaamBevat(String deelNaam);
	Optional<Klant> read(long id);
}
