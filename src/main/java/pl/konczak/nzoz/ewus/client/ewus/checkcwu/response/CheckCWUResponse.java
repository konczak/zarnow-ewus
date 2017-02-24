package pl.konczak.nzoz.ewus.client.ewus.checkcwu.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public XMLGregorianCalendar getDate() {
        return date;
    }

    public void setDate(XMLGregorianCalendar date) {
        this.date = date;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

}
