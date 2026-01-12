package fr.insa.coffee.LEDMS.service;

import fr.insa.coffee.LEDMS.model.*;
import fr.insa.coffee.LEDMS.repository.LEDMsRepository;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Service
public class LEDMsService {
    private final LEDMsRepository LEDMsRepository;

    public LEDMsService(LEDMsRepository LEDMsRepository) {
        this.LEDMsRepository = LEDMsRepository;
    }

    // Enregistrer ou mettre à jour la valeur du capteur
    public LEDMS saveOrUpdateLEDMsData(Long machineId, LEDStatus status) throws SQLException {
        LEDMS LEDMS = new LEDMS(machineId, status, LocalDateTime.now());
        return LEDMsRepository.saveOrUpdate(LEDMS);
    }

    // RéLEDérer la dernière valeur pour une machine
    public LEDMS getLatestLEDMsData(Long machineId) throws SQLException {
        return LEDMsRepository.findLatestByMachineId(machineId);
    }
}
