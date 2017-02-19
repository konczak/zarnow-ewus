package pl.konczak.nzoz.ewus.client.ewus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessDatabse {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessDatabse.class);

    public void connect() {
        LOGGER.info("-------- Paradox JDBC Connection Testing ------------");

        try {
            Class.forName("com.googlecode.paradox.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.error("Where is your Paradox JDBC Driver?", e);
            return;
        }

        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection("jdbc:paradox:e:\\programowanie\\nzoz\\db");

        } catch (SQLException e) {
            LOGGER.error("Connection Failed! Check output console", e);
            return;
        }

        if (connection == null) {
            LOGGER.info("Failed to make connection!");
            return;
        } else {
            LOGGER.info("You made it, take control your database now!");
        }

        select(connection);

        try {
            connection.close();

            LOGGER.info("Connection closed - THX!");
        } catch (SQLException ex) {
            LOGGER.error("Failed to close connection", ex);
        }
    }

    private void select(Connection connection) {
        Statement statement = null;

        String selectTableSQL = "SELECT PESEL from Bazap";

        try {
            statement = connection.createStatement();

            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(selectTableSQL);
            int count = 0;

            while (rs.next()) {
                count++;

                String pesel = rs.getString("PESEL");

                LOGGER.info("Read <{}> pesel <{}>", count, pesel);
            }

        } catch (SQLException e) {
            LOGGER.error("select failed", e);

        } finally {

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    LOGGER.error("failed to close statement", ex);
                }
            }

        }
    }
}
