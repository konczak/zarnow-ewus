package pl.konczak.nzoz.ewus.domain.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Credentials {

    private final String session;

    private final String authToken;

    private final String response;

}
