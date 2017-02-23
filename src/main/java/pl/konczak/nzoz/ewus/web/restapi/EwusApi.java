package pl.konczak.nzoz.ewus.web.restapi;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.konczak.nzoz.ewus.client.ewus.AccessDatabse;
import pl.konczak.nzoz.ewus.client.ewus.AuthClient;
import pl.konczak.nzoz.ewus.client.ewus.BrokerClient;
import pl.konczak.nzoz.ewus.client.ewus.LoginResponse;

@RestController
@RequestMapping("/ewus")
public class EwusApi {

    private final AuthClient authClient;

    private final BrokerClient brokerClient;

    public EwusApi(AuthClient authClient,
            BrokerClient brokerClient) {
        this.authClient = authClient;
        this.brokerClient = brokerClient;
    }

    @RequestMapping(value = "/login",
                    method = RequestMethod.GET)
    public HttpEntity<LoginResponse> loginSecond() throws Exception {
        LoginResponse loginResponse = authClient.login();

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/db",
                    method = RequestMethod.GET)
    public HttpEntity<Void> db() {
        AccessDatabse accessDatabse = new AccessDatabse();

        accessDatabse.connect();

        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/full",
                    method = RequestMethod.GET)
    public HttpEntity<Void> full() throws Exception {
        LoginResponse loginResponse = authClient.login();
        
        brokerClient.checkCWU(loginResponse);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
