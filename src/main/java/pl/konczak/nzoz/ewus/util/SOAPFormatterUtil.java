package pl.konczak.nzoz.ewus.util;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;

import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

public final class SOAPFormatterUtil {

    private static final Logger LOGGER = Logger.getLogger(SOAPFormatterUtil.class);

    public static String format(SOAPMessage soapMessage) {
        try {
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();

            //tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            // Set formatting
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            Source sc = soapMessage.getSOAPPart().getContent();

            ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(streamOut);
            tf.transform(sc, result);

            return streamOut.toString();
        } catch (Exception e) {
            LOGGER.error("Failed to format SOAPMessage", e);
            return null;
        }
    }

}
