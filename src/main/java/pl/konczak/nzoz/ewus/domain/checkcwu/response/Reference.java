package pl.konczak.nzoz.ewus.domain.checkcwu.response;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Reference {

    @XmlElement(name = "Transforms")
    private List<Transform> transforms;

    @XmlElement(name = "DigestMethod")
    private DigestMethod digestMethod;

    @XmlElement(name = "DigestValue")
    private String digestValue;

}
