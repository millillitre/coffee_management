package fr.insa.coffee.LEDMS.model;

import java.time.LocalDateTime;

public class LEDMS {
    private Long actionId;
    private Long machineId;
    private LEDStatus status;
    private LocalDateTime timestamp;

    // Constructeurs
    public LEDMS() {
    }

    public LEDMS(Long machineId, LEDStatus status, LocalDateTime timestamp) {
        this.machineId = machineId;
        this.status = status;
        this.timestamp = timestamp;
    }

    // Getters et Setters
    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public LEDStatus getStatus() {
        return status;
    }

    public void setStatus(LEDStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
