package pl.konczak.nzoz.ewus.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TotpConfig {

    private String secret;

    private String issuer;

    private String algorithm;

    private Integer digits;

    private Integer period;

}
