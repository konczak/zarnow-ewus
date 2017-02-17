package pl.konczak.nzoz.ewus.client.old;

import java.io.IOException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.support.MarshallingUtils;

import pl.konczak.nzoz.ewus.client.old.broker.AuthToken;
import pl.konczak.nzoz.ewus.client.old.broker.LoginRequest;
import pl.konczak.nzoz.ewus.client.old.broker.Session;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class AuthenticationClient
        extends WebServiceGatewaySupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationClient.class);

    private final Jaxb2Marshaller marshaller;

    private final LoginRequestFactory loginRequestFactory;

    public AuthenticationClient(Jaxb2Marshaller marshaller,
            LoginRequestFactory loginRequestFactory) {
        this.marshaller = marshaller;
        this.loginRequestFactory = loginRequestFactory;
    }

//    public void login(String login, String password) {
//        LOGGER.info("init: login <{}> <{}>", login, password);
//
//        JAXBElement<LoginRequest> loginRequest = prepareLoginRequest(login, password);
//
//        Object response = getWebServiceTemplate()
//                .marshalSendAndReceive("https://ewus.nfz.gov.pl/ws-broker-server-ewus-auth-test/services/Auth",
//                        loginRequest,
//                        new SoapActionCallback("https://ewus.nfz.gov.pl/ws-broker-server-ewus-auth-test/services/Auth"));
//        JAXBElement<String> something = (JAXBElement<String>) response;
//
//        LOGGER.info("something <{}>", something.getValue());
//
//        LOGGER.info("complete: login");
//    }
    public LoginResponse login() {
        LOGGER.debug("login");

        JAXBElement<LoginRequest> loginRequest = loginRequestFactory.create();

        LoginResponse loginResponse = getWebServiceTemplate().sendAndReceive(
                new WebServiceMessageCallback() {
                    public void doWithMessage(WebServiceMessage message) throws IOException {
                        MarshallingUtils.marshal(marshaller, loginRequest, message);
                    }
                },
                new WebServiceMessageExtractor<LoginResponse>() {
                    public LoginResponse extractData(WebServiceMessage message) throws IOException {
                        SoapHeader header = ((SoapMessage) message).getSoapHeader();
//                        Iterator<SoapHeaderElement> all = header.examineAllHeaderElements();
//                        while (all.hasNext()) {
//                            SoapHeaderElement soapHeaderElement = all.next();
//                            LOGGER.info("header qname <{}> getNamespaceURI <{}> getPrefix <{}> getLocalPart <{}>",
//                                    soapHeaderElement.getName().toString(),
//                                    soapHeaderElement.getName().getNamespaceURI(),
//                                    soapHeaderElement.getName().getPrefix(),
//                                    soapHeaderElement.getName().getLocalPart());
//                            Iterator<QName> qnameIterator = soapHeaderElement.getAllAttributes();
//                            while (qnameIterator.hasNext()) {
//                                QName qName = qnameIterator.next();
//                                LOGGER.info("qname <{}> <{}> <{}> <{}>",
//                                        qName.getNamespaceURI(),
//                                        qName.getPrefix(),
//                                        qName.getLocalPart(),
//                                        qName.toString());
//                            }
//                        }

                        Iterator<SoapHeaderElement> it = header.examineHeaderElements(new QName("http://xml.kamsoft.pl/ws/common", "session", "ns1"));
                        Session session = it.hasNext() ? (Session) marshaller.unmarshal(it.next().getSource()) : null;
                        it = header.examineHeaderElements(new QName("http://xml.kamsoft.pl/ws/common", "authToken", "ns1"));
                        AuthToken authToken = it.hasNext() ? (AuthToken) marshaller.unmarshal(it.next().getSource()) : null;
                        return new LoginResponse(session,
                                authToken,
                                ((JAXBElement<String>) MarshallingUtils.unmarshal(marshaller, message)).getValue());
                    }
                });

        LOGGER.info("loginResponse session <{}> authToken <{}> response <{}>",
                loginResponse.getSession().getId(),
                loginResponse.getAuthToken().getId(),
                loginResponse.getResponse());

        LOGGER.debug("complete: login");

        return loginResponse;
    }

}
