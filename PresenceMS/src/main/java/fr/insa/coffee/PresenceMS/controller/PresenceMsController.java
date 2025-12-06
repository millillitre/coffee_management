package fr.insa.coffee.PresenceMS.controller;

import fr.insa.coffee.PresenceMS.model.PresenceMs;
import fr.insa.coffee.PresenceMS.service.PresenceMsService;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/presence-Ms")
public class PresenceMsController {
    private final PresenceMsService presenceMsService;

    public PresenceMsController(PresenceMsService presenceMsService) {
        this.presenceMsService = presenceMsService;
    }

    @PostMapping
    public PresenceMs savePresenceMsData(@RequestParam Long machineId, @RequestParam Integer value)
            throws SQLException {
        return presenceMsService.savePresenceMsData(machineId, value);
    }

    @GetMapping("/history/{machineId}")
    public List<PresenceMs> getPresenceMsHistory(@PathVariable Long machineId) throws SQLException {
        return presenceMsService.getPresenceMsHistory(machineId);
    }
}
