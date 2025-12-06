package fr.insa.coffee.CupMS.service;

import fr.insa.coffee.CupMS.model.CupMs;
import fr.insa.coffee.CupMS.repository.CupMsRepository;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Service
public class CupMsService {
    private final CupMsRepository cupMsRepository;

    public CupMsService(CupMsRepository cupMsRepository) {
        this.cupMsRepository = cupMsRepository;
    }

    // Enregistrer ou mettre à jour la valeur du capteur
    public CupMs saveOrUpdateCupMsData(Long machineId, Integer value) throws SQLException {
        CupMs cupMs = new CupMs(machineId, value, LocalDateTime.now());
        return cupMsRepository.saveOrUpdate(cupMs);
    }

    // Récupérer la dernière valeur pour une machine
    public CupMs getLatestCupMsData(Long machineId) throws SQLException {
        return cupMsRepository.findLatestByMachineId(machineId);
    }

    // Idée pour l'orchestrateur: Vérifier si le stock de tasses est critique
    /*
     * public String checkCupStatus(Long machineId) throws SQLException {
     * List<CupMs> latestData =
     * cupMsRepository.findByMachineIdOrderByTimestampDesc(machineId);
     * if (latestData.isEmpty()) {
     * return "off";
     * }
     * Integer latestValue = latestData.get(0).getValue();
     * if (latestValue <= 0) {
     * return "red";
     * } else if (latestValue < 5) {
     * return "blue";
     * } else {
     * return "green";
     * }
     * }
     */
}
