package day11.xml_Extra;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class Item {

    private int id ;
    private String name;
    private String gender;
    private long phone ;

}
