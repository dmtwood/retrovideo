package be.vdab.retrovideo.domain;

import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

public class TotalePrijs {
	@NumberFormat(pattern = "€ 0.00")
	private final BigDecimal waarde;

	public TotalePrijs(BigDecimal waarde) {
		this.waarde = waarde;
	}

	public BigDecimal getWaarde() {
		return waarde;
	}
}
