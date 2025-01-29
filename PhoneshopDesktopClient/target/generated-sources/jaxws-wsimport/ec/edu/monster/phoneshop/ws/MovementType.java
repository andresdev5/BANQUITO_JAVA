
package ec.edu.monster.phoneshop.ws;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * <p>Clase Java para movementType.</p>
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.</p>
 * <pre>{@code
 * <simpleType name="movementType">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="DEPOSIT"/>
 *     <enumeration value="WITHDRAW"/>
 *     <enumeration value="TRANSFER"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "movementType")
@XmlEnum
public enum MovementType {

    DEPOSIT,
    WITHDRAW,
    TRANSFER;

    public String value() {
        return name();
    }

    public static MovementType fromValue(String v) {
        return valueOf(v);
    }

}
