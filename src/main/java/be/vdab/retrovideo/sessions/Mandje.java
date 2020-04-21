package be.vdab.retrovideo.sessions;

import be.vdab.retrovideo.domain.TotalePrijs;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface Mandje {
  void addFilmId(int filmId);  
  Set<Integer> getFilmIds(); 
  void verwijderFilmId(int[] ids);
  void mandjeLeeg();
  TotalePrijs berekenTotalePrijs(List<BigDecimal> filmPrijzen);
}
