package fr.insa.coffee.OrchestratorMS.controller;

import fr.insa.coffee.OrchestratorMS.service.OrchestratorMsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Orchestrator-Ms")
public class OrchestratorMsController {
	private final OrchestratorMsService OrchestratorMsService;

	public OrchestratorMsController(OrchestratorMsService OrchestratorMsService) {
		this.OrchestratorMsService = OrchestratorMsService;
	}	
}
