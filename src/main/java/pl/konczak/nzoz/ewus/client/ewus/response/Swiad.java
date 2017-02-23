package pl.konczak.nzoz.ewus.client.ewus.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Swiad {

    @XmlElement(name = "id_swiad",
                namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v3")
    private String idSwiad;

    @XmlElement(name = "id_ow",
                namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v3")
    private String idOw;

    @XmlElement(name = "id_operatora",
                namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v3")
    private String idOperatora;

    public String getIdSwiad() {
        return idSwiad;
    }

    public void setIdSwiad(String idSwiad) {
        this.idSwiad = idSwiad;
    }

    public String getIdOw() {
        return idOw;
    }

    public void setIdOw(String idOw) {
        this.idOw = idOw;
    }

    public String getIdOperatora() {
        return idOperatora;
    }

    public void setIdOperatora(String idOperatora) {
        this.idOperatora = idOperatora;
    }

}
