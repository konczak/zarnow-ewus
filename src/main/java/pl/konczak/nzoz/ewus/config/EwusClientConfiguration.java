package pl.konczak.nzoz.ewus.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ewus.client")
public class EwusClientConfiguration {

    private String name;

    private String version;

}
