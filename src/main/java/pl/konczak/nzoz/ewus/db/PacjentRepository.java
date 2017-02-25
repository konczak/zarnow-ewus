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

    @Cacheable(value = "pesel-list")
    public List<String> findAllPesel() {
        List<String> listOfPesel = new ArrayList<>();
        try {
            Connection connection = paradoxDatabaseManager.getConnection();

            listOfPesel = select(connection);

            paradoxDatabaseManager.closeConnection(connection);
        } catch (Exception e) {
            LOGGER.error("Failed to load list of pesel", e);
        }

        return listOfPesel.stream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = "pesel-list")
    public void clearCache() {
        LOGGER.info("pesel-list cache cleared");
    }

    private List<String> select(Connection connection) throws SQLException {
        List<String> listOfPesel = new ArrayList<>();
        Statement statement = null;
        String selectTableSQL = "SELECT PESEL from Bazap";

        try {
            statement = connection.createStatement();

            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(selectTableSQL);
            int count = 0;

            while (rs.next()) {
                count++;
                try {
                    String pesel = rs.getString("PESEL");
                    listOfPesel.add(pesel);
                    LOGGER.debug("Read <{}> pesel <{}>", count, pesel);
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
}
