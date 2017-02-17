package pl.konczak.nzoz.ewus.client.old;

import org.springframework.stereotype.Component;

import pl.konczak.nzoz.ewus.client.old.broker.LoginParam;
import pl.konczak.nzoz.ewus.client.old.broker.LoginParams;
import pl.konczak.nzoz.ewus.client.old.broker.LoginRequest;
import pl.konczak.nzoz.ewus.client.old.broker.ObjectFactory;
import pl.konczak.nzoz.ewus.client.old.broker.ParamValue;

import javax.xml.bind.JAXBElement;

@Component
public class LoginRequestFactory {

    private final ObjectFactory objectFactory;

    private final String login;

    private final String password;

    LoginRequestFactory() {
        this.objectFactory = new ObjectFactory();
        this.login = "TEST";
        this.password = "qwerty!@#";
    }

    public JAXBElement<LoginRequest> create() {
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
