package fr.insa.coffee.OrchestratorMS.controller;

import fr.insa.coffee.OrchestratorMS.service.OrchestratorMsService;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/Orchestrator-Ms")
public class OrchestratorMsController {
	private final OrchestratorMsService OrchestratorMsService;

	public OrchestratorMsController(OrchestratorMsService OrchestratorMsService) {
		this.OrchestratorMsService = OrchestratorMsService;
	}

	// Enregistrer ou mettre à jour une valeur de capteur
	@PostMapping
	public OrchestratorMs saveOrUpdateOrchestratorMsData(@RequestParam Long machineId, @RequestParam Integer value) throws SQLException {
		return OrchestratorMsService.saveOrUpdateOrchestratorMsData(machineId, value);
	}

	// RéOrchestratorérer la dernière valeur pour une machine
	@GetMapping("/{machineId}")
	public OrchestratorMs getLatestOrchestratorMsData(@PathVariable Long machineId) throws SQLException {
		return OrchestratorMsService.getLatestOrchestratorMsData(machineId);
	}
}
