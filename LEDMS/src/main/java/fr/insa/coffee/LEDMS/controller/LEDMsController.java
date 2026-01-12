package fr.insa.coffee.LEDMS.controller;

import fr.insa.coffee.LEDMS.model.*;
import fr.insa.coffee.LEDMS.service.LEDMsService;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/led-Ms")
public class LEDMsController {
	private final LEDMsService LEDService;

	public LEDMsController(LEDMsService LEDMsService) {
		this.LEDService = LEDMsService;
	}

	// Enregistrer ou mettre à jour une valeur de led
	@PostMapping
	public LEDMS saveOrUpdateLEDMSData(@RequestParam Long machineId, @RequestParam LEDStatus status)
			throws SQLException {
		return LEDService.saveOrUpdateLEDMsData(machineId, status);
	}

	// Récupérer la dernière valeur pour une machine
	@GetMapping("/{machineId}")
	public LEDMS getLatestLEDMSsata(@PathVariable Long machineId) throws SQLException {
		return LEDService.getLatestLEDMsData(machineId);
	}
}
