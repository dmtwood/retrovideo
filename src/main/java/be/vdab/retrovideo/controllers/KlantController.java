package be.vdab.retrovideo.controllers;

import be.vdab.retrovideo.domain.Klant;
import be.vdab.retrovideo.forms.DeelNaamForm;
import be.vdab.retrovideo.services.KlantenService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.List;

/**
 * Controller Class handling klant requests
 *  depends on klantenService
 *  creates KlantenController-Bean
 */
@Controller
@RequestMapping("/klant")
class KlantController {
	
	private final KlantenService klantenService;

	KlantController(KlantenService klantenService){
		this.klantenService = klantenService;
	}


	private final static String KLANT_MAV = "klant";
	@GetMapping()
    ModelAndView zoekKlant() {
		return new ModelAndView(KLANT_MAV)
				.addObject(
						new DeelNaamForm()
				);
	}


	@GetMapping("deelNaam")
    ModelAndView zoekOpDeelNaam(@Valid DeelNaamForm deelNaamForm, Errors errors) {
		ModelAndView modelAndView = new ModelAndView(KLANT_MAV);
		if(errors.hasErrors()) {
			return modelAndView;
		}
		List<Klant> klanten = klantenService.findByFamilienaamBevat(
				deelNaamForm.getDeelNaam()
		);
		if(klanten.isEmpty()) {
			return modelAndView;
		}
		else {
			modelAndView.addObject(
					"klanten",
					klanten);
		}
		return modelAndView;
	}
}
