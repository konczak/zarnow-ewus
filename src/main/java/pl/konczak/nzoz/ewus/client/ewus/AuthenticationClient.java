package pl.konczak.nzoz.ewus.client.ewus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import pl.konczak.nzoz.ewus.client.broker.LoginParam;
import pl.konczak.nzoz.ewus.client.broker.LoginParams;
import pl.konczak.nzoz.ewus.client.broker.LoginRequest;
import pl.konczak.nzoz.ewus.client.broker.ObjectFactory;
import pl.konczak.nzoz.ewus.client.broker.ParamValue;

import javax.xml.bind.JAXBElement;

public class AuthenticationClient
        extends WebServiceGatewaySupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationClient.class);

    private final ObjectFactory objectFactory;

    public AuthenticationClient() {
        objectFactory = new ObjectFactory();
    }

    public void login(String login, String password) {
        LOGGER.info("init: login <{}> <{}>", login, password);

        JAXBElement<LoginRequest> loginRequest = prepareLoginRequest(login, password);

        Object response = getWebServiceTemplate()
                .marshalSendAndReceive("https://ewus.nfz.gov.pl/ws-broker-server-ewus-auth-test/services/Auth",
                        loginRequest,
                        new SoapActionCallback("https://ewus.nfz.gov.pl/ws-broker-server-ewus-auth-test/services/Auth"));
        JAXBElement<String> something = (JAXBElement<String>) response;

        LOGGER.info("something <{}>", something.getValue());

        LOGGER.info("complete: login");
    }

    private JAXBElement<LoginRequest> prepareLoginRequest(String login, String password) {
        LoginRequest loginRequest = objectFactory.createLoginRequest();
        LoginParams loginParams = objectFactory.createLoginParams();

        loginParams.getItem().add(domainParam());
        loginParams.getItem().add(loginParam(login));
        loginParams.getItem().add(typeParam());
        loginParams.getItem().add(idSwdParam());

        loginRequest.setCredentials(loginParams);
        loginRequest.setPassword(password);

        return objectFactory.createLogin(loginRequest);
    }

    private LoginParam domainParam() {
        LoginParam domainParam = objectFactory.createLoginParam();
        ParamValue paramValue = objectFactory.createParamValue();

        domainParam.setName("domain");
        //what is this?
        paramValue.setStringValue("01");
        domainParam.setValue(paramValue);

        return domainParam;
    }

    private LoginParam loginParam(String login) {
        LoginParam loginParam = objectFactory.createLoginParam();
        ParamValue loginValue = objectFactory.createParamValue();

        loginParam.setName("login");
        loginValue.setStringValue(login);
        loginParam.setValue(loginValue);

        return loginParam;
    }

    private LoginParam typeParam() {
        LoginParam typeParam = objectFactory.createLoginParam();
        ParamValue typeValue = objectFactory.createParamValue();

        typeParam.setName("type");
        typeValue.setStringValue("SWD");
        typeParam.setValue(typeValue);

        return typeParam;
    }

    private LoginParam idSwdParam() {
        LoginParam idSwdParam = objectFactory.createLoginParam();
        ParamValue idSwdValue = objectFactory.createParamValue();

        idSwdParam.setName("idntSwd");
        idSwdValue.setStringValue("123456789");
        idSwdParam.setValue(idSwdValue);

        return idSwdParam;
    }
}
