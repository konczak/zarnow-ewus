package pl.konczak.nzoz.ewus.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "database")
public class DatabaseConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private String driver;

    private String url;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (url != null
                && !url.isEmpty()
                && url.endsWith("/")) {
            //remove last character which is "/"
            this.url = url.substring(0, url.length() - 1);
            LOGGER.info("Trimmed last character of database URL");
        } else {
            this.url = url;
        }
    }

}
