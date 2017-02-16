package pl.konczak.nzoz.ewus.web.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.konczak.nzoz.ewus.client.ewus.AuthenticationClient;
import pl.konczak.nzoz.ewus.client.ewus.LoginResponse;
import pl.konczak.nzoz.ewus.client.ewus.ServiceBrokerClient;

@RestController
@RequestMapping("/ewus")
public class EwusApi {

    private final AuthenticationClient authenticationClient;

    private final ServiceBrokerClient serviceBrokerClient;

    @Autowired
    public EwusApi(AuthenticationClient authenticationClient,
            ServiceBrokerClient serviceBrokerClient) {
        this.authenticationClient = authenticationClient;
        this.serviceBrokerClient = serviceBrokerClient;
    }

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<LoginResponse> login() {

        LoginResponse loginResponse = authenticationClient.login();

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,
                    params = {"pesel"})
    public HttpEntity<Void> checkCWU(@RequestParam("pesel") String pesel) {

        LoginResponse loginResponse = authenticationClient.login();
        serviceBrokerClient.call(pesel, loginResponse);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
