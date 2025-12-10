package fr.insa.coffee.PresenceMS.model;

import java.time.LocalDateTime;

public class PresenceMs {
    private Long sensorId;
    private Long machineId;
    private Integer value;  // Nombre de personnes détectées
    private LocalDateTime timestamp;

    // Constructeurs
    public PresenceMs() {}

    public PresenceMs(Long machineId, Integer value, LocalDateTime timestamp) {
        this.machineId = machineId;
        this.value = value;
        this.timestamp = timestamp;
    }

    // Getters et Setters
    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

