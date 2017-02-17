package pl.konczak.nzoz.ewus.web.restapi;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.konczak.nzoz.ewus.client.ewus.AuthClient;
import pl.konczak.nzoz.ewus.client.old.AuthenticationClient;
import pl.konczak.nzoz.ewus.client.old.LoginResponse;
import pl.konczak.nzoz.ewus.client.old.ServiceBrokerClient;

@RestController
@RequestMapping("/ewus")
public class EwusApi {

    private AuthenticationClient authenticationClient;

    private ServiceBrokerClient serviceBrokerClient;

    private final AuthClient authClient;

    public EwusApi(AuthClient authClient) {
        this.authClient = authClient;
    }

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<LoginResponse> login() throws Exception {
        LoginResponse loginResponse = authenticationClient.login();

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/login",
                    method = RequestMethod.GET)
    public HttpEntity<Void> loginSecond() throws Exception {
        authClient.login();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,
                    params = {"pesel"})
    public HttpEntity<Void> checkCWU(@RequestParam("pesel") String pesel) {

        LoginResponse loginResponse = authenticationClient.login();
        serviceBrokerClient.call(pesel, loginResponse);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
