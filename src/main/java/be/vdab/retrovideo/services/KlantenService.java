package be.vdab.retrovideo.services;

import be.vdab.retrovideo.domain.Klant;

import java.util.List;

public interface KlantenService {
	List<Klant> findByFamilienaamBevat(String deelNaam);
	Klant read(long id);
}
