package pl.konczak.nzoz.ewus.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ewus.persistence")
public class EwusPersistenceConfiguration {

    private boolean execute;

    private String folder;

    public void setFolder(String folder) {
        if (folder != null
                && !folder.isEmpty()
                && !folder.endsWith("/")) {
            this.folder = folder + "/";
            log.info("Add to ewus persistence folder path ending /");
        } else {
            this.folder = folder;
        }
    }

}
