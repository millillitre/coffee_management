package fr.insa.coffee.CupMS.controller;

import fr.insa.coffee.CupMS.model.CupMs;
import fr.insa.coffee.CupMS.service.CupMsService;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/cup-Ms")
public class CupMsController {
	private final CupMsService cupMsService;

	public CupMsController(CupMsService cupMsService) {
		this.cupMsService = cupMsService;
	}

	// Enregistrer ou mettre à jour une valeur de capteur
	@PostMapping
	public CupMs saveOrUpdateCupMsData(@RequestParam Long machineId, @RequestParam Integer value) throws SQLException {
		return cupMsService.saveOrUpdateCupMsData(machineId, value);
	}

	// Récupérer la dernière valeur pour une machine
	@GetMapping("/{machineId}")
	public CupMs getLatestCupMsData(@PathVariable Long machineId) throws SQLException {
		return cupMsService.getLatestCupMsData(machineId);
	}
}
