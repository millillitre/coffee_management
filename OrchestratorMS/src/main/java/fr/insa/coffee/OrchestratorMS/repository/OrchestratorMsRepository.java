package fr.insa.coffee.OrchestratorMS.repository;

import javax.crypto.Mac;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.insa.coffee.OrchestratorMS.model.OrchestratorMs;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class OrchestratorMsRepository {

    @Autowired
    private DataSource dataSource;

    // Recuperer la derniere liste a jour des id de machines
    public List<Long> getMachineIds() throws SQLException {
        List<Long> listOfIds = new ArrayList<>();
        String checkSql = "SELECT machine_id FROM machine";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                ResultSet rs = checkStmt.executeQuery()) {
            while (rs.next()) {
                listOfIds.add(rs.getLong("machine_id"));
            }
        }
        return listOfIds;
    }

    // Ajout d'un element dans la table action_history (changement de valeur d'une
    // LED)
    public void insertActionHistory(Long machineId, long actionId, String newStatus, LocalDateTime timeStamp)
            throws SQLException {
        // Verification que il y a bien changement d'etat
        String checkSql = "SELECT status FROM action_history WHERE machine_id=? AND action_id=? ORDER BY timestamp DESC LIMIT 1";
        String insertSql = "INSERT INTO action_history (machineId, status, timestamp) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                ResultSet rs = checkStmt.executeQuery()) {
            if (rs.next()) {
                // Si il n'y as pas de changement d'etat, on ne fait rien
                if (rs.getString("status").equals(newStatus)) {
                    return;
                }
                // Si il y a bien changement d'etat, on le documente dans la BDD
                else {
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql,
                            Statement.RETURN_GENERATED_KEYS)) {
                        insertStmt.setLong(1, machineId);
                        insertStmt.setString(2, newStatus);
                        insertStmt.setTimestamp(3, Timestamp.valueOf(timeStamp));
                        insertStmt.executeUpdate();
                    }
                }
            }
        }

    }

}
