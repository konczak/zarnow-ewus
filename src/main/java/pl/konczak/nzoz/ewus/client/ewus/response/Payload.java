package pl.konczak.nzoz.ewus.client.ewus.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Payload {

    @XmlElement(name = "textload",
                namespace = "http://xml.kamsoft.pl/ws/broker")
    private Textload textload;

    public Textload getTextload() {
        return textload;
    }

    public void setTextload(Textload textload) {
        this.textload = textload;
    }

}
