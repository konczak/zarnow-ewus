package pl.konczak.nzoz.ewus.domain.checkcwu;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import pl.konczak.nzoz.ewus.domain.checkcwu.response.CheckCWUResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

@Component
public class CheckCWUResponseFactory {

    CheckCWUResponse create(SOAPMessage message) throws Exception {
        SOAPMessage clonedMessage = stringToMessage(messageToString(message));

        Document document = clonedMessage.getSOAPBody().extractContentAsDocument();
        JAXBContext jaxbContext = JAXBContext.newInstance(CheckCWUResponse.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (CheckCWUResponse) jaxbUnmarshaller.unmarshal(document);
    }

    public static String messageToString(SOAPMessage soap) throws SOAPException, IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        soap.writeTo(stream);
        return new String(stream.toByteArray());
    }

    public static SOAPMessage stringToMessage(String soap) throws IOException, SOAPException {
        InputStream inputStream = new ByteArrayInputStream(soap.getBytes());
        return MessageFactory.newInstance().createMessage(null, inputStream);
    }

}
