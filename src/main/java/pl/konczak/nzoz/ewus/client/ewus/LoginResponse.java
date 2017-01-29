package pl.konczak.nzoz.ewus.client.ewus;

import pl.konczak.nzoz.ewus.client.broker.AuthToken;
import pl.konczak.nzoz.ewus.client.broker.Session;

public class LoginResponse {

    private Session session;

    private AuthToken authToken;

    private String response;

    public LoginResponse(Session session, AuthToken authToken, String response) {
        this.session = session;
        this.authToken = authToken;
        this.response = response;
    }

    public Session getSession() {
        return session;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public String getResponse() {
        return response;
    }

}
