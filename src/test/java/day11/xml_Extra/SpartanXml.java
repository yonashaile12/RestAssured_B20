package day11.xml_Extra;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;


@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "item")
public class SpartanXml {

    private int id ;
    private String name;
    private String gender;
    private long phone ;


}
