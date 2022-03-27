package pl.konczak.nzoz.ewus.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ewus.check-listed")
public class EwusCheckListedConfiguration {

    private String filePath;
}
