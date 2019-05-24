package pl.konczak.nzoz.ewus.db;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.konczak.nzoz.ewus.config.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
@Component
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ParadoxDatabaseManager {

    private final DatabaseConfiguration databaseConfiguration;

    public Connection getConnection() throws Exception {
        try {
            Class.forName(databaseConfiguration.getDriver());
        } catch (ClassNotFoundException e) {
            log.error("Where is your Paradox JDBC Driver?", e);
            throw e;
        }

        Connection connection;

        try {
            connection = DriverManager.getConnection(databaseConfiguration.getUrl());

        } catch (SQLException e) {
            log.error("Connection Failed! Check output console", e);
            throw e;
        }

        if (connection == null) {
            throw new RuntimeException("Database connection is missing");
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            log.error("Failed to close connection", ex);
        }
    }
}
