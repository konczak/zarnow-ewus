package pl.konczak.nzoz.ewus.client.ewus.checkcwu;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import pl.konczak.nzoz.ewus.client.ewus.checkcwu.response.CheckCWUResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPMessage;

@Component
public class CheckCWUResponseFactory {

    CheckCWUResponse create(SOAPMessage message) throws Exception {
        Document document = message.getSOAPBody().extractContentAsDocument();
        JAXBContext jaxbContext = JAXBContext.newInstance(CheckCWUResponse.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (CheckCWUResponse) jaxbUnmarshaller.unmarshal(document);
    }
}
