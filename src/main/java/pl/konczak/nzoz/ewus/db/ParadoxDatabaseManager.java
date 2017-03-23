package pl.konczak.nzoz.ewus.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.konczak.nzoz.ewus.config.DatabaseConfiguration;

@Component
public class ParadoxDatabaseManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParadoxDatabaseManager.class);

    private final DatabaseConfiguration databaseConfiguration;

    public ParadoxDatabaseManager(DatabaseConfiguration databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

    public Connection getConnection() throws Exception {
        try {
            Class.forName(databaseConfiguration.getDriver());
        } catch (ClassNotFoundException e) {
            LOGGER.error("Where is your Paradox JDBC Driver?", e);
            throw e;
        }

        Connection connection;

        try {
            connection = DriverManager.getConnection(databaseConfiguration.getUrl());

        } catch (SQLException e) {
            LOGGER.error("Connection Failed! Check output console", e);
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
            LOGGER.error("Failed to close connection", ex);
        }
    }
}
