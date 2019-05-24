package pl.konczak.nzoz.ewus.web.restapi.ewus;

import lombok.Getter;
import pl.konczak.nzoz.ewus.domain.authentication.Credentials;

@Getter
public class LoginResponse {

    private final String session;

    private final String authToken;

    private final String response;

    public LoginResponse(Credentials credentials) {
        this.session = credentials.getSession();
        this.authToken = credentials.getAuthToken();
        this.response = credentials.getResponse();
    }

}
