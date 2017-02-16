package pl.konczak.nzoz.ewus.web.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.konczak.nzoz.ewus.client.ewus.AuthenticationClient;
import pl.konczak.nzoz.ewus.client.ewus.LoginResponse;

@RestController
@RequestMapping("/ewus")
public class EwusApi {

    private final AuthenticationClient authenticationClient;

    @Autowired
    public EwusApi(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<LoginResponse> login() {

        LoginResponse loginResponse = authenticationClient.login();

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
}
