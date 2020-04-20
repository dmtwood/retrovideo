package be.vdab.retrovideo.services;

import be.vdab.retrovideo.domain.Klant;
import be.vdab.retrovideo.repositories.KlantenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultKlantenService implements KlantenService {
	
	private final KlantenRepository repository;
	
	DefaultKlantenService(KlantenRepository repository){
		this.repository = repository;
	}

	@Override
	public List<Klant> findByFamilienaamBevat(String deelNaam) {
		return repository.findByFamilienaamBevat(deelNaam);
	}

	@Override
	public Klant read(int id) {
		return repository.read(id);
	}
	

}
