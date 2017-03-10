package pl.konczak.nzoz.ewus.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ewus.persistence")
public class EwusPersistenceConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(EwusPersistenceConfiguration.class);

    private boolean execute;

    private String folder;

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        if (folder != null
                && !folder.isEmpty()
                && !folder.endsWith("/")) {
            this.folder = folder + "/";
            LOGGER.info("Add to ewus persistence folder path ending /");
        } else {
            this.folder = folder;
        }
    }

}
