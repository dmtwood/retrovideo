package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Klant;

import java.util.List;

public interface KlantenRepository {
	List<Klant> findByFamilienaamBevat(String deelNaam);
	Klant read(long id);
}
