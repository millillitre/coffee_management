package fr.insa.coffee.CupMS.repository;

import fr.insa.coffee.CupMS.model.CupMs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;

@Repository
public class CupMsRepository {

    @Autowired
    private DataSource dataSource;

    public CupMs saveOrUpdate(CupMs cupMs) throws SQLException {
        String checkSql = "SELECT sensor_id FROM cup_sensor WHERE machine_id = ?";
        String insertSql = "INSERT INTO cup_sensor (machine_id, value, timestamp) VALUES (?, ?, ?)";
        String updateSql = "UPDATE cup_sensor SET value = ?, timestamp = ? WHERE machine_id = ?";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setLong(1, cupMs.getMachineId());

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, cupMs.getValue());
                        updateStmt.setTimestamp(2, Timestamp.valueOf(cupMs.getTimestamp()));
                        updateStmt.setLong(3, cupMs.getMachineId());
                        updateStmt.executeUpdate();
                    }
                    cupMs.setSensorId(rs.getLong("sensor_id"));
                } else {
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql,
                            Statement.RETURN_GENERATED_KEYS)) {
                        insertStmt.setLong(1, cupMs.getMachineId());
                        insertStmt.setInt(2, cupMs.getValue());
                        insertStmt.setTimestamp(3, Timestamp.valueOf(cupMs.getTimestamp()));
                        insertStmt.executeUpdate();

                        try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                cupMs.setSensorId(generatedKeys.getLong(1));
                            }
                        }
                    }
                }
            }
        }
        return cupMs;
    }

    public CupMs findLatestByMachineId(Long machineId) throws SQLException {
        String sql = "SELECT * FROM cup_sensor WHERE machine_id = ? ORDER BY timestamp DESC LIMIT 1";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, machineId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CupMs sensor = new CupMs();
                    sensor.setSensorId(rs.getLong("sensor_id"));
                    sensor.setMachineId(rs.getLong("machine_id"));
                    sensor.setValue(rs.getInt("value"));
                    sensor.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                    return sensor;
                }
            }
        }
        return null;
    }
}
