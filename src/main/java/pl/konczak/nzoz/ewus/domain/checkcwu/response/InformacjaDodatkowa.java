package pl.konczak.nzoz.ewus.domain.checkcwu.response;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class InformacjaDodatkowa {

    @XmlAttribute(name = "kod")
    private String kod;

    @XmlAttribute(name = "poziom")
    private String poziom;

    @XmlAttribute(name = "wartosc")
    private String wartosc;
}
