
package ec.edu.monster.phoneshop.ws;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * <p>Clase Java para responseStatus.</p>
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.</p>
 * <pre>{@code
 * <simpleType name="responseStatus">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="SUCCESS"/>
 *     <enumeration value="ERROR"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "responseStatus")
@XmlEnum
public enum ResponseStatus {

    SUCCESS,
    ERROR;

    public String value() {
        return name();
    }

    public static ResponseStatus fromValue(String v) {
        return valueOf(v);
    }

}
