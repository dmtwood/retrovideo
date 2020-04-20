package be.vdab.retrovideo.forms;

import javax.validation.constraints.NotEmpty;

public class DeelNaamForm {
	@NotEmpty
	private String deelNaam;

	public String getDeelNaam() {
		return deelNaam;
	}

	public void setDeelNaam(String deelNaam) {
		this.deelNaam = deelNaam;
	}
}
