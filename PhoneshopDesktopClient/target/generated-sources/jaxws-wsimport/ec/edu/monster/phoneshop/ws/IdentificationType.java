
package ec.edu.monster.phoneshop.ws;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * <p>Clase Java para identificationType.</p>
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.</p>
 * <pre>{@code
 * <simpleType name="identificationType">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="PASSPORT"/>
 *     <enumeration value="ID_CARD"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "identificationType")
@XmlEnum
public enum IdentificationType {

    PASSPORT,
    ID_CARD;

    public String value() {
        return name();
    }

    public static IdentificationType fromValue(String v) {
        return valueOf(v);
    }

}
