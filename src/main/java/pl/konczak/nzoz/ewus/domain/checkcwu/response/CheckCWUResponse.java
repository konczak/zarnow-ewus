package pl.konczak.nzoz.ewus.domain.checkcwu.response;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

@Getter
@Setter
@XmlRootElement(name = "executeServiceReturn",
        namespace = "http://xml.kamsoft.pl/ws/broker")
@XmlAccessorType(XmlAccessType.FIELD)
public class CheckCWUResponse {

    @XmlElement(name = "location",
            namespace = "http://xml.kamsoft.pl/ws/common")
    private Location location;

    @XmlElement(name = "date",
            namespace = "http://xml.kamsoft.pl/ws/broker")
    private XMLGregorianCalendar date;

    @XmlElement(name = "payload",
            namespace = "http://xml.kamsoft.pl/ws/broker")
    private Payload payload;

}
