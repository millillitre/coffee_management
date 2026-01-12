package fr.insa.coffee.PresenceMS.repository;

import fr.insa.coffee.PresenceMS.model.PresenceMs;
import org.springframework.stereotype.Repository;
import java.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PresenceMsRepository {

    @Autowired
    private DataSource dataSource;

    public PresenceMs save(PresenceMs presenceMs) throws SQLException {
        String sql = "INSERT INTO presence_sensor (machine_id, value, timestamp) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, presenceMs.getMachineId());
            stmt.setInt(2, presenceMs.getValue());
            stmt.setTimestamp(3, Timestamp.valueOf(presenceMs.getTimestamp()));
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    presenceMs.setSensorId(generatedKeys.getLong(1));
                }
            }
        }
        return presenceMs;
    }

    public List<PresenceMs> findByMachineIdOrderByTimestampDesc(Long machineId) throws SQLException {
        List<PresenceMs> sensors = new ArrayList<>();
        String sql = "SELECT * FROM presence_sensor WHERE machine_id = ? ORDER BY timestamp DESC";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, machineId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PresenceMs sensor = new PresenceMs();
                    sensor.setSensorId(rs.getLong("sensor_id"));
                    sensor.setMachineId(rs.getLong("machine_id"));
                    sensor.setValue(rs.getInt("value"));
                    sensor.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                    sensors.add(sensor);
                }
            }
        }
        return sensors;
    }
}
