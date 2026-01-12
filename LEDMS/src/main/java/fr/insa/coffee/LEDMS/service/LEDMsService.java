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

    // Récupérer la dernière valeur pour une machine
    public LEDMS getLatestLEDMsData(Long machineId) throws SQLException {
        return LEDMsRepository.findLatestByMachineId(machineId);
    }

    // Mettre à jour le statut de la LED en fonction des données des capteurs
    public LEDMS updateLEDStatusFromSensors(Long machineId, int cupQuantity, int presenceValue) throws SQLException {
        LEDStatus newStatus;
        if (cupQuantity <= 0) {
            newStatus = LEDStatus.BLUE; // Plus de gobelets : service dégradé
        } else if (presenceValue > 15) {
            newStatus = LEDStatus.ORANGE; // Trop de monde
        } else {
            newStatus = LEDStatus.GREEN; // Tout est normal alors LED verte
        }
        return saveOrUpdateLEDMsData(machineId, newStatus);
    }
}
