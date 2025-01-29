
package ec.edu.monster.phoneshop.ws;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * <p>Clase Java para bankAccountType.</p>
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.</p>
 * <pre>{@code
 * <simpleType name="bankAccountType">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="SAVINGS"/>
 *     <enumeration value="CHECKING"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "bankAccountType")
@XmlEnum
public enum BankAccountType {

    SAVINGS,
    CHECKING;

    public String value() {
        return name();
    }

    public static BankAccountType fromValue(String v) {
        return valueOf(v);
    }

}
