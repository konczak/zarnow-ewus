package pl.konczak.nzoz.ewus.web.restapi;

import pl.konczak.nzoz.ewus.client.ewus.auth.Credentials;

public class LoginResponse {

    private final String session;

    private final String authToken;

    private final String response;

    public LoginResponse(Credentials credentials) {
        this.session = credentials.getSession();
        this.authToken = credentials.getAuthToken();
        this.response = credentials.getResponse();
    }

    public String getSession() {
        return session;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getResponse() {
        return response;
    }

}
