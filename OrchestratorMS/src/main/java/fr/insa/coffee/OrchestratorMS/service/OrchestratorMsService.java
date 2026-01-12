package fr.insa.coffee.OrchestratorMS.service;

import fr.insa.coffee.OrchestratorMS.repository.OrchestratorMsRepository;
import org.springframework.stereotype.Service;


@Service
public class OrchestratorMsService {
    private final OrchestratorMsRepository OrchestratorMsRepository;

    public OrchestratorMsService(OrchestratorMsRepository OrchestratorMsRepository) {
        this.OrchestratorMsRepository = OrchestratorMsRepository;
    }

}
