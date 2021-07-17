package pl.konczak.nzoz.ewus.domain.checkcwu.response;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Swiad {

    @XmlElement(name = "id_swiad",
            namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v5")
    private String idSwiad;

    @XmlElement(name = "id_ow",
            namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v5")
    private String idOw;

    @XmlElement(name = "id_operatora",
            namespace = "https://ewus.nfz.gov.pl/ws/broker/ewus/status_cwu/v5")
    private String idOperatora;

}
