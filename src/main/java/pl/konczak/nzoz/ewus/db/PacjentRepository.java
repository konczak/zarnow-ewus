package pl.konczak.nzoz.ewus.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class PacjentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacjentRepository.class);

    private final ParadoxDatabaseManager paradoxDatabaseManager;

    public PacjentRepository(ParadoxDatabaseManager paradoxDatabaseManager) {
        this.paradoxDatabaseManager = paradoxDatabaseManager;
    }

    @Cacheable(value = "patient-list")
    public List<Patient> findAll() {
        List<Patient> list = new ArrayList<>();
        try {
            Connection connection = paradoxDatabaseManager.getConnection();

            list = select(connection);

            paradoxDatabaseManager.closeConnection(connection);
        } catch (Exception e) {
            LOGGER.error("Failed to load list of Patient", e);
        }

        return list.stream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = "patient-list")
    public void clearCache() {
        LOGGER.info("patient-list cache cleared");
    }

    private List<Patient> select(Connection connection) throws SQLException {
        List<Patient> listOfPesel = new ArrayList<>();
        Statement statement = null;
        String selectTableSQL = "SELECT PESEL, STATUS from Bazap";

        try {
            statement = connection.createStatement();

            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(selectTableSQL);
            int count = 0;

            while (rs.next()) {
                count++;
                try {
                    String pesel = rs.getString("PESEL");
                    String status = rs.getString("STATUS");
                    PatientStatus patientStatus = PatientStatus.mapByFlag(status);
                    listOfPesel.add(new Patient(pesel, patientStatus));
                    LOGGER.debug("Read <{}> pesel <{}> status <{}>", count, pesel, patientStatus);
                } catch (SQLDataException sQLDataException) {
                    //got to the end
                    break;
                }
            }

        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return listOfPesel;
    }

    public void testAccess() throws Exception {
        Connection connection = null;
        try {
            connection = paradoxDatabaseManager.getConnection();

            select(connection);
        } catch (Exception e) {
            LOGGER.error("Failed to load list of Patient", e);
            throw e;
        } finally {
            paradoxDatabaseManager.closeConnection(connection);
        }
    }
}
