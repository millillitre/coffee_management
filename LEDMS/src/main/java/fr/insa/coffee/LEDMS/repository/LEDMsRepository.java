
package fr.insa.coffee.LEDMS.repository;

import javax.crypto.Mac;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.insa.coffee.LEDMS.model.LEDMS;
import fr.insa.coffee.LEDMS.model.LEDStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class LEDMsRepository {

    @Autowired
    private DataSource dataSource;

    public LEDMS saveOrUpdate(LEDMS ledms) throws SQLException {
        String checkSql = "SELECT action_id FROM action_history WHERE machine_id = ?";
        String insertSql = "INSERT INTO action_history (machine_id, status, timestamp) VALUES (?, ?, ?)";
        String updateSql = "UPDATE action_history SET status = ?, timestamp = ? WHERE machine_id = ?";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setLong(1, ledms.getMachineId());

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, ledms.getStatus().name()); // Utilise .name() pour obtenir le nom de
                                                                           // l'enum
                        updateStmt.setTimestamp(2, Timestamp.valueOf(ledms.getTimestamp()));
                        updateStmt.setLong(3, ledms.getMachineId());
                        updateStmt.executeUpdate();
                    }
                    ledms.setActionId(rs.getLong("action_id"));
                } else {
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql,
                            Statement.RETURN_GENERATED_KEYS)) {
                        insertStmt.setLong(1, ledms.getMachineId());
                        insertStmt.setString(2, ledms.getStatus().name()); // Utilise .name() pour obtenir le nom de
                                                                           // l'enum
                        insertStmt.setTimestamp(3, Timestamp.valueOf(ledms.getTimestamp()));
                        insertStmt.executeUpdate();

                        try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                ledms.setActionId(generatedKeys.getLong(1));
                            }
                        }
                    }
                }
            }
        }
        return ledms;
    }

    public LEDMS findLatestByMachineId(Long machineId) throws SQLException {
        String sql = "SELECT * FROM action_history WHERE machine_id = ? ORDER BY timestamp DESC LIMIT 1";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, machineId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LEDMS sensor = new LEDMS();
                    sensor.setActionId(rs.getLong("action_id"));
                    sensor.setMachineId(rs.getLong("machine_id"));
                    String statusValue = rs.getString("status");
                    LEDStatus status = LEDStatus.valueOf(statusValue);
                    sensor.setStatus(status);
                    sensor.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                    return sensor;
                }
            }
        }
        return null;
    }
}
