package pl.konczak.nzoz.ewus.client.old;

import pl.konczak.nzoz.ewus.client.old.broker.AuthToken;
import pl.konczak.nzoz.ewus.client.old.broker.Session;

public class LoginResponse {

    private String session;

    private String authToken;

    private String response;

    public LoginResponse(String session, String authToken, String response) {
        this.session = session;
        this.authToken = authToken;
        this.response = response;
    }

    public LoginResponse(Session session, AuthToken authToken, String response) {
    }

    public Session getSessionX() {
        return null;
    }

    public AuthToken getAuthTokenX() {
        return null;
    }

    public String getResponseX() {
        return null;
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
