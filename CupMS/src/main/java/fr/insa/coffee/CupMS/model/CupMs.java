package fr.insa.coffee.CupMS.model;

import java.time.LocalDateTime;

public class CupMs {
    private Long sensorId;
    private Long machineId;
    private Integer value;
    private LocalDateTime timestamp;

    // Constructeurs
    public CupMs() {
    }

    public CupMs(Long machineId, Integer value, LocalDateTime timestamp) {
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
