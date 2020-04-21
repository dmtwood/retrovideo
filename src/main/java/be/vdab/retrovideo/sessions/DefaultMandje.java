package be.vdab.retrovideo.sessions;

import be.vdab.retrovideo.domain.TotalePrijs;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component
@SessionScope
public class DefaultMandje implements Serializable, Mandje {

	private static final long serialVersionUID = 1L;
	private final Set<Integer> filmIds = new LinkedHashSet<>();

	@Override
	public void addFilmId(int filmId) {
		filmIds.add(filmId);
	}

	@Override
	public Set<Integer> getFilmIds() {
		return filmIds;
	}

	@Override
	public void verwijderFilmId(int[] ids) {
		Arrays.stream(ids).forEach(id -> filmIds.remove(Integer.valueOf(id)));
	}

	@Override
	public TotalePrijs berekenTotalePrijs(List<BigDecimal> filmPrijzen) {
		TotalePrijs totaal = new TotalePrijs(
				filmPrijzen.stream().reduce((vorigeSom, getal) -> vorigeSom.add(getal)).orElse(BigDecimal.ZERO));
		return totaal;
	}

}
