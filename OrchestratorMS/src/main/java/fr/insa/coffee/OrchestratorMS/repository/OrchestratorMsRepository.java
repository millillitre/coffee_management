package fr.insa.coffee.OrchestratorMS.repository;

import fr.insa.coffee.OrchestratorMS.util.DatabaseConnection;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.utils.*;

@Repository
public class OrchestratorMsRepository {

    // Recuperer la derniere liste a jour des id de machines
    public List<Long> getMachineIds() throws SQLException {
        List<Long> listOfIds = new ArrayList<>();
        String checkSql = "SELECT machine_id FROM machine";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                ResultSet rs = checkStmt.executeQuery()) {
            while (rs.next()) {
                listOfIds.add(rs.getLong("machine_id"));
            }
        }
        return listOfIds;
    }

    // Ajout d'un element dans la table action_history (changement de valeur d'une LED)
    public void updateActionHistory(Long machineId,long actionId,String newStatus,LocalDateTime timeStamp) throws SQLException {
        // Verification que il y a bien changement d'etat
        String checkSql = "SELECT status FROM action_history WHERE machine_id= AND action_id= ORDER BY timestamp DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                ResultSet rs = checkStmt.executeQuery()) {
            if (rs.next()){
                // Si il n'y as pas de changement d'etat, on ne fait rien
                if (rs.getString("status").equals(newStatus)){
                    return;
                }
                // Si il y a bien changement d'etat, on le documente dans la BDD
                else {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setLong(1, actionId);
                        updateStmt.setLong(1, machineId);
                        updateStmt.setString(1, newStatus);
                        updateStmt.setTimestamp(2, Timestamp.valueOf(timeStamp));
                        updateStmt.executeUpdate();
                    }
                }
            }
        }
                
    }
        
}
