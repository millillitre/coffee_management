package fr.insa.coffee.PresenceMS.service;

import fr.insa.coffee.PresenceMS.model.PresenceMs;
import fr.insa.coffee.PresenceMS.repository.PresenceMsRepository;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PresenceMsService {
    private final PresenceMsRepository presenceMsRepository;

    public PresenceMsService(PresenceMsRepository presenceMsRepository) {
        this.presenceMsRepository = presenceMsRepository;
    }

    public PresenceMs savePresenceMsData(Long machineId, Integer value) throws SQLException {
        PresenceMs presenceMs = new PresenceMs(machineId, value, LocalDateTime.now());
        return presenceMsRepository.save(presenceMs);
    }

    public List<PresenceMs> getPresenceMsHistory(Long machineId) throws SQLException {
        return presenceMsRepository.findByMachineIdOrderByTimestampDesc(machineId);
    }
}
