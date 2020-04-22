package be.vdab.retrovideo.domain;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class TotalePrijs {
	@NumberFormat(pattern = "€ 0.00")
	@Positive @NotBlank @NotEmpty
	private final BigDecimal waarde;

	public TotalePrijs(BigDecimal waarde) {
		this.waarde = waarde;
	}

	public BigDecimal getWaarde() {
		return waarde;
	}
}
