package fr.insa.coffee.OrchestratorMS.repository;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
}
